package iti0301.backend.Models;

public class Player {
    final private String connID;
    final private float x;
    final private float y;

    public Player(String connID, float x, float y) {
        this.connID = connID;
        this.x = x;
        this.y = y;
    }

    public String getConnId() {
        return this.connID;
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

}
