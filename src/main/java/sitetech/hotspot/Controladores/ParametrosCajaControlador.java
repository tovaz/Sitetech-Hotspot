/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sitetech.hotspot.Controladores;

import Util.Validar;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import java.util.Date;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import sitetech.hotspot.MainApp;

/**
 * FXML Controller class
 *
 * @author megan
 */
public class ParametrosCajaControlador extends MiControlador {
    @FXML private JFXTextField tcaja;
    @FXML private JFXDatePicker dpfechai;
    @FXML private JFXDatePicker dpfechaf;
    @FXML private Label lcaja;
    @FXML private Label lfechai;
    @FXML private Label lfechaf;
    @FXML private AnchorPane pfechas;
    @FXML private Label lfechaTicket;
    
    public boolean correcto;
    public ButtonType BotonPresionado = ButtonType.Cancelar;
    
    public static enum ButtonType { Ok, Cancelar };
    public static enum VistaType { Fecha, Caja, Fecha_Ticket };
    private VistaType tipoVista;
    public ParametrosCajaControlador(MainApp _app) {
        cargarEscena("/Reportes/Caja/parametrosCaja.fxml", "Parametros de seleccion", Modality.WINDOW_MODAL, _app);
    }
    
    //********************** OPCIONES DE VISUALIZACION ********************
    public void showFechas(){    
        lfechaTicket.setVisible(false);
        pfechas.setVisible(true); tcaja.setVisible(false); dpfechai.requestFocus(); tipoVista = VistaType.Fecha;
        this.showAndWait();
    }
    public void showCaja(){
        lfechaTicket.setVisible(false);
        tcaja.setVisible(true); tcaja.setText(""); pfechas.setVisible(false); tcaja.requestFocus(); tipoVista = VistaType.Caja;
        this.showAndWait();
    }
    public void showFechaTicket(){
        pfechas.setVisible(true);
        lfechaTicket.setVisible(true); tcaja.setVisible(false); dpfechai.requestFocus(); tipoVista = VistaType.Fecha_Ticket;
        this.showAndWait();
    }
    //*******************************************************************
    
    @FXML
    public void onaceptar(ActionEvent event) {
        if (tipoVista == VistaType.Caja){
            if ( !Validar.esTextfieldVacio(tcaja, lcaja, "Debe de escribir un numero de caja valido.") )
                correcto = true;
        }
        else{
            if ( Validar.esFechaCorrecta(dpfechai, lfechai, "Seleccione una fecha valida.") && Validar.esFechaCorrecta(dpfechaf, lfechaf, "Seleccione una fecha valida."))
                correcto = true;
        }
        
        if (correcto) { BotonPresionado = ButtonType.Ok; thisStage.close(); }
    }

    @FXML
    public void oncancelar(ActionEvent event) {
        BotonPresionado = ButtonType.Cancelar;
        thisStage.close();
    }

    
    public int getNumeroCaja() {
        return Integer.parseInt(tcaja.getText());
    }

    public Date getFechaInicio() {
        return java.sql.Date.valueOf(dpfechai.getValue());
    }

    public Date getFechaFin() {
        return java.sql.Date.valueOf(dpfechaf.getValue());
    }
    
    
    
}
