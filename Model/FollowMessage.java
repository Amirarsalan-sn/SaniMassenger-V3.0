package Model;

public class FollowMessage extends Message{
    public static final long serialVersionUID = 306;

    public final String uName ;

    public FollowMessage(String uName) {
        this.uName = uName;
    }
}
