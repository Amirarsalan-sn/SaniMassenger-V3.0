package controller;

import Model.Connection;
import Model.Main;
import Model.PageLoader;
import Model.ReConnectMessage;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.io.IOException;

public class BeginControl {
    public ImageView beginImage;
    public ImageView saniImage;
    public Label welcome;

    @FXML
    public void initialize() throws IOException {
        fadeIn(beginImage);
        fadeIn(saniImage);
        fadeIn(welcome);
        playNode(beginImage, -200);
        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(2));
        pauseTransition.setOnFinished(e -> playNode(saniImage, -10));
        pauseTransition.play();
        pauseTransition = new PauseTransition(Duration.seconds(4));
        pauseTransition.setOnFinished(e -> {
            welcomeFadeOut();
        });
        pauseTransition.play();
        pauseTransition = new PauseTransition(Duration.seconds(8));
        pauseTransition.setOnFinished(e -> {
            if (!Connection.connect()) {
                connectionFailed();
                welcome.setText("    connection failed . . .");
            } else {
                if(Main.uName != null) {
                    try {
                        new PageLoader().load("mainPage");
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                } else {
                    try {
                        new PageLoader().load("loginPage");
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            }
        });
        pauseTransition.play();
    }

    private void welcomeFadeOut() {
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(5));
        translateTransition.setNode(welcome);
        translateTransition.setByY(-100);
        translateTransition.play();
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(2000));
        fadeTransition.setNode(welcome);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        fadeTransition.play();
    }

    private <T extends Node> void playNode(T node, int pos) {
        TranslateTransition translateTransition = new TranslateTransition();
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setNode(node);
        translateTransition.setNode(node);
        translateTransition.setDuration(Duration.millis(2000));
        fadeTransition.setDuration(Duration.millis(2000));
        translateTransition.setByY(pos);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        fadeTransition.play();
        translateTransition.play();
    }

    public <T extends Node> void fadeIn(T node) {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(5));
        fadeTransition.setNode(node);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);
        fadeTransition.play();
    }

    public <T extends Node> void fadeIn(T node, int milliTime) {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(milliTime));
        fadeTransition.setNode(node);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);
        fadeTransition.play();
    }

    public <T extends Node> void fadeOut(T node, int milliTime) {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(milliTime));
        fadeTransition.setNode(node);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        fadeTransition.play();
    }

    public void connectionFailed() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("""
                Failed to connect :\s
                1. Check your internet connection .
                2. Close the program and open it again later .
                """);
        alert.show();
    }
}
