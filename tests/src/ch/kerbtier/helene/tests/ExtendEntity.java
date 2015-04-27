package ch.kerbtier.helene.tests;

import static org.junit.Assert.*;

import java.nio.file.Paths;

import org.junit.Test;

import ch.kerbtier.helene.Parse;
import ch.kerbtier.helene.entities.EntityMap;
import ch.kerbtier.helene.entities.StringEntity;
import ch.kerbtier.helene.impl.ImpEntityMap;

public class ExtendEntity {
  
  private EntityMap root;
  
  @Test()
  public void checkNewAttribute() {
    root = new ImpEntityMap(null, "");
    Parse.extend(root, Paths.get("tests", "post.model"));
    Parse.extend(root, Paths.get("tests", "post.extend.model"));

    assertTrue(root.getObject("post").get("title2") instanceof StringEntity);
  }

  @Test()
  public void checkOldAttribute() {
    root = new ImpEntityMap(null, "");
    Parse.extend(root, Paths.get("tests", "post.model"));
    Parse.extend(root, Paths.get("tests", "post.extend.model"));

    assertTrue(root.getObject("post").get("title") instanceof StringEntity);
  }

  @Test()
  public void checkNewAttributeInList() {
    root = new ImpEntityMap(null, "");
    Parse.extend(root, Paths.get("tests", "post.model"));
    Parse.extend(root, Paths.get("tests", "post.extend.model"));

    assertTrue(root.getObject("post").getList("comments").getObject().get("name2") instanceof StringEntity);
  }

  @Test()
  public void checkOldAttributeInList() {
    root = new ImpEntityMap(null, "");
    Parse.extend(root, Paths.get("tests", "post.model"));
    Parse.extend(root, Paths.get("tests", "post.extend.model"));

    assertTrue(root.getObject("post").getList("comments").getObject().get("name") instanceof StringEntity);
  }


}

