package ee.taltech.iti0301.game.Messages;

import ee.taltech.iti0301.game.Character.MoveDirection;

public class MoveUpdateEvent {
    private float newX;
    private float newY;
    private MoveDirection moveDirection;

    /**
     * Empty moveUpdateEvet constructor
     */
    public MoveUpdateEvent() {

    }

    public float getNewX() {
        return newX;
    }

    public void setNewX(float newX) {
        this.newX = newX;
    }

    public float getNewY() {
        return newY;
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
