package controller;

import Model.Comment;
import Model.Main;
import Model.PageLoader;
import Model.Post;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;

import java.net.URL;
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
        commentObservableList.add(new Comment(Main.uName , commentTextArea.getText()));

    }

    public void refresh(MouseEvent mouseEvent) {
        refresh() ;
    }

    private void refresh() {
    }

    public void back(MouseEvent mouseEvent) {

    }
}
