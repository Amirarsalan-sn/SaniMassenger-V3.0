package Model;

public class UnfollowMessage extends Message{
    public static final long serialVersionUID = 307L;

    public final String uName;

    public UnfollowMessage(String uName) {
        this.uName = uName;
    }
}
