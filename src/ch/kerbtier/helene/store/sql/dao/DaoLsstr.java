package ch.kerbtier.helene.store.sql.dao;

import ch.kerbtier.webb.db.Column;
import ch.kerbtier.webb.db.Table;


@Table(name = "lsstr")
public class DaoLsstr implements DaoLs<String> {

  @Column(key = true)
  private int id = -1;

  @Column
  private int index;

  @Column
  private int parent;

  @Column
  private String value;


  public DaoLsstr(int parent, int index, String value) {
    this.parent = parent;
    this.index = index;
    this.value = value;
  }
  
  public DaoLsstr() {
    // for db
  }

  public void setId(int id) {
    this.id = id;
  }

  @Override
  public String getValue() {
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
  public void setValue(String value) {
    this.value = value;
  }
}
