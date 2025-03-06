package iti0301.backend.Helpers.Runnables;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import iti0301.backend.Handlers.PlayerHandler;
import iti0301.backend.Helpers.ConnectionHelpers;
import iti0301.backend.Helpers.TimerHelper;
import iti0301.backend.Messages.EnemySpawnedMessage;

public class StopSchedulerRunnable implements Runnable {
    private final ScheduledFuture<?> handle;
    private final ScheduledExecutorService scheduledExecutorService;
    private Object message;

    public StopSchedulerRunnable(ScheduledFuture<?> handle, ScheduledExecutorService scheduledExecutorService) {
        this.handle = handle;
        this.scheduledExecutorService = scheduledExecutorService;
    }

    public StopSchedulerRunnable(ScheduledFuture<?> handle, ScheduledExecutorService scheduledExecutorService,
            Object message) {
        this.handle = handle;
        this.scheduledExecutorService = scheduledExecutorService;
        this.message = message;
    }

    @Override
    public void run() {
        System.out.println("Stop timer / scheduled executor");

        if (message instanceof EnemySpawnedMessage) {
            EnemySpawnedMessage enemySpawnedMessage = (EnemySpawnedMessage) message;
            TimerHelper.setIsEnemyTimerOn(false);
            PlayerHandler.INSTANCE.setEnemy(enemySpawnedMessage.getX(), enemySpawnedMessage.getY());
        }

        if (message != null) {
            ConnectionHelpers.sendMessageToAllPlayers(message);
        }

        handle.cancel(true);
        scheduledExecutorService.shutdown();
    }
}
