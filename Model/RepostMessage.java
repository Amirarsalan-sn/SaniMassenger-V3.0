package Model;

import java.time.LocalDateTime;

public class RepostMessage extends Message  {
    public static final long serialVersionUID = 38L;

    public final String author ;
    public final LocalDateTime localDateTime ;

    public RepostMessage(String author, LocalDateTime localDateTime) {
        this.author = author;
        this.localDateTime = localDateTime;
    }
}
