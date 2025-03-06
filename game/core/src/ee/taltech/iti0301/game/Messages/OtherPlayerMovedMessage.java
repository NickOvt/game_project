package ee.taltech.iti0301.game.Messages;

import ee.taltech.iti0301.game.Character.MoveDirection;

public class OtherPlayerMovedMessage {
    private float newX;
    private float newY;
    final private String connID;
    private MoveDirection moveDirection;

    /**
     * Empty constructor
     */
    public OtherPlayerMovedMessage() {
        this.connID = "";
    }

    /**
     * OtherPlayerMovedMessage containing the connection ID and data of the movement
     * of other connected player.
     * @param connID
     */
    public OtherPlayerMovedMessage(String connID) {
        this.connID = connID;
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
