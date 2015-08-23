package ch.kerbtier.helene.tests;

import java.math.BigInteger;
import java.util.Date;

import static org.junit.Assert.*;

import org.junit.Test;

import ch.kerbtier.helene.HList;
import ch.kerbtier.helene.HObject;
import ch.kerbtier.helene.ModifiableNode;
import ch.kerbtier.helene.Types;
import ch.kerbtier.helene.exceptions.InvalidFieldException;
import ch.kerbtier.helene.exceptions.WrongFieldTypeException;
import ch.kerbtier.helene.tests.util.StorImpls;
import ch.kerbtier.helene.tests.util.StoreFactory;

public class BasicObjectTests extends StorImpls {
  
  public BasicObjectTests(StoreFactory sf, String name) {
    super(sf, name);
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
  
  @Test
  public void deletePrimitiveList() {
    HList<Date> dates = store.getObject("post").getList("hits", Types.DATE);
    dates.add(new Date());
    assertEquals(1, store.getObject("post").getList("hits", Types.DATE).size());
    store.getObject("post").getList("hits", Types.DATE).delete();
    assertEquals(0, store.getObject("post").getList("hits", Types.DATE).size());
  }

  @Test
  public void deleteAttObject() {
    ModifiableNode mn = store.getObject("post").getObject("meta").update();
    mn.set("foo", "zebra");
    mn.commit();
    assertEquals("zebra", store.getObject("post").getObject("meta").getString("foo"));
    store.getObject("post").delete("meta");
    assertNull(store.getObject("post").getObject("meta").getString("foo"));
  }

  @Test
  public void deleteAttPrimitive() {
    ModifiableNode mn = store.getObject("post").getObject("meta").update();
    mn.set("foo", "zebra");
    mn.commit();
    assertEquals("zebra", store.getObject("post").getObject("meta").getString("foo"));
    store.getObject("post").getObject("meta").delete("foo");
    assertNull(store.getObject("post").getObject("meta").getString("foo"));
  }

  @Test
  public void deleteAttList() {
    HList<Date> dates = store.getObject("post").getList("hits", Types.DATE);
    dates.add(new Date());
    assertEquals(1, store.getObject("post").getList("hits", Types.DATE).size());
    store.getObject("post").delete("hits");
    assertEquals(0, store.getObject("post").getList("hits", Types.DATE).size());
  }
  
  @Test
  public void deleteAttObjectList() {
    
    ModifiableNode comment = store.getObject("post").getObjects("comments").add();
    comment.set("email", "chribel@chrable");
    comment.commit();
    assertEquals(1, store.getObject("post").getObjects("comments").size());
    store.getObject("post").delete("comments");
    assertEquals(0, store.getObject("post").getObjects("comments").size());
  }
  
  @Test(expected = InvalidFieldException.class)
  public void deleteWrongAtt() {
    HObject post = store.getObject("post");
    post.delete("lala");
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
