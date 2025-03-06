package ee.taltech.iti0301.game.Listeners;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import ee.taltech.iti0301.game.Character;
import ee.taltech.iti0301.game.Messages.MoveUpdateEvent;
import ee.taltech.iti0301.game.Messages.OtherPlayerMovedMessage;

public class MovementUpdateListener extends Listener {
    private Character character;
    private HashMap<String, Character> otherPlayers;
    private OrthographicCamera camera;

    /**
     * MovementUpdateListener constructor. This listener is used to listen to
     * different movement related messages and update the game accordingly.
     * 
     * @param character
     * @param otherPlayers
     * @param camera
     */
    public MovementUpdateListener(Character character, HashMap<String, Character> otherPlayers,
            OrthographicCamera camera) {
        this.character = character;
        this.otherPlayers = otherPlayers;
        this.camera = camera;
    }

    @Override
    public void received(Connection connection, Object object) {
        if (object instanceof MoveUpdateEvent) {
            /**
             * Got basic moveUpdateEvent message back. Update local player position to the
             * remote (server) one
             */
            final MoveUpdateEvent moveUpdateEvent = (MoveUpdateEvent) object;

            // System.out.println("Received move update for charachter position!");
            // System.out.println(moveUpdateEvent.getNewX());
            // System.out.println(moveUpdateEvent.getNewY());
            // System.out.println("Received move");
            character.oldX = character.getBounds().x;
            character.oldY = character.getBounds().y;
            character.lastMoveX = moveUpdateEvent.getNewX();
            character.lastMoveY = moveUpdateEvent.getNewY();

            Gdx.app.postRunnable(new Runnable() {
                public void run() {
                    character.moveDirection = moveUpdateEvent.getMoveDirection();

                    character.moveToNewPosition(character.getBounds().x + moveUpdateEvent.getNewX(),
                            character.getBounds().y + moveUpdateEvent.getNewY());

                    character.setTextureBasedOnMoveDirection();
                    camera.position.set(character.getBounds().x, character.getBounds().y, 0);
                }
            });
        } else if (object instanceof OtherPlayerMovedMessage) {
            /**
             * Got a message containing data about a movement of another player. Update
             * local HashMap of players accordingly
             */
            System.out.println("OTHER PLAYER MOVED!");
            OtherPlayerMovedMessage otherPlayerMovedMessage = (OtherPlayerMovedMessage) object;

            Character otherCharacter = otherPlayers.get(otherPlayerMovedMessage.getConnID());

            if (otherCharacter != null) {
                otherCharacter.moveDirection = otherPlayerMovedMessage.getMoveDirection();
                otherCharacter.setTextureBasedOnMoveDirection();
                otherCharacter.moveToNewPosition(otherPlayerMovedMessage.getNewX(), otherPlayerMovedMessage.getNewY());
            }
        }
    }
}
