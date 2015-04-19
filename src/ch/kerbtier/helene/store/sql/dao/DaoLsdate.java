package ch.kerbtier.helene.store.sql.dao;

import java.util.Date;

import ch.kerbtier.webb.db.Column;
import ch.kerbtier.webb.db.Table;


@Table(name = "lsdate")
public class DaoLsdate implements DaoLs<Date> {

  @Column(key = true)
  private int id = -1;

  @Column
  private int index;

  @Column
  private int parent;

  @Column
  private Date value;


  public DaoLsdate(int parent, int index, Date value) {
    this.parent = parent;
    this.index = index;
    this.value = value;
  }
  
  public DaoLsdate() {
    // for db
  }

  public void setId(int id) {
    this.id = id;
  }

  @Override
  public Date getValue() {
    return value;
  }

  @Override
  public void setIndex(int index) {
    this.index = index;
  }
  
  @Override
  public void setValue(Date value) {
    this.value = value;
  }

  public int getIndex() {
    return index;
  }

  public int getParent() {
    return parent;
  }
}
