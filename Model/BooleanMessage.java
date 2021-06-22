package Model;

public class BooleanMessage extends Message{
    public static final long serialVersionUID = 35L ;

    public final boolean value ;

    public BooleanMessage(boolean value) {
        this.value = value;
    }
}
