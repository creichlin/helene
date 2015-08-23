package ch.kerbtier.helene.porter;

import java.nio.file.Paths;

import ch.kerbtier.helene.HList;
import ch.kerbtier.helene.HObject;
import ch.kerbtier.helene.ModifiableNode;
import ch.kerbtier.helene.Parse;
import ch.kerbtier.helene.Types;
import ch.kerbtier.helene.entities.Entity;
import ch.kerbtier.helene.entities.EntityList;
import ch.kerbtier.helene.entities.EntityMap;
import ch.kerbtier.helene.impl.ImpEntityMap;
import ch.kerbtier.helene.store.memory.MemoryStore;
import ch.kerbtier.helene.store.sql.SqlStore;

public class Porter {
  public static void main(String[] args) {
    
    new Porter().port(args[0], args[1], args[2], args[3], args[4]);
    
  }

  private void port(String model, String sourceFile, String targetFile, String user, String password) {
    ImpEntityMap root = new ImpEntityMap();
    
    Parse.extend(root, Paths.get(model));
    
    MemoryStore source = new MemoryStore(root, Paths.get(sourceFile));
    SqlStore target = new SqlStore(root, "org.h2.Driver", "jdbc:h2:file:" + Paths.get(targetFile).toAbsolutePath() + ";USER=" + user + ";PASSWORD=" + password, Paths.get(targetFile).getParent().resolve("bin"));
    
    copy(root, source, target);
  }

  private void copy(EntityMap root, HObject source, HObject target) {
    ModifiableNode mn = target.update();
    for(String p: root.getProperties()) {
      Entity ent = root.get(p);
      if(ent instanceof EntityMap) {
        copy((EntityMap)ent, source.getObject(p), target.getObject(p));
      } else if(ent instanceof EntityList) {
        copy((EntityList)ent, source.getList(p, null), target.getList(p, null));
      } else {
        mn.set(p, source.get(p));
      }
    }
    mn.commit();
  }

  private void copy(EntityList ent, HList source, HList target) {
    if(ent.get().is(Types.OBJECT)) {
      for(HObject ho: (HList<HObject>)source) {
        ModifiableNode mn = target.add();
        HObject nn = mn.commit();
        copy(ent.getObject(), ho, nn);
      }
    } else {
      for(Object o: source) {
        target.add(o);
      }
    }
  }
}
