package iti0301.backend.Helpers.Runnables;

import iti0301.backend.Helpers.ConnectionHelpers;
import iti0301.backend.Messages.TimerUpdateMessage;

public class TimerUpdateMessageRunnable implements Runnable {
    private int currentTimerValue;

    public TimerUpdateMessageRunnable(int period) {
        this.currentTimerValue = period;
    }

    @Override
    public void run() {
        // System.out.println(currentTimerValue);
        TimerUpdateMessage timerUpdateMessage = new TimerUpdateMessage(currentTimerValue);
        ConnectionHelpers.sendMessageToAllPlayers(timerUpdateMessage);
        currentTimerValue--;
        // perform the task
        // loop all players and send them TimerUpdate message so that they can update
        // their timer
    }
}
