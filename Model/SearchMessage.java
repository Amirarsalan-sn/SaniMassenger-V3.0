package Model;

import ServerSide.Person;

public class SearchMessage extends Message{
    public static final long serialVersionUID = 308L;

    public final String uName;
    public final Person result ;

    public SearchMessage(String uName, Person result) {
        this.uName = uName;
        this.result = result;
    }
}
