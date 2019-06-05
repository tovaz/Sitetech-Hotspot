/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sitetech.hotspot.Controladores;

import Util.Dialogo;
import com.jfoenix.controls.JFXSpinner;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sitetech.hotspot.MainApp;
import sitetech.hotspot.Temas;

/**
 * FXML Controller class
 *
 * @author megan
 */
public class SplashControlador implements Initializable {

    @FXML private Label lmensaje;
    @FXML private JFXSpinner spcargando;
    
    @FXML private AnchorPane rootPanel;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        new Thread(new SplashScreen()).start();
    }    

    MainApp App;
    public static Stage thisStage;
    public SplashControlador(MainApp _app) {
        thisStage = new Stage();
        App = _app;
        try {
            //FXMLLoader loader = new FXMLLoader();
            //loader.setLocation(MainApp.class.getResource("/Vistas/splash.fxml"));
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/Vistas/splash.fxml"));
            loader.setController(this);
            
            thisStage.initStyle(StageStyle.TRANSPARENT);
            thisStage.initModality(Modality.NONE);
            Scene scene = new Scene((Parent) loader.load());
            scene.setFill(Color.TRANSPARENT);
            
            thisStage.getIcons().add(MainApp.iconoApp); // ICONO DE LA APP
            thisStage.setScene(scene);
            thisStage.setTitle("Cargando Sitetech Hotspot");
            
            rootPanel.setStyle("-fx-background-color: transparent;");
        } catch (IOException e) {
            Dialogo.mostrar(e.getMessage(), "Error al iniciar Splash Screen", Alert.AlertType.ERROR, ButtonType.OK);
        }
    }

    public void mostrar(){
        thisStage.show();
        SplashScreen sc = new SplashScreen();
    }
    
    public void postSplash(){
        //App.checkLogin("admin", "correr", this);
        App.loginScene(this);
        //thisStage.close();
    }
    class SplashScreen extends Task {
        @Override
        public Object call() throws IOException, InterruptedException{
            Thread.sleep(2000);
            
            try {
                App.cargarApp();
            } catch (Exception ex) {
                Logger.getLogger(SplashControlador.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            Platform.runLater(new Runnable() {
                @Override
                public void run(){
                    postSplash();
                }
            });
            return null;
        }
    }
}
