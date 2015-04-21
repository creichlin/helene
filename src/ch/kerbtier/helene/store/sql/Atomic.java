package ch.kerbtier.helene.store.sql;

import java.sql.SQLException;

import ch.kerbtier.helene.entities.Entity;
import ch.kerbtier.helene.store.sql.dao.DaoAtt;
import ch.kerbtier.helene.store.sql.dao.DaoLs;
import ch.kerbtier.webb.db.Db;
import ch.kerbtier.webb.db.DbPs;
import ch.kerbtier.webb.db.Introspector;
import ch.kerbtier.webb.db.TableModel;
import ch.kerbtier.webb.db.exceptions.NoMatchFound;

public class Atomic {

  private Db db;

  public Atomic(Db db) {
    this.db = db;
  }

  public boolean exists(SqlHObject object, String property) throws SQLException {
    Entity ent = object.getDef().get(property);
    try {
      db.selectFirst(Util.ATT_TYPES.get(ent.getType()), "parent = ? and name = ?", object.dao.getId(), property);
      return true;
    } catch (NoMatchFound e) {
      return false;
    }
  }

  public void delete(SqlHObject object, String property) throws SQLException {
    Entity ent = object.getDef().get(property);
    try {
      DaoAtt<?> att = db.selectFirst(Util.ATT_TYPES.get(ent.getType()), "parent = ? and name = ?", object.dao.getId(),
          property);
      db.delete(att);
    } catch (NoMatchFound e) {
      assert (false);
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
  public void delete(DaoLs lsElement) throws SQLException {
    db.delete(lsElement);
    TableModel<? extends DaoLs> tm = Introspector.getModel(lsElement.getClass());
    DbPs ps = db.prepareStatement("update `" + tm.getName() + "` SET index=index-1 WHERE parent = ? and index > ?");
    ps.setInt(1, lsElement.getParent());
    ps.setInt(2, lsElement.getIndex());
    ps.executeUpdate();
  }

  public void delete(Object dao) throws SQLException {
    db.delete(dao);
  }

  public void delete(Class type, String where, int id) throws SQLException {
    TableModel<? extends DaoLs> tm = Introspector.getModel(type);
    DbPs ps = db.prepareStatement("delete from `" + tm.getName() + "` where " + where);
    ps.setInt(1, id);
    ps.executeUpdate();
  }

}
