package iti0301.backend.Listeners;

import java.util.Optional;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import iti0301.backend.Messages.MoveUpdateEvent;
import iti0301.backend.Messages.OtherPlayerMovedMessage;
import iti0301.backend.Models.ServersidePlayer;
import iti0301.backend.Handlers.PlayerHandler;
import iti0301.backend.Helpers.ConnectionHelpers;

public class MoveListener extends Listener {

    @Override
    public void received(Connection connection, Object object) {
        // super.received(connection, object);

        if (object instanceof MoveUpdateEvent) {
            final Optional<ServersidePlayer> serversidePlayer = PlayerHandler.INSTANCE.getPlayerByConnection(connection);

            final MoveUpdateEvent moveUpdateEvent = (MoveUpdateEvent) object;

            if (serversidePlayer.isPresent()) {
                ServersidePlayer player = serversidePlayer.get();
                // System.out.println("Received move update for charachter position");
                // System.out.println("Player name: " + player.getUsername() + " | " + player.getConnection().getID());
                // System.out.println(player.getUsername() + " pos -> X: " + player.getX() + " | Y: " + player.getY());

                player.setX(player.getX() + moveUpdateEvent.getNewX());
                player.setY(player.getY() + moveUpdateEvent.getNewY());

                connection.sendTCP(moveUpdateEvent);

                final OtherPlayerMovedMessage otherPlayerMovedMessage = new OtherPlayerMovedMessage(String.valueOf(connection.getID()));
                otherPlayerMovedMessage.setNewX(player.getX());
                otherPlayerMovedMessage.setNewY(player.getY());
                otherPlayerMovedMessage.setMoveDirection(moveUpdateEvent.getMoveDirection());

                ConnectionHelpers.broadcastToOtherExceptSpecifiedConnection(connection, PlayerHandler.INSTANCE.getPlayers(), otherPlayerMovedMessage);
                // System.out.println("Sent out the movement to: " + player.getUsername());
            }
        }
    }
}
