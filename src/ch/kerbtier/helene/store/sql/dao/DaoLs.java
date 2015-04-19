package ch.kerbtier.helene.store.sql.dao;

public interface DaoLs<X> {
  X getValue();

  void setIndex(int i);
  void setValue(X value);
}
