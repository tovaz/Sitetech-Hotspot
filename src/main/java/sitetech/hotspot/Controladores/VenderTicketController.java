/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sitetech.hotspot.Controladores;

import Util.StageManager;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sitetech.Helpers.LabelHelper;
import sitetech.hotspot.MainApp;
import sitetech.hotspot.Modelos.Ticket;

/**
 * FXML Controller class
 *
 * @author sitet
 */
public class VenderTicketController implements Initializable {

    @FXML private Label lusuario;
    @FXML private Label lcontraseña;
    @FXML private Label ltiempo;
    @FXML private Label lvbajada;
    @FXML private Label lvsubida;
    @FXML private Label lpaquete;

    public final Stage thisStage;
    private TicketsController tc;
    private MainApp App;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    public VenderTicketController(TicketsController _tc, MainApp _app) {
        tc = _tc;
        App = _app;
        
        thisStage = new Stage();
        StageManager.cargarStage("/Vistas/Tickets/venderTicket.fxml", "Vender ticket", thisStage, this, Modality.APPLICATION_MODAL, App.configuracion);
    }

    public void showStage() {
        thisStage.showAndWait();
    }

    public void cargarDatos(Ticket ticket){
        lusuario.setText(ticket.getUsuario());
        lcontraseña.setText(ticket.getContraseña());
        
        LabelHelper.asignarTexto(ltiempo, ticket.getDuracion());
        LabelHelper.asignarTexto(lvbajada, ticket.getLimiteInternetDown());
        LabelHelper.asignarTexto(lvsubida, ticket.getLimiteInternetUp());
        
        LabelHelper.asignarTexto(lpaquete, ticket.getPaquete().getNombre());
    }
    
    
    
    @FXML
    private void onCancelar(ActionEvent event) {
        thisStage.close();
    }

    @FXML
    private void onVender(ActionEvent event) {
        
    }
    
}
