/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sitetech.hotspot.Modelos.Configuracion;
import sitetech.hotspot.Temas;

/**
 *
 * @author sitet
 */
public class StageManager {
    public static Object mostrarStage(String vista, String titulo, Stage main, Object Controlador, Modality modalidad, Configuracion config)
    {
        Stage nuevaStage;
        if (main == null)
            nuevaStage = new Stage();
        else
            nuevaStage = main;
        
        try {
            FXMLLoader loader = new FXMLLoader(Controlador.getClass().getResource(vista));
            loader.setController(Controlador);

            nuevaStage.initModality(modalidad);
            Scene thisScene = new Scene((Parent)loader.load());
            
            Temas.aplicarTema(thisScene, config);
            nuevaStage.setScene(thisScene);
            nuevaStage.setTitle(titulo);
            
            if (modalidad == Modality.NONE)
                nuevaStage.show();
            else
                nuevaStage.showAndWait();
            return Controlador;
            
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    
    public static Object cargarStage(String vista, String titulo, Stage main, Object Controlador, Modality modalidad, Configuracion config)
    {
        try {
            FXMLLoader loader = new FXMLLoader(Controlador.getClass().getResource(vista));
            loader.setController(Controlador);

            main.initModality(modalidad);
            Scene thisScene = new Scene((Parent)loader.load());
            
            Temas.aplicarTema(thisScene, config); /// APLICAR EL TEMA
            main.setScene(thisScene);
            main.setTitle(titulo);
            return Controlador;
            
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    
    public static Object cargarEscenaEnPanel(String vista, String titulo, Object Controlador)
    {
        FXMLLoader loader = new FXMLLoader(Controlador.getClass().getResource(vista));
        loader.setController(Controlador);
        Object control;
        try {
            control = loader.load();
            return control;
        } catch (IOException ex) {
            Logger.getLogger(util.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
