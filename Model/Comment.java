package Model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Comment implements Serializable {
    public static final long serialVersionUID = 51L;

    public final String author ;
    public final String context ;
    public final LocalDateTime localDateTime = LocalDateTime.now();
    public final String date = now();

    private String now() {
        String result = localDateTime.toString();
        result = result.replaceAll("-" , "/");
        result = result.replaceAll(":\\d\\d\\.\\d+" , "");
        result = result.replaceAll("[A-Z]" , " ");
        return result;
    }

    public Comment(String author, String context) {
        this.author = author;
        this.context = context;
    }
}
