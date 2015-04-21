package ch.kerbtier.helene.store.sql;

import java.sql.SQLException;

import ch.kerbtier.helene.HObject;
import ch.kerbtier.helene.Types;
import ch.kerbtier.helene.entities.Entity;
import ch.kerbtier.helene.store.sql.dao.DaoAttlist;
import ch.kerbtier.helene.store.sql.dao.DaoAttobj;
import ch.kerbtier.helene.store.sql.dao.DaoList;
import ch.kerbtier.helene.store.sql.dao.DaoLsobj;
import ch.kerbtier.helene.store.sql.dao.DaoObject;
import ch.kerbtier.webb.db.Db;
import ch.kerbtier.webb.db.exceptions.NoMatchFound;

public class DeleteVisitor implements Visitor<Object> {

  private Db db;

  public DeleteVisitor(Db db) {
    this.db = db;
  }

  @Override
  public Object visit(SqlHList list) {
    int iid = list.dao.getId();

    Atomic atomic = new Atomic(db);
    try {
      // delete children
      
      atomic.delete(list.type, "parent = ?", iid);
      
      try {
        // calls commit
        deleteListOfObject(list, atomic, iid);
      } catch(NoMatchFound e) {
        // TODO if it's a list with parent list
        atomic.commit();
      }

      list.listeners.trigger(list.dao);
    } catch (SQLException e) {
      e.printStackTrace();
      atomic.rollback();
    }

    
    return null;
  }

  @Override
  public Object visit(SqlHObjectList list) {
    int iid = list.dao.getId();

    Atomic atomic = new Atomic(db);
    try {
      // delete children
      for(HObject ho: list) {
        ((SqlHObject)ho).accept(this);
      }
      
      try {
        deleteListOfObject(list, atomic, iid);
      } catch(NoMatchFound e) {
        // no luck, must be a list of list
        atomic.commit();
      }
    } catch (SQLException e) {
      e.printStackTrace();
      db.rollback();
    }

    return null;
  }

  @Override
  public Object visit(SqlHObject object) {
    Atomic atomic = new Atomic(db);
    int iid = object.dao.getId();

    try {
      // delete all children
      deleteChildrenOfObject(object, atomic);
      try {
        // we are part of an object?
        // commits changes
        deleteObjectOfObject(object, atomic, iid);
      } catch (NoMatchFound e) {
        try {
          // nope, we are part of a list
          // commits changes
          deleteObjectOfList(object, atomic, iid);
        } catch (NoMatchFound e2) {
          // heck
          atomic.commit();
          assert (false);
        }
      }
    } catch (SQLException e) {
      atomic.rollback();
    }
    return null;
  }

  private void deleteChildrenOfObject(SqlHObject object, Atomic atomic) throws SQLException {
    for (String property : object.getProperties()) {
      if (atomic.exists(object, property)) {
        Entity type = object.getDef().get(property);
        if (type.is(Types.LIST) || type.is(Types.OBJECT)) {
          ((SqlHNode) object.get(property)).accept(this);
        } else {
          atomic.delete(object, property);
        }

      }
    }
  }

  private void deleteListOfObject(SqlHBaseList list, Atomic atomic, int iid) throws SQLException {
    DaoAttlist attParent = db.selectFirst(DaoAttlist.class, "value = ?", iid);
    DaoObject objParent = db.select(DaoObject.class, attParent.getParent());

    // delete myself
    atomic.delete(attParent);
    atomic.delete(list.dao);

    // trigger parents change/remove events
    SqlHObject sho = new SqlHObject(list.getStore(), list.getStore().getDef().getObject(objParent.getType()),
        objParent);
    atomic.commit();
    sho.triggerDeleteEvent(attParent.getName());
  }

  private void deleteObjectOfList(SqlHObject object, Atomic atomic, int iid) throws SQLException {
    DaoLsobj lsParent = db.selectFirst(DaoLsobj.class, "value = ?", iid);
    DaoList listParent = db.select(DaoList.class, lsParent.getParent());

    // delete myself
    atomic.delete(lsParent);
    atomic.delete(object.dao);

    // trigger parents change/remove events
    SqlHObjectList shol = new SqlHObjectList(object.getStore(), object.getStore().getDef()
        .getList(listParent.getType()), listParent);
    atomic.commit();
    shol.triggerDeleteEvent(lsParent.getIndex());
  }

  private void deleteObjectOfObject(SqlHObject object, Atomic atomic, int iid) throws SQLException {
    DaoAttobj attParent = db.selectFirst(DaoAttobj.class, "value = ?", iid);
    DaoObject objParent = db.select(DaoObject.class, attParent.getParent());

    // delete myself
    atomic.delete(attParent);
    atomic.delete(object.dao);

    // trigger parents change/remove events
    SqlHObject sho = new SqlHObject(object.getStore(), object.getStore().getDef().getObject(objParent.getType()),
        objParent);
    atomic.commit();
    sho.triggerDeleteEvent(attParent.getName());
  }
}
