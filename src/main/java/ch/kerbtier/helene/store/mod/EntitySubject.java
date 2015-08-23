package ch.kerbtier.helene.store.mod;

import java.util.Map;

import ch.kerbtier.helene.HObject;

public interface EntitySubject {

  HObject create(Map<String, Object> data);


}
