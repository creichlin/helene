package ch.kerbtier.helene.tests;

import java.math.BigInteger;
import java.util.Date;

import static org.junit.Assert.*;

import org.junit.Test;

import ch.kerbtier.helene.HObject;
import ch.kerbtier.helene.ModifiableNode;
import ch.kerbtier.helene.exceptions.WrongFieldTypeException;
import ch.kerbtier.helene.tests.util.StorImpls;
import ch.kerbtier.helene.tests.util.StoreFactory;

public class BasicStore extends StorImpls {
  
  public BasicStore(StoreFactory sf) {
    super(sf);
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
    
    assertEquals("post.comments", store.getObject("post").getObjects("comments").get(0).getName());
  }

  @Test(expected = WrongFieldTypeException.class)
  public void getByWrongType() {
    store.getString("post");
  }
  
  @Test()
  public void getInstance() {
    HObject obj = store.getObject("post");
    assertNotNull(obj);
  }

  @Test()
  public void getInstanceTwice() {
    HObject obj1 = store.getObject("post");
    HObject obj2 = store.getObject("post");
    assertEquals(obj1, obj2);
  }
  
  @Test
  public void writeValue() {
    HObject obj = store.getObject("post");
    ModifiableNode node = obj.update();
    node.set("title", "Title1");
    assertNull(obj.getString("title"));
    node.commit();
    assertEquals("Title1", obj.getString("title"));
  }
  
  @Test
  public void writeNullValue() {
    HObject obj = store.getObject("post");
    ModifiableNode node = obj.update();
    node.set("title", "Title1");
    obj = node.commit();
    
    node = obj.update();
    node.set("title", (String)null);
    node.commit();
    
    assertNull(obj.getString("title"));
  }
  
  @Test(expected = WrongFieldTypeException.class)
  public void writeWrongValueType() {
    HObject obj = store.getObject("post");
    ModifiableNode node = obj.update();
    node.set("title", new BigInteger("100"));
  }

  @Test()
  public void getSubInstance() {
    HObject obj = store.getObject("post").getObject("meta");
    assertNotNull(obj);
  }
  
  @Test
  public void deleteObject()  {
    ModifiableNode mn = store.getObject("post").update();
    mn.set("title", "cupulu");
    mn.commit();
    assertEquals("cupulu", store.getObject("post").getString("title"));
    
    store.getObject("post").delete();
    
    assertNull(store.getObject("post").getString("title"));
  }

  @Test()
  public void getSubInstanceTwice() {
    HObject obj1 = store.getObject("post").getObject("meta");
    HObject obj2 = store.getObject("post").getObject("meta");
    assertEquals(obj1, obj2);
  }

  @Test()
  public void setSubInstanceValue() {
    ModifiableNode node = store.getObject("post").getObject("meta").update();
    node.set("foo", "lala");
    node.commit();

    assertEquals("lala", store.getObject("post").getObject("meta").getString("foo"));
  }
  
  @Test() 
  public void setAndGetDate() {
    ModifiableNode node = store.getObject("post").update();
    
    Date date = new Date();
    node.set("date", date);
    node.commit();
    
    assertEquals(date, store.getObject("post").getDate("date"));
  }


}
