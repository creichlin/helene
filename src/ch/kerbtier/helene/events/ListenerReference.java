package ch.kerbtier.helene.events;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

public class ListenerReference {
  private Runnable runnable;
  
  private static List<Runnable> forever = new ArrayList<>();
  private static Map<Object, List<Runnable>> forInstance = new WeakHashMap<>();
  
  public ListenerReference(Runnable runnable) {
    this.runnable = runnable;
  }

  public ListenerReference keepFor(Object instance) {
    if(!forInstance.containsKey(instance)) {
      synchronized(forInstance) {
        if(!forInstance.containsKey(instance)) {
          forInstance.put(instance, new ArrayList<Runnable>());
        }
      }
    }
    forInstance.get(instance).add(runnable);
    return this;
  }
}
