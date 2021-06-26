package Model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class Post implements Serializable {
    public static final long serialVersionUID = 5L ;

    public final String author ;
    public final String title ;
    public final String context ;
    public final LocalDateTime localDateTime = LocalDateTime.now();
    public final String date = now() ;
    private AtomicInteger likes = new AtomicInteger(0);
    private AtomicInteger reposts = new AtomicInteger(0);
    private ArrayList<Comment> comments ;

    public Post(String author, String title, String context) {
        this.author = author;
        this.title = title;
        this.context = context;
    }


    private String now() {
        String result = localDateTime.toString();
        result = result.replaceAll("-" , "/");
        result = result.replaceAll(":\\d\\d\\.\\d+" , "");
        result = result.replaceAll("[A-Z]" , " ");
        return result;
    }

    public int getLike() {
        return likes.get();
    }

    public int getRepost()
    {
        return reposts.get();
    }

    public void like() {
        likes.addAndGet(1);
    }

    public void repost() {
        reposts.addAndGet(1);
    }
}
