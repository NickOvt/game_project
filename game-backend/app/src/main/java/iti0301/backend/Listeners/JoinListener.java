package iti0301.backend.Listeners;

import java.time.LocalDateTime;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import iti0301.backend.Messages.ConnectRequest;
import iti0301.backend.Messages.ConnectResponse;
import iti0301.backend.Messages.GetAllOtherPlayersMessage;
import iti0301.backend.Messages.OtherPlayerConnectedMessage;
import iti0301.backend.Messages.ConnectResponse.Type;
import iti0301.backend.Models.ServersidePlayer;
import iti0301.backend.Handlers.PlayerHandler;
import iti0301.backend.Helpers.TimerHelper;

public class JoinListener extends Listener {
    /**
     * Listener for new joins of players. (Username must be not taken)
     */
    @Override
    public void received(Connection connection, Object object) {
        // Player joins

        if (object instanceof ConnectRequest) {
            final ConnectRequest connectRequest = (ConnectRequest) object;

            String username = connectRequest.getPlayerName();

            final ServersidePlayer serversidePlayer = new ServersidePlayer(username, connection);
            serversidePlayer.setX(connectRequest.getX());
            serversidePlayer.setY(connectRequest.getY());

            // Check if such username is not in use, in that case, fail the joining
            if (PlayerHandler.INSTANCE.getPlayerByUsername(username).isPresent()) {
                final ConnectResponse connectResponse = new ConnectResponse(
                        "There was an error connecting! This username is already in use.", Type.FAIL);
                connection.sendTCP(connectResponse);
                return;
            }

            // final ConnectResponse connectResponse = new ConnectResponse("Successfully
            // connected!", Type.SUCCESS);
            final OtherPlayerConnectedMessage otherPlayerConnectedMessage = new OtherPlayerConnectedMessage(
                    connection.getID());

            // connection.sendTCP(connectResponse);
            for (ServersidePlayer currPlayer : PlayerHandler.INSTANCE.getPlayers()) {
                if (currPlayer.getConnection().getID() != connection.getID()) {
                    // currPlayer.getConnection().sendTCP(connectResponse);
                    currPlayer.getConnection().sendTCP(otherPlayerConnectedMessage);
                }
            }

            GetAllOtherPlayersMessage getAllOtherPlayersMessage = new GetAllOtherPlayersMessage(
                    PlayerHandler.INSTANCE.getPlayers());

            serversidePlayer.getConnection().sendTCP(getAllOtherPlayersMessage);

            PlayerHandler.INSTANCE.addPlayer(serversidePlayer);

            System.out.println("Added new user: " + serversidePlayer.getUsername() + " with connection ID: "
                    + serversidePlayer.getConnection().getID() + " date: " + LocalDateTime.now());

            if (!TimerHelper.getIsEnemyTimerOn()) {
                TimerHelper.startTimerForEnemySpawn("Timer until enemy spawns", 10);
            }

            System.out.println("Sent sucessful message back to user: " +
                    serversidePlayer.getUsername());
        }

        // super.received(connection, object);
    }
}
