package ch.kerbtier.helene.store.sql.dao;

import ch.kerbtier.webb.db.Column;
import ch.kerbtier.webb.db.Table;

@Table(name = "list")
public class DaoList {
  @Column(key = true)
  private int id = -1;

  @Column
  private String type;

  public DaoList(String type) {
    this.type = type;
  }

  public DaoList() {
    // for Db
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getId() {
    return id;
  }

  public String getType() {
    return type;
  }

  @Override
  public int hashCode() {
    return 31 + id;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    DaoList other = (DaoList) obj;
    return id == other.id;
  }
}
