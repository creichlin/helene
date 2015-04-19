package ch.kerbtier.helene.store.sql.dao;

import ch.kerbtier.webb.db.Column;
import ch.kerbtier.webb.db.Table;

@Table(name = "attstr")
public class DaoAttstr implements DaoAtt<String> {

  @Column(key = true)
  private int id = -1;

  @Column
  private String value;

  @Column
  private String name;

  @Column
  private int parent;

  public DaoAttstr(int parent, String name, String value) {
    this.parent = parent;
    this.name = name;
    this.value = value;
  }

  public DaoAttstr(int parent, String name) {
    this.parent = parent;
    this.name = name;
  }

  public DaoAttstr() {
    // for db
  }

  public void setId(int id) {
    this.id = id;
  }

  @Override
  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }
}
