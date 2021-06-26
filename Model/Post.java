package Model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.atomic.AtomicInteger;

public class Post implements Serializable {
    public static final long serialVersionUID = 5L ;

    public final String author ;
    public final String title ;
    public final String context ;
    public final LocalDateTime localDateTime = LocalDateTime.now();
    public final String date = now() ;
    private Set<String> likes = new ConcurrentSkipListSet<>();
    private Set<String> reposts = new ConcurrentSkipListSet<>();
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

    public int getLikes() {
        return likes.size();
    }

    public int getReposts() {
        return reposts.size();
    }

    public void like(String liker) {
        likes.add(liker);
    }

    public void repost(String reposter) {
        reposts.add(reposter);
    }
}
