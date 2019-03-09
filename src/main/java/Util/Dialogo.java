/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import java.awt.JobAttributes.DialogType;
import java.util.ArrayList;
import java.util.Optional;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.text.Text;
import sitetech.hotspot.MainApp;

/**
 *
 * @author megan
 */
public class Dialogo {
    private static ButtonType mostrar(String mensaje, String titulo, AlertType tipo, ButtonType... btn)
    {
        Alert alert = new Alert(tipo, mensaje, btn);
        alert.setHeaderText(titulo);
        alert.showAndWait();
        
        mostrar2(null, DialogType.COMMON, titulo, mensaje, btn);
        return alert.getResult();
    }
    
    private static Optional<ButtonType> mostrar2(Object obj, DialogType tipoDialogo, String titulo, String mensaje, ButtonType... btn){
        JFXAlert<ButtonType> alerta = new JFXAlert<>();
        
        JFXDialogLayout content = new JFXDialogLayout();
	content.setHeading(new Text(titulo));
	content.setBody(new Text(mensaje));
	
        
        
        content.setActions(new JFXButton("Ok"));
        alerta.setContent(content);
        //JFXDialog dialog = new JFXDialog(mainWindow, content, DialogTransition.CENTER, true);
	//dialog.setLayoutX(mainWindow.getWidth()/2);
	//dialog.setLayoutY(mainWindow.getHeight()/2);
	
	//dialog.show(mainWindow);
        return alerta.showAndWait();
    }
    
    public static ButtonType mostrarAlerta(String mensaje, String titulo, ButtonType... btn)
    {
        return mostrar(mensaje, titulo, AlertType.WARNING, btn);
    }
    
    public static ButtonType mostrarConfirmacion(String mensaje, String titulo, ButtonType... btn)
    {
        return mostrar(mensaje, titulo, AlertType.CONFIRMATION, btn);
    }
    
    public static ButtonType mostrarInformacion(String mensaje, String titulo, ButtonType... btn)
    {
        return mostrar(mensaje, titulo, AlertType.INFORMATION, btn);
    }
    
    public static ButtonType mostrarError(String mensaje, String titulo, ButtonType... btn)
    {
        return mostrar(mensaje, titulo, AlertType.ERROR, btn);
    }
    
}
