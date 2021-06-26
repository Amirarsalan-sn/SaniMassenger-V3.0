package Model;


import java.time.LocalDateTime;

public class LikeMessage extends Message {
    public static final long serialVersionUID = 39L;

    public final String author ;
    public final LocalDateTime localDateTime ;

    public LikeMessage(String author, LocalDateTime localDateTime) {
        this.author = author;
        this.localDateTime = localDateTime;
    }
}
