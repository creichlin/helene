package ch.kerbtier.helene.store.sql.dao;

import ch.kerbtier.hopsdb.model.annotations.Column;

public abstract class DaoAtt<X> {
  

  @Column(key = true)
  private int id = -1;

  @Column
  private String name;

  @Column
  private int parent;

  public DaoAtt(int parent, String name) {
    this.parent = parent;
    this.name = name;
  }
  
  public DaoAtt() {
    
  }
  
  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public int getParent() {
    return parent;
  }

  public abstract X getValue();

  public abstract void setValue(X value);
}
