package ch.kerbtier.helene.tests;

import java.math.BigInteger;

import org.junit.Test;

import ch.kerbtier.helene.HObject;
import ch.kerbtier.helene.ModifiableNode;
import ch.kerbtier.helene.exceptions.UndefinedFieldException;
import ch.kerbtier.helene.tests.util.StorImpls;
import ch.kerbtier.helene.tests.util.StoreFactory;

public class InvalidTypes extends StorImpls {
  public InvalidTypes(StoreFactory sf, String name) {
    super(sf, name);
  }
  
  @Test(expected = UndefinedFieldException.class)
  public void undefinedObject() {
    store.getObject("noPost");
  }

  @Test(expected = UndefinedFieldException.class)
  public void undefinedBigInteger() {
    store.getInteger("noInt");
  }

  @Test(expected = UndefinedFieldException.class)
  public void undefinedString() {
    store.getString("noString");
  }

  @Test(expected = UndefinedFieldException.class)
  public void undefinedDate() {
    store.getDate("noDate");
  }

  @Test(expected = UndefinedFieldException.class)
  public void undefinedALl() {
    store.get("nonono");
  }

  @Test(expected = UndefinedFieldException.class)
  public void undefinedObjectList() {
    store.getObjects("noPostList");
  }

  @Test(expected = UndefinedFieldException.class)
  public void undefinedStringList() {
    store.getStrings("noStringList");
  }

  @Test(expected = UndefinedFieldException.class)
  public void writeUndefinedField() {
    HObject obj = store.getObject("post");
    ModifiableNode node = obj.update();
    node.set("blabla", new BigInteger("100"));
  }
}
