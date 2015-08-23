package ch.kerbtier.helene.store.sql.dao;

import java.math.BigInteger;

import ch.kerbtier.hopsdb.model.annotations.Column;
import ch.kerbtier.hopsdb.model.annotations.Table;

@Table(name = "attint")
public class DaoAttint extends DaoAtt<BigInteger> {

  @Column
  private String value;

  public DaoAttint(int parent, String name, BigInteger value) {
    super(parent, name);
    this.value = value.toString();
  }

  public DaoAttint(int parent, String name) {
    super(parent, name);
  }

  public DaoAttint() {
    // for db
  }

  @Override
  public BigInteger getValue() {
    return new BigInteger(value);
  }

  @Override
  public void setValue(BigInteger value) {
    this.value = value.toString();
  }
}
