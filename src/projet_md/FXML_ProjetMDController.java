/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet_md;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

/**
 *
 * @author Hadjebar
 */
public class FXML_ProjetMDController implements Initializable {
    
    @FXML
    private AnchorPane panel;
    @FXML
    private Button btnLaunch;
    
    static FXMLCanvasController cref;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnLaunch.setOnAction(e -> {
            FadeOut();
        });
    }    
    
    void FadeOut() {
        FadeTransition ft = new FadeTransition();
        ft.setDuration(Duration.millis(1000));
        ft.setNode(panel);
        ft.setFromValue(1);
        ft.setToValue(0);
        ft.setOnFinished(e -> {
            loadNextScene();
        });
        ft.play();
        System.out.println("Here");
    }
    
    void loadNextScene() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLCanvas.fxml"));
            Parent root = loader.load();
            Scene newScene = new Scene(root);
            cref = loader.getController();
            
            System.out.println("Controller ref: " + cref);
            newScene.getStylesheets().add(getClass().getResource("canvas.css").toExternalForm());
            Projet_MD.primaryStage.setScene(newScene);
        } catch (IOException ex) {
            Logger.getLogger(FXML_ProjetMDController.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 
    
}
