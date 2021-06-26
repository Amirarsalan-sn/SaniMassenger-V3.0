package Model;

public class NewPostMessage extends Message{
    public static final long serialVersionUID = 309L;

    public final Post post;

    public NewPostMessage(Post post) {
        this.post = post;
    }
}
