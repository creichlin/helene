package ch.kerbtier.helene.store.sql.dao;

import java.math.BigInteger;

import ch.kerbtier.webb.db.Column;
import ch.kerbtier.webb.db.Table;

@Table(name = "attint")
public class DaoAttint implements DaoAtt<BigInteger> {

  @Column(key = true)
  private int id = -1;

  @Column
  private String value;

  @Column
  private String name;

  @Column
  private int parent;



  public DaoAttint(int parent, String name, BigInteger value) {
    this.parent = parent;
    this.name = name;
    this.value = value.toString();
  }

  public DaoAttint(int parent, String name) {
    this.parent = parent;
    this.name = name;
  }

  public DaoAttint() {
    // for db
  }

  public void setId(int id) {
    this.id = id;
  }

  @Override
  public BigInteger getValue() {
    return new BigInteger(value);
  }

  public void setValue(BigInteger value) {
    this.value = value.toString();
  }
}
