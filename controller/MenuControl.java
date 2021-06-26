package controller;

import Model.Connection;
import Model.Main;
import Model.PageLoader;
import Model.ProfileMessage;
import ServerSide.Person;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class MenuControl {

    public ImageView exitIcon;
    public Button profileButton;
    public Button newPostButton;
    public Button logOutButton;

    public void exit(MouseEvent mouseEvent) throws IOException {
        new PageLoader().load("mainPage");
    }

    public void profile(ActionEvent actionEvent) throws IOException {
        new PageLoader().load("myProfilePage");
    }

    public void publishNewPost(ActionEvent actionEvent) throws IOException {
        new PageLoader().load("newPostPage");
    }

    public void logOut(ActionEvent actionEvent) {
        showWarnAlert("Log out ?" , """
                You seriously want to log out ?.
                "you have to log in again next time you open the application"
                """);
    }

    private void showWarnAlert(String title, String context) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setContentText(context);
        ButtonType yesButton = ButtonType.YES;
        ButtonType noButton = ButtonType.NO;
        alert.getButtonTypes().setAll(yesButton , noButton);
        alert.showAndWait().ifPresent(clicked -> {
            if(clicked == yesButton) {
                sureLogOut() ;
            } else {
                //do nothing
            }
        });
    }

    private void sureLogOut() {
        Main.loggedIn = Boolean.FALSE;
        Main.uName = null ;
        try {
            new PageLoader().load("loginPage");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cursorToHand(MouseEvent mouseEvent) {
        PageLoader.cursorToHand();
    }

    public void cursorToDefault(MouseEvent mouseEvent) {
        PageLoader.cursorToDefault();
    }
}
