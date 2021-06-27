package controller;

import Model.*;
import ServerSide.Person;
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
import java.util.ResourceBundle;

public class CommentPageControl implements Initializable {
    public TextArea commentTextArea;
    public ListView<Comment> commentList;
    private ObservableList<Comment> commentObservableList;
    private Post post;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        commentObservableList = FXCollections.observableArrayList();
        commentObservableList.setAll(post.getComments());
        commentList.setItems(commentObservableList);
        commentList.setCellFactory(commentListView -> new commentListCell());
    }

    public CommentPageControl(Post post) {
        this.post = post;
    }

    public void cursorToHand(MouseEvent mouseEvent) {
        PageLoader.cursorToHand();
    }

    public void cursorToDefault(MouseEvent mouseEvent) {
        PageLoader.cursorToDefault();
    }

    public void comment(ActionEvent actionEvent) {
        if (checkTextArea()) {
            Comment comment = new Comment(Main.uName, commentTextArea.getText());
            if(Connection.send(new SendCommentMessage(comment, post.author, post.localDateTime)) instanceof ErrorMessage) {
                showErrorAlert("Sending failed", "You are disconnected from server try to reconnect from the main page .");
            } else {
            commentObservableList.add(comment);
            }
        }
    }

    private boolean checkTextArea() {
        if (commentTextArea.getText().equals("")) {
            showErrorAlert("Empty comment", "You can't submit an empty comment .");
            return false;
        }
        return true;
    }

    private void showErrorAlert(String title, String context) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(context);
        alert.show();
    }

    public void back(MouseEvent mouseEvent) throws IOException {
        Connection.send(new ProfileMessage(post.author, null));
        try {
            Person person = ((ProfileMessage) Connection.receive()).profile;
            if (person != null) {
                if (person.uname.equals(Main.uName))
                    new PageLoader().load("myProfilePage");
                else
                    new PageLoader().load("otherProfilePage", person);
            } else {
                showErrorAlert("Deleted account" , "The account you want to visit has been deleted");
                new PageLoader().load("mainPage");
            }
        }catch (ClassCastException e) {
            new PageLoader().load("mainPage");
        }
    }
}
