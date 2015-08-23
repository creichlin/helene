package ch.kerbtier.helene.store.sql.dao;


import ch.kerbtier.hopsdb.model.annotations.Column;

public abstract class DaoLs<X> {

  @Column(key = true)
  private int id = -1;

  @Column
  private int index;

  @Column
  private int parent;

  
  public DaoLs(int parent, int index) {
    this.parent = parent;
    this.index = index;
  }
  
  public DaoLs() {
    
  }

  public int getId() {
    return id;
  }

  public int getIndex() {
    return index;
  }

  public int getParent() {
    return parent;
  }

  public void setIndex(int index) {
    this.index = index;
  }
  
  public abstract X getValue();

  public abstract void setValue(X value);

}
