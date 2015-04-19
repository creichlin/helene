package ch.kerbtier.helene.store.sql.dao;

import ch.kerbtier.webb.db.Column;
import ch.kerbtier.webb.db.Table;

@Table(name = "object")
public class DaoObject {

  @Column(key = true)
  private int id = -1;

  @Column
  private String type;


  public DaoObject(String type) {
    if(type == null) {
      throw new RuntimeException("type must not be null");
    }
    this.type = type;
  }
  
  public DaoObject() {
    // for Db
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getId() {
    return id;
  }

  @Override
  public int hashCode() {
    return 31 + id + type.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    DaoObject other = (DaoObject) obj;
    return id == other.id && type.equals(other.type);
  }

  public String getType() {
    return type;
  }
}
