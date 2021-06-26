package Model;

import java.time.LocalDateTime;

public class RepostMessage extends Message  {
    public static final long serialVersionUID = 38L;

    public final String reposter ;
    public final String author ;
    public final LocalDateTime localDateTime ;

    public RepostMessage(String reposter, String author, LocalDateTime localDateTime) {
        this.reposter = reposter;
        this.author = author;
        this.localDateTime = localDateTime;
    }
}
