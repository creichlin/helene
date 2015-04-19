package ch.kerbtier.helene.profile;

import java.util.ArrayList;
import java.util.List;

public class Benchmark {
  private Task run;
  private int repetitions;

  private List<Long> times = new ArrayList<>();

  public Benchmark(Task run, int repetitions) {
    this.run = run;
    this.repetitions = repetitions;

    run();
  }

  public void run() {

    for (int cnt = 0; cnt < repetitions; cnt++) {
      run.setup();
      long time = System.nanoTime();
      run.run();
      time = System.nanoTime() - time;
      times.add(time);
    }

    for (long ns : times) {
      System.out.println(run.getName() + " > " + (ns / 1000000 / repetitions) + "ms");
    }
  }
}
