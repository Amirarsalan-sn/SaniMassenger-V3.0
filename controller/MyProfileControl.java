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
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class MyProfileControl implements Initializable {
    private static final String ERROR_TEXT = """
            You are not connected to the server .
            * Check your connection and use refresh icon .
            * Close the Program and run it again .
            """;
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
    private boolean connected = true;

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
        if(checkConnection()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Delete Account ??");
            alert.setContentText("Are you sure you want to delete your account ??");
            ButtonType yesButton = ButtonType.YES;
            ButtonType noButton = ButtonType.NO;
            alert.getButtonTypes().setAll(yesButton, noButton);
            alert.showAndWait().ifPresent(clicked -> {
                if (clicked == ButtonType.YES) {
                    Connection.send(new DeleteAccMessage());
                    Main.uName = null;
                    try {
                        new PageLoader().load("loginPage");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    //do nothing .
                }
            });
        }
    }

    private boolean checkConnection() {
        if(connected)
            return true;
        showErrorAlert(ERROR_TEXT);
        return false;
    }

    private void showErrorAlert(String errorText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(errorText);
        alert.show();
    }

    public void change(ActionEvent actionEvent) {
        if(checkConnection()) {
            String newName = nameTextField.getText();
            String newBirthDate = dateTextField.getText();
            Connection.send(new ChangeInfoMessage(newName, newBirthDate));
            changeButton.setVisible(false);
            changeInfoButton.setVisible(true);
            if (!newName.equals(""))
                name.setText(newName);
            nameTextField.setVisible(false);
            name.setVisible(true);
            if (!newBirthDate.equals(""))
                birthDate.setText(dateTextField.getText());
            dateTextField.setVisible(false);
            birthDate.setVisible(true);
        }
    }

    public void menuLoad(MouseEvent mouseEvent) throws IOException {
        new PageLoader().load("menuPage");
    }

    public void refresh(MouseEvent mouseEvent) {
        refresh();
    }

    private void refresh() {
        if(!Connection.isOpen()){
            if (!Connection.connect()) {
                connected = false;
                return;
            }
        }
        Connection.send(new ProfileMessage(Main.uName , null));
        Person profile = ((ProfileMessage) Connection.receive()).profile;
        person = profile;
        personObservableList.setAll(person.getPosts());
        setTextFields();
        connected = true;
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

    public void showFollowers(MouseEvent mouseEvent) throws IOException {
        if(checkConnection()) {
            List<FollowerOrFollowing> followData = person.getFollowerNames().stream().map(FollowerOrFollowing::new).collect(Collectors.toList());
            new PageLoader().load("followerOrFollowingPage", new FollowerOrFollowingPageControl(person, followData));
        }
    }

    public void showFollowings(MouseEvent mouseEvent) throws IOException {
        if(checkConnection()) {
            List<FollowerOrFollowing> followData = person.getFollowingNames().stream().map(FollowerOrFollowing::new).collect(Collectors.toList());
            new PageLoader().load("followerOrFollowingPage", new FollowerOrFollowingPageControl(person, followData));
        }
    }
}
