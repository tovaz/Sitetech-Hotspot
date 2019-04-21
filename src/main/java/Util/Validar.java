/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import java.util.regex.Pattern;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import slider.control.CircularSlider;

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
    
    public static void setearLabel(Label lb){
        lb.getStyleClass().remove("labelError");
    }
    
    public static  void setearTexto(TextField tf){
        tf.getStyleClass().remove("controlError");
    }
    
    public static void setearDatePicker(JFXDatePicker dp){
        dp.getStyleClass().remove("customControlError");
    }
    
    public static void setearCampo(TextField tf, Label lb){
        setearLabel(lb);
        setearTexto(tf);
    }
    
    public static boolean esTextfieldVacio(TextField tf, Label lb, String mensaje){
        boolean b = false;
        String msg = null;
        setearCampo(tf,lb);
        
        if (esTextfieldVacio(tf)){
            b = true;
            msg = mensaje;
            tf.getStyleClass().add("controlError");
            lb.getStyleClass().add("labelError");
        }
        lb.setText(msg);
        return b;
    }
    
    public static boolean esTextfieldVacio(PasswordField tf, Label lb, String mensaje){
        boolean b = false;
        String msg = null;
        setearCampo(tf,lb);
        
        if (esTextfieldVacio(tf)){
            b = true;
            msg = mensaje;
            tf.getStyleClass().add("controlError");
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
        
        setearCampo(tf,lb);
        if (!esTextfieldNumero(tf, conDecimal)){
            b = true;
            msg = mensaje;
            tf.getStyleClass().add("controlError");
            lb.getStyleClass().add("labelError");
        }
        lb.setText(msg);
        return b;
    }
    
    public static boolean esFechaCorrecta(JFXDatePicker dp, Label lb, String mensaje){
        String msg = null;
        setearDatePicker(dp);
        setearLabel(lb);
        if (dp.getValue() == null){
            msg = mensaje;
            dp.getStyleClass().add("controlError");
            lb.getStyleClass().add("labelError");
            lb.setText(msg);
            return false;
        }
        
        return true;
    }
    
    public static boolean VerificarContraseña(TextField tf1, TextField tf2, Label lb, String mensaje){
        setearCampo(tf1,lb);
        setearTexto(tf2);
                
        if ( !tf1.getText().equals(tf2.getText()) ){
            tf1.getStyleClass().add("controlError");
            tf2.getStyleClass().add("controlError");
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
        boolean b = true;
        String msg = null;
        cb.getStyleClass().remove("customControlError");
        lb.getStyleClass().remove("labelError");
        if (!esComboboxCorrecto(cb)){
            b = false;
            msg = mensaje;
            cb.getStyleClass().add("customControlError");
            lb.getStyleClass().add("labelError");
        }
        lb.setText(msg);
        return b;
    }
    
    public static boolean esComboboxCorrecto(JFXComboBox cb){
        return !(cb.getValue() == null);
    }
    
    public static boolean esComboboxCorrecto(JFXComboBox cb, Label lb, String mensaje){
        boolean b = true;
        String msg = null;
        cb.getStyleClass().remove("customControlError");
        setearLabel(lb);
        if (!esComboboxCorrecto(cb)){
            b = false;
            msg = mensaje;
            cb.getStyleClass().add("customControlError");
            lb.getStyleClass().add("labelError");
        }
        lb.setText(msg);
        return b;
    }
    
    public static boolean esCircularSliderCorrecto(CircularSlider cs, Label lb, String mensaje){
        boolean b = true;
        String msg = null;
        cs.getStyleClass().remove("customControlError");
        setearLabel(lb);
        if ( cs.getValue()<=0 ){
            b = false;
            msg = mensaje;
            cs.getStyleClass().add("customControlError");
            lb.getStyleClass().add("labelError");
        }
        lb.setText(msg);
        return b;
    }
    
    
}
