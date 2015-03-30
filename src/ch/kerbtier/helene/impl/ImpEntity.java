package ch.kerbtier.helene.impl;

import ch.kerbtier.helene.entities.Entity;

public abstract class ImpEntity implements Entity {
  @Override
  public boolean is(Class<?> type) {
    if(type == null) {
      return false;
    }
    return isOf().isAssignableFrom(type);
  }


}
