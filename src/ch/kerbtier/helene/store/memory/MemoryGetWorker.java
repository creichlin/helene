package ch.kerbtier.helene.store.memory;

import ch.kerbtier.helene.HList;
import ch.kerbtier.helene.HObject;
import ch.kerbtier.helene.Types;
import ch.kerbtier.helene.def.GetOperation;
import ch.kerbtier.helene.entities.Entity;
import ch.kerbtier.helene.entities.EntityList;
import ch.kerbtier.helene.entities.EntityMap;
import ch.kerbtier.helene.exceptions.WrongFieldDataException;

public class MemoryGetWorker implements GetOperation.Worker {

  private MemoryHObject subject;

  public MemoryGetWorker(MemoryHObject subject) {
    this.subject = subject;
  }

  @Override
  public <X> HList<X> byList(String name, Class<X> expected, EntityList def) {
    Object value = subject.data.get(name);
    if (value != null) {
      if (Types.LIST.isAssignableFrom(value.getClass())) {
        return (HList<X>) value;
      } else {
        throw new WrongFieldDataException("invalid datatype in field '" + name + "' found '" + value.getClass()
            + "' instead of [" + expected.getName() + "]");
      }
    } else {
      HList<X> hl = null;
      if (HObject.class.isAssignableFrom(def.get().isOf())) {
        hl = (HList<X>)new MemoryHObjectList(subject.getStore(), subject, def.getObject());
      } else if (HList.class.isAssignableFrom(def.get().isOf())) {
        return null; // TODO lists of lists not supported yet
      } else {
        hl = new MemoryHList<>(subject.getStore(), subject, def.get());
      }
      
      subject.data.put(name, hl);
      return (HList<X>) subject.data.get(name);
    }
  }

  @Override
  public <X> X byObject(String name, EntityMap def) {
    Object value = subject.data.get(name);
    if (value != null) {
      if (def.is(value.getClass())) {
        return (X) value;
      } else {
        throw new WrongFieldDataException("data value is wrong type");
      }
    } else {
      subject.data.put(name, new MemoryHObject(subject.getStore(), subject, def));
      return (X) subject.data.get(name);
    }
  }

  @Override
  public <X> X byValue(String name, Class<X> expected, Entity def) {
    Object value = subject.data.get(name);
    if (value != null) {
      if (def.is(value.getClass())) {
        return (X) value;
      } else {
        throw new WrongFieldDataException("data value is wrong type");
      }
    } else {
      return null; // TODO default
    }
  }

}
