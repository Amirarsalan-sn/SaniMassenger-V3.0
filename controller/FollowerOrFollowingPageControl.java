package controller;

import Model.PageLoader;
import ServerSide.Person;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class FollowerOrFollowingPageControl implements Initializable {
    public ListView<FollowerOrFollowing> followersList;
    private ObservableList<FollowerOrFollowing> followersObservableList;
    private List<FollowerOrFollowing> followData;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void back(MouseEvent mouseEvent) {

    }

    public void cursorToHand(MouseEvent mouseEvent) {
        PageLoader.cursorToHand();
    }

    public void cursorToDefault(MouseEvent mouseEvent) {
        PageLoader.cursorToDefault();
    }
}
