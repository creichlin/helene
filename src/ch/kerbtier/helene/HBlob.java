package ch.kerbtier.helene;

import java.nio.ByteBuffer;

public interface HBlob {
  /**
   * use SHA2 currently, must be exchangeable. nothing should break if hash
   * changes, so no references to the hash but it's ok for caching.
   * 
   * @return
   */
  String getHash();
  
  ByteBuffer asBuffer();
  byte[] asArray();
  
  void setData(ByteBuffer byteBuffer);

}
