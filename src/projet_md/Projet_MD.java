/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet_md;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Hadjebar
 */
public class Projet_MD extends Application {
    
    public static Stage primaryStage;
    
    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        Parent root = FXMLLoader.load(getClass().getResource("FXML_ProjetMD.fxml"));
        
        Scene scene = new Scene(root);
        String css = Projet_MD.class.getResource("pageAccueil.css").toExternalForm();
        scene.getStylesheets().add(css);
        
        primaryStage.setTitle("Application_Clique");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
