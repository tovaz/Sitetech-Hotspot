
package sitetech.hotspot.Controladores;

import Util.Dialogo;
import com.jfoenix.controls.JFXCheckBox;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sitetech.hotspot.Modelos.Ticket;
//import ticketcontrol.ticketControl;

/**
 * FXML Controller class
 *
 * @author willi
 */
public class ImprimirTicketsController implements Initializable, Printable {
    @FXML private TextField tancho;
    @FXML private TextField tlargo;
    @FXML private JFXCheckBox checkFecha;
    @FXML private JFXCheckBox checkLimiteTiempo;
    @FXML private JFXCheckBox checkLimiteSubida;
    @FXML private JFXCheckBox checkLimiteDescarga;
    @FXML private JFXCheckBox checkInstrucciones;
    @FXML private TilePane ptickets;
    
    private final Stage thisStage;
    private ObservableList<Ticket> listaTickets;
    public ImprimirTicketsController (ObservableList<Ticket> _listaTickets){
        thisStage = new Stage();
        Util.util.cargarStage("/Vistas/Tickets/imprimirTickets.fxml", "Imprimir Tickets", thisStage, this, Modality.APPLICATION_MODAL);
        listaTickets = _listaTickets;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public void showStage() throws IOException {
        this.cargarTickets();
        thisStage.showAndWait();
    }

    public void cargarTickets() throws IOException{
        ptickets.getChildren().removeAll();
        
        /*for (Ticket tx : listaTickets){
            ticketControl tc = crearControl(tx);
            ptickets.getChildren().add(tc);
        }*/
    }
    
    /*public ticketControl crearControl(Ticket tx){
        ticketControl tc = new ticketControl();
        tc.setmostrarFecha(true);
        tc.setmostrarInstrucciones(true);
        tc.setmostrarLimiteDescarga(true);
        tc.setmostrarLimiteTiempo(true);
        
        tc.setUsuario(tx.getUsuario());
        tc.setContraseña(tx.getContraseña());
        tc.setlimiteDescarga(tx.getLimiteGigasDown() + " Gb + " + tx.getLimiteMegasDown() + " Mb");
        tc.setlimiteSubida(tx.getLimiteGigasUp() + " Gb + " + tx.getLimiteMegasUp() + " Mb");
        tc.setInstrucciones("Aqui van unas instrucciones de uso...");
        tc.setTiempo(tx.getLimiteDias() + "dias y " + tx.getLimiteHoras() + ":" + tx.getLimiteMinutos());
        return tc;
    }*/
    
    @FXML
    private void verAction(ActionEvent event) {
        
        /*for (Node tc : ptickets.getChildren()){
            ticketControl tcx = (ticketControl) tc;
            
        }*/
    }

    @FXML
    private void imprimirAction(ActionEvent event) {
        try {
            PrinterJob gap = PrinterJob.getPrinterJob();
            gap.setPrintable(this);
            boolean dialogo = gap.printDialog();
            if (dialogo)
                gap.print();
            
        } catch (PrinterException ex) {
            Dialogo.mostrarError("Error al intentar enviar la impresion", "Error al imprimir", ButtonType.OK);
            System.out.println(ex.getMessage());
        }
        
        
    }

    @Override
    public int print(Graphics graphs, PageFormat pageFormat, int index) throws PrinterException {
        if (index > 0)
            return NO_SUCH_PAGE;
        Graphics2D hub = (Graphics2D) graphs;
        
        hub.scale(1.0, 1.0);
        
        return PAGE_EXISTS;
    }
    
}
