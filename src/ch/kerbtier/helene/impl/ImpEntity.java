package ch.kerbtier.helene.impl;

import ch.kerbtier.helene.Entity;

public abstract class ImpEntity implements Entity {
  @Override
  public boolean is(Class<?> type) {
    return isOf().isAssignableFrom(type);
  }


}
