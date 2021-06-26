package Model;

import java.time.LocalDateTime;

public class SendCommentMessage extends Message{
    public static final long serialVersionUID = 303L;

    public final Comment comment ;
    public final String author ;
    public final LocalDateTime localDateTime;

    public SendCommentMessage(Comment comment, String author, LocalDateTime localDateTime) {
        this.comment = comment;
        this.author = author;
        this.localDateTime = localDateTime;
    }
}
