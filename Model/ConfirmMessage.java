package Model;

public class ConfirmMessage extends Message {
    public static final long serialVersionUID = 34L;
    public final String message ;

    public ConfirmMessage(String message) {
        this.message = message;
    }
}
