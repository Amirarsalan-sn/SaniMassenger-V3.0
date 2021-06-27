package controller;

import Model.Connection;
import Model.PageLoader;
import Model.Post;
import Model.PostRefMessage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;


import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class MainPageControl implements Initializable {

    public ImageView menuButton;
    public ImageView refreshButton;
    public ListView<Post> posts;
    private ObservableList<Post> postObservableList;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        postObservableList = FXCollections.observableArrayList();
        refresh();
        posts.setItems(postObservableList);
        posts.setCellFactory(postListView -> new PostListCell());
    }


    public void menuPage(MouseEvent mouseEvent) throws IOException {
        new PageLoader().load("menuPage");
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

    public void refresh() {
        if(!Connection.isOpen()) {
            if(!Connection.connect())
                return;
        }
        Connection.send(new PostRefMessage(null));
        try {
            Post[] refPosts = ((PostRefMessage) Connection.receive()).posts;
            Arrays.sort(refPosts , (post1,post2) -> post1.localDateTime.compareTo(post2.localDateTime));
            postObservableList.setAll(refPosts);
        }catch (ClassCastException e) {
            return;
        }
    }

    public void searchPeople(ActionEvent actionEvent) throws IOException {
        new PageLoader().load("search");
    }
}
