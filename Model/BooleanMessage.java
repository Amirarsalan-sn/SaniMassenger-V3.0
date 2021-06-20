package Model;

public class BooleanMessage implements Message{
    public final boolean value ;

    public BooleanMessage(boolean value) {
        this.value = value;
    }
}
