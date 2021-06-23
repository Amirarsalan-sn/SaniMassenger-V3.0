package controller;

import Model.PageLoader;
import Model.Post;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class MainPageControl implements Initializable {

    public ImageView menuButton;
    public ImageView refreshButton;
    public ListView<Post> posts;
    private ObservableList<Post> postObservableList;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        postObservableList = FXCollections.observableArrayList();
        postObservableList.addAll(
                new Post("Root" , "Welcome" , """
                        \"Welcome to SaniMessenger .
                        find new friends , like their post or repost them .
                        you have everything you want :) .\"
                        """) ,
                new Post("Arsalan" , "Salam be hame" , "\"Salam bache ha chetoorin ?\""),
                new Post("Arsi" , "rooze no" , "\"ajab rooze khoobie na ???\"")
        );
        posts.setItems(postObservableList);
        posts.setCellFactory(postListView -> new PostListCell());
        posts.setBackground(new Background(new BackgroundFill(Color.valueOf("#36454F") , null , null)));
        posts.setPadding(new Insets(0));
    }


    public void menuPage(MouseEvent mouseEvent) {

    }

    public void cursorToHand(MouseEvent mouseEvent) {
        PageLoader.cursorToHand();
    }

    public void cursorToDefault(MouseEvent mouseEvent) {
        PageLoader.cursorToDefault();
    }

    public void refresh(MouseEvent mouseEvent) {

    }
}
