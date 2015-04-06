package ch.kerbtier.helene.store.memory;

import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;

import ch.kerbtier.helene.HBlob;

public class MemoryHBlob implements HBlob {

  private byte[] data;
  private String hash;

  @Override
  public String getHash() {
    return hash;
  }

  @Override
  public ByteBuffer asBuffer() {
    return ByteBuffer.wrap(this.data);
  }

  @Override
  public byte[] asArray() {
    return data;
  }

  @Override
  public void setData(ByteBuffer byteBuffer) {
    data = byteBuffer.array();
    if (data != null) {
      MessageDigest md;
      try {
        md = MessageDigest.getInstance("SHA-256");
      } catch (NoSuchAlgorithmException e) {
        throw new RuntimeException(e);
      }
      byte[] dg = md.digest(data);
      hash = DatatypeConverter.printHexBinary(dg);
    } else {
      hash = null;
    }
  }
}
