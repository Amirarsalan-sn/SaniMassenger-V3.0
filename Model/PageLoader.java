package Model;

import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class PageLoader {
    private static Stage primaryStage ;
    private static final int height = 575;
    private static final int width = 461;
    private static Scene scene ;

    public static void setStage (Stage stage) {
        primaryStage = stage;
        primaryStage.setResizable(false);
        primaryStage.setTitle("SMS V3.0");
        primaryStage.getIcons().add(new Image("workSpace/Logo_images/main.png"));
    }

    public static void load (String url) throws IOException {
        Parent root = FXMLLoader.load(PageLoader.class.getResource("../View/"+url+".fxml"));
        scene = new Scene(root, width, height);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void cursorToHand() {
        scene.setCursor(Cursor.HAND);
    }

    public static void cursorToDefault() {
        scene.setCursor(Cursor.DEFAULT);
    }
}
