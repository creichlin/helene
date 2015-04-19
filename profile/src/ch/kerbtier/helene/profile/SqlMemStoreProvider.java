package ch.kerbtier.helene.profile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import ch.kerbtier.helene.Parse;
import ch.kerbtier.helene.Store;
import ch.kerbtier.helene.impl.ImpEntityMap;
import ch.kerbtier.helene.store.sql.SqlStore;

class SqlMemStoreProvider implements StoreProvider {

  private int number = 0;
  
  @Override
  public Store create() {
    Path base = Paths.get("profile/temp");
    try {
      Files.createDirectories(base);
    } catch (IOException e) {
      e.printStackTrace();
    }
    
    ImpEntityMap root = new ImpEntityMap();
    Parse.extend(root, Paths.get("profile/create_and_mod.model"));
    
    SqlStore sql = new SqlStore(root, "org.h2.Driver", "jdbc:h2:mem:test" + (number++) + ";USER=test;PASSWORD=test", base.resolve("bin"));
    return sql;
  }

  @Override
  public String getName() {
    return "H2Memory";
  }
  
}