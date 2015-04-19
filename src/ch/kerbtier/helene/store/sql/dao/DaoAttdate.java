package ch.kerbtier.helene.store.sql.dao;

import java.util.Date;

import ch.kerbtier.webb.db.Column;
import ch.kerbtier.webb.db.Table;

@Table(name = "attdate")
public class DaoAttdate implements DaoAtt<Date> {

  @Column(key = true)
  private int id = -1;

  @Column
  private Date value;

  @Column
  private String name;

  @Column
  private int parent;



  public DaoAttdate(int parent, String name, Date value) {
    this.parent = parent;
    this.name = name;
    this.value = value;
  }

  public DaoAttdate(int parent, String name) {
    this.parent = parent;
    this.name = name;
  }

  public DaoAttdate() {
    // for db
  }

  public void setId(int id) {
    this.id = id;
  }

  @Override
  public Date getValue() {
    return value;
  }

  public void setValue(Date value) {
    this.value = value;
  }
}
