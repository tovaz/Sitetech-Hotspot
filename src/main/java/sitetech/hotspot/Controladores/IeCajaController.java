/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sitetech.hotspot.Controladores;

import Util.Validar;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.math.BigDecimal;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import sitetech.hotspot.MainApp;
import sitetech.hotspot.Modelos.Caja;
import sitetech.hotspot.Modelos.CajaManager;
import sitetech.hotspot.Modelos.detalleCaja;

/**
 * FXML Controller class
 *
 * @author megan
 */
public class IeCajaController extends MiControlador {

    @FXML private Label ltitulo;
    @FXML private Label ltipo;
    @FXML private JFXComboBox<String> cbtipo;
    @FXML private JFXTextField tmonto;
    @FXML private Label lemonto;
    @FXML private JFXTextField tdetalle;
    
    detalleCaja.TipoDetalle tipoDetalle;
    ObservableList<String> categorias;
    public IeCajaController(MainApp _app) {
        cargarEscena("/Vistas/Caja/ieCaja.fxml", "Ingreso / Egreso de caja", Modality.WINDOW_MODAL, _app);
    }

    public void mostrarIngreso(){
        thisStage.setTitle("Ingreso de Caja");
        ltitulo.setText("Ingreso de Caja");
        ltipo.setText("Tipo de ingreso");
        tipoDetalle = detalleCaja.TipoDetalle.Ingreso;
        
        categorias = FXCollections.observableArrayList();
        categorias.addAll("Ingreso de dinero", "Venta de servicio");
        cbtipo.setItems(categorias);
        cbtipo.getSelectionModel().select(0);
        showAndWait();
    }
    
    public void mostrarEgreso(){
        thisStage.setTitle("Egreso de Caja");
        ltitulo.setText("Egreso de Caja");
        ltipo.setText("Tipo de egreso");
        tipoDetalle = detalleCaja.TipoDetalle.Egreso;
        
        categorias = FXCollections.observableArrayList();
        categorias.addAll("Egreso de dinero", "Pago de servicios", "Pago de salario", "Error de ingreso");
        cbtipo.setItems(categorias);
        cbtipo.getSelectionModel().select(0);
        showAndWait();
    }
    

    @FXML
    private void onCancelar(ActionEvent event) {
        thisStage.close();
    }

    @FXML
    private void onAceptar(ActionEvent event) {
        CajaManager cm = new CajaManager();
        if (!Validar.esTextfieldNumero(tmonto, lemonto, "Debe de ingresar un monto.", true)){
            Double monto = Double.valueOf(tmonto.getText());
            detalleCaja dc = new detalleCaja(App.cajaAbierta, null, tipoDetalle, cbtipo.getValue() + " - " + tdetalle.getText(), BigDecimal.valueOf(monto), detalleCaja.EstadoDetalle.Correcto);
            Caja cajaNueva = cm.agregarDetalle(dc);
            if (cajaNueva != null) App.actualizarCaja(cajaNueva);
            
            thisStage.close();
        }
    }
    
}
