package Model;

import ServerSide.Person;

public class ProfileMessage extends Message{
    public static final long serialVersionUID = 301L ;

    public final String uname ;
    public final Person profile ;

    public ProfileMessage(String uname, Person profile) {
        this.uname = uname;
        this.profile = profile;
    }
}
