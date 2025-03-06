package ee.taltech.iti0301.game.Listeners;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import ee.taltech.iti0301.game.Character;
import ee.taltech.iti0301.game.Messages.ConnectResponse;
import ee.taltech.iti0301.game.Messages.GetAllOtherPlayersMessage;
import ee.taltech.iti0301.game.Messages.OtherPlayerConnectedMessage;
import ee.taltech.iti0301.game.Messages.ConnectResponse.Type;
import ee.taltech.iti0301.game.Models.Player;

public class JoinListener extends Listener {
    HashMap<String, Character> players;

    /**
     * Join listener constructor
     * Used to listen for joins of other players.
     * 
     * @param players
     */
    public JoinListener(HashMap<String, Character> players) {
        this.players = players;
    }

    @Override
    public void received(Connection connection, Object object) {

        if (object instanceof ConnectResponse) {
            /**
             * Basic connection. When received it means that this instance of the client has
             * successfully sent a connection message to the server
             * and is ready to receive next messages.
             */
            ConnectResponse response = (ConnectResponse) object;
            if (response.getType().equals(Type.SUCCESS)) {
                // Continue
                // Character character = new Character(new Vector2(70, 20));
                // players.put(String.valueOf(connection.getID()), new
                // ArrayList<>(Arrays.asList(70, 20)));
                // System.out.println("Other player connected!");

            } else {
                connection.close();
                System.out.println(response.getMessage());
                // System.exit(0);
            }
        } else if (object instanceof GetAllOtherPlayersMessage) {
            /**
             * Got the message containing data about all the OTHER players currently connected to the server.
             * Update local HashMap of players accordingly.
             */
            System.out.println("GET ALL OTHER PLAYERS!");
            final GetAllOtherPlayersMessage otherPlayersMessage = (GetAllOtherPlayersMessage) object;
            System.out.println(otherPlayersMessage.getAllOtherPlayers());

            Gdx.app.postRunnable(new Runnable() {
                public void run() {
                    for (Player player : otherPlayersMessage.getAllOtherPlayers()) {
                        System.out.println(player.getX());
                        System.out.println(player.getY());
                        players.put(player.getConnID(), new Character(new Vector2(player.getX(), player.getY())));
                        // players.put(player.getConnID(), new
                        // ArrayList<>(Arrays.asList(Math.round(player.getX()),
                        // Math.round(player.getY()))));
                    }
                }
            });
        } else if (object instanceof OtherPlayerConnectedMessage) {
            /**
             * Got a message that contains data about single new connected user.
             */
            final OtherPlayerConnectedMessage otherPlayerConnectedMessage = (OtherPlayerConnectedMessage) object;

            Gdx.app.postRunnable(new Runnable() {
                public void run() {
                    players.put(otherPlayerConnectedMessage.getConnID(),
                            new Character(new Vector2(1280 / 2f, 720 / 2f)));
                    // players.put(otherPlayerConnectedMessage.getConnID(), new
                    // ArrayList<>(Arrays.asList(1280 / 2, 720 / 2)));
                }
            });
            System.out.println("Other player connected!");
        }
    }
}
