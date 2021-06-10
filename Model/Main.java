package Model;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    private final int height = 575;
    private final int width = 461;


    @Override
    public void start(Stage primaryStage) throws Exception{
        PageLoader.setStage(primaryStage);
        PageLoader.load("beginning");
        /* #36454F*/
    }


    public static void main(String[] args) {
        launch(args);
    }
}
