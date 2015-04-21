package ch.kerbtier.helene.store.sql.dao;

import ch.kerbtier.webb.db.Column;
import ch.kerbtier.webb.db.Table;

@Table(name = "attstr")
public class DaoAttstr extends DaoAtt<String> {

  @Column
  private String value;

  public DaoAttstr(int parent, String name, String value) {
    super(parent, name);
    this.value = value;
  }

  public DaoAttstr(int parent, String name) {
    super(parent, name);
  }

  public DaoAttstr() {
    // for db
  }

  @Override
  public String getValue() {
    return value;
  }

  @Override
  public void setValue(String value) {
    this.value = value;
  }
}
