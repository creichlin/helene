package ch.kerbtier.helene.store.mod;

import java.nio.ByteBuffer;

public class ValueConverters {

  public static Object convert(Object value) {
    if (value instanceof ByteBuffer) {
      value = createHBlob(value);
    }

    return value;
  }

  private static Object createHBlob(Object value) {
    ByteBufferHBlob mhb = new ByteBufferHBlob();
    mhb.setData(((ByteBuffer) value));
    value = mhb;
    return value;
  }
}
