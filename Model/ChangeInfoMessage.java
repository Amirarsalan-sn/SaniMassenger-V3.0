package Model;
/** if a field was equal to "" it should not change the person's property */
public class ChangeInfoMessage extends Message{
    public static final long serialVersionUID = 305L;

    public final String name ;
    public final String birthDate ;

    public ChangeInfoMessage(String name, String birthDate) {
        this.name = name;
        this.birthDate = birthDate;
    }
}
