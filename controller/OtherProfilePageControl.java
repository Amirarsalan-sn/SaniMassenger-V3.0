package controller;

import Model.*;
import ServerSide.Person;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
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


    public OtherProfilePageControl(Person person) {
        this.person = person;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(person.uname.equals(Main.uName)){
            try {
                new PageLoader().load("myProfileControl");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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

    public void back(MouseEvent mouseEvent) throws IOException {
        new PageLoader().load("mainPage");
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
        Connection.send(new ProfileMessage(person.uname , null));
        person = ((ProfileMessage) Connection.receive()).profile;
        setItems();
    }

    public void follow(ActionEvent actionEvent) {
        Connection.send(new FollowMessage(person.uname));
        person.addFollowerNames(Main.uName);
        followerNumber.setText(String.valueOf(person.getFollowersNumber()));
        unfollowButton.setVisible(true);
        followButton.setVisible(false);
    }

    public void unfollow(ActionEvent actionEvent) {
        Connection.send(new UnfollowMessage(person.uname));
        person.removeFollowerNames(Main.uName);
        followerNumber.setText(String.valueOf(person.getFollowersNumber()));
        followButton.setVisible(true);
        unfollowButton.setVisible(false);
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
