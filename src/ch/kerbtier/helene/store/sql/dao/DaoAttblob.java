package ch.kerbtier.helene.store.sql.dao;

import ch.kerbtier.helene.HBlob;
import ch.kerbtier.webb.db.Column;
import ch.kerbtier.webb.db.Table;

@Table(name = "attblob")
public class DaoAttblob implements DaoAtt<HBlob> {

  @Column(key = true)
  private int id = -1;

  @Column
  private String value;

  @Column
  private String name;

  @Column
  private int parent;

  public DaoAttblob(int parent, String name, HBlob value) {
    this.parent = parent;
    this.name = name;
    this.value = value == null ? null: value.getHash();
  }

  public DaoAttblob(int parent, String name) {
    this.parent = parent;
    this.name = name;
  }

  public DaoAttblob() {
    // for db
  }

  public void setId(int id) {
    this.id = id;
  }

  @Override
  public HBlob getValue() {
    return new SqlHBlob(value);
  }

  @Override
  public void setValue(HBlob value) {
    this.value = value == null ? null: value.getHash();
  }
}
