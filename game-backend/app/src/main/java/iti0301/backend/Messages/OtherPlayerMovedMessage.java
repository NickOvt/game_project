package iti0301.backend.Messages;

import iti0301.backend.Models.ServersidePlayer.MoveDirection;

public class OtherPlayerMovedMessage {
    private float newX;
    private float newY;
    final private String connID;
    private MoveDirection moveDirection;

    public OtherPlayerMovedMessage(String connID) {
        this.connID = connID;
    }

    public OtherPlayerMovedMessage() {
        this.connID = "";
    }

    public float getNewX() {
        return this.newX;
    }

    public void setNewX(float newX) {
        this.newX = newX;
    }

    public float getNewY() {
        return this.newY;
    }

    public void setNewY(float newY) {
        this.newY = newY;
    }

    public String getConnID() {
        return connID;
    }

    public void setMoveDirection(MoveDirection moveDirection) {
        this.moveDirection = moveDirection;
    }

    public MoveDirection getMoveDirection() {
        return moveDirection;
    }
}
