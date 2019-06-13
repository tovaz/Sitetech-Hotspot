/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import com.jfoenix.controls.JFXTextField;
import java.math.BigDecimal;
import java.util.Optional;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import sitetech.hotspot.Modelos.Caja;
import sitetech.hotspot.Modelos.Configuracion;
import sitetech.hotspot.Temas;
import static sitetech.hotspot.Temas.getCssporNombre;

/**
 *
 * @author megan
 */
public class Dialogo {
    private static ButtonType mostrar(String mensaje, String titulo, AlertType tipo, Configuracion config, ButtonType... btn)
    {
        Alert alert = new Alert(tipo, mensaje, btn);
        alert.setHeaderText(titulo);
        
        DialogPane dialogPane = alert.getDialogPane();
        ObservableList<String> enfasis = getCssporNombre(config.getColorEnfasis(), Temas.getEnfasis()).getCssList();
        dialogPane.getStylesheets().add( enfasis.get(3) );
        dialogPane.getStylesheets().add( Temas.getTemasMap().get(config.getColorTema()).getCssList().get(0) );
        dialogPane.getStyleClass().add("dialog-pane");
        
        //mostrar2(null, DialogType.COMMON, titulo, mensaje, btn);
        
        alert.showAndWait();
        return alert.getResult();
    }
    
    public static ButtonType mostrar(String mensaje, String titulo, AlertType tipo, ButtonType... btn)
    {
        Alert alert = new Alert(tipo, mensaje, btn);
        alert.setHeaderText(titulo);
        
        alert.showAndWait();
        return alert.getResult();
    }
    
    public static ButtonType mostrarAlerta(String mensaje, String titulo, Configuracion config, ButtonType... btn)
    {
        return mostrar(mensaje, titulo, AlertType.WARNING, config, btn);
    }
    
    public static ButtonType mostrarConfirmacion(String mensaje, String titulo, Configuracion config, ButtonType... btn)
    {
        return mostrar(mensaje, titulo, AlertType.CONFIRMATION, config, btn);
    }
    
    public static ButtonType mostrarInformacion(String mensaje, String titulo, Configuracion config, ButtonType... btn)
    {
        return mostrar(mensaje, titulo, AlertType.INFORMATION, config, btn);
    }
    
    public static ButtonType mostrarError(String mensaje, String titulo, Configuracion config, ButtonType... btn)
    {
        return mostrar(mensaje, titulo, AlertType.ERROR, config, btn);
    }
    
    public static ButtonType mostrarError(String mensaje, String titulo, ButtonType... btn)
    {
        return mostrar(mensaje, titulo, AlertType.ERROR, btn);
    }
    
    public static Optional<Retiro>  CerrarCaja(Configuracion config, Caja caja){
        Dialog<Retiro> dialog = new Dialog<>();
        
        dialog.setTitle("Cierre de caja");
        dialog.setHeaderText("¿Cuanto dinero va a retirar de caja?");
        dialog.setResizable(false);

        Label lcaja = new Label("Total en caja: " + Moneda.Formatear(caja.getTotal()));
        Label label1 = new Label("Ingresa cantidad: ");
        Label label2 = new Label("Vuelva a ingresar la cantidad: ");
        Separator separador = new Separator();
        JFXTextField tretiro1 = new JFXTextField();
        JFXTextField tretiro2 = new JFXTextField();
        lcaja.setStyle("-fx-font-weight: bold; -fx-text-fill: #F50031;");
        //label2.setPadding(new Insets(10, 0, 20, 0));
        //tretiro2.setPadding(new Insets(10, 0, 20, 0));
        
        GridPane grid = new GridPane();
        grid.add(lcaja, 1, 1);
        grid.add(separador, 1, 2);
        grid.add(label1, 1, 3);
        grid.add(tretiro1, 2, 3);
        grid.add(label2, 1, 5);
        grid.add(tretiro2, 2, 5);
        dialog.getDialogPane().setContent(grid);

        //***************** APARIENCIA DEL DIALOGO Y TEXTOS ********************
        ObservableList<String> enfasis = getCssporNombre(config.getColorEnfasis(), Temas.getEnfasis()).getCssList();
        tretiro1.getStylesheets().addAll( enfasis );
        tretiro2.getStylesheets().addAll( enfasis );
        
        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getStylesheets().add( enfasis.get(3) );
        dialogPane.getStylesheets().add( Temas.getTemasMap().get(config.getColorTema()).getCssList().get(0) );
        dialogPane.getStyleClass().add("dialog-pane");
        //**********************************************************************
        
        dialog.getDialogPane().getButtonTypes().add(ButtonType.YES);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        dialog.setResultConverter(new Callback<ButtonType, Retiro>() {
            @Override
            public Retiro call(ButtonType b) {

                if (b == ButtonType.YES) {
                    return new Retiro(tretiro1.getText(), tretiro2.getText());
                }

                return null;
            }
        });
        
        
        Optional<Dialogo.Retiro> retiro = dialog.showAndWait();
        Dialogo.Retiro retiroData = new Dialogo.Retiro("0", "0");
        if (retiro.isPresent()){
            retiroData = retiro.get();
            if (retiroData.retiro1.compareTo(retiroData.retiro2) != 0) {
                Dialogo.mostrarError("Debe de escribir la misma cantidad.", "Error al ingresar retiro", 
                        config, ButtonType.OK);
                return CerrarCaja(config, caja);
            }
            else if ( (retiroData.retiro1.compareTo(caja.getTotal()) == 1) ){
                Dialogo.mostrarError("Debe de escribir una cantidad menor a la cantidad total de dinero en caja.", "Error al ingresar retiro", 
                        config, ButtonType.OK);
                return CerrarCaja(config, caja);
            }
        }
        
        return retiro;
    }
    
    public static class Retiro {
        public BigDecimal retiro1 = BigDecimal.ZERO;
        public BigDecimal retiro2 = BigDecimal.ZERO;
        public Retiro(String a, String b){
            retiro1 = new BigDecimal(a);
            retiro2 = new BigDecimal(b);
        }
    }
}
