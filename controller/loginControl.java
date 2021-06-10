package controller;

import Model.Connection;
import Model.LoginMessage;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.net.ConnectException;

public class loginControl {
    public ImageView saniImage;
    public TextField username;
    public TextField password;
    public Button logInButton;
    public CheckBox showPas;
    public Label WrongPass;
    public Button signInButton;
    public PasswordField passField;


    public void logIn(ActionEvent actionEvent) {
        if (!Connection.send(new LoginMessage(username.isVisible() ? username.getText() : passField.getText()
                , password.getText()))) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("""
                    Failed to send your info to server :
                    1. Check your internet connection .
                    2. Try to send again .
                    3. Close the program and start it again later .
                    """);
            alert.show();
        }
        username.setText("");
        passField.setText("");
        password.setText("");
    }

    public void showPassword(ActionEvent actionEvent) {
        if (password.isVisible()) {
            passField.setText(password.getText());
            password.setText("");
            passField.setVisible(true);
            password.setVisible(false);
        } else {
            password.setText(passField.getText());
            passField.setText("");
            password.setVisible(true);
            passField.setVisible(false);
        }
    }

    public void singIn(ActionEvent actionEvent) {

    }

    public <T extends Node> void fadeIn(double milliTime, T... nodes) {
        for (Node node : nodes) {
            FadeTransition fadeTransition = new FadeTransition(Duration.millis(milliTime));
            fadeTransition.setNode(node);
            fadeTransition.setFromValue(1.0);
            fadeTransition.setToValue(0.0);
            fadeTransition.play();
        }
    }

    public <T extends Node> void fadeOut(double milliTime, T... nodes) {
        for (Node node : nodes) {
            FadeTransition fadeTransition = new FadeTransition(Duration.millis(milliTime));
            fadeTransition.setNode(node);
            fadeTransition.setFromValue(0.0);
            fadeTransition.setToValue(1.0);
            fadeTransition.play();
        }
    }

    public <T extends Node> void move(T node, int pos) {

    }
}
