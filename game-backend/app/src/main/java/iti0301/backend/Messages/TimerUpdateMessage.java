package iti0301.backend.Messages;

public class TimerUpdateMessage {
    private final int currentTimerValue;

    public TimerUpdateMessage(int currentTimerValue) {
        this.currentTimerValue = currentTimerValue;
    }


    public TimerUpdateMessage() {
        currentTimerValue = 0;
    }


    public int getCurrentTimerValue() {
        return currentTimerValue;
    }
}
