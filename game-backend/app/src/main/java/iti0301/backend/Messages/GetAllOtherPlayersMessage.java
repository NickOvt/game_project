package iti0301.backend.Messages;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import iti0301.backend.Models.Player;
import iti0301.backend.Models.ServersidePlayer;

public class GetAllOtherPlayersMessage {
    final private List<Player> otherPlayers = new ArrayList<>();

    public GetAllOtherPlayersMessage(LinkedList<ServersidePlayer> allPlayers) {
        for (ServersidePlayer player : allPlayers) {
            otherPlayers.add(new Player(String.valueOf(player.getConnection().getID()), player.getX(), player.getY()));
        }
    }

    /**
     * Empty getAllOtherPlayersMessage
     */
    public GetAllOtherPlayersMessage() {

    }

    /**
     * Get all other players
     * 
     * @return other players as a list
     */
    public List<Player> getAllOtherPlayers() {
        return otherPlayers;
    }
}
