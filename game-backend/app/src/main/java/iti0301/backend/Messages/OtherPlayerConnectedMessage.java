package iti0301.backend.Messages;

public class OtherPlayerConnectedMessage {
    final private String connID;

    public OtherPlayerConnectedMessage() {
        connID = "";
    }

    public OtherPlayerConnectedMessage(int connID) {
        this.connID = String.valueOf(connID);
    }

    public String getConnID() {
        return connID;
    }
}
