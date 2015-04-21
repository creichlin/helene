package ch.kerbtier.helene.tests.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import ch.kerbtier.helene.Parse;
import ch.kerbtier.helene.Store;
import ch.kerbtier.helene.impl.ImpEntityMap;
import ch.kerbtier.helene.store.memory.MemoryStore;
import ch.kerbtier.helene.store.sql.SqlStore;

@RunWith(Parameterized.class)
public class StorImpls {
  
  protected Store store;
  
  @Parameterized.Parameters
  public static List<Object[]> data() {
    final ImpEntityMap root = new ImpEntityMap();
    Parse.extend(root, Paths.get("tests", "post.model"));
    
    StoreFactory mem = new StoreFactory() {
      @Override
      public Store create() {
        return new MemoryStore(root);
      }
    };
    
    
    StoreFactory h2sqlmem = new StoreFactory() {
      @Override
      public Store create() {
        return new SqlStore(root, "org.h2.Driver", "jdbc:h2:mem:name1;USER=test;PASSWORD=test;INIT=DROP ALL OBJECTS", Paths.get("tmp"));
      }
      
    };
    
    final Path dbpath = Paths.get("tmp").toAbsolutePath();
    
    FileUtils.deleteQuietly(dbpath.toFile());
    
    try {
      Files.createDirectories(dbpath);
    } catch (IOException e) {
      e.printStackTrace();
    }
    
    
    StoreFactory h2sqlfile = new StoreFactory() {
      private int count = 0;
      @Override
      public Store create() {
        return new SqlStore(root, "org.h2.Driver", "jdbc:h2:file:" + dbpath.resolve("h222_" + (count++)) + ";USER=test;PASSWORD=test", dbpath);
      }
      
    };
    
    return Arrays.asList(new Object[][]{{mem}, {h2sqlmem}, {h2sqlfile}});
  }
  
  public StorImpls(StoreFactory sf) {
    this.store = sf.create();
  }
}
