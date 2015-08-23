package ch.kerbtier.helene.store.sql.dao;

import ch.kerbtier.hopsdb.model.annotations.Column;
import ch.kerbtier.hopsdb.model.annotations.Table;


@Table(name = "attobj")
public class DaoAttobj extends DaoAtt<Integer> {

  @Column
  private int value;


  public DaoAttobj(int parent, String name, int value) {
    super(parent, name);
    this.value = value;
  }
  
  public DaoAttobj(int parent, String name) {
    super(parent, name);
  }
  
  public DaoAttobj() {
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
