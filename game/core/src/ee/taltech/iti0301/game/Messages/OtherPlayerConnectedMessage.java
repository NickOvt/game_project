package ee.taltech.iti0301.game.Messages;

public class OtherPlayerConnectedMessage {
    final private String connID;

    /**
     * Empty otherPlayerConnectedMessage
     */
    public OtherPlayerConnectedMessage() {
        connID = "";
    }

    /**
     * OtherPlayerConnectedMessage that has the connection ID of newly connected
     * other player.
     * @param connID
     */
    public OtherPlayerConnectedMessage(int connID) {
        this.connID = String.valueOf(connID);
    }

    public String getConnID() {
        return connID;
    }
}
