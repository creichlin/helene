package ch.kerbtier.helene.store.sql.dao;

import ch.kerbtier.webb.db.Column;
import ch.kerbtier.webb.db.Table;


@Table(name = "lsobj")
public class DaoLsobj implements DaoLs<Integer> {

  @Column(key = true)
  private int id = -1;

  @Column
  private int index;

  @Column
  private int parent;

  @Column
  private int value;


  public DaoLsobj(int parent, int index, int value) {
    this.parent = parent;
    this.index = index;
    this.value = value;
  }
  
  public DaoLsobj() {
    // for db
  }

  public void setId(int id) {
    this.id = id;
  }

  @Override
  public Integer getValue() {
    return value;
  }

  @Override
  public void setIndex(int index) {
    this.index = index;
  }

  public int getIndex() {
    return index;
  }

  public int getParent() {
    return parent;
  }

  @Override
  public void setValue(Integer value) {
    this.value = value;
  }
}
