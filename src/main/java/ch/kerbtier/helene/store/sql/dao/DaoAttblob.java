package ch.kerbtier.helene.store.sql.dao;

import ch.kerbtier.helene.HBlob;
import ch.kerbtier.hopsdb.model.annotations.Column;
import ch.kerbtier.hopsdb.model.annotations.Table;

@Table(name = "attblob")
public class DaoAttblob extends DaoAtt<HBlob> {

  @Column
  private String value;

  public DaoAttblob(int parent, String name, HBlob value) {
    super(parent, name);
    this.value = value == null ? null: value.getHash();
  }

  public DaoAttblob(int parent, String name) {
    super(parent, name);
  }

  public DaoAttblob() {
    // for db
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
