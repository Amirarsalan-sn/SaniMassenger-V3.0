package Model;

public class SignInMessage extends Message{
    public static final long serialVersionUID = 32L;

    public final String uName;
    public final String passWord ;
    public final String name ;
    public final String lastName ;
    public final String BirthDate ;

    public SignInMessage(String name, String lastName, String birthDate, String uName, String passWord) {
        this.name = name;
        this.lastName = lastName;
        BirthDate = birthDate;
        this.uName = uName;
        this.passWord = passWord;
    }

}
