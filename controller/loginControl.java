package controller;

import Model.*;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

import java.io.IOException;


public class loginControl {
    public ImageView saniImage;
    public TextField username;
    public TextField password;
    public Button logInButton;
    public CheckBox showPas;
    public Label WrongPass;
    public Button signInButton;
    public PasswordField passField;
    /** for the time you click the log in button multiple times */
    private boolean loginClicked = false;


    public void logIn(ActionEvent actionEvent) { // remember
        if(!loginClicked) {
            loginClicked = true;
            WrongPass.setVisible(false);
            if (!Connection.send(new LoginMessage(username.isVisible() ? username.getText() : passField.getText()
                    , password.getText()))) {
                clearTextFields();
                showAlert("""
                Failed to send your info to server :
                1. Check your internet connection .
                2. Try to send again .
                3. Close the program and start it again later .
                """);
            } else {
                try {
                    Message message = Connection.receive();
                    handle(message);
                }catch (Exception e) {
                    showAlert(e.getMessage());
                }
            }
            loginClicked = false;
        }
    }

    private void showAlert(String s) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(s);
        alert.show();
    }

    private void clearTextFields() {
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

    public void singIn(ActionEvent actionEvent) throws IOException {
        PageLoader.load("signinPage");
    }

    public void handle(Message message) {
        if(((BooleanMessage) message).value) {
            //load main page
        } else {
            WrongPass.setVisible(true);
            clearTextFields();
        }
    }
}
