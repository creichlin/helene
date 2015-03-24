package ch.kerbtier.helene;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

public class ListenerReference {
  private Runnable runnable;
  
  private static List<Runnable> forever = new ArrayList<Runnable>();
  private static Map<Object, Runnable> forInstance = new WeakHashMap<Object, Runnable>();
  
  public ListenerReference(Runnable runnable) {
    this.runnable = runnable;
  }

  public ListenerReference keepFor(Object instance) {
    forInstance.put(instance, runnable);
    return this;
  }
}
