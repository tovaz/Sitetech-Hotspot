/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sitetech.hotspot.Controladores;

import Util.StageManager;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sitetech.hotspot.MainApp;
import sitetech.hotspot.Modelos.Configuracion;

/**
 *
 * @author megan
 */
public class MiControlador implements Initializable{
    public static Stage thisStage;
    public MainApp App;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
    
    public void cargarEscena(String escena, String titulo, Modality modalidad, MainApp _app){
        App = _app;
        thisStage = new Stage();
        StageManager.cargarStage(escena, titulo, thisStage, this, modalidad, App.configuracion);
        App.agregarEscena("scene_" + escena, thisStage.getScene());
    }
    
    public void showAndWait() {
        thisStage.showAndWait();
    }
    
    public void show() {
        thisStage.show();
    }
}
