package ch.kerbtier.helene.store.sql;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import ch.kerbtier.helene.HObject;
import ch.kerbtier.helene.ModifiableNode;
import ch.kerbtier.helene.entities.EntityList;
import ch.kerbtier.helene.entities.EntityMap;
import ch.kerbtier.helene.exceptions.DuplicateSlugException;
import ch.kerbtier.helene.store.mod.EntitySubject;
import ch.kerbtier.helene.store.mod.HObjectModifiableNode;
import ch.kerbtier.helene.store.sql.dao.DaoAttlist;
import ch.kerbtier.helene.store.sql.dao.DaoList;
import ch.kerbtier.helene.store.sql.dao.DaoLsobj;
import ch.kerbtier.helene.store.sql.dao.DaoObject;
import ch.kerbtier.webb.db.Db;
import ch.kerbtier.webb.db.DbPs;
import ch.kerbtier.webb.db.DbRs;
import ch.kerbtier.webb.db.NoMatchFound;

public class SqlHObjectList extends SqlHBaseList<HObject> implements EntitySubject {
  private EntityList def;
  private EntityMap elementDef;

  public SqlHObjectList(SqlStore store, EntityList def, DaoList dao) {
    super(store, dao);
    this.def = def;
    this.elementDef = def.getObject();
  }

  @Override
  public void delete() {
    Db db = getStore().getDb();
    try {
      try {
        DaoAttlist dl = db.selectFirst(DaoAttlist.class, "value = ?", dao.getId());
        db.delete(dl);
        db.delete(dao);
      } catch(NoMatchFound e) {
        // no luck, must be a list of list
      }
      db.commit();
    } catch (SQLException e) {
      e.printStackTrace();
      db.rollback();
    }
  }

  @Override
  public Iterator<HObject> iterator() {
    Db db = getStore().getDb();
    List<HObject> hobjects = new ArrayList<>();
    try {
      for (DaoLsobj lsobj : db.select(DaoLsobj.class, "parent = ? order by index", dao.getId())) {
        
        DaoObject object = db.select(DaoObject.class, lsobj.getValue());
        
        hobjects.add(new SqlHObject(getStore(), elementDef, object));
      }
      db.commit();
    } catch (SQLException e) {
      e.printStackTrace();
      db.rollback();
    }
    return hobjects.iterator();
  }

  @Override
  public int size() {
    Db db = getStore().getDb();
    try {
      DbPs ps = db.prepareStatement("select count(*) from lsobj where parent = ?");
      ps.setInt(1, dao.getId());
      DbRs rs = ps.executeQuery();
      rs.next();
      int index = rs.getInt(1);
      db.commit();
      return index;
    } catch (SQLException e) {
      db.rollback();
    }
    return 0;
  }

  @Override
  public HObject get(int cnt) {
    Db db = getStore().getDb();
    try {
      DaoLsobj lsobj = db.selectFirst(DaoLsobj.class, "parent = ? and index = ?", dao.getId(), cnt);
      DaoObject obj = db.select(DaoObject.class, lsobj.getValue());
      db.commit();
      return new SqlHObject(getStore(), elementDef, obj);
    } catch (Exception e) {
      db.rollback();
    }
    return null;
  }

  @Override
  public ModifiableNode add() {
    return new HObjectModifiableNode(this, elementDef);
  }

  @Override
  public void add(HObject value) {
    throw new UnsupportedOperationException("use add()");
  }

  @Override
  public void set(int i, HObject value) {
    throw new UnsupportedOperationException("use get(x).update()");
  }

  @Override
  public void delete(int i) {
    Db db = getStore().getDb();

    try {

      List<DaoLsobj> elements = db.select(DaoLsobj.class, "parent = ? order by index", dao.getId());

      int index = 0;

      for (DaoLsobj e : elements) {
        if (index == i) {
          db.delete(e);
        } else if (index > i) {
          e.setIndex(index - 1);
          db.update(e);
        }
        index++;
      }

      db.commit();
      listeners.trigger(dao);
    } catch (SQLException e) {
      e.printStackTrace();
      db.rollback();
    }
  }

  @Override
  public void swap(int i1, int i2) {
    Db db = getStore().getDb();

    try {

      DaoLsobj e1 = db.selectFirst(DaoLsobj.class, "parent = ? and index = ?", dao.getId(), i1);
      DaoLsobj e2 = db.selectFirst(DaoLsobj.class, "parent = ? and index = ?", dao.getId(), i2);

      int index = e1.getIndex();
      e1.setIndex(e2.getIndex());
      e2.setIndex(index);

      db.update(e1);
      db.update(e2);

      db.commit();
      listeners.trigger(dao);
    } catch (SQLException e) {
      e.printStackTrace();
      db.rollback();
    }
  }

  @Override
  public HObject create(Map<String, Object> data) {
    Db db = getStore().getDb();
    try {

      DbPs ps = db.prepareStatement("select max(index) from lsobj where parent = ?");
      ps.setInt(1, dao.getId());
      DbRs rs = ps.executeQuery();
      rs.next();
      int index = rs.getInt(1) == null ? 0 : rs.getInt(1) + 1;

      DaoObject obj = new DaoObject(elementDef.getName());
      db.create(obj);

      DaoLsobj lsobj = new DaoLsobj(dao.getId(), index, obj.getId());
      db.create(lsobj);

      SqlHObject object = new SqlHObject(getStore(), elementDef, obj);
      object.createRaw(db, data);

      db.commit();
      listeners.trigger(dao);
      return object;
    } catch (SQLException e) {
      if(e.getErrorCode()  == 23505) {
        throw new DuplicateSlugException("slug exists already");
      } else {
        e.printStackTrace();
      }
      db.rollback();
    }

    return null;
  }

  @Override
  public void up() {
    // TODO Auto-generated method stub

  }

  @Override
  public void down() {
    // TODO Auto-generated method stub

  }

  @Override
  public String getName() {
    return def.getName();
  }

}
