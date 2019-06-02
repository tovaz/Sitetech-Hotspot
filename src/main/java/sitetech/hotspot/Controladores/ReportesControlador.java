/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sitetech.hotspot.Controladores;

import Util.Dialogo;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconName;
import java.util.HashMap;
import java.util.Map;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import net.sf.jasperreports.engine.JasperPrint;
import sitetech.Helpers.imagenHelper;
import sitetech.Helpers.reporteHelper;
import sitetech.hotspot.MainApp;
import sitetech.hotspot.Modelos.Caja;
import sitetech.hotspot.Modelos.CajaManager;
import sitetech.hotspot.Modelos.TicketManager;

/**
 * FXML Controller class
 *
 * @author megan
 */
public class ReportesControlador extends MiControlador {

    @FXML private AnchorPane preporte;
    @FXML private TreeView<String> tvlista;
    public ReporteViewerControlador rviewer;
    private ParametrosCajaControlador pcaja;
    private CajaManager cm;
    private TicketManager tm;
            
    public ReportesControlador(MainApp _app) {
        cargarEscena("/Reportes/Reportes.fxml", "Reportes", Modality.WINDOW_MODAL, _app);
        rviewer = new ReporteViewerControlador(App);
        
        cm = new CajaManager();
        tm = new TicketManager();
        
        cargarInfo();
    }
    
    private void cargarInfo(){
        TreeItem rootItem = new TreeItem("Reportes", imagenHelper.getIcono(FontAwesomeIconName.FILE_TEXT_ALT, "12px", "-fx-fill: -fx-accent;"));

        TreeItem itemsCaja = new TreeItem("Caja", imagenHelper.getIcono(FontAwesomeIconName.MONEY, "12px", "-fx-fill: -fx-accent;"));
        itemsCaja.getChildren().add(new TreeItem("Cajas por fecha", imagenHelper.getIcono(FontAwesomeIconName.CIRCLE_ALT, "12px", "-fx-fill: -fx-accent;")));
        itemsCaja.getChildren().add(new TreeItem("Detalles de caja", imagenHelper.getIcono(FontAwesomeIconName.CIRCLE_ALT, "12px", "-fx-fill: -fx-accent;")));
        itemsCaja.getChildren().add(new TreeItem("Caja actual", imagenHelper.getIcono(FontAwesomeIconName.CIRCLE_ALT, "12px", "-fx-fill: -fx-accent;")));
        rootItem.getChildren().add(itemsCaja);
        
        TreeItem itemsTickets = new TreeItem("Tickets", imagenHelper.getIcono(FontAwesomeIconName.TAGS, "12px", "-fx-fill: -fx-accent;"));
        itemsTickets.getChildren().add(new TreeItem("Vendidos", imagenHelper.getIcono(FontAwesomeIconName.CIRCLE_ALT, "12px", "-fx-fill: -fx-accent;")));
        itemsTickets.getChildren().add(new TreeItem("No Vendidos", imagenHelper.getIcono(FontAwesomeIconName.CIRCLE_ALT, "12px", "-fx-fill: -fx-accent;")));
        itemsTickets.getChildren().add(new TreeItem("Todos", imagenHelper.getIcono(FontAwesomeIconName.CIRCLE_ALT, "12px", "-fx-fill: -fx-accent;")));
        rootItem.getChildren().add(itemsTickets);
        
        rootItem.setExpanded(true);
        itemsCaja.setExpanded(true);
        itemsTickets.setExpanded(true);
        tvlista.setRoot(rootItem);
        
        try {
            rviewer.cargarPanel(preporte);
        }catch (Exception ex){ 
            Dialogo.mostrarError("Error al cargar el reporte", "Error", App.configuracion, ButtonType.OK); 
        }
        
    }
    
    @FXML
    void onclick(MouseEvent event) {
        TreeItem<String> selectedItem = (TreeItem<String>) tvlista.getSelectionModel().getSelectedItem();
        selectTreeView(selectedItem);
    }
    
    private void selectTreeView(TreeItem<String> selectedItem){
        if (pcaja == null)
            pcaja = new ParametrosCajaControlador(App);
        
        switch (selectedItem.getValue()){
            case "Cajas por fecha":
                pcaja.showFechas();
                
                if (pcaja.BotonPresionado == ParametrosCajaControlador.ButtonType.Ok){
                    try {
                        ObservableList<Caja> cajas = cm.getPorFecha(pcaja.getFechaInicio(), pcaja.getFechaFin());
                        JasperPrint jp = reporteHelper.getJasperPrint("/Reportes/Caja/CajasporFecha.jasper", cajas, generarParametros(pcaja, "hola", ""), App.configuracion);

                        if (cajas != null)
                            rviewer.cargarReporte("Reporte de cajas por fechas", jp);
                        else
                            rviewer.errorReporte("No se encontraron registros para generar un reporte.");

                        App.agregarEscena("reporte", rviewer.thisStage.getScene());

                    } catch (Exception ex) { 
                        System.out.println(ex.getMessage()); 
                        Dialogo.mostrarError(ex.getMessage(), "Error al cargar el reporte", App.configuracion, ButtonType.OK);
                    }
                }
                
                break;
            
            case "Detalles de caja":
                pcaja.showCaja();
                
                if (pcaja.BotonPresionado == ParametrosCajaControlador.ButtonType.Ok){
                    showDetalleCaja(pcaja.getNumeroCaja());
                }
                break;
            
            case "Caja actual":
                showDetalleCaja(App.cajaAbierta.getId());
                break;
                
            case "Vendidos":
                pcaja.showFechaTicket();
                if (pcaja.BotonPresionado == ParametrosCajaControlador.ButtonType.Ok){
                    try {
                        JasperPrint jp = reporteHelper.getJasperPrint("/Reportes/Tickets/Tickets.jasper", null, generarParametros(pcaja, "Reporte de Tickets Vendidos", "Vendido"), App.configuracion);
                        rviewer.cargarReporte("Reporte de tickets", jp);
                        
                        App.agregarEscena("reporte", rviewer.thisStage.getScene());
                    } catch (Exception ex) { 
                        System.out.println(ex.getMessage()); 
                        Dialogo.mostrarError(ex.getMessage(), "Error al cargar el reporte", App.configuracion, ButtonType.OK);
                    }
                }
                break;
                
            case "No Vendidos":
                pcaja.showFechaTicket();
                if (pcaja.BotonPresionado == ParametrosCajaControlador.ButtonType.Ok){
                    try {
                        JasperPrint jp = reporteHelper.getJasperPrint("/Reportes/Tickets/Tickets.jasper", null, generarParametros(pcaja, "Reporte de Tickets no Vendidos", "Activo"), App.configuracion);
                        rviewer.cargarReporte("Reporte de tickets", jp);
                        
                        App.agregarEscena("reporte", rviewer.thisStage.getScene());
                    } catch (Exception ex) { 
                        System.out.println(ex.getMessage()); 
                        Dialogo.mostrarError(ex.getMessage(), "Error al cargar el reporte", App.configuracion, ButtonType.OK);
                    }
                }
                break;
            
            case "Todos":
                pcaja.showFechaTicket();
                if (pcaja.BotonPresionado == ParametrosCajaControlador.ButtonType.Ok){
                    try {
                        JasperPrint jp = reporteHelper.getJasperPrint("/Reportes/Tickets/Tickets.jasper", null, generarParametros(pcaja, "Reporte de Tickets", ""), App.configuracion);
                        rviewer.cargarReporte("Reporte de tickets", jp);
                        
                        App.agregarEscena("reporte", rviewer.thisStage.getScene());
                    } catch (Exception ex) { 
                        System.out.println(ex.getMessage()); 
                        Dialogo.mostrarError(ex.getMessage(), "Error al cargar el reporte", App.configuracion, ButtonType.OK);
                    }
                }
                break;
                
            default: break;
        }
    }
    
    private Map<String,Object> generarParametros(ParametrosCajaControlador pcaja, String titulo, String estado){
        Map<String,Object> parametros = new HashMap<String,Object>();
        parametros.put("FechaInicio", pcaja.getFechaInicio());
        parametros.put("FechaFin", pcaja.getFechaFin()); 
        parametros.put("UsuarioLogueado", App.usuarioLogeado.getNombre()); 
        parametros.put("titulo", titulo);
        parametros.put("Estado", "%"+estado+"%"); 
        return parametros;
    } 
    
    public void showDetalleCaja(int caja){
        Map<String,Object> parametros = new HashMap<String,Object>();
        parametros.put("UsuarioLogueado", App.usuarioLogeado.getNombre()); 
        try {
            ObservableList<Caja> cajas = cm.getDetallesdeCaja(caja);
            JasperPrint jp = reporteHelper.getJasperPrint("/Reportes/Caja/DetallesdeCaja.jasper", cajas, parametros, App.configuracion);

            if (cajas != null)
                rviewer.cargarReporte("Reporte de caja", jp);
            else
                rviewer.errorReporte("No se encontraron registros para generar un reporte.");
            App.agregarEscena("reporte", rviewer.thisStage.getScene());
        } catch (Exception ex) { 
            System.out.println(ex.getMessage()); 
            Dialogo.mostrarError(ex.getMessage(), "Error al cargar el reporte", App.configuracion, ButtonType.OK);
        }
    }
}
