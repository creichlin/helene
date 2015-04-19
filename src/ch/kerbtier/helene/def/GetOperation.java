package ch.kerbtier.helene.def;

import static ch.kerbtier.helene.Types.*;
import ch.kerbtier.helene.HList;
import ch.kerbtier.helene.HObject;
import ch.kerbtier.helene.HSlug;
import ch.kerbtier.helene.Store;
import ch.kerbtier.helene.entities.Entity;
import ch.kerbtier.helene.entities.EntityList;
import ch.kerbtier.helene.entities.EntityMap;
import ch.kerbtier.helene.exceptions.WrongFieldTypeException;

public class GetOperation {

  public static <X, Y> X get(Store store, String name, Class<X> expected, Class<Y> subExpected, EntityMap def,
      Worker worker) {
    if (name.startsWith("#")) { // its a slug

      if (HObject.class.isAssignableFrom(expected) || expected == null) {
        return (X) store.get(new HSlug(name));
      } else {
        throw new WrongFieldTypeException("Slug return type is always HObject");
      }
    } else {
      Entity entity = def.get(name);
      if (expected == null || entity.is(expected)) {
        if (entity.is(LIST)) {
          Entity subType = ((EntityList) entity).get();
          if (subExpected == null || subType.is(subExpected)) {
            return (X)worker.byList(name, subExpected, (EntityList) entity);
          } else {
            throw new WrongFieldTypeException("field '" + name + "' is of type '[" + subType + "]' instead of '["
                + subExpected.getName() + "]'");
          }

        } else if (entity.is(OBJECT)) {

          return worker.byObject(name, (EntityMap) entity);
        } else {
          return worker.byValue(name, expected, entity);
        }

      }

      throw new WrongFieldTypeException("field '" + name + "' is of type '" + entity + "' instead of '"
          + expected.getName() + "'");
    }
  }

  public interface Worker {
    public <X> HList<X> byList(String name, Class<X> subType, EntityList def);

    public <X> X byObject(String name, EntityMap def);

    public <X> X byValue(String name, Class<X> expected, Entity def);
  }
}
