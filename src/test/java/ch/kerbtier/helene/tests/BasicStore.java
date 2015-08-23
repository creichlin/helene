package ch.kerbtier.helene.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import ch.kerbtier.helene.ModifiableNode;
import ch.kerbtier.helene.exceptions.WrongFieldTypeException;
import ch.kerbtier.helene.tests.util.StorImpls;
import ch.kerbtier.helene.tests.util.StoreFactory;

public class BasicStore extends StorImpls {
  
  public BasicStore(StoreFactory sf, String name) {
    super(sf, name);
  }
  
  @Test
  public void checkName() {
    assertEquals("post", store.getObject("post").getName());
  }

  @Test
  public void checkListsName() {
    
    ModifiableNode mn = store.getObject("post").getObjects("comments").add();
    mn.set("name", "lalala");
    mn.commit();
    
    assertEquals("post.comments._", store.getObject("post").getObjects("comments").get(0).getName());
  }

  @Test(expected = WrongFieldTypeException.class)
  public void getByWrongType() {
    store.getString("post");
  }
}
