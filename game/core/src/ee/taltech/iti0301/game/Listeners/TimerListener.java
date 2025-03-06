package ee.taltech.iti0301.game.Listeners;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import ee.taltech.iti0301.game.Hud;
import ee.taltech.iti0301.game.Messages.TimerUpdateMessage;

public class TimerListener extends Listener {
    Hud hud;

    public TimerListener(Hud hud) {
        this.hud = hud;
    }

    @Override
    public void received(Connection connection, Object object) {
        if (object instanceof TimerUpdateMessage) {
            TimerUpdateMessage timerUpdateMessage = (TimerUpdateMessage) object;
            hud.setCurrentTimerValue(timerUpdateMessage.getCurrentTimerValue());
        }
    }
}
