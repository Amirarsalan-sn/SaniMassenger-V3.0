package Model;

import java.io.Serializable;

public class PostRefMessage extends Message implements Serializable {
    public static final long serialVersionUID = 37L;

    public final Post[] posts ;

    public PostRefMessage(Post[] posts) {
        this.posts = posts;
    }
}
