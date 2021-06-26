package controller;

import Model.Connection;
import Model.PageLoader;
import Model.SearchMessage;
import ServerSide.Person;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class SearchControl {

    public TextField searchField;
    public Label nothing;
    public AnchorPane resultAnchor;
    public Label uName;
    public Label name;
    public Label birthDate;
    private Person result ;

    public void search(ActionEvent actionEvent) {
        Connection.send(new SearchMessage(searchField.getText(),null));
        result = ((SearchMessage) Connection.receive()).result;
        checkResult();
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
        // todo
    }

    public void otherLoad(MouseEvent mouseEvent) throws IOException {
        new PageLoader().load("otherProfilePage" , new OtherProfilePageControl(result));
    }
}
