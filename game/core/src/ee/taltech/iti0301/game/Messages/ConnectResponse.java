package ee.taltech.iti0301.game.Messages;

public class ConnectResponse {
    private String message;
    private Type type;

    /**
     * Empty constructor for Connect Response
     */
    public ConnectResponse() {

    }

    /**
     * Connect response constructor with messaage and type
     * @param message
     * @param type
     */
    public ConnectResponse(String message, Type type) {
        this.message = message;
        this.type = type;
    }

    /**
     * Types of ConnectResponses
     */
    public enum Type {
        SUCCESS, FAIL
    }

    public Type getType() {
        return this.type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
