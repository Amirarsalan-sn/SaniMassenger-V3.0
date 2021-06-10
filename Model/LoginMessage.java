package Model;

import java.io.Serializable;

public class LoginMessage implements Message , Serializable {
    public final String  Uname ;
    public final String Pass ;

    public LoginMessage(String uname, String pass) {
        Uname = uname;
        Pass = pass;
    }
}
