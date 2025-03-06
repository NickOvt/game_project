package ee.taltech.iti0301.game.Messages;

public class DisconnectMessage {
    final private String connID;

    /**
     * Defaul empty constructor
     */
    public DisconnectMessage() {
        connID = "";
    }

    /**
     * Disconnect message constructor. Holds the connection ID of the player that
     * disconnected.
     * 
     * @param connID
     */
    public DisconnectMessage(String connID) {
        this.connID = connID;
    }

    public String getConnID() {
        return connID;
    }
}
