package ch.kerbtier.helene.profile;

import java.nio.file.Paths;

import ch.kerbtier.helene.Parse;
import ch.kerbtier.helene.Store;
import ch.kerbtier.helene.impl.ImpEntityMap;
import ch.kerbtier.helene.store.memory.MemoryStore;

class MemStoreProvider implements StoreProvider {

  @Override
  public Store create() {
    ImpEntityMap root = new ImpEntityMap();
    Parse.extend(root, Paths.get("profile/create_and_mod.model"));
    
    MemoryStore mem = new MemoryStore(root);
    return mem;
  }

  @Override
  public String getName() {
    return "Memory";
  }
  
}