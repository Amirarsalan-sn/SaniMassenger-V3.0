package controller;

import Model.Connection;
import Model.Main;
import Model.PageLoader;
import Model.ProfileMessage;
import ServerSide.Person;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class SearchControl {

    private static final String ERROR_TEXT = """
            You are not connected to the server .
            * Check your connection and use refresh icon .
            * Close the Program and run it again .
            """;
    public TextField searchField;
    public Label nothing;
    public AnchorPane resultAnchor;
    public Label uName;
    public Label name;
    public Label birthDate;
    private Person result ;
    private boolean connected = true;

    public void search(ActionEvent actionEvent) {
        if(checkConnection()) {
            Connection.send(new ProfileMessage(searchField.getText(), null));
            try {
                result = ((ProfileMessage) Connection.receive()).profile;
            }catch (ClassCastException e) {
                connected = false;
                showErrorAlert(ERROR_TEXT);
            }
            checkResult();
        }
    }

    private boolean checkConnection() {
        if(connected){
            return true;
        }
        showErrorAlert(ERROR_TEXT);
        return false;
    }

    private void showErrorAlert(String errorText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(errorText);
        alert.show();
    }

    private void checkResult() {
        if(result != null) {
            resultAnchor.setVisible(true);
            nothing.setVisible(false);
        }
    }

    public void cursorToHand(MouseEvent mouseEvent) {
        PageLoader.cursorToHand();
    }

    public void cursorToDefault(MouseEvent mouseEvent) {
        PageLoader.cursorToDefault();
    }

    public void back(MouseEvent mouseEvent) throws IOException {
        new PageLoader().load("mainPage");
    }

    public void refresh(MouseEvent mouseEvent) {
        if(!Connection.isOpen()){
            if(!Connection.connect()) {
                connected = false;
                return;
            }
        }
        connected = true;
    }

    public void otherLoad(MouseEvent mouseEvent) throws IOException {
        if(result.uname.equals(Main.uName))
            new PageLoader().load("myProfilePage");
        else
            new PageLoader().load("otherProfilePage" , new OtherProfilePageControl(result));
    }
}
