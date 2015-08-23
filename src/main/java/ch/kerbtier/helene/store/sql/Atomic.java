package ch.kerbtier.helene.store.sql;

import java.sql.SQLException;

import ch.kerbtier.helene.entities.Entity;
import ch.kerbtier.helene.store.sql.SqlHObject;
import ch.kerbtier.helene.store.sql.Util;
import ch.kerbtier.helene.store.sql.dao.DaoAtt;
import ch.kerbtier.helene.store.sql.dao.DaoLs;
import ch.kerbtier.hopsdb.Db;
import ch.kerbtier.hopsdb.DbPs;
import ch.kerbtier.hopsdb.exceptions.NoMatchFound;
import ch.kerbtier.hopsdb.model.TableModel;

public class Atomic {

  private Db db;

  public Atomic(Db db) {
    this.db = db;
  }

  public boolean exists(SqlHObject object, String property) throws SQLException {
    Entity ent = object.getDef().get(property);
    try {
      db.select(Util.ATT_TYPES.get(ent.getType())).where("parent = ? and name = ?", object.dao.getId(), property).first();
      return true;
    } catch (NoMatchFound e) {
      return false;
    }
  }

  public void delete(SqlHObject object, String property) throws SQLException {
    Entity ent = object.getDef().get(property);
    try {
      DaoAtt<?> att = db.select(Util.ATT_TYPES.get(ent.getType())).where("parent = ? and name = ?", object.dao.getId(),
          property).first();
      db.delete(att);
    } catch (NoMatchFound e) {
      throw new RuntimeException();
    }
  }

  public void commit() {
    db.commit();
  }

  public void rollback() {
    db.rollback();
  }

  /**
   * deletes an element out of a list and updates the later siblings index
   * @param lsElement
   * @throws SQLException
   */
  public void delete(DaoLs<?> lsElement) throws SQLException {
    db.delete(lsElement);
    TableModel<? extends DaoLs> tm = db.getModels().getModel(lsElement.getClass());
    DbPs ps = db.prepareStatement("update `" + tm.getName() + "` SET index=index-1 WHERE parent = ? and index > ?");
    ps.setParameter(1, lsElement.getParent());
    ps.setParameter(2, lsElement.getIndex());
    ps.executeUpdate();
  }

  public void delete(Object dao) throws SQLException {
    db.delete(dao);
  }

  public void delete(Class<? extends DaoLs<?>> type, String where, int id) throws SQLException {
    TableModel<? extends DaoLs<?>> tm = db.getModels().getModel(type);
    DbPs ps = db.prepareStatement("delete from `" + tm.getName() + "` where " + where);
    ps.setParameter(1, id);
    ps.executeUpdate();
  }

}
