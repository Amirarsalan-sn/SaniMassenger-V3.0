package controller;

import Model.*;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

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
    public ImageView refreshIcon;
    /** for the time you click the log in button multiple times */
    private boolean loginClicked = false;


    public void logIn(ActionEvent actionEvent) { // remember
        if(!loginClicked) {
            loginClicked = true;
            WrongPass.setVisible(false);
            Message sendResult = Connection.send(new LoginMessage(username.getText() ,
                    password.isVisible() ? password.getText() : passField.getText()));
            handle(sendResult);
            clearTextFields();
            handle(Connection.receive());
            loginClicked = false;
        }
    }

    private void showConfirmAlert(String s) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText(s);
        alert.show();
    }

    private void showErrorAlert(String s) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(s);
        alert.show();
    }

    private void clearTextFields() {
        username.clear();
        passField.clear();
        password.clear();
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
       new PageLoader().load("signinPage");
    }

    public void handle(Message message) {
        switch (message.getClass().getSimpleName()) {
            case "BooleanMessage" : {
                if (((BooleanMessage) message).value) {
                    showConfirmAlert("You have logged in successfully .");
                } else {
                    WrongPass.setVisible(true);
                    clearTextFields();
                }
                break;
            }
            case "ErrorMessage" : {
                showErrorAlert(((ErrorMessage) message).message);
                break;
            }
        }
    }

    public void refresh(MouseEvent mouseEvent) throws IOException {
        if(!Connection.isOpen()) {
            Connection.connect();
        }
        new PageLoader().load("loginPage");
    }

    public void cursorToHand(MouseEvent mouseEvent) {
        PageLoader.cursorToHand();
    }

    public void cursorToDefault(MouseEvent mouseEvent) {
        PageLoader.cursorToDefault();
    }
}
