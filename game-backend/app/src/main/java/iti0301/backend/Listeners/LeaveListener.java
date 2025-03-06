package iti0301.backend.Listeners;

import java.util.Optional;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import iti0301.backend.Messages.DisconnectMessage;
import iti0301.backend.Models.ServersidePlayer;
import iti0301.backend.Handlers.PlayerHandler;
import iti0301.backend.Helpers.SmartConnection;

public class LeaveListener extends Listener {
    @Override
    public void disconnected(Connection connection) {
        super.disconnected(connection);
        SmartConnection myConnection = new SmartConnection(connection);

        final Optional<ServersidePlayer> serversidePlayer = PlayerHandler.INSTANCE.getPlayerByConnection(connection);

        // System.out.println(PlayerHandler.INSTANCE.getPlayers().stream().map(p -> p.getConnection().getID()).toList());

        if (serversidePlayer.isPresent()) {
            DisconnectMessage disMsg = new DisconnectMessage(String.valueOf(myConnection.getConnection().getID()));
            myConnection.broadcastToOtherExceptSpecifiedConnection(PlayerHandler.INSTANCE.getPlayers(), disMsg);
            PlayerHandler.INSTANCE.removePlayer(serversidePlayer.get());
            System.out.println("Player with username: " + serversidePlayer.get().getUsername() + " was disconnected and removed from list of all players.");
            return;
        }

        System.out.println("Error removing player, player not set. Still closed connection. Closed Connection ID: " + connection.getID());
    }
}
