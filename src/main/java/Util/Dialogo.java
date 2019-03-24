/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import sitetech.hotspot.Temas;

/**
 *
 * @author megan
 */
public class Dialogo {
    private static ButtonType mostrar(String mensaje, String titulo, AlertType tipo, String StyleSheet, ButtonType... btn)
    {
        Alert alert = new Alert(tipo, mensaje, btn);
        alert.setHeaderText(titulo);
        alert.showAndWait();
        
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().setAll( Temas.getTemasMap().get(StyleSheet) );
        dialogPane.getStyleClass().add("dialog-pane");
        
        //mostrar2(null, DialogType.COMMON, titulo, mensaje, btn);
        
        return alert.getResult();
    }
    
    public static ButtonType mostrarAlerta(String mensaje, String titulo,String StyleSheet, ButtonType... btn)
    {
        return mostrar(mensaje, titulo, AlertType.WARNING, StyleSheet, btn);
    }
    
    public static ButtonType mostrarConfirmacion(String mensaje, String titulo, String StyleSheet, ButtonType... btn)
    {
        return mostrar(mensaje, titulo, AlertType.CONFIRMATION, StyleSheet, btn);
    }
    
    public static ButtonType mostrarInformacion(String mensaje, String titulo, String StyleSheet, ButtonType... btn)
    {
        return mostrar(mensaje, titulo, AlertType.INFORMATION, StyleSheet, btn);
    }
    
    public static ButtonType mostrarError(String mensaje, String titulo, String StyleSheet, ButtonType... btn)
    {
        return mostrar(mensaje, titulo, AlertType.ERROR, StyleSheet, btn);
    }
    
}
