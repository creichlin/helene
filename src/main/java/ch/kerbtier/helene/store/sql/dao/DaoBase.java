package ch.kerbtier.helene.store.sql.dao;

import ch.kerbtier.hopsdb.model.annotations.Column;

public class DaoBase {
  @Column(key = true)
  private int id = -1;

  @Column
  private String type;

  public DaoBase(String type) {
    this.type = type;
  }

  public DaoBase() {

  }

  public String getType() {
    return type;
  }

  public int getId() {
    return id;
  }

  @Override
  public int hashCode() {
    return 31 + id + type.hashCode() + getClass().hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    DaoBase other = (DaoBase) obj;
    return id == other.id && type.equals(other.type);
  }
}
