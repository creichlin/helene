package ch.kerbtier.helene.store.sql.dao;

import ch.kerbtier.webb.db.Column;
import ch.kerbtier.webb.db.Table;


@Table(name = "attobj")
public class DaoAttobj implements DaoAtt<Integer> {

  @Column(key = true)
  private int id = -1;

  @Column
  private String name;

  @Column
  private int parent;

  @Column
  private int value;


  public DaoAttobj(int parent, String name, int value) {
    this.parent = parent;
    this.name = name;
    this.value = value;
  }
  
  public DaoAttobj(int parent, String name) {
    this.parent = parent;
    this.name = name;
  }
  
  public DaoAttobj() {
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

  public int getParent() {
    return parent;
  }

  public String getName() {
    return name;
  }
}
