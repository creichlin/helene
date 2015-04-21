package ch.kerbtier.helene.store.sql.dao;

import ch.kerbtier.webb.db.Table;

@Table(name = "list")
public class DaoList extends DaoBase {

  public DaoList(String type) {
    super(type);
  }

  public DaoList() {
    // for Db
  }

}
