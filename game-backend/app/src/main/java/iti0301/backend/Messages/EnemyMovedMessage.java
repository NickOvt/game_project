package iti0301.backend.Messages;

public class EnemyMovedMessage {
    private float x;
    private float y;

    public EnemyMovedMessage() {
    }

    public EnemyMovedMessage(float x, float y) {
        this.x = x;
        this.y = y;
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

}
