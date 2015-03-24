package ch.kerbtier.helene;

import java.math.BigInteger;
import java.util.Date;

public interface ModifiableNode {
  
  void set(String name, Date value);
  void set(String name, String value);
  void set(String name, BigInteger value);
  void set(String name, HeleneUser value);

  HObject commit();
}
