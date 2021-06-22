package Model;

public class ErrorMessage extends Message {
    public static final long serialVersionUID = 33L;

    public final  String message ;

    public ErrorMessage(String message) {
        this.message = message;
    }
}
