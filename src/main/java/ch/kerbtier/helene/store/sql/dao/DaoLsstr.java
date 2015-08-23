package ch.kerbtier.helene.store.sql.dao;

import ch.kerbtier.hopsdb.model.annotations.Column;
import ch.kerbtier.hopsdb.model.annotations.Table;


@Table(name = "lsstr")
public class DaoLsstr extends DaoLs<String> {

  @Column
  private String value;


  public DaoLsstr(int parent, int index, String value) {
    super(parent, index);
    this.value = value;
  }
  
  public DaoLsstr() {
    // for db
  }

  @Override
  public String getValue() {
    return value;
  }

  @Override
  public void setValue(String value) {
    this.value = value;
  }
}
