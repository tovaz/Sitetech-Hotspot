/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sitetech.hotspot.Controladores;

import Util.Dialogo;
import Util.Moneda;
import Util.StageManager;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSpinner;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sitetech.hotspot.MainApp;
import sitetech.hotspot.Modelos.Caja;
import sitetech.hotspot.Modelos.CajaManager;
import sitetech.hotspot.Modelos.detalleCaja;

/**
 * FXML Controller class
 *
 * @author megan
 */
public class DetalleCajaControlador implements Initializable {

    @FXML private Label lcaja;
    @FXML private Label ltickets;
    @FXML private Label ltotaltickets;
    @FXML private Label lingresos;
    @FXML private Label legresos;
    @FXML private Label ltotal;
    @FXML private Label lcajainicial;
    @FXML private JFXSpinner spespere;
    @FXML private JFXButton bcancelar;
    @FXML private JFXButton bcerrar;
    @FXML private Label lespere;
    
    MainApp App;
    CajaManager cm;
    public static Stage thisStage;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    public DetalleCajaControlador(MainApp _app) {
        App = _app;
        thisStage = new Stage();
        StageManager.cargarStage("/Vistas/Caja/detalleCaja.fxml", "Detalle de caja", thisStage, this, Modality.APPLICATION_MODAL, App.configuracion);
    }
    
    Caja caja;
    public void cargarInfo(){
        caja = App.cajaAbierta;
        cm = new CajaManager();
        ObservableList<detalleCaja> listaDetalles = cm.getTicketsdeCaja(caja);
        
        String tickets = String.valueOf(listaDetalles.size());
        BigDecimal totalTickets = new BigDecimal(0.0);
        for (int i=0; i<listaDetalles.size(); i++){
            System.err.println("MONTO : " + listaDetalles.get(i).getMonto());
            totalTickets = totalTickets.add(listaDetalles.get(i).getMonto());
        }
        
        ltickets.setText(tickets);
        ltotaltickets.setText(Moneda.Formatear(totalTickets, Locale.getDefault()));
        lcaja.setText(String.valueOf(caja.getId()));
        lcajainicial.setText( Moneda.Formatear(caja.getCajaInicial()) );
        lingresos.setText(caja.getTotalIngresoF());
        legresos.setText(caja.getTotalEgresoF());
        ltotal.setText(caja.getTotalF());
    }
    
    public void showStage() {
        cargarInfo();
        thisStage.showAndWait();
    }
    
    
    @FXML
    private void onCancelar(ActionEvent event) {
        thisStage.close();
    }

    @FXML
    private void onCerrarCaja(ActionEvent event) {
        Optional<Dialogo.Retiro> retiro = Dialogo.CerrarCaja(App.configuracion, caja);
        Dialogo.Retiro retiroData = new Dialogo.Retiro("0", "0");
        if (retiro.isPresent()){
            retiroData = retiro.get();
            cerrar(retiroData);
        }
    }
    
    private void cerrar(Dialogo.Retiro retiro)
    {
        BigDecimal totalCierre = BigDecimal.ZERO;
        lespere.setText("Espere mientras se cierra la caja...");
        lespere.setVisible(true);
        spespere.setVisible(true);
        bcerrar.setVisible(false);
        bcancelar.setVisible(false);
            
        caja.setUsuarioCierre(App.usuarioLogeado);
        caja.setFechaCierre(new Date());
        caja.setTotalCierre(caja.getTotal().subtract(retiro.retiro1));
        
        try {
            cm.Editar(caja);
            Caja cajaNueva = cm.nuevaCajaconSaldo( App.usuarioLogeado, caja.getTotal().subtract(retiro.retiro1) ); // RETIRO1 -> tiene el saldo inicial de la siguiente caja.
            App.actualizarCaja(cajaNueva);

            Dialogo.mostrarInformacion("Caja cerrada exitosamente.", "Caja cerrada", App.configuracion, ButtonType.OK);
        }
        catch (Exception ex){ 
            Dialogo.mostrarError("Error al cerrar la caja, porfavor vuelve a intentar nuevamente.", "Error al cerrar la caja", App.configuracion, ButtonType.OK);
        }
        
        thisStage.close();
    }
    
}
