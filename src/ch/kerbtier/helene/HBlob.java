package ch.kerbtier.helene;

import java.nio.ByteBuffer;

public interface HBlob {
  String getHash();
  ByteBuffer asBuffer();
  byte[] asArray();
}
