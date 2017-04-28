package fr.cils.projet.stage;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


/**
 * Classe permettant la mise en place et le paramètrage du stage principal de l'application
 */
public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("ui/auth.fxml"));
        primaryStage.setTitle("Gestion des offres de stage");
        //Resize non permis lorsque l'on se login
        primaryStage.setResizable(false);
        primaryStage.setMinHeight(200);
        primaryStage.setMinWidth(400);
        primaryStage.getIcons().add(new Image("file:res/icon.png"));
        primaryStage.setScene(new Scene(root, 400, 200));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
