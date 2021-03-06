package ch.kerbtier.helene.tests;

import java.nio.ByteBuffer;

import static org.junit.Assert.*;

import org.junit.Test;

import ch.kerbtier.helene.HBlob;
import ch.kerbtier.helene.ModifiableNode;
import ch.kerbtier.helene.tests.util.StorImpls;
import ch.kerbtier.helene.tests.util.StoreFactory;

public class Blob extends StorImpls {
  
  public Blob(StoreFactory sf, String name) {
    super(sf, name);
  }
  
  @Test
  public void checkName() {
    
    ModifiableNode mn = store.getObject("post").update();
    
    byte[] data = new byte[]{-128, 0, 0, 127};
    
    
    mn.set("image", ByteBuffer.wrap(data));
    
    mn.commit();
    
    assertArrayEquals(data, ((HBlob)store.getObject("post").get("image")).asArray());
  }
}
