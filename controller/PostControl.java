package controller;

import Model.*;
import ServerSide.Person;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

import java.awt.event.MouseEvent;
import java.io.IOException;

public class PostControl {

    public Label author;
    public Label postTitle;
    public Button repostButton;
    public Button commentButton;
    public Button likeButton;
    public Label repostNumber;
    public Label likeNumber;
    public Label date;
    public TextArea postContext;
    public AnchorPane postBase;
    private Post post ;

    public  PostControl(Post post) throws IOException {
        new PageLoader().load("postPage",this);
        this.post = post;
    }

    public AnchorPane init() {
        postTitle.setText(post.title);
        postTitle.setAlignment(Pos.BASELINE_CENTER);
        author.setText(post.author);
        date.setText(post.date);
        postContext.setText(post.context);
        repostNumber.setText(String.valueOf(post.getReposts()));
        likeNumber.setText(String.valueOf(post.getLikes()));
        return postBase;
    }


    public void repost(ActionEvent actionEvent) {
        Connection.send(new RepostMessage(Main.uName, post.author , post.localDateTime));
        repostNumber.setText(String.valueOf(post.getReposts()));
    }

    public void comment(ActionEvent actionEvent) throws IOException {
        new PageLoader().load("commentPage" , new CommentPageControl(post));
    }

    public void like(ActionEvent actionEvent) {
        Connection.send(new LikeMessage(Main.uName , post.author , post.localDateTime));
        likeNumber.setText(String.valueOf(post.getLikes()));
    }

    public void cursorToHand(MouseEvent mouseEvent) {
        PageLoader.cursorToHand();
    }

    public void cursorToDefault(MouseEvent mouseEvent) {
        PageLoader.cursorToDefault();
    }

    public void cursorToHand(javafx.scene.input.MouseEvent mouseEvent) {
        PageLoader.cursorToHand();
    }

    public void cursorToDefault(javafx.scene.input.MouseEvent mouseEvent) {
        PageLoader.cursorToDefault();
    }

    public void otherProfileLoad(javafx.scene.input.MouseEvent mouseEvent) throws IOException {
        Connection.send(new ProfileMessage(post.author , null));
        Person profile = ((ProfileMessage) Connection.receive()).profile;
        new PageLoader().load("otherProfilePage" , new OtherProfilePageControl(profile));
    }
}
