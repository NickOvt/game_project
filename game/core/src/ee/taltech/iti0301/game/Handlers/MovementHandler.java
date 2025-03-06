package ee.taltech.iti0301.game.Handlers;

import com.esotericsoftware.kryonet.Client;

import ee.taltech.iti0301.game.Character.MoveDirection;
import ee.taltech.iti0301.game.Messages.MoveUpdateEvent;

public class MovementHandler {
    /**
     * Move the character by specified moveX and moveY amount. Sens the movement request to the server
     * @param client
     * @param moveX
     * @param moveY
     * @param moveDirection
     */
    public static void moveCharacter(Client client, float moveX, float moveY, MoveDirection moveDirection) {
        MoveUpdateEvent moveUpdateEvent = new MoveUpdateEvent();
        moveUpdateEvent.setNewX(moveX);
        moveUpdateEvent.setNewY(moveY);
        moveUpdateEvent.setMoveDirection(moveDirection);
        client.sendTCP(moveUpdateEvent);
    }
}
