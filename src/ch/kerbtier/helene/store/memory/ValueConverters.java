package ch.kerbtier.helene.store.memory;

import java.nio.ByteBuffer;

public class ValueConverters {

  public static Object convert(Object value) {
    if (value instanceof ByteBuffer) {
      value = createHBlob(value);
    }

    return value;
  }

  private static Object createHBlob(Object value) {
    MemoryHBlob mhb = new MemoryHBlob();
    mhb.setData(((ByteBuffer) value));
    value = mhb;
    return value;
  }
}
