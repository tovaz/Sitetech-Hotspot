/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sitetech.hotspot.Controladores;

import Util.Validar;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
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
    
    public boolean correcto;
    public static enum VistaType { Fecha, Caja };
    private VistaType tipoVista;
    public ParametrosCajaControlador(MainApp _app, VistaType tipo) {
        tipoVista = tipo;
        cargarEscena("/Reportes/Caja/parametrosCaja.fxml", "Parametros de seleccion", Modality.APPLICATION_MODAL, _app);
        
        if (tipoVista == VistaType.Caja) pfechas.setVisible(false);
        if (tipoVista == VistaType.Fecha) tcaja.setVisible(false);
    }
    
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
        
        if (correcto) thisStage.close();
    }

    @FXML
    public void oncancelar(ActionEvent event) {
        thisStage.close();
    }
}
