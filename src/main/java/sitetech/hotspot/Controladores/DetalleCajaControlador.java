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
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
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
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import sitetech.Helpers.DateTimeHelper;
import sitetech.Helpers.mailHelper;
import sitetech.Helpers.reporteHelper;
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
        ltotaltickets.setText(Moneda.Formatear(totalTickets));
        lcaja.setText(String.valueOf(caja.getId()));
        lcajainicial.setText( Moneda.Formatear(caja.getCajaInicial()) );
        lingresos.setText(Moneda.Formatear(caja.getTotalIngreso().subtract(totalTickets)));
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
        caja.setTotalCierre(retiro.retiro1);
        
        try {
            cm.Editar(caja);
            Caja cajaNueva = cm.nuevaCajaconSaldo( App.usuarioLogeado, caja.getTotal().subtract(retiro.retiro1) ); // RETIRO1 -> tiene el saldo inicial de la siguiente caja.
            App.actualizarCaja(cajaNueva);

            enviarReporte(caja);
            Dialogo.mostrarInformacion("Caja cerrada exitosamente.", "Caja cerrada", App.configuracion, ButtonType.OK);
        }
        catch (Exception ex){ 
            Dialogo.mostrarError("Error al cerrar la caja, porfavor vuelve a intentar nuevamente.", "Error al cerrar la caja", App.configuracion, ButtonType.OK);
        }
        
        thisStage.close();
    }
    
    private void enviarReporte(Caja caja) throws IOException{
        mailHelper correo = new mailHelper();
        String path = new File(".").getCanonicalPath() + "/cajas/";
        File ff = new File(path); ff.mkdirs(); // CREAMOS EL DIRECTORIO SI NO EXISTE.
        
        exportarCaja(caja, path);
        
        String contenido = "<h2>REPORTE DE CIERRE DE CAJA</h2>";
        contenido +="<h3><b>Fecha: </b>" + DateTimeHelper.getFechayHoraHoy() + "</h3>";
        
        contenido +="<h3>CAJA</h3>";
        contenido +="<b>No: </b>" + lcaja.getText().toString();
        contenido +="<br/><b>Fecha creacion: </b>" + caja.getFechaApertura().toString();
        contenido +="<br/><b>Caja Inicial: </b>" + lcajainicial.getText().toString();
        contenido +="<br/><br/><br/>";
        
        contenido +="<br/><b>Tickets Vendidos: </b>" + ltickets.getText().toString();
        contenido +="<br/><b>Total Tickets: </b>" + ltotaltickets.getText().toString();
        contenido +="<br/><b>Total Ingreso: </b>" + lingresos.getText().toString();
        contenido +="<br/><b>Total Egresos: </b>" + legresos.getText().toString();
        
        contenido +="<br/><b>Total: </b>" + ltotal.getText().toString();
        contenido +="<br/><br/><br/>";
        
        contenido +="<br/><h3><b>Total de caja: </b>" + ltotal.getText().toString() + "</h3>";
        
        correo.enviarCierreCaja(contenido, new File(path + DateTimeHelper.getFecha() + ".pdf"));
    }
    
    public boolean exportarCaja(Caja caja, String path){
        Map<String,Object> parametros = new HashMap<String,Object>();
        parametros.put("UsuarioLogueado", App.usuarioLogeado.getNombre()); 
        try {
            ObservableList<Caja> cajas = cm.getDetallesdeCaja(caja.getId());
            JasperPrint jp = reporteHelper.getJasperPrint("/Reportes/Caja/DetallesdeCaja.jasper", cajas, parametros, App.configuracion);

            if (cajas != null){
                OutputStream output = new FileOutputStream(new File(path + DateTimeHelper.getFecha()+ ".pdf")); 
                JasperExportManager.exportReportToPdfStream(jp, output); 
                return true;
            }else
                return false;
            
            
        } catch (Exception ex) { 
            System.out.println(ex.getMessage()); 
            Dialogo.mostrarError(ex.getMessage(), "Error al cargar el reporte", App.configuracion, ButtonType.OK);
        }
        
        return false;
    }
}
