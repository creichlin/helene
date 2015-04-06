package ch.kerbtier.helene.store.memory;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

import ch.kerbtier.helene.HObject;
import ch.kerbtier.helene.HSlug;
import ch.kerbtier.helene.Store;
import ch.kerbtier.helene.entities.EntityMap;
import ch.kerbtier.helene.exceptions.DuplicateSlugException;

public class MemoryStore extends MemoryHObject implements Store {
  
  private XStream xstream = new XStream(new StaxDriver());

  private Map<HSlug, HObject> slugs = new HashMap<>();
  
  public MemoryStore(EntityMap def) {
    super(def);
    xstream.alias("object", MemoryHObject.class);
    xstream.alias("olist", MemoryHObjectList.class);
    xstream.alias("list", MemoryHList.class);
    xstream.alias("root", MemoryStore.class);
    xstream.alias("slug", HSlug.class);
    
    xstream.omitField(MemoryHNode.class, "parent");

    xstream.omitField(MemoryHObject.class, "store");
    xstream.omitField(MemoryHObject.class, "def");
    xstream.omitField(MemoryHObject.class, "listeners");

    xstream.omitField(MemoryHBaseList.class, "onChange");
    
    xstream.omitField(MemoryHObjectList.class, "store");
    xstream.omitField(MemoryHObjectList.class, "entity");
    
    xstream.omitField(MemoryHList.class, "store");
    xstream.omitField(MemoryHList.class, "entity");
    
    xstream.omitField(MemoryStore.class, "xstream");
  }

  public MemoryStore(EntityMap def, Path source) {
    this(def);
    read(source);
    reconstruct();
  }

  protected void reconstruct() {
    super.reconstruct(null, this, def);
  }

  public void release(HSlug slug) {
    synchronized(slugs) {
      slugs.remove(slug);
    }
  }
  
  public void register(HSlug slug, MemoryHObject object) {
    synchronized(slugs) {
      if(slugs.containsKey(slug)) {
        throw new DuplicateSlugException("slug is already registered");
      }
      slugs.put(slug, object);
    }
  }

  public HObject get(HSlug slug) {
    return slugs.get(slug);
  }
  
  public void write(Path path) {
    try {
      FileUtils.write(path.toFile(), xstream.toXML(this));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
  
  private void read(Path path) {
    xstream.fromXML(path.toFile(), this);
  }
}
