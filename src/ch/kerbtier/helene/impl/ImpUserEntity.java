package ch.kerbtier.helene.impl;

import ch.kerbtier.helene.HeleneUser;
import ch.kerbtier.helene.UserEntity;

public class ImpUserEntity extends ImpEntity implements UserEntity {

  @Override
  public String getType() {
    return "user";
  }

  @Override
  public Class<?> isOf() {
    return HeleneUser.class;
  }

}
