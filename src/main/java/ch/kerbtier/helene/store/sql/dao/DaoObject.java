package ch.kerbtier.helene.store.sql.dao;

import ch.kerbtier.hopsdb.model.annotations.Table;

@Table(name = "object")
public class DaoObject extends DaoBase {

  public DaoObject(String type) {
    super(type);
  }
  
  public DaoObject() {
    // for Db
  }

}
