package ch.kerbtier.helene.store.mod;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import ch.kerbtier.helene.HObject;
import ch.kerbtier.helene.HSlug;
import ch.kerbtier.helene.HeleneUser;
import ch.kerbtier.helene.ModifiableNode;
import ch.kerbtier.helene.entities.Entity;
import ch.kerbtier.helene.entities.EntityMap;
import ch.kerbtier.helene.exceptions.WrongFieldTypeException;

public class HObjectModifiableNode implements ModifiableNode {
  
  private EntitySubject subject;
  private EntityMap entityMap;
  private Map<String, Object> data = new HashMap<>();

  
  public HObjectModifiableNode(EntitySubject subject, EntityMap entityMap) {
    this.subject = subject;
    this.entityMap = entityMap;
  }

  @Override
  public HObject commit() {
    return subject.create(data);
  }

  @Override
  public void set(String name, Date value) {
    set(name, (Object)value);
  }

  @Override
  public void set(String name, String value) {
    set(name, (Object)value);
  }

  @Override
  public void set(String name, BigInteger value) {
    set(name, (Object)value);
  }

  @Override
  public void set(String name, HeleneUser value) {
    set(name, (Object) value);
  }

  @Override
  public void set(String name, HSlug value) {
    set(name, (Object)value);
  }

  @Override
  public void set(String name, ByteBuffer value) {
    set(name, (Object)value);
  }

  public void set(String name, Object value) {
    
    value = ValueConverters.convert(value);
    
    
    Entity entity = entityMap.get(name);
    
    if(value == null) {
      data.remove(name);
    } else if(!entity.is(value.getClass())) {
      throw new WrongFieldTypeException("field '" + name + "' is not of type '" + value.getClass() + "'");
    }
    data.put(name, value);
  }

}
