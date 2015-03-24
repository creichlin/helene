package ch.kerbtier.helene.tests.util;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import ch.kerbtier.helene.Parse;
import ch.kerbtier.helene.Store;
import ch.kerbtier.helene.impl.ImpEntityMap;
import ch.kerbtier.helene.store.jdom.JDomStore;
import ch.kerbtier.helene.store.memory.MemoryStore;

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
    
    StoreFactory dom = new StoreFactory() {
      @Override
      public Store create() {
        return new JDomStore(root);
      }
    };
    
    return Arrays.asList(new Object[][]{{mem}, {dom}});
  }
  
  public StorImpls(StoreFactory sf) {
    this.store = sf.create();
  }
}
