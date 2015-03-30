package ch.kerbtier.helene.tests;

import static org.junit.Assert.*;

import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;

import ch.kerbtier.helene.HObject;
import ch.kerbtier.helene.Parse;
import ch.kerbtier.helene.entities.Entity;
import ch.kerbtier.helene.entities.EntityMap;
import ch.kerbtier.helene.entities.SlugEntity;
import ch.kerbtier.helene.entities.StringEntity;
import ch.kerbtier.helene.impl.ImpEntityMap;

public class BasicEntity {
  
  private EntityMap root;
  
  @Before
  public void init() {
    root = new ImpEntityMap();
    Parse.extend(root, Paths.get("tests", "post.model"));
  }
  
  @Test()
  public void checkStringType() {
    assertTrue(root.getObject("post").get("title") instanceof StringEntity);
  }
  
  @Test()
  public void checkSlugType() {
    assertTrue(root.getObject("post").get("slug") instanceof SlugEntity);
  }
  

  @Test()
  public void checkExistingMapType() {
    Entity entity = root.get("post");
    assertTrue(entity instanceof EntityMap);
  }

  @Test()
  public void checkExistingMapTypeWithIs() {
    Entity entity = root.get("post");
    assertTrue(entity.is(HObject.class));
  }
}
