package ch.kerbtier.helene.store.sql;

public interface Visitable {
  Object accept(Visitor<? extends Object> visitor);
}
