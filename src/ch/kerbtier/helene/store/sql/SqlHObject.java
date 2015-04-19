package ch.kerbtier.helene.store.sql;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.antlr.v4.runtime.misc.NotNull;
import org.apache.commons.io.FileUtils;

import ch.kerbtier.helene.HBlob;
import ch.kerbtier.helene.HList;
import ch.kerbtier.helene.HObject;
import ch.kerbtier.helene.ModifiableNode;
import static ch.kerbtier.helene.Types.*;
import ch.kerbtier.helene.def.GetOperation;
import ch.kerbtier.helene.entities.Entity;
import ch.kerbtier.helene.entities.EntityList;
import ch.kerbtier.helene.entities.EntityMap;
import ch.kerbtier.helene.events.ListenerReference;
import ch.kerbtier.helene.events.MappedListeners;
import ch.kerbtier.helene.store.mod.EntitySubject;
import ch.kerbtier.helene.store.mod.HObjectModifiableNode;
import ch.kerbtier.helene.store.sql.dao.DaoAtt;
import ch.kerbtier.helene.store.sql.dao.DaoAttobj;
import ch.kerbtier.helene.store.sql.dao.DaoList;
import ch.kerbtier.helene.store.sql.dao.DaoLsobj;
import ch.kerbtier.helene.store.sql.dao.DaoObject;
import ch.kerbtier.webb.db.Db;
import ch.kerbtier.webb.db.NoMatchFound;

public class SqlHObject extends SqlHNode implements HObject, EntitySubject {

  private DaoObject dao = null;
  private EntityMap def;

  private static Map<DaoObject, MappedListeners<String>> changeListeners = new HashMap<>();

  public SqlHObject(@NotNull SqlStore store, @NotNull EntityMap def, @NotNull DaoObject dao) {
    super(store);
    this.def = def;
    this.dao = dao;
  }

  public SqlHObject(@NotNull EntityMap def) {
    super(null);
    this.def = def;
  }

  protected EntityMap getDef() {
    return def;
  }

  @Override
  public Object get(String name) {
    return get(name, null);
  }

  @Override
  public <T> HList<T> getList(String name, Class<T> type) {
    return get(name, LIST, type);
  }

  protected DaoObject getDao() {
    return dao;
  }

  protected void setDao(DaoObject dao) {
    this.dao = dao;
  }

  @Override
  public <T> T get(String name, Class<T> type) {
    return get(name, type, null);
  }

  public <X> X get(String name, Class<X> expected, Class<?> subExpected) {
    return GetOperation.get(getStore(), name, expected, subExpected, def, new SqlGetOperation(this));
  }

  protected void createRaw(Db db, Map<String, Object> data) throws SQLException {
    for (String field : data.keySet()) {
      Entity f = def.get(field);
      Object value = data.get(field);
      Class<? extends DaoAtt> daodef = Util.ATT_TYPES.get(f.getType());
      if (value == null) {
        try {
          Object attr = db.selectFirst(daodef, "parent = ? and name = ?", dao.getId(), field);
          db.delete(attr);
        } catch (NoMatchFound e) {
          // then we don't have to delete
        }
      } else {
        try {
          DaoAtt d = db.selectFirst(daodef, "parent = ? and name = ?", dao.getId(), field);
          d.setValue(value);
          db.update(d);
        } catch (NoMatchFound e) {
          try {
            DaoAtt d = daodef.getConstructor(Integer.TYPE, String.class).newInstance(dao.getId(), field);
            d.setValue(value);
            db.create(d);
          } catch (InvocationTargetException | InstantiationException | IllegalAccessException
              | IllegalArgumentException | NoSuchMethodException | SecurityException ex) {
            throw new RuntimeException(ex);
          }
        }

        if (value instanceof HBlob) {
          HBlob val = (HBlob) value;
          try {
            FileUtils.writeByteArrayToFile(getStore().getBinaryFolder().resolve(val.getHash()).toFile(), val.asArray());
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
        }
      }
      MappedListeners<String> listeners = changeListeners.get(dao);
      if (listeners != null) {
        listeners.trigger(field);
      }
    }
  }

  @Override
  public HObject create(Map<String, Object> data) {
    Db db = getStore().getDb();
    try {
      createRaw(db, data);

      db.commit();
    } catch (Exception e) {
      e.printStackTrace();
      db.rollback();
    }

    return this;
  }

  @Override
  public ListenerReference onChange(String attrib, Runnable run) {
    MappedListeners<String> listeners = changeListeners.get(dao);

    if (listeners == null) {
      synchronized (changeListeners) {
        listeners = new MappedListeners<>();
        changeListeners.put(dao, listeners);
      }
    }

    return listeners.on(attrib, run);
  }

  @Override
  public Set<String> getProperties() {
    return def.getProperties();
  }

  @Override
  public String getName() {
    return def.getName();
  }

  @Override
  public ModifiableNode update() {
    return new HObjectModifiableNode(this, def);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((dao == null) ? 0 : dao.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    SqlHObject other = (SqlHObject) obj;

    return dao.equals(other.dao);
  }

  @Override
  public void delete() {
    Db db = getStore().getDb();
    try {
      try {
        DaoAttobj att = db.selectFirst(DaoAttobj.class, "value = ?", dao.getId());
        DaoObject obj = db.select(DaoObject.class, att.getParent());
        
        System.out.println("path:" + obj.getType() + ":" + getStore().getDef().get(obj.getType()));
        
        
        new SqlHObject(getStore(), getStore().getDef().getObject(obj.getType()), obj).delete(att.getName());
      } catch (NoMatchFound e) {
        try {
          DaoLsobj ls = db.selectFirst(DaoLsobj.class, "value = ?", dao.getId());
          DaoList list = db.select(DaoList.class, ls.getParent());
          new SqlHObjectList(getStore(), getStore().getDef().getList(list.getType()), list).delete(ls.getIndex());
        } catch (NoMatchFound e2) {
          // verweist
        }
      }
      db.commit();
    } catch (SQLException e) {
      e.printStackTrace();
      db.rollback();
    }
  }

  protected SqlHObjectList getParentList() {
    Db db = getStore().getDb();
    Entity parent = def.getParent();
    if (parent.is(LIST)) {
      try {
        try {
          DaoLsobj lsobj = db.selectFirst(DaoLsobj.class, " value = ?", dao.getId());
          DaoList list = db.select(DaoList.class, lsobj.getParent());
          return new SqlHObjectList(getStore(), (EntityList) def.getParent(), list);
        } catch (NoMatchFound e) {
          return null;
        } finally {
          db.commit();
        }
      } catch (SQLException e) {
        e.printStackTrace();
        db.rollback();
      }
    }
    return null;
  }

  @Override
  public void up() {
    int ci = getIndex();
    if (ci > 0) {
      SqlHObjectList list = getParentList();
      if (list != null) {
        list.swap(ci, ci - 1);
      }
    }
  }

  private int getIndex() {
    int index = -1;
    try {
      try {
        DaoLsobj lsobj = getStore().getDb().selectFirst(DaoLsobj.class, "value = ?", dao.getId());
        index = lsobj.getIndex();
      } catch (NoMatchFound e) {
        // then no index
      }
      getStore().getDb().commit();
    } catch (SQLException e) {
      e.printStackTrace();
      getStore().getDb().rollback();
    }
    return index;
  }

  @Override
  public void down() {
    int ci = getIndex();
    SqlHObjectList list = getParentList();
    if (list != null && ci > -1 && ci < (list.size() - 1)) {
      list.swap(ci, ci + 1);
    }
  }

  @Override
  public void delete(String field) {
    Entity ent = def.get(field);
    Db db = getStore().getDb();
    try {
      try {
        DaoAtt<?> att = db.selectFirst(Util.ATT_TYPES.get(ent.getType()), "parent = ? and name = ?", dao.getId(),
            field);
        db.delete(att);
      } catch (NoMatchFound e) {
        // then don't delete it
      }
      db.commit();
    } catch (SQLException e) {
      db.rollback();
      e.printStackTrace();
    }
  }
}
