package Model;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Formatter;
import java.util.Scanner;

public class Main extends Application {
    public static final String MAIN_INIT_TXT = "there is no such file called MainInit.txt";
    /** field that specifies the past log in situation .
     *  if it is null , then the user have logged out or have deleted his(her) account .
     * */
    public static String uName = null ;
    private final int height = 575;
    private final int width = 461;
    private static String path = "C:\\Users\\Arsalan\\IdeaProjects\\SaniMassengerV3\\src\\Model\\MainInit.txt";


    @Override
    public void start(Stage primaryStage) throws Exception{
        init();
        PageLoader.setStage(primaryStage);
        new PageLoader().load("mainPage");
        /* #36454F back color */ /*#ffdb11 gold color */ /*  #a1890f dark yellow */
    }


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init(){
        try(Scanner scanner = new Scanner(new File(path))) {
            String userName = scanner.nextLine();
            if(!userName.equals("null")) {
                uName = userName;
            }
        }catch (FileNotFoundException e) {
            throw new AssertionError(MAIN_INIT_TXT);
        }
    }

    @Override
    public void stop() {
        try(Formatter formatter = new Formatter(new File(path))) {
            if(uName == null) {
                formatter.format("null");
            } else {
                formatter.format(uName);
            }
            formatter.flush();
        } catch (FileNotFoundException e) {
            throw new AssertionError(MAIN_INIT_TXT);
        }
    }
}
