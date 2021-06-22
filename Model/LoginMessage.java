package Model;

public class LoginMessage extends Message  {
    public static final long serialVersionUID = 31L ;

    public final String  Uname ;
    public final String Pass ;

    public LoginMessage(String uname, String pass) {
        Uname = uname;
        Pass = pass;
    }
}
