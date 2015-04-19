package ch.kerbtier.helene.store.sql.dao;

import ch.kerbtier.webb.db.Column;
import ch.kerbtier.webb.db.Table;


@Table(name = "attlist")
public class DaoAttlist implements DaoAtt<Integer> {

  @Column(key = true)
  private int id = -1;

  @Column
  private String name;

  @Column
  private int parent;

  @Column
  private int value;


  public DaoAttlist(int parent, String name, int value) {
    this.parent = parent;
    this.name = name;
    this.value = value;
  }
  
  public DaoAttlist(int parent, String name) {
    this.parent = parent;
    this.name = name;
  }
  
  public DaoAttlist() {
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
  public void setValue(Integer value) {
    this.value = value;
  }
}
