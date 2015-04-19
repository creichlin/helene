package ch.kerbtier.helene.impl;

import ch.kerbtier.helene.entities.Entity;

public abstract class ImpEntity implements Entity {
  
  private Entity parent;
  private String name;
  
  public ImpEntity(Entity parent, String name) {
    this.parent = parent;
    this.name = name;
  }
  
  
  @Override
  public boolean is(Class<?> type) {
    if(type == null) {
      return false;
    }
    return isOf().isAssignableFrom(type);
  }

  @Override
  public Entity getParent() {
    return parent;
  }
  
  @Override
  public String getName() {
    return name;
  }
}
