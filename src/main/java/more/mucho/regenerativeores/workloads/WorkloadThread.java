package more.mucho.regenerativeores.workloads;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.ConcurrentLinkedDeque;

public class WorkloadThread implements Runnable {

    //1s = 1000
    //1 tick = 50 millis;
    private static final double MAX_MILLIS_PER_TICK = 20;
    private static final int MAX_NANOS_PER_TICK = (int) (MAX_MILLIS_PER_TICK * 1E6);

    private final ConcurrentLinkedDeque<Workload> workloadDeque = new ConcurrentLinkedDeque<>();

    public void addWorkload(Workload workload) {
        this.workloadDeque.add(workload);
    }

    @Override
    public void run() {
        long stopTime = System.nanoTime() + MAX_NANOS_PER_TICK;
        Workload nextLoad;
        // Note: Don't permute the conditions because sometimes the time will be over but the queue will still be polled then.
        while (System.nanoTime() <= stopTime && (nextLoad = this.workloadDeque.poll()) != null) {
            nextLoad.compute();
        }
    }

}