package ch.kerbtier.helene.impl;

import ch.kerbtier.helene.HeleneUser;
import ch.kerbtier.helene.entities.Entity;
import ch.kerbtier.helene.entities.UserEntity;

public class ImpUserEntity extends ImpEntity implements UserEntity {

  public ImpUserEntity(Entity parent, String name) {
    super(parent, name);
  }

  @Override
  public String getType() {
    return "user";
  }

  @Override
  public Class<?> isOf() {
    return HeleneUser.class;
  }

}
