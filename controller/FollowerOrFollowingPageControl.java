package controller;

import Model.PageLoader;
import ServerSide.Person;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class FollowerOrFollowingPageControl implements Initializable {
    public ListView<FollowerOrFollowing> followersList;
    private ObservableList<FollowerOrFollowing> followersObservableList;
    private List<FollowerOrFollowing> followData;
    private Person resourcePerson ;

    public FollowerOrFollowingPageControl(Person resourcePerson , List<FollowerOrFollowing> followData) {
        this.resourcePerson = resourcePerson;
        this.followData = followData;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        followersObservableList = FXCollections.observableArrayList();
        followersObservableList.setAll(followData);
        followersList.setItems(followersObservableList);
        followersList.setCellFactory(param -> new FollowDataListCell());
    }

    public void back(MouseEvent mouseEvent) throws IOException {
        new PageLoader().load("otherProfilePage" , new OtherProfilePageControl(resourcePerson));
    }

    public void cursorToHand(MouseEvent mouseEvent) {
        PageLoader.cursorToHand();
    }

    public void cursorToDefault(MouseEvent mouseEvent) {
        PageLoader.cursorToDefault();
    }
}
