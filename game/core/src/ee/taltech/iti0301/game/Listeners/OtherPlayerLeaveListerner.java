package ee.taltech.iti0301.game.Listeners;

import java.util.HashMap;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import ee.taltech.iti0301.game.Character;
import ee.taltech.iti0301.game.Messages.DisconnectMessage;

public class OtherPlayerLeaveListerner extends Listener {
    private HashMap<String, Character> otherPlayers;

    /**
     * OtherPlayerLeaveListener constructor. Used to listen for messages regarding
     * other players leaving.
     * 
     * @param otherPlayers
     */
    public OtherPlayerLeaveListerner(HashMap<String, Character> otherPlayers) {
        this.otherPlayers = otherPlayers;
    }

    @Override
    public void received(Connection connection, Object object) {
        if (object instanceof DisconnectMessage) {
            /**
             * Got message containing data about the player that left. Delete the player
             * (and its light source) from the local player HashMap.
             */
            System.out.println("Other player left!");

            DisconnectMessage disMsg = (DisconnectMessage) object;

            String connID = disMsg.getConnID();

            // System.out.println(connID);
            // System.out.println(otherPlayers);

            Character character = otherPlayers.get(connID);
            character.getLight().remove();

            otherPlayers.remove(connID);
            // System.out.println(otherPlayers);
        }
    }
}
