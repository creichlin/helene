package ch.kerbtier.helene.store.sql;

public interface Visitor<X> {
  X visit(SqlHList list);
  X visit(SqlHObjectList list);
  X visit(SqlHObject object);
}
