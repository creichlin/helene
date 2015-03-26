package ch.kerbtier.helene.events;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class Listeners {
  private final List<WeakReference<Runnable>> listeners = new ArrayList<>();

  public ListenerReference onEvent(Runnable run) {
    listeners.add(new WeakReference<>(run));
    return new ListenerReference(run);
  }

  public void trigger() {
    List<WeakReference<Runnable>> tor = new ArrayList<>();
    for (WeakReference<Runnable> wr : listeners) {
      Runnable r = wr.get();
      if (r != null) {
        r.run();
      } else {
        tor.add(wr);
      }
    }
    tor.removeAll(tor);
  }
}
