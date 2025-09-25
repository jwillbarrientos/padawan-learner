package enthuwareOcp;

import static java.util.concurrent.TimeUnit.*;
import java.util.concurrent.*;

class BeeperControl {
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    public void beepForAnHour() {
        //print beep every 10 seconds with an initial delay of 10 seconds too
        final ScheduledFuture<?> beeperHandle = scheduler.scheduleAtFixedRate(() -> System.out.println("beep"), 10, 10, SECONDS);
        //cancel after 1 hour
        scheduler.schedule(() -> beeperHandle.cancel(true), 60 * 60, SECONDS);
    }

    public void someMethod() {
        boolean enabled = false;
        assert enabled = true;
        assert enabled;
    }

    public static void main(String args[]) {
        new BeeperControl().beepForAnHour();
    }
}