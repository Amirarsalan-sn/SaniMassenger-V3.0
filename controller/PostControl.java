package controller;

import Model.PageLoader;
import Model.Post;
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

    public PostControl(Post post) throws IOException {
        new PageLoader().load("postPage",this);
        this.post = post;
    }

    public AnchorPane init() {
        postTitle.setText(post.title);
        postTitle.setAlignment(Pos.BASELINE_CENTER);
        author.setText(post.author);
        date.setText(post.date);
        postContext.setText(post.context);
        repostNumber.setText(post.getRepost());
        likeNumber.setText(post.getLike());
        return postBase;
    }


    public void repost(ActionEvent actionEvent) {

    }

    public void comment(ActionEvent actionEvent) {

    }

    public void like(ActionEvent actionEvent) {

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
        //send and receive person .
        OtherProfilePage.person = null;
        new PageLoader().load("otherProfilePage");
    }
}
