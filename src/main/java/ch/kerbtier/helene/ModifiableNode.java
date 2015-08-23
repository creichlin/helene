package ch.kerbtier.helene;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.Date;

public interface ModifiableNode {
  
  void set(String name, Object value);
  void set(String name, Date value);
  void set(String name, String value);
  void set(String name, BigInteger value);
  void set(String name, HeleneUser value);
  void set(String name, HSlug value);
  void set(String name, ByteBuffer wrap);

  HObject commit();
}
