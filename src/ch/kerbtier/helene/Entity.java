package ch.kerbtier.helene;

public interface Entity {
  boolean is(Class<?> type);

  Class<?> isOf();
  
  String getType();
}
