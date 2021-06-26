package controller;

import Model.PageLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class FollowerOrFollowingControl {

    public Label uName;
    public AnchorPane base;
    private FollowerOrFollowing data ;

    public FollowerOrFollowingControl(FollowerOrFollowing data) throws IOException {
        new PageLoader().load("followerOrFollowing" , this);
        this.data = data;
    }

    public AnchorPane init() {
        uName.setText(data.uName);
        uName.setAlignment(Pos.BASELINE_CENTER);
        return base;
    }
}
