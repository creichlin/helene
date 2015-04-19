package ch.kerbtier.helene.store.sql.dao;

import ch.kerbtier.helene.HSlug;
import ch.kerbtier.webb.db.Column;
import ch.kerbtier.webb.db.Table;


@Table(name = "attslug")
public class DaoAttslug implements DaoAtt<HSlug> {

  @Column(key = true)
  private int id = -1;

  @Column
  private String name;

  @Column
  private int parent;

  @Column
  private String value;


  public DaoAttslug(int parent, String name, HSlug value) {
    this.parent = parent;
    this.name = name;
    this.value = value == null ? null : value.getValue();
  }
  
  public DaoAttslug(int parent, String name) {
    this.parent = parent;
    this.name = name;
  }
  
  public DaoAttslug() {
    // for db
  }

  public void setId(int id) {
    this.id = id;
  }

  @Override
  public HSlug getValue() {
    return new HSlug(value);
  }

  @Override
  public void setValue(HSlug value) {
    this.value = value == null ? null : value.getValue();
  }

  public int getParent() {
    return parent;
  }
}
