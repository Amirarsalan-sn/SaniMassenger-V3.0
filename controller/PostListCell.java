package controller;

import Model.Post;
import javafx.scene.control.ListCell;

import java.io.IOException;

public class PostListCell extends ListCell<Post> {

    @Override
    public void updateItem(Post post , boolean empty) {
        super.updateItem(post , empty);
        if(post != null) {
            try {
                setGraphic(new PostControl(post).init());
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
