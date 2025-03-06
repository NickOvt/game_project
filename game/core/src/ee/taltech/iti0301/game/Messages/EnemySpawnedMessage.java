package ee.taltech.iti0301.game.Messages;

public class EnemySpawnedMessage {
    private String message;
    private float x;
    private float y;

    public EnemySpawnedMessage() {

    }

    public EnemySpawnedMessage(String message, float x, float y) {
        this.message = message;
        this.x = x;
        this.y = y;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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
