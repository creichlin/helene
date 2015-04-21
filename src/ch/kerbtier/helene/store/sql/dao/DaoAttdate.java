package ch.kerbtier.helene.store.sql.dao;

import java.util.Date;

import ch.kerbtier.webb.db.Column;
import ch.kerbtier.webb.db.Table;

@Table(name = "attdate")
public class DaoAttdate extends DaoAtt<Date> {

  @Column
  private Date value;

  public DaoAttdate(int parent, String name, Date value) {
    super(parent, name);
    this.value = value;
  }

  public DaoAttdate(int parent, String name) {
    super(parent, name);
  }

  public DaoAttdate() {
    // for db
  }

  @Override
  public Date getValue() {
    return value;
  }

  @Override
  public void setValue(Date value) {
    this.value = value;
  }
}
