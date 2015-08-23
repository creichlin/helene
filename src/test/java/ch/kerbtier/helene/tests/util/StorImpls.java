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
  private static int cnt = 0;
  
  private String name;
  protected Store store;
  
  @Parameterized.Parameters(name = "{1}")
  public static List<Object[]> data() {
    final ImpEntityMap root = new ImpEntityMap();
    Parse.extend(root, Paths.get("src/test/resources/", "post.model"));
    
    StoreFactory mem = new StoreFactory() {
      @Override
      public Store create() {
        return new MemoryStore(root);
      }
    };
    
    
    StoreFactory h2sqlmem = new StoreFactory() {
      @Override
      public Store create() {
        // need to be named because tests might use multiple connections
        return new SqlStore(root, "org.h2.Driver", "jdbc:h2:mem:m" + (cnt++) + ";USER=test;PASSWORD=test;DB_CLOSE_DELAY=-1", Paths.get("tmp"));
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
      @Override
      public Store create() {
        return new SqlStore(root, "org.h2.Driver", "jdbc:h2:file:" + dbpath.resolve("h222_" + (cnt++)) + ";USER=test;PASSWORD=test", dbpath);
      }
      
    };

    return Arrays.asList(new Object[][]{{mem, "mem"}, {h2sqlfile, "sql-h2-file"}, {h2sqlmem, "sql-h2-mem"}});
  }
  
  public String getName() {
    return name;
  }



  public StorImpls(StoreFactory sf, String name) {
    this.store = sf.create();
    this.name = name;
  }
}
