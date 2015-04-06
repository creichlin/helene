package ch.kerbtier.helene;

import java.math.BigInteger;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Types {
  public static final Class<String> STRING = String.class; 
  public static final Class<HSlug> SLUG = HSlug.class; 
  public static final Class<BigInteger> INTEGER = BigInteger.class; 
  public static final Class<HeleneUser> USER = HeleneUser.class; 
  public static final Class<Date> DATE = Date.class; 
  public static final Class<HObject> OBJECT = HObject.class; 
  public static final Class<HBlob> BLOB = HBlob.class; 
  @SuppressWarnings("rawtypes")
  public static final Class<HList> LIST = HList.class;
  
  private static final Set<Class<?>> TYPES_BACKEND = new HashSet<>();
  public static final Set<Class<?>> TYPES = Collections.unmodifiableSet(TYPES_BACKEND);
  
  static {
    TYPES_BACKEND.add(STRING);
    TYPES_BACKEND.add(SLUG);
    TYPES_BACKEND.add(INTEGER);
    TYPES_BACKEND.add(USER);
    TYPES_BACKEND.add(DATE);
    TYPES_BACKEND.add(OBJECT);
    TYPES_BACKEND.add(LIST);
    TYPES_BACKEND.add(BLOB);
  }
}
