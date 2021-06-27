package Model;

public class ReConnectMessage extends Message{
    public static final long serialVersionUID = 310L;

    public final String uName;

    public ReConnectMessage(String uName) {
        this.uName = uName;
    }
}
