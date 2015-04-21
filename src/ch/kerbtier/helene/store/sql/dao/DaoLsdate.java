package ch.kerbtier.helene.store.sql.dao;

import java.util.Date;

import ch.kerbtier.webb.db.Column;
import ch.kerbtier.webb.db.Table;

@Table(name = "lsdate")
public class DaoLsdate extends DaoLs<Date> {

  @Column
  private Date value;

  public DaoLsdate(int parent, int index, Date value) {
    super(parent, index);
    this.value = value;
  }

  public DaoLsdate() {
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
