package iti0301.backend.Helpers;

import java.util.LinkedList;

import com.esotericsoftware.kryonet.Connection;

import iti0301.backend.Handlers.PlayerHandler;
import iti0301.backend.Models.ServersidePlayer;

public class ConnectionHelpers {
    public static void broadcastToOtherExceptSpecifiedConnection(Connection connection,
            LinkedList<ServersidePlayer> allPlayers, Object response) {
        for (ServersidePlayer currPlayer : allPlayers) {
            if (currPlayer.getConnection().getID() != connection.getID()) {
                currPlayer.getConnection().sendTCP(response);
            }
        }
    }

    public static void sendMessageToAllPlayers(Object message) {
        for (ServersidePlayer currPlayer : PlayerHandler.INSTANCE.getPlayers()) {
            currPlayer.getConnection().sendTCP(message);
        }
    }
}
