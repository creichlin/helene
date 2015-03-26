package ch.kerbtier.helene.events;

import java.util.HashMap;
import java.util.Map;

public class MappedListeners<T> {
  private final Map<T, Listeners> listeners = new HashMap<>();

  public ListenerReference on(T name, Runnable run) {
    if (!listeners.containsKey(name)) {
      listeners.put(name, new Listeners());
    }
    listeners.get(name).onEvent(run);
    return new ListenerReference(run);
  }

  public void trigger(T name) {
    if (listeners.containsKey(name)) {
      listeners.get(name).trigger();
    }
  }
}