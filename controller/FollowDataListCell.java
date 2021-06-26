package controller;

import javafx.scene.control.ListCell;

import java.io.IOException;

public class FollowDataListCell extends ListCell<FollowerOrFollowing> {

    @Override
    public void updateItem(FollowerOrFollowing followData , boolean empty){
        super.updateItem(followData , empty);
        if(followData != null) {
            try {
                setGraphic(new FollowerOrFollowingControl(followData).init());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
