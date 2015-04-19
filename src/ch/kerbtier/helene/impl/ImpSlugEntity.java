package ch.kerbtier.helene.impl;

import ch.kerbtier.helene.HSlug;
import ch.kerbtier.helene.entities.Entity;
import ch.kerbtier.helene.entities.SlugEntity;

public class ImpSlugEntity extends ImpEntity implements SlugEntity {

  public ImpSlugEntity(Entity parent, String name) {
    super(parent, name);
  }

  @Override
  public Class<?> isOf() {
    return HSlug.class;
  }

  @Override
  public String getType() {
    return "slug";
  }

}
