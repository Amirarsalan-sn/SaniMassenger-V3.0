package controller;

import Model.PageLoader;
import Model.Post;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class MyProfileControl {
    public Button changeInfoButton;
    public Label name;
    public Label uName;
    public Label birthDate;
    public Label followerNumber;
    public Label followingNumber;
    public TextField dateTextField;
    public TextField nameTextField;
    public Button changeButton;
    public AnchorPane myPostsPane;
    public ListView<Post> myPostsList;
    public ImageView menuIcon;

    public void changeInfo(ActionEvent actionEvent) {
        changeInfoButton.setVisible(false);
        changeButton.setVisible(true);
        birthDate.setVisible(false);
        dateTextField.setVisible(true);
        nameTextField.setVisible(true);
        name.setVisible(false);
    }

    public void cursorToHand(MouseEvent mouseEvent) {
        PageLoader.cursorToHand();
    }

    public void cursorToDefault(MouseEvent mouseEvent) {
        PageLoader.cursorToDefault();
    }

    public void deleteAccount(ActionEvent actionEvent) {

    }

    public void change(ActionEvent actionEvent) {
        //server work
        changeButton.setVisible(false);
        changeInfoButton.setVisible(true);
        name.setText(nameTextField.getText());
        nameTextField.setVisible(false);
        name.setVisible(true);
        birthDate.setText(dateTextField.getText());
        dateTextField.setVisible(false);
        birthDate.setVisible(true);
    }

    public void menuLoad(MouseEvent mouseEvent) throws IOException {
        new PageLoader().load("menuPage");
    }

    public void refresh(MouseEvent mouseEvent) {

    }
}
