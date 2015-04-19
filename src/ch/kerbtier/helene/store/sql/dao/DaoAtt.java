package ch.kerbtier.helene.store.sql.dao;

public interface DaoAtt<X> {
  X getValue();

  void setValue(X value);
}
