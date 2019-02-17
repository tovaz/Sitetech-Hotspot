/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import java.util.regex.Pattern;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 *
 * @author megan
 */
public class Validar {
    public static boolean esTextfieldVacio(TextField tf) {
        boolean b = true;
        if (tf.getText().length() != 0 || !tf.getText().isEmpty())
            b = false;
        return b;
    }
    
    public static boolean esTextfieldVacio(TextField tf, Label lb, String mensaje){
        boolean b = false;
        String msg = null;
        tf.getStyleClass().remove("mensajeError");
        lb.getStyleClass().remove("labelError");
        
        if (esTextfieldVacio(tf)){
            b = true;
            msg = mensaje;
            tf.getStyleClass().add("mensajeError");
            lb.getStyleClass().add("labelError");
        }
        lb.setText(msg);
        return b;
    }
    
    public static boolean esTextfieldVacio(PasswordField tf, Label lb, String mensaje){
        boolean b = false;
        String msg = null;
        tf.getStyleClass().remove("mensajeError");
        lb.getStyleClass().remove("labelError");
        
        if (esTextfieldVacio(tf)){
            b = true;
            msg = mensaje;
            tf.getStyleClass().add("mensajeError");
            lb.getStyleClass().add("labelError");
        }
        lb.setText(msg);
        return b;
    }
    
    public static boolean esTextfieldNumero(TextField tf, boolean conDecimal){
        if (conDecimal)
            return tf.getText().matches("\\d+(.\\d+)?");
        else 
            return tf.getText().matches("[0-9]+");
    }
    
    public static boolean esTextfieldNumero(TextField tf, Label lb, String mensaje, Boolean conDecimal){
        boolean b = false;
        String msg = null;
        tf.getStyleClass().remove("mensajeError");
        lb.getStyleClass().remove("labelError");
        if (!esTextfieldNumero(tf, conDecimal)){
            b = true;
            msg = mensaje;
            tf.getStyleClass().add("mensajeError");
            lb.getStyleClass().add("labelError");
        }
        lb.setText(msg);
        return b;
    }
    
    public static boolean VerificarContraseña(TextField tf1, TextField tf2, Label lb, String mensaje){
        if ( !tf1.getText().equals(tf1.getText()) ){
            tf1.getStyleClass().add("mensajeError");
            tf2.getStyleClass().add("mensajeError");
            lb.getStyleClass().add("labelError");
            lb.setText(mensaje);

            return false;
        }
        return true;
    }
    //********************************** COMBOBOX VALIDATE ****************
    public static boolean esComboboxCorrecto(ComboBox cb){
        return !(cb.getValue() == null);
    }
    
    public static boolean esComboboxCorrecto(ComboBox cb, Label lb, String mensaje){
        boolean b = false;
        String msg = null;
        cb.getStyleClass().remove("controlError");
        lb.getStyleClass().remove("labelError");
        if (!esComboboxCorrecto(cb)){
            b = true;
            msg = mensaje;
            cb.getStyleClass().add("controlError");
            lb.getStyleClass().add("labelError");
        }
        lb.setText(msg);
        return b;
    }
    
    
}
