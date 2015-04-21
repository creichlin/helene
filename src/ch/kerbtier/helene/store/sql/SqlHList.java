package ch.kerbtier.helene.store.sql;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ch.kerbtier.helene.ModifiableNode;
import ch.kerbtier.helene.entities.Entity;
import ch.kerbtier.helene.entities.EntityList;
import ch.kerbtier.helene.store.sql.dao.DaoAttlist;
import ch.kerbtier.helene.store.sql.dao.DaoList;
import ch.kerbtier.helene.store.sql.dao.DaoLs;
import ch.kerbtier.webb.db.Db;
import ch.kerbtier.webb.db.DbPs;
import ch.kerbtier.webb.db.DbRs;
import ch.kerbtier.webb.db.exceptions.NoMatchFound;

public class SqlHList<T> extends SqlHBaseList<T> {

  private EntityList def;
  private Entity elementDef;
  Class<? extends DaoLs> type;

  public SqlHList(SqlStore store, EntityList def, DaoList dao) {
    super(store, dao);
    this.def = def;
    this.elementDef = def.get();
    this.type = Util.LIST_TYPES.get(elementDef.getType());
    
    if (this.type == null) {
      throw new RuntimeException();
    }
    if (this.elementDef == null) {
      throw new RuntimeException();
    }
  }

  @Override
  public String getName() {
    // TODO Auto-generated method stub
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
  public Iterator<T> iterator() {
    List<T> data = new ArrayList<>();
    try {

      for (DaoLs<T> ls : getStore().getDb().select(type, "parent = ? order by index", dao.getId())) {
        data.add(ls.getValue());
      }

      getStore().getDb().commit();
    } catch (SQLException e) {
      e.printStackTrace();
      getStore().getDb().rollback();
    }

    return data.iterator();
  }

  @Override
  public int size() {
    try {

      DbPs ps = getStore().getDb().prepareStatement(
          "select count(*) from ls" + elementDef.getType() + " where parent = ?");
      ps.setInt(1, dao.getId());
      DbRs rs = ps.executeQuery();
      rs.next();
      int size = rs.getInt(1);

      getStore().getDb().commit();
      return size;
    } catch (SQLException e) {
      e.printStackTrace();
      getStore().getDb().rollback();
    }
    return 0;
  }

  @Override
  public T get(int index) {
    try {
      try {
        DaoLs<T> ls = getStore().getDb().selectFirst(type, "parent = ? and index = ?", dao.getId(), index);
        return ls.getValue();
      } catch (NoMatchFound e) {
        // then don't return it
      } finally {
        getStore().getDb().commit();
      }
    } catch (SQLException e) {
      e.printStackTrace();
      getStore().getDb().rollback();
    }
    return null;
  }

  @Override
  public ModifiableNode add() {
    throw new UnsupportedOperationException("use add(x)");
  }

  @Override
  public void add(Object value) {
    int current = size();
    try {
      DaoLs<?> ls = type.getConstructor(Integer.TYPE, Integer.TYPE, elementDef.isOf()).newInstance(dao.getId(),
          current, value);
      getStore().getDb().create(ls);

      getStore().getDb().commit();
      listeners.trigger(dao);
    } catch (SQLException e) {
      e.printStackTrace();
      getStore().getDb().rollback();
    } catch (Exception e) {
      getStore().getDb().rollback();
      throw new RuntimeException(e);
    }
  }

  @Override
  public void set(int i, T value) {
    try {
      try {
        DaoLs<T> ls = getStore().getDb().selectFirst(type, "parent = ? and index = ?", dao.getId(), i);
        ls.setValue(value);
        getStore().getDb().update(ls);
      } catch (NoMatchFound e) {
        // then don't return it
      } finally {
        getStore().getDb().commit();
      }
    } catch (SQLException e) {
      e.printStackTrace();
      getStore().getDb().rollback();
    }
  }

  @Override
  public void delete(int i) {
    Atomic atomic = new Atomic(getStore().getDb());
    try {
      try {
        DaoLs<T> dao = getStore().getDb().selectFirst(type, "parent = ? and index = ?", this.dao.getId(), i);
        atomic.delete(dao);
      }catch(NoMatchFound e) {
        // can happen, be silent
      }

      atomic.commit();
      listeners.trigger(dao);
    } catch (SQLException e) {
      e.printStackTrace();
      atomic.rollback();
    }
  }
  
  @Override
  public void delete() {
    accept(new DeleteVisitor(getStore().getDb()));
  }

  @Override
  public void swap(int i1, int i2) {
    try {
      try {
        DaoLs<T> o1 = getStore().getDb().selectFirst(type, "parent = ? and index = ?", dao.getId(), i1);
        DaoLs<T> o2 = getStore().getDb().selectFirst(type, "parent = ? and index = ?", dao.getId(), i2);
        o1.setIndex(i2);
        o2.setIndex(i1);
        getStore().getDb().update(o1);
        getStore().getDb().update(o2);
        getStore().getDb().commit();
        listeners.trigger(dao);
      } catch (NoMatchFound e) {
        getStore().getDb().commit();
      }
    } catch (SQLException e) {
      e.printStackTrace();
      getStore().getDb().rollback();
    }
  }

  @Override
  public Object accept(Visitor<? extends Object> visitor) {
    return visitor.visit(this);
  }
}
