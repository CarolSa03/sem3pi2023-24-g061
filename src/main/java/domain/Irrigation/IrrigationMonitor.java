package domain.Irrigation;

import BDDAD.dataAccess.repositories.Repositories;
import LAPR3.ui.WriteInFieldbookUI;
import utils.Utils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class IrrigationMonitor implements Runnable {
    private final IrrigationSystem irrigationSystem;
    private final Iterator<LocalDateTime> scheduleIterator;
    private ScheduledExecutorService scheduler;
    private LocalDateTime lastExecutionDateTime;

    public IrrigationMonitor(List<LocalDateTime> scheduleTimes) {
        this.irrigationSystem = getIrrigationSystem();
//        this.irrigationSystem = new IrrigationSystem();
        this.scheduleIterator = scheduleTimes.iterator();
        this.scheduler = Executors.newScheduledThreadPool(1);
        this.lastExecutionDateTime = LocalDateTime.now().minusDays(1);
    }

    private IrrigationSystem getIrrigationSystem() {
        if (irrigationSystem == null) {
            return Repositories.getInstance().getIrrigationSystemRepository().getIrrigationSystem();
        }
        return irrigationSystem;
    }

    @Override
    public void run() {
        scheduleNextTask();
    }

    private void scheduleNextTask() {
        if (scheduler == null || scheduler.isShutdown()) {
            scheduler = Executors.newScheduledThreadPool(1);
        }
        if (scheduleIterator.hasNext()) {
            LocalDateTime time = scheduleIterator.next();
            long delay = Math.max(Duration.between(LocalDateTime.of(Utils.getCurrentDateToDay(), Utils.getCurrentTimeToMinutes()), time).toMinutes(), 0);
            scheduler.schedule(() -> {
                try {
                    executeTask();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }, delay, TimeUnit.MINUTES);
        }
    }

    private void executeTask() throws InterruptedException {
        if (irrigationSystem.endOfIrrigation()) {
            LocalDateTime now = LocalDateTime.of(Utils.getCurrentDateToDay(), Utils.getCurrentTimeToMinutes());
            if (!Utils.dateAndTimeEquals(now, lastExecutionDateTime)) {
                new WriteInFieldbookUI().run();
                lastExecutionDateTime = now;
                stopScheduler();
            }
        }
        scheduleNextTask();
    }

    public void stopScheduler() {
        if (scheduler != null) {
            scheduler.shutdown();
            scheduler = Executors.newScheduledThreadPool(1);
            scheduler.schedule(this, 60, TimeUnit.SECONDS);
        }
    }

    public void shutDown() {
        if (scheduler != null) {
            scheduler.shutdownNow();
        }
    }

}
