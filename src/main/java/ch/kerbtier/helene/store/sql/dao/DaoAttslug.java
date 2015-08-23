package ch.kerbtier.helene.store.sql.dao;

import ch.kerbtier.helene.HSlug;
import ch.kerbtier.hopsdb.model.annotations.Column;
import ch.kerbtier.hopsdb.model.annotations.Table;


@Table(name = "attslug")
public class DaoAttslug extends DaoAtt<HSlug> {

  @Column
  private String value;


  public DaoAttslug(int parent, String name, HSlug value) {
    super(parent, name);
    this.value = value == null ? null : value.getValue();
  }
  
  public DaoAttslug(int parent, String name) {
    super(parent, name);
  }
  
  public DaoAttslug() {
    // for db
  }

  @Override
  public HSlug getValue() {
    return new HSlug(value);
  }

  @Override
  public void setValue(HSlug value) {
    this.value = value == null ? null : value.getValue();
  }
}
