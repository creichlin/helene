package ch.kerbtier.helene.store.sql;

import java.sql.SQLException;

import ch.kerbtier.helene.HList;
import ch.kerbtier.helene.Types;
import ch.kerbtier.helene.def.GetOperation.Worker;
import ch.kerbtier.helene.entities.Entity;
import ch.kerbtier.helene.entities.EntityList;
import ch.kerbtier.helene.entities.EntityMap;
import ch.kerbtier.helene.store.sql.dao.DaoAttlist;
import ch.kerbtier.helene.store.sql.dao.DaoAttobj;
import ch.kerbtier.helene.store.sql.dao.DaoList;
import ch.kerbtier.helene.store.sql.dao.DaoObject;
import ch.kerbtier.helene.store.sql.dao.SqlHBlob;
import ch.kerbtier.hopsdb.Db;
import ch.kerbtier.hopsdb.exceptions.NoMatchFound;

public class SqlGetOperation implements Worker {

  private SqlHObject subject;

  public SqlGetOperation(SqlHObject subject) {
    this.subject = subject;
  }

  @Override
  public <X> HList<X> byList(String name, Class<X> subType, EntityList def) {

    if (def.get().is(Types.OBJECT)) {
      return byListObj(name, def);
    } else {
      return byListPrimitive(name, subType, def);
    }
  }

  private <X> HList<X> byListPrimitive(String name, Class<X> subType, EntityList def) {
    try {
      Db db = subject.getStore().getDb();
      DaoAttlist attlist = null;
      DaoList list = null;
      try {
        attlist = db.select(DaoAttlist.class).where("parent = ? and name = ?", subject.getDao().getId(), name).first();
        list = db.select(DaoList.class, attlist.getValue());
      } catch (NoMatchFound e) {
        // list doesnt exist, create it
        list = new DaoList(def.getName());
        db.create(list);
        attlist = new DaoAttlist(subject.getDao().getId(), name, list.getId());
        db.create(attlist);
      }
      db.commit();
      return (HList<X>) new SqlHList<X>(subject.getStore(), def, list);
    } catch (SQLException e) {
      e.printStackTrace();
      subject.getStore().getDb().rollback();
    }
    return null;
  }

  private <X> HList<X> byListObj(String name, EntityList def) {
    try {
      Db db = subject.getStore().getDb();

      DaoAttlist attlist = null;
      DaoList list = null;
      try {
        attlist = db.select(DaoAttlist.class).where("parent = ? and name = ?", subject.getDao().getId(), name).first();
        list = db.select(DaoList.class, attlist.getValue());
      } catch (NoMatchFound e) {
        // list doesnt exist, create it
        list = new DaoList(def.getName());
        db.create(list);

        attlist = new DaoAttlist(subject.getDao().getId(), name, list.getId());
        db.create(attlist);
      }
      db.commit();
      return (HList<X>) new SqlHObjectList(subject.getStore(), def, list);
    } catch (SQLException e) {
      e.printStackTrace();
      subject.getStore().getDb().rollback();
    }
    return null;
  }

  @Override
  public <X> X byObject(String name, EntityMap def) {
    try {
      Db db = subject.getStore().getDb();

      DaoAttobj attobj = null;
      DaoObject object = null;
      try {
        attobj = db.select(DaoAttobj.class).where("parent = ? and name = ?", subject.getDao().getId(), name).first();
        object = db.select(DaoObject.class, attobj.getValue());
      } catch (NoMatchFound e) {
        // object doesnt exist, create it

        object = new DaoObject(def.getName());
        db.create(object);

        attobj = new DaoAttobj(subject.getDao().getId(), name, object.getId());
        db.create(attobj);
      }
      db.commit();
      return (X) new SqlHObject(subject.getStore(), def, object);
    } catch (SQLException e) {
      e.printStackTrace();
      subject.getStore().getDb().rollback();
    }
    return null;
  }

  @Override
  public <X> X byValue(String name, Class<X> expected, Entity def) {
    X value = null;
    Db db = subject.getStore().getDb();

    try {
      value = (X) db.select(Util.ATT_TYPES.get(def.getType()))
          .where("parent = ? and name = ?", subject.getDao().getId(), name).first().getValue();
      if (value instanceof SqlHBlob) {
        ((SqlHBlob) value).init(subject.getStore().getBinaryFolder());
      }
      db.commit();
    } catch (NoMatchFound e) {
      // that's fine, return default, so far always null
      db.commit();
    } catch (SQLException e) {
      // only read access
      db.rollback();
    }

    return value;
  }

}
