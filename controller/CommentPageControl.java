package controller;

import Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class CommentPageControl implements Initializable {
    public TextArea commentTextArea;
    public ListView<Comment> commentList;
    private ObservableList<Comment> commentObservableList;
    private Post post ;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        commentObservableList = FXCollections.observableArrayList();
        refresh();
        commentList.setItems(commentObservableList);
        commentList.setCellFactory(commentListView -> new commentListCell());
    }

    public CommentPageControl(Post post) {
        this.post = post ;
    }

    public void cursorToHand(MouseEvent mouseEvent) {
        PageLoader.cursorToHand();
    }

    public void cursorToDefault(MouseEvent mouseEvent) {
        PageLoader.cursorToDefault();
    }

    public void comment(ActionEvent actionEvent) {
        if(checkTextArea()) {
            Comment comment = new Comment(Main.uName, commentTextArea.getText());
            Connection.send(new SendCommentMessage(comment , post.author , post.localDateTime));
            commentObservableList.add(comment);
        }
    }

    private boolean checkTextArea() {
        if(commentTextArea.getText().equals("")) {
            showErrorAlert("Empty comment" ,"You can't submit an empty comment .");
            return false;
        }
        return true;
    }

    private void showErrorAlert(String title , String context) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(context);
        alert.show();
    }

    public void refresh(MouseEvent mouseEvent) {
        refresh() ;
    }

    private void refresh() {
        Connection.send(new CommentRefMessage(post.author , post.localDateTime , null));
        Comment[] comments = ((CommentRefMessage) Connection.receive()).comments;
        Arrays.sort(comments , (c1 , c2) -> c1.localDateTime.compareTo(c2.localDateTime));
        commentObservableList.setAll(comments);
    }

    public void back(MouseEvent mouseEvent) throws IOException {
        new PageLoader().load("mainPage");
    }
}
