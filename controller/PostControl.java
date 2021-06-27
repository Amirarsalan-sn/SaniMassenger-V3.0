package controller;

import Model.*;
import ServerSide.Person;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

import java.awt.event.MouseEvent;
import java.io.IOException;

public class PostControl {

    public static final String DISCONNECTED = "You are disconnected from the server .";
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
        if(Connection.send(new RepostMessage(Main.uName, post.author , post.localDateTime)) instanceof ErrorMessage)
            showErrorAlert(DISCONNECTED);
        else
            repostNumber.setText(String.valueOf(post.getReposts()));
    }

    public void comment(ActionEvent actionEvent) throws IOException {
        new PageLoader().load("commentPage" , new CommentPageControl(post));
    }

    public void like(ActionEvent actionEvent) {
        if(Connection.send(new LikeMessage(Main.uName , post.author , post.localDateTime)) instanceof ErrorMessage)
            showErrorAlert(DISCONNECTED);
        else
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
        try {
            Person profile = ((ProfileMessage) Connection.receive()).profile;
            if(profile != null)
                if(profile.uname.equals(Main.uName))
                    new PageLoader().load("myProfilePage");
                else
                    new PageLoader().load("otherProfilePage" , new OtherProfilePageControl(profile));
            else
                showErrorAlert("The user you wanted to visit his(her) profile has deleted his(her) account .");
        }catch (ClassCastException e) {
            showErrorAlert(DISCONNECTED);
        }
    }

    private void showErrorAlert(String s) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(s);
        alert.show();
    }
}
