package Model;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    public static Boolean loggedIn = Boolean.FALSE;
    public static String uName ;
    private final int height = 575;
    private final int width = 461;


    @Override
    public void start(Stage primaryStage) throws Exception{
        PageLoader.setStage(primaryStage);
        new PageLoader().load("myProfilePage");
        /* #36454F back color */ /*#ffdb11 gold color */ /*  #a1890f dark yellow */
    }


    public static void main(String[] args) {
        launch(args);
    }
}
