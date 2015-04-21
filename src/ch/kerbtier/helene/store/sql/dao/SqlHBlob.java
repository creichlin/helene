package ch.kerbtier.helene.store.sql.dao;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;

import ch.kerbtier.helene.HBlob;

public class SqlHBlob implements HBlob {

  private String hash;
  private Path path;

  public SqlHBlob(String hash) {
    this.hash = hash;
  }

  public void init(Path pathParam) {
    this.path = pathParam;
  }

  @Override
  public ByteBuffer asBuffer() {
    if (path == null) {
      throw new RuntimeException("path needs to be initialized");
    }

    if (hash != null) {
      try {
        FileChannel sbc = FileChannel.open(path.resolve(hash));
        int lngth = (int) sbc.size();
        MappedByteBuffer out = sbc.map(FileChannel.MapMode.READ_ONLY, 0, lngth);
        return out;
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }
    return null;
  }

  @Override
  public byte[] asArray() {
    if(hash != null) {
      try {
        return Files.readAllBytes(path.resolve(hash));
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
    return null;
  }

  @Override
  public String getHash() {
    return hash;
  }
}
