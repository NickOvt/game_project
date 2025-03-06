package iti0301.backend.Helpers;

import java.util.LinkedList;

import com.esotericsoftware.kryonet.Connection;

import iti0301.backend.Models.ServersidePlayer;

public class SmartConnection {
    private Connection connection;

    public SmartConnection(Connection connection) {
        this.connection = connection;
    }

    public Connection getConnection() {
        return connection;
    }

    public void broadcastToOtherExceptSpecifiedConnection(LinkedList<ServersidePlayer> allPlayers, Object response) {
        for (ServersidePlayer currPlayer : allPlayers) {
            if (currPlayer.getConnection().getID() != this.connection.getID()) {
                currPlayer.getConnection().sendTCP(response);
            }
        }
    }
}
