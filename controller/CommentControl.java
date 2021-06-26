package controller;

import Model.Comment;
import Model.PageLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class CommentControl {

    public AnchorPane commentBase;
    public TextArea commentTextArea;
    public Label uName;
    public Label date;
    private Comment comment;

    public CommentControl(Comment comment) throws IOException {
        new PageLoader().load("commentCell" , this);
        this.comment = comment;
    }

    public AnchorPane init() {
        uName.setText(comment.author);
        commentTextArea.setText(comment.context);
        date.setText(comment.date);
        return commentBase;
    }

    public void otherPageLoad(MouseEvent mouseEvent) {
        // load other's page .
    }

    public void cursorToHand(MouseEvent mouseEvent) {
        PageLoader.cursorToHand();
    }

    public void cursorToDefault(MouseEvent mouseEvent) {
        PageLoader.cursorToDefault();
    }
}
