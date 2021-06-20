package controller;

import Model.PageLoader;
import javafx.event.ActionEvent;
import javafx.scene.control.*;

import java.io.IOException;

public class SignInControl {
    public TextField firstName;
    public TextField lastName;
    public TextField userName;
    public TextField password;
    public TextField reEnterPass;
    public TextField country;
    public TextField city;
    public TextField email;
    public Button logInIcon;
    public Button signInIcon;
    public CheckBox showIcon;
    public PasswordField passField;
    public PasswordField reEnterPassFeild;
    /** for the time you click the sign in button multiple times */
    private boolean signinClicked = false;

    public void logIn(ActionEvent actionEvent) throws IOException {
        PageLoader.load("loginPage");
    }

    public void signIn(ActionEvent actionEvent) {
        if(!signinClicked) {
            signinClicked = true;
            if (password.isVisible()) {
                if (!password.getText().equals(reEnterPass.getText())) {
                    showAlert();
                }
            } else if (passField.isVisible()) {
                if (!passField.getText().equals(reEnterPassFeild.getText())) {
                    showAlert();
                }
            }
            signinClicked = true;
        }
    }

    private void showAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("""
                Password and Re-enter Password fields are not equal .
                """);
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
}
