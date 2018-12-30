package nju.hwx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        try {
            final Controller controller  = new Controller();
            primaryStage.setScene(new Scene(controller.initPane(),1024,576));
            controller.initGame();
            primaryStage.setResizable(false);
            primaryStage.setTitle("葫芦娃");
            primaryStage.show();
            controller.setPrimaryStage(primaryStage);
            controller.startGame();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
