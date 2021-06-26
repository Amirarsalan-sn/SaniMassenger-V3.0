package Model;

import java.time.LocalDateTime;

public class CommentRefMessage extends Message{
    public static final long serialVersionUID = 302L;

    public final String author ;
    public final LocalDateTime localDateTime;
    public final Comment[] comments ;

    public CommentRefMessage(String author, LocalDateTime localDateTime, Comment[] comments) {
        this.author = author;
        this.localDateTime = localDateTime;
        this.comments = comments;
    }
}
