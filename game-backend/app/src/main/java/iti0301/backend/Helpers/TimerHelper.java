package iti0301.backend.Helpers;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import iti0301.backend.Helpers.Runnables.StopSchedulerRunnable;
import iti0301.backend.Helpers.Runnables.TimerUpdateMessageRunnable;
import iti0301.backend.Messages.EnemySpawnedMessage;

public class TimerHelper {
    private static boolean isEnemyTimerOn = false;
    private static final int DEFAULT_DELAY = 0;
    private static final int DEFAULT_PERIOD = 1;

    private static void timerStartedLog(String timerName) {
        System.out.println("Started > " + timerName);
        System.out.println("Timer is working! < " + timerName);
    }

    /**
     * Start timer until the enemy spawns (ticks every second)
     * 
     * @param timerName name of the timer
     * @param time      time for this timer to run for
     */
    public static void startTimerForEnemySpawn(String timerName, int time) {
        System.out.println("Timer for enemy spawn start!");
        // try to do the task
        final ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

        timerStartedLog(timerName);
        TimerUpdateMessageRunnable task = new TimerUpdateMessageRunnable(time);

        final ScheduledFuture<?> enemySpawnHandle = scheduledExecutorService.scheduleAtFixedRate(task, DEFAULT_DELAY,
                DEFAULT_PERIOD,
                TimeUnit.SECONDS);

        isEnemyTimerOn = true;

        scheduledExecutorService.schedule(
                new StopSchedulerRunnable(enemySpawnHandle, scheduledExecutorService, new EnemySpawnedMessage("Enemy spawned!", 1280 / 2, 720 / 2)), time,
                TimeUnit.SECONDS);
    }

    public static boolean getIsEnemyTimerOn() {
        return isEnemyTimerOn;
    }

    public static void setIsEnemyTimerOn(boolean isEnemyTimerOnNew) {
        isEnemyTimerOn = isEnemyTimerOnNew;
    }
}
