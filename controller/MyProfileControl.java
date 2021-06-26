package controller;

import Model.*;
import ServerSide.Person;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MyProfileControl implements Initializable {
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
    private Person person ;
    private ObservableList<Post> personObservableList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        refresh();
        personObservableList = FXCollections.observableArrayList();
        myPostsList.setItems(personObservableList);
        myPostsList.setCellFactory(param -> new PostListCell());
    }

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
        Alert alert = new Alert(Alert.AlertType.WARNING) ;
        alert.setTitle("Delete Account ??");
        alert.setContentText("Are you sure you want to delete your account ??");
        ButtonType yesButton = ButtonType.YES;
        ButtonType noButton = ButtonType.NO;
        alert.getButtonTypes().setAll(yesButton , noButton);
        alert.showAndWait().ifPresent(clicked -> {
            if(clicked == ButtonType.YES){
                Connection.send(new DeleteAccMessage());
            }else{
                //do nothing .
            }
        });
    }

    public void change(ActionEvent actionEvent) {
        String newName = nameTextField.getText();
        String newBirthDate = dateTextField.getText();
        Connection.send(new ChangeInfoMessage(newName, newBirthDate));
        changeButton.setVisible(false);
        changeInfoButton.setVisible(true);
        if(!newName.equals(""))
            name.setText(newName);
        nameTextField.setVisible(false);
        name.setVisible(true);
        if(!newBirthDate.equals(""))
            birthDate.setText(dateTextField.getText());
        dateTextField.setVisible(false);
        birthDate.setVisible(true);
    }

    public void menuLoad(MouseEvent mouseEvent) throws IOException {
        new PageLoader().load("menuPage");
    }

    public void refresh(MouseEvent mouseEvent) {
        refresh();
    }

    private void refresh() {
        Connection.send(new ProfileMessage(Main.uName , null));
        Person profile = ((ProfileMessage) Connection.receive()).profile;
        person = profile;
        personObservableList.setAll(person.getPosts());
        setTextFields();
    }

    private void setTextFields() {
        if(!person.name.equals(""))
            name.setText(person.name);
        uName.setText(person.uname);
        if(!person.birthDate.equals(""))
            birthDate.setText(person.birthDate);
        followerNumber.setText(String.valueOf(person.getFollowersNumber()));
        followingNumber.setText(String.valueOf(person.getFollowingNumber()));
    }
}
