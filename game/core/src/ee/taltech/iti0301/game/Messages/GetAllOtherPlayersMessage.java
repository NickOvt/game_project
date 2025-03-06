package ee.taltech.iti0301.game.Messages;

import java.util.ArrayList;
import java.util.List;

import ee.taltech.iti0301.game.Models.Player;

public class GetAllOtherPlayersMessage {
    final private List<Player> otherPlayers = new ArrayList<>();

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
