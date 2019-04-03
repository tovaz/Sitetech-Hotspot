/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sitetech.hotspot.Controladores;

import Util.Dialogo;
import Util.Moneda;
import Util.StageManager;
import java.math.BigDecimal;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sitetech.Helpers.DateTimeHelper;
import static sitetech.Helpers.DateTimeHelper.getFechayHoraHoy;
import sitetech.Helpers.LabelHelper;
import sitetech.hotspot.MainApp;
import sitetech.hotspot.Modelos.Caja;
import sitetech.hotspot.Modelos.CajaManager;
import sitetech.hotspot.Modelos.Ticket;
import sitetech.hotspot.Modelos.TicketManager;
import sitetech.hotspot.Modelos.detalleCaja;
import sitetech.hotspot.Modelos.detalleCaja.EstadoDetalle;
import sitetech.hotspot.Modelos.detalleCaja.TipoDetalle;

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

    Ticket ticket;
    public void cargarDatos(Ticket _ticket){
        ticket = _ticket;
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
        if (ticket.getEstado() != Ticket.EstadosType.Activo){ // por si llega a abrir esta ventana y el ticket tiene un estado incorrecto.
            Dialogo.mostrarAlerta("El estado del ticket es incorrecto, ticket ya vendido o en uso.", "Ticket ya vendido o en uso.", 
                    App.configuracion, ButtonType.OK);
            return;
        }
        
        ButtonType btn = Dialogo.mostrarInformacion("¿Confirme la venta del ticket " + ticket.getUsuario() + " ? por un precio de " + Moneda.Formatear(ticket.getPaquete().getPrecio(), Locale.getDefault()) + ".", "Confirmar venta", 
                App.configuracion, ButtonType.YES, ButtonType.NO);
        
        if (btn == ButtonType.YES){
            CajaManager cm = new CajaManager();
            detalleCaja dt = new detalleCaja(App.cajaAbierta, ticket, TipoDetalle.Venta_Ticket, "", ticket.getPaquete().getPrecio(), EstadoDetalle.Correcto);

            Caja caja = cm.agregarDetalle(dt);
            
            if ( caja != null){
                TicketManager tm = new TicketManager();
                ticket.setFechaVenta(new Date());
                ticket.setEstado(Ticket.EstadosType.Vendido);
                tm.EditarTicket(ticket);
                
                App.actualizarCaja(caja);
                tc.actualizarTicket(ticket);
                thisStage.close();
            }
            else
                Dialogo.mostrarError("Ocurrio un error al intentar vender el ticket, comunicate con el administrador para informar el error.", "Error al ingresar la venta del ticket", 
                        App.configuracion, ButtonType.OK);
        }
        
    }
    
}
