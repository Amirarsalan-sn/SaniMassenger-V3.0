package ServerSide;

import Model.Post;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Person implements Serializable {
    public static final long serialVersionUID = 4L;

    public final String uname;
    public final String passWord;
    public String name;
    public String birthDate;
    private ArrayList<Post> myPost = new ArrayList<>();
    private ArrayList<String> followerNames = new ArrayList<>();
    private ArrayList<String> followingNames = new ArrayList<>();

    public Person(String uname, String passWord, String name, String birthDate) {
        this.uname = uname;
        this.passWord = passWord;
        this.name = name;
        this.birthDate = birthDate;
    }

    public void addPost(Post post) {
        myPost.add(post);
    }

    public List<Post> getTwoLastPosts() {
        int size = myPost.size();
        return myPost.subList(size-2 , size);
    }

    public void addFollowerNames(String uname) {
        followerNames.add(uname);
    }

    public void addFollowingNames(String uname) {
        followingNames.add(uname);
    }

    public List<String> getFollowerNames() {
        return new ArrayList<>(followerNames);
    }

    public List<String> getFollowingNames() {
        return new ArrayList<>(followingNames);
    }

    public void removeFollowerNames(String uname) {
        followerNames.remove(uname);
    }

    public void removeFollowingNames(String uname) {
        followingNames.remove(uname);
    }

    public int getFollowersNumber(){
        return followerNames.size();
    }

    public int getFollowingNumber() {
        return followingNames.size();
    }

    public List<Post> getPosts() {
        return new ArrayList<>(myPost);
    }

    public Post getPost(LocalDateTime localDateTime) {
        return myPost.stream().filter(post -> post.localDateTime.equals(localDateTime)).findFirst().get();
    }
 }
