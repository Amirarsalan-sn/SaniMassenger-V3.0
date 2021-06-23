package controller;

import Model.PageLoader;
import Model.Post;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class NewPostPageControl {

    public TextField postTitle;
    public TextArea postContext;
    public ImageView menuIcon;

    public void menuLoad(MouseEvent mouseEvent) throws IOException {
        new PageLoader().load("menuPage");
    }

    public void cursorToHand(MouseEvent mouseEvent) {
        PageLoader.cursorToHand();
    }

    public void cursorToDefault(MouseEvent mouseEvent) {
        PageLoader.cursorToDefault();
    }

    public void publish(ActionEvent actionEvent) {
        if(checkTextFields()) {
            showInfoAlert("Publish this post ?", """
                    Are you sure you want to publish this post ???
                    """);
        } else {
            showErrorAlert("Empty field detected" , """
                    Where is your Post Title ??!
                    Or maybe its because of your Empty Post Context !
                    Maybe Both ?!!
                    """);
        }
    }

    private void showErrorAlert(String title, String context) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(context);
        alert.show();
    }

    private boolean checkTextFields() {
        return !postTitle.getText().equals("") && !postContext.getText().equals("");
    }

    private void showInfoAlert(String title , String context) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(context);
        ButtonType yesButton = ButtonType.YES;
        ButtonType noButton = ButtonType.NO;
        alert.getButtonTypes().setAll(noButton , yesButton);
        alert.showAndWait().ifPresent(clicked -> {
            if(clicked == yesButton) {
                surePublish();
            } else {
                //do nothing
            }
        });

    }

    private void surePublish() {
        Post post = new Post(null , postTitle.getText() , postContext.getText());
        //send
        clearTextFields();
    }

    private void clearTextFields() {
        postTitle.clear();
        postContext.clear();
    }


}
