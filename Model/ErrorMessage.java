package Model;

public class ErrorMessage implements Message {
    public final  String message ;

    public ErrorMessage(String message) {
        this.message = message;
    }
}
