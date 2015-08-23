package ch.kerbtier.helene.entities;

public interface Entity {
  boolean is(Class<?> type);

  Class<?> isOf();
  
  String getType();
  
  Entity getParent();

  /*
   * name is full path of model with list slot written as _, comma delimited
   */
  String getName();
}
