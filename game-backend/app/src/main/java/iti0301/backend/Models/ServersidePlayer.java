package iti0301.backend.Models;

import com.esotericsoftware.kryonet.Connection;

public class ServersidePlayer {
    private final String username;
    private final Connection connection;

    private boolean moveUp, moveDown, moveLeft, moveRight;

    private float speed = 1.0F;

    private float x;
    private float y;

    public enum MoveDirection {
        UP, DOWN, LEFT, RIGHT, IDLE
    }

    public ServersidePlayer(String username, Connection connection) {
        this.username = username;
        this.connection = connection;
    }

    // public float getSpeed() {
    // return this.speed;
    // }

    // public void setSpeed(float speed) {
    // this.speed = speed;
    // }

    public String getUsername() {
        return this.username;
    }

    public Connection getConnection() {
        return this.connection;
    }

    public float getX() {
        return this.x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return this.y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public boolean getMoveUp() {
        return this.moveUp;
    }

    public void setMoveUp(boolean moveUp) {
        this.moveUp = moveUp;
    }

    public boolean getMoveDown() {
        return this.moveDown;
    }

    public void setMoveDown(boolean moveDown) {
        this.moveDown = moveDown;
    }

    public boolean getMoveLeft() {
        return this.moveLeft;
    }

    public void setMoveLeft(boolean moveLeft) {
        this.moveLeft = moveLeft;
    }

    public boolean getMoveRight() {
        return this.moveRight;
    }

    public void setMoveRight(boolean moveRight) {
        this.moveRight = moveRight;
    }

}
