package controller;

import Model.*;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class SignInControl {
    public static final String FIELDS_ARE_NOT_EQUAL = """
            Password and Re-enter Password fields are not equal .
            """;
    public static final String MANDATORY = """
            it is necessary to enter User Name and Password .
            """;
    public static final String LONGER_THAN_8_CHARACTERS = "Your password should be longer than 8 characters .";
    public static final String CHARACTERS_OR_NUMBERS = "Your password should just have English characters or numbers .";
    public TextField firstName;
    public TextField lastName;
    public TextField userName;
    public TextField password;
    public TextField reEnterPass;
    public Button logInIcon;
    public Button signInIcon;
    public CheckBox showIcon;
    public PasswordField passField;
    public PasswordField reEnterPassFeild;
    public TextField birthDate;
    public ImageView refreshIcon;
    /**
     * for the time you click the sign in button multiple times repeatedly
     */
    private boolean signinClicked = false;

    public void logIn(ActionEvent actionEvent) throws IOException {
        new PageLoader().load("loginPage");
    }

    public void signIn(ActionEvent actionEvent) {
        if (!signinClicked) {
            signinClicked = true;
            if (checkNecessaryFields() && checkPasswordFields() && checkPasswordCorrectness()) {
                Message sendResult = Connection.send(new SignInMessage(firstName.getText(), lastName.getText(),
                        birthDate.getText(), userName.getText(), password.isVisible() ? password.getText() : passField.getText()));
                handle(sendResult);
                clearTextFields();
                Message respond = Connection.receive();
                handle(respond);
            }
            signinClicked = false;
        }
    }

    private void clearTextFields() {
        firstName.clear();
        lastName.clear();
        userName.clear();
        passField.clear();
        password.clear();
        reEnterPass.clear();
        reEnterPassFeild.clear();
        birthDate.clear();
    }

    private void handle(Message respond) {
        switch (respond.getClass().getSimpleName()) {
            case "ErrorMessage": {
                showErrorAlert(((ErrorMessage) respond).message);
                break;
            }
            case "ConfirmMessage": {
                showConfirmAlert(((ConfirmMessage) respond).message);
                break;
            }
        }
    }

    private boolean checkPasswordFields() {
        if (!password.getText().equals(reEnterPass.getText())) {
            showErrorAlert(FIELDS_ARE_NOT_EQUAL);
            return false;
        }
        return true;
    }

    public boolean checkPasswordCorrectness() {
        String regex = "[a-zA-Z0-9]+";
        String s = password.isVisible() ? password.getText() : passField.getText();
        if (s.matches(regex)) {
            if (s.length() >= 8)
                return true;
            showErrorAlert(LONGER_THAN_8_CHARACTERS);
        } else {
            showErrorAlert(CHARACTERS_OR_NUMBERS);
        }
        return false;
    }

    private boolean checkNecessaryFields() {
        if (userName.getText().equals("")) {
            showErrorAlert(MANDATORY);
            return false;
        }
        return true;
    }

    private void showErrorAlert(String s) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(s);
        alert.show();
    }

    private void showConfirmAlert(String s) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText(s);
        alert.show();
    }

    public void showPass(ActionEvent actionEvent) {
        if (password.isVisible()) {
            password.setVisible(false);
            reEnterPass.setVisible(false);
            passField.setVisible(true);
            reEnterPassFeild.setVisible(true);
            passField.setText(password.getText());
            reEnterPassFeild.setText(reEnterPass.getText());
        } else if (passField.isVisible()) {
            password.setVisible(true);
            reEnterPass.setVisible(true);
            passField.setVisible(false);
            reEnterPassFeild.setVisible(false);
            password.setText(passField.getText());
            reEnterPass.setText(reEnterPassFeild.getText());
        }
    }

    public void refresh(MouseEvent mouseEvent) throws IOException {
        if (!Connection.isOpen()) {
            Connection.connect();
        }
        new PageLoader().load("signinPage");
    }

    public void cursorToHand(MouseEvent mouseEvent) {
        PageLoader.cursorToHand();
    }


    public void cursorToDefault(MouseEvent mouseEvent) {
        PageLoader.cursorToDefault();
    }
}
