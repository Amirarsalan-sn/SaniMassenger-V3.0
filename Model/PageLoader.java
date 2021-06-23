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

    public void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }


    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/View/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public void load(String url) throws IOException {
        scene = new Scene(new PageLoader().loadFXML(url));
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public  void load(String url , Object controller) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/View/" + url + ".fxml"));
        fxmlLoader.setController(controller);
        fxmlLoader.load();
    }

    public static void cursorToHand() {
        scene.setCursor(Cursor.HAND);
    }

    public static void cursorToDefault() {
        scene.setCursor(Cursor.DEFAULT);
    }
}
