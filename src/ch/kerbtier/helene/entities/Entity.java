package ch.kerbtier.helene.entities;

public interface Entity {
  boolean is(Class<?> type);

  Class<?> isOf();
  
  String getType();
}
