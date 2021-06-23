package Model;

import java.time.LocalDateTime;

public class Post {
    public final String author ;
    public final String title ;
    public final String context ;
    public final String date = now() ;
    private int likes ;
    private int reposts ;

    public Post(String author, String title, String context) {
        this.author = author;
        this.title = title;
        this.context = context;
    }

    private String now() {
        LocalDateTime localDateTime = LocalDateTime.now();
        String result = localDateTime.toString();
        result = result.replaceAll("-" , "/");
        result = result.replaceAll(":\\d\\d\\.\\d+" , "");
        result = result.replaceAll("[A-Z]" , " ");
        return result;
    }

    public String getLike() {
        return String.valueOf(likes);
    }

    public String getRepost() {
        return String.valueOf(reposts);
    }

}
