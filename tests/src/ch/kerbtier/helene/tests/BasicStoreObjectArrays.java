package ch.kerbtier.helene.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import ch.kerbtier.helene.HList;
import ch.kerbtier.helene.HObject;
import ch.kerbtier.helene.ModifiableNode;
import ch.kerbtier.helene.tests.util.StorImpls;
import ch.kerbtier.helene.tests.util.StoreFactory;

public class BasicStoreObjectArrays extends StorImpls {
  public BasicStoreObjectArrays(StoreFactory sf) {
    super(sf);
  }

  @Test
  public void checkSizeOfEmptyList() {
    HList<HObject> comments = store.getObject("post").getObjects("comments");
    assertEquals(0, comments.size());
  }

  @Test
  public void addNewObjectToList() {
    HList<HObject> comments = store.getObject("post").getObjects("comments");
    ModifiableNode mn = comments.add();
    mn.commit();
    assertEquals(1, store.getObject("post").getObjects("comments").size());
  }

  @Test
  public void addAnotherObjectToList() {
    HList<HObject> comments = store.getObject("post").getObjects("comments");
    ModifiableNode mn = comments.add();
    mn.set("name", "rosenheidi");
    mn.commit();
    assertEquals("rosenheidi", store.getObject("post").getObjects("comments").get(0).getString("name"));
  }

  @Test
  public void checkIterator() {
    HList<HObject> comments = store.getObject("post").getObjects("comments");
    
    ModifiableNode mn = comments.add();
    mn.set("name", "rosenheidi");
    HObject i1 = mn.commit();

    ModifiableNode mn2 = comments.add();
    mn2.set("name", "tulpenpeter");
    HObject i2 = mn.commit();
    
    int cnt = 0;
    for(HObject ho: store.getObject("post").getObjects("comments")) {
      if(cnt == 0) {
        assertEquals(i1, ho);
      } else {
        assertEquals(i2, ho);
      }
      cnt++;
    }
  }
  
  

  @Test
  public void addAndModNewObjectToList() {
    HList<HObject> comments = store.getObject("post").getObjects("comments");
    ModifiableNode mn = comments.add();
    mn.set("name", "rosenheidi");
    mn.commit();
    
    mn = store.getObject("post").getObjects("comments").get(0).update();
    mn.set("name", "quarzkopf");
    mn.commit();
    
    assertEquals("quarzkopf", store.getObject("post").getObjects("comments").get(0).getString("name"));
  }
  
  @Test
  public void swapTwoObjs() {
    HList<HObject> comments = store.getObject("post").getObjects("comments");
    ModifiableNode mn = comments.add();
    mn.set("name", "rosenheidi");
    mn.commit();
    
    mn = comments.add();
    mn.set("name", "quarzkopf");
    mn.commit();
    
    store.getObject("post").getObjects("comments").swap(0, 1);
    
    assertEquals("quarzkopf", store.getObject("post").getObjects("comments").get(0).getString("name"));
    assertEquals("rosenheidi", store.getObject("post").getObjects("comments").get(1).getString("name"));
  }

  @Test
  public void upObj() {
    HList<HObject> comments = store.getObject("post").getObjects("comments");
    ModifiableNode mn = comments.add();
    mn.set("name", "rosenheidi");
    mn.commit();
    
    mn = comments.add();
    mn.set("name", "quarzkopf");
    mn.commit();
    
    store.getObject("post").getObjects("comments").get(0).down();
    
    assertEquals("quarzkopf", store.getObject("post").getObjects("comments").get(0).getString("name"));
    assertEquals("rosenheidi", store.getObject("post").getObjects("comments").get(1).getString("name"));
  }

  @Test
  public void downObj() {
    HList<HObject> comments = store.getObject("post").getObjects("comments");
    ModifiableNode mn = comments.add();
    mn.set("name", "rosenheidi");
    mn.commit();
    
    mn = comments.add();
    mn.set("name", "quarzkopf");
    mn.commit();
    
    store.getObject("post").getObjects("comments").get(1).up();
    
    assertEquals("quarzkopf", store.getObject("post").getObjects("comments").get(0).getString("name"));
    assertEquals("rosenheidi", store.getObject("post").getObjects("comments").get(1).getString("name"));
  }

  @Test
  public void deleteFromArraysObjects() {
    HList<HObject> comments = store.getObject("post").getObjects("comments");
    ModifiableNode mn = comments.add();
    mn.set("name", "rosenheidi");
    mn.commit();

    mn = comments.add();
    mn.set("name", "zupenpaul");
    mn.commit();

    assertEquals(2, store.getObject("post").getObjects("comments").size());
    
    store.getObject("post").getObjects("comments").get(1).delete();
    assertEquals(1, store.getObject("post").getObjects("comments").size());
    
    store.getObject("post").getObjects("comments").get(0).delete();
    assertEquals(0, store.getObject("post").getObjects("comments").size());
  }

  @Test
  public void deleteFromArray() {
    HList<HObject> comments = store.getObject("post").getObjects("comments");
    ModifiableNode mn = comments.add();
    mn.set("name", "rosenheidi");
    mn.commit();

    mn = comments.add();
    mn.set("name", "zupenpaul");
    mn.commit();

    assertEquals(2, store.getObject("post").getObjects("comments").size());
    
    store.getObject("post").getObjects("comments").delete(1);
    assertEquals(1, store.getObject("post").getObjects("comments").size());
    
    store.getObject("post").getObjects("comments").delete(0);
    assertEquals(0, store.getObject("post").getObjects("comments").size());
  }

}
