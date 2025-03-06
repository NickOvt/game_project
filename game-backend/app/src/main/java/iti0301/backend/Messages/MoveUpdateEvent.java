package iti0301.backend.Messages;

import iti0301.backend.Models.ServersidePlayer.MoveDirection;

public class MoveUpdateEvent {
    private float newX;
    private float newY;
    private MoveDirection moveDirection;

    public MoveUpdateEvent() {

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

    public void setMoveDirection(MoveDirection moveDirection) {
        this.moveDirection = moveDirection;
    }

    public MoveDirection getMoveDirection() {
        return moveDirection;
    }
}
