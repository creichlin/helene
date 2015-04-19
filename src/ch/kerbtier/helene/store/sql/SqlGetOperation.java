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
import ch.kerbtier.webb.db.Db;
import ch.kerbtier.webb.db.DbPs;
import ch.kerbtier.webb.db.NoMatchFound;

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

      DbPs ps = db.prepareStatement("select * from attlist where parent = ? and name = ?");
      ps.setInt(1, subject.getDao().getId());
      ps.setString(2, name);
      DaoAttlist attlist = null;
      DaoList list = null;
      try {
        attlist = ps.selectFirst(DaoAttlist.class);
        list = db.select(DaoList.class, attlist.getValue());
      } catch (NoMatchFound e) {
        // object doesnt exist, create it
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

      DbPs ps = db.prepareStatement("select * from attlist where parent = ? and name = ?");
      ps.setInt(1, subject.getDao().getId());
      ps.setString(2, name);
      DaoAttlist attlist = null;
      DaoList list = null;
      try {
        attlist = ps.selectFirst(DaoAttlist.class);
        list = db.select(DaoList.class, attlist.getValue());
      } catch (NoMatchFound e) {
        // object doesnt exist, create it
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

      DbPs ps = db.prepareStatement("select * from attobj where parent = ? and name = ?");
      ps.setInt(1, subject.getDao().getId());
      ps.setString(2, name);
      DaoAttobj attobj = null;
      DaoObject object = null;
      try {
        attobj = ps.selectFirst(DaoAttobj.class);
        object = db.select(DaoObject.class, attobj.getValue());
      } catch (NoMatchFound e) {
        // object doesnt exist, create it

        object = new DaoObject(def.getName());
        DbPs cps = db.prepareCreate(DaoObject.class);
        cps.setEntityColumns(object);
        cps.executeUpdate();
        object.setId(cps.getGeneratedIntKey());

        attobj = new DaoAttobj(subject.getDao().getId(), name, object.getId());
        cps = db.prepareCreate(DaoAttobj.class);
        cps.setEntityColumns(attobj);
        cps.executeUpdate();
        attobj.setId(cps.getGeneratedIntKey());

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

    String sql = "select * from att" + def.getType() + " where parent = ? and name = ?";

    Db db = subject.getStore().getDb();

    try {
      DbPs ps = db.prepareStatement(sql);
      ps.setInt(1, subject.getDao().getId());
      ps.setString(2, name);

      try {
        value = (X) ps.selectFirst(Util.ATT_TYPES.get(def.getType())).getValue();
        if(value instanceof SqlHBlob) {
          ((SqlHBlob)value).init(subject.getStore().getBinaryFolder());
        }
      } catch (NoMatchFound e) {
        // that's fine, return default, so far always null
      }
      db.commit();
    } catch (SQLException e) {
      // only read access
      db.rollback();
    }

    return value;
  }

}
