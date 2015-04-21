package ch.kerbtier.helene.profile;

import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;


public class CreatAndMod {
  
  public static void main(String[] args) {
    
    FileUtils.deleteQuietly(Paths.get("profile/temp").toFile());
    
    
    StoreProvider mem = new MemStoreProvider();
    StoreProvider h2File = new SqlFileStoreProvider();
    StoreProvider h2Mem = new SqlMemStoreProvider();
    
    int count = 10;
    
    new Benchmark(new Test1(mem), count);
    new Benchmark(new Test1(h2File), count);
    new Benchmark(new Test1(h2Mem), count);
    
    new Benchmark(new Test2Read(mem), count);
    new Benchmark(new Test2Read(h2File), count);
    new Benchmark(new Test2Read(h2Mem), count);

    new Benchmark(new Test3Delete(mem), count);
    new Benchmark(new Test3Delete(h2File), count);
    new Benchmark(new Test3Delete(h2Mem), count);
  }
}
