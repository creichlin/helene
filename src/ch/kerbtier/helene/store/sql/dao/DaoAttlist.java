package ch.kerbtier.helene.store.sql.dao;

import ch.kerbtier.webb.db.Column;
import ch.kerbtier.webb.db.Table;


@Table(name = "attlist")
public class DaoAttlist extends DaoAtt<Integer> {

  @Column
  private int value;


  public DaoAttlist(int parent, String name, int value) {
    super(parent, name);
    this.value = value;
  }
  
  public DaoAttlist(int parent, String name) {
    super(parent, name);
  }
  
  public DaoAttlist() {
    // for db
  }

  @Override
  public Integer getValue() {
    return value;
  }

  @Override
  public void setValue(Integer value) {
    this.value = value;
  }
}
