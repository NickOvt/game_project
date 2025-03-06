package ee.taltech.iti0301.game.Messages;

public class ConnectRequest {
    private String playerName;
    private float x;
    private float y;

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    /**
     * Empty constructor for a Connect Request
     */
    public ConnectRequest() {

    }

    /**
     * Connect request constructor with playerName
     * @param playerName
     */
    public ConnectRequest(String playerName, float x, float y) {
        this.playerName = playerName;
        this.x = x;
        this.y = y;
    }
}
