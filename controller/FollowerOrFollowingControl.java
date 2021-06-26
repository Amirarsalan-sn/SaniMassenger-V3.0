package controller;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class FollowerOrFollowingControl {

    public Label uName;
    public AnchorPane base;
    private FollowerOrFollowing data ;

    public FollowerOrFollowingControl(FollowerOrFollowing data) {
        this.data = data;
    }

    public AnchorPane init() {
        uName.setText(data.uName);
        uName.setAlignment(Pos.BASELINE_CENTER);
        return base;
    }
}
