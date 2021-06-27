package controller;

import Model.*;
import ServerSide.Person;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class OtherProfilePageControl implements Initializable {


    private static final String ERROR_TEXT = """
            You are not connected to the server .
            * Check your connection and use refresh icon .
            * Close the Program and run it again .
            """;
    public ImageView backIcon;
    public ImageView refreshIcon;
    public Label uName;
    public Label name;
    public Label birthDate;
    public Label followerNumber;
    public Label followingNumber;
    public AnchorPane otherPostsBase;
    public ListView<Post> postList;
    public Button followButton;
    public Button unfollowButton;
    private ObservableList<Post> postObservableList;

    private Person person ;
    private boolean connected = true ;


    public OtherProfilePageControl(Person person) {
        this.person = person;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        postObservableList = FXCollections.observableArrayList();
        setItems();
        postList.setItems(postObservableList);
        postList.setCellFactory(param -> new PostListCell());
    }

    private void setItems() {
        postObservableList.setAll(person.getPosts());
        uName.setText(person.uname);
        if(!person.name.equals(""))
            name.setText(person.name);
        if(!person.birthDate.equals(""))
            birthDate.setText(person.birthDate);
        followerNumber.setText(String.valueOf(person.getFollowersNumber()));
        followingNumber.setText(String.valueOf(person.getFollowingNumber()));
        if(isFollower()){
            unfollowButton.setVisible(true);
            followButton.setVisible(false);
        } else {
            followButton.setVisible(true);
            unfollowButton.setVisible(false);
        }
    }

    private boolean isFollower() {
        return person.getFollowerNames().contains(Main.uName);
    }

    public void back(MouseEvent mouseEvent) {
        back();
    }

    private void back()  {
        try {
            new PageLoader().load("mainPage");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cursorToHand(MouseEvent mouseEvent) {
        PageLoader.cursorToHand();
    }

    public void cursorToDefault(MouseEvent mouseEvent) {
        PageLoader.cursorToDefault();
    }

    public void refresh(MouseEvent mouseEvent) {
        refresh();
    }

    private void refresh() {
        if(!Connection.isOpen()){
            if(!Connection.connect()){
                connected = false ;
                return;
            }
        }
        Connection.send(new ProfileMessage(person.uname , null));
        try {
            person = ((ProfileMessage) Connection.receive()).profile;
            if(person != null) {
                setItems();
                connected = true;
            } else {
                showAlert("This account has been deleted .");
                back();
            }
        }catch (ClassCastException e) {
            connected = false;
        }
    }

    public void follow(ActionEvent actionEvent) {
        if(checkConnection()) {
            Connection.send(new FollowMessage(person.uname));
            person.addFollowerNames(Main.uName);
            followerNumber.setText(String.valueOf(person.getFollowersNumber()));
            unfollowButton.setVisible(true);
            followButton.setVisible(false);
        }
    }

    private boolean checkConnection() {
        if(connected)
            return true;
        showAlert(ERROR_TEXT);
        return false;
    }

    private void showAlert(String errorText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(errorText);
        alert.show();
    }

    public void unfollow(ActionEvent actionEvent) {
        if(checkConnection()) {
            Connection.send(new UnfollowMessage(person.uname));
            person.removeFollowerNames(Main.uName);
            followerNumber.setText(String.valueOf(person.getFollowersNumber()));
            followButton.setVisible(true);
            unfollowButton.setVisible(false);
        }
    }

    public void showFollowers(MouseEvent mouseEvent) throws IOException {
        List<FollowerOrFollowing> followData = person.getFollowerNames().stream().map(FollowerOrFollowing::new).collect(Collectors.toList());
        new PageLoader().load("followerOrFollowingPage" , new FollowerOrFollowingPageControl(person , followData));
    }

    public void showFollowings(MouseEvent mouseEvent) throws IOException {
        List<FollowerOrFollowing> followData = person.getFollowingNames().stream().map(FollowerOrFollowing::new).collect(Collectors.toList());
        new PageLoader().load("followerOrFollowingPage" ,  new FollowerOrFollowingPageControl(person , followData));
    }
}
