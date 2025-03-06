package ee.taltech.iti0301.game.Models;

public class Player {
    final private String connID;
    final private float x;
    final private float y;

    /**
     * Empty constructor
     */
    public Player() {
        connID = "";
        x = 70;
        y = 70;
    }

    /**
     * Serverside player model. Contains Connection ID, x position and y position
     * @param connID
     * @param x
     * @param y
     */
    public Player(String connID, float x, float y) {
        this.connID = connID;
        this.x = x;
        this.y = y;
    }

    public String getConnID() {
        return this.connID;
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

}
