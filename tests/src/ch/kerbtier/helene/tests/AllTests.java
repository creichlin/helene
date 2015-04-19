package ch.kerbtier.helene.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ BasicEntity.class, EntityPathGetters.class, BasicStore.class, BasicObjectTests.class, BasicStoreObjectArrays.class, BasicStorePrimitiveArrays.class,
    InvalidTypes.class, BasicEvents.class, Slugs.class, Blob.class })
public class AllTests {
  // body
}
