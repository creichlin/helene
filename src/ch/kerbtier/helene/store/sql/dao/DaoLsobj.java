package ch.kerbtier.helene.store.sql.dao;

import ch.kerbtier.webb.db.Column;
import ch.kerbtier.webb.db.Table;


@Table(name = "lsobj")
public class DaoLsobj extends DaoLs<Integer> {

  @Column
  private int value;


  public DaoLsobj(int parent, int index, int value) {
    super(parent, index);
    this.value = value;
  }
  
  public DaoLsobj() {
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
