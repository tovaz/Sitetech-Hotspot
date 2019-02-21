/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

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
        return alert.getResult();
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
