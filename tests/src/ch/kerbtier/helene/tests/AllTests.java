package ch.kerbtier.helene.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ BasicEntity.class, BasicStore.class, BasicStoreObjectArrays.class, BasicStorePrimitiveArrays.class,
    InvalidTypes.class, BasicEvents.class })
public class AllTests {
  // body
}
