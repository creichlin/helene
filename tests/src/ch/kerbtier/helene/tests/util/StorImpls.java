package ch.kerbtier.helene.tests.util;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

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
    
    
    StoreFactory h2sql = new StoreFactory() {
      @Override
      public Store create() {
        return new SqlStore(root, "org.h2.Driver", "jdbc:h2:mem:name1;USER=test;PASSWORD=test;INIT=DROP ALL OBJECTS", Paths.get("tmp"));
      }
      
    };
    
    return Arrays.asList(new Object[][]{{mem}, {h2sql}});
  }
  
  public StorImpls(StoreFactory sf) {
    this.store = sf.create();
  }
}
