package iti0301.backend.Messages;

public class DisconnectMessage {
    final private String connID;

    public DisconnectMessage() {
        connID = "";
    }

    public DisconnectMessage(String connID) {
        this.connID = connID;
    }

    public String getConnId() {
        return connID;
    }
}
