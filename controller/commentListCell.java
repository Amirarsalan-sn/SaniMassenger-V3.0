package controller;

import Model.Comment;
import javafx.scene.control.ListCell;

import java.io.IOException;

public class commentListCell extends ListCell<Comment> {

    @Override
    public void updateItem(Comment comment , boolean empty) {
        super.updateItem(comment , empty);
        if(comment != null) {
            try {
                setGraphic(new CommentControl(comment).init());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
