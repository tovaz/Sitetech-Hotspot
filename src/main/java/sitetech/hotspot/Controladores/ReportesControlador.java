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

/**
 * FXML Controller class
 *
 * @author megan
 */
public class ReportesControlador extends MiControlador {

    @FXML private AnchorPane preporte;
    @FXML private TreeView<String> tvlista;
    private ReporteViewerControlador rviewer;
    
    public ReportesControlador(MainApp _app) {
        //(String escena, String titulo, Modality modalidad, Configuracion conf)
        cargarEscena("/Reportes/Reportes.fxml", "Reportes", Modality.WINDOW_MODAL, _app);
        rviewer = new ReporteViewerControlador(App);
        cargarInfo();
    }
    
    private void cargarInfo(){
        TreeItem rootItem = new TreeItem("Reportes", imagenHelper.getIcono(FontAwesomeIconName.FILE_TEXT_ALT, "12px", "-fx-fill: -fx-accent;"));

        TreeItem itemsCaja = new TreeItem("Caja", imagenHelper.getIcono(FontAwesomeIconName.MONEY, "12px", "-fx-fill: -fx-accent;"));
        itemsCaja.getChildren().add(new TreeItem("Cajas por fecha", imagenHelper.getIcono(FontAwesomeIconName.CIRCLE_ALT, "12px", "-fx-fill: -fx-accent;")));
        itemsCaja.getChildren().add(new TreeItem("Detalles de caja", imagenHelper.getIcono(FontAwesomeIconName.CIRCLE_ALT, "12px", "-fx-fill: -fx-accent;")));
        rootItem.getChildren().add(itemsCaja);
        
        TreeItem itemsTickets = new TreeItem("Tickets", imagenHelper.getIcono(FontAwesomeIconName.TAGS, "12px", "-fx-fill: -fx-accent;"));
        itemsTickets.getChildren().add(new TreeItem("Vendidos", imagenHelper.getIcono(FontAwesomeIconName.CIRCLE_ALT, "12px", "-fx-fill: -fx-accent;")));
        itemsTickets.getChildren().add(new TreeItem("Sin Vender", imagenHelper.getIcono(FontAwesomeIconName.CIRCLE_ALT, "12px", "-fx-fill: -fx-accent;")));
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
    
    ParametrosCajaControlador pcaja;
    private void selectTreeView(TreeItem<String> selectedItem){
        CajaManager cm = new CajaManager();
        System.out.println("Selected Text : " + selectedItem.getValue());
        switch (selectedItem.getValue()){
            case "Cajas por fecha":
                pcaja = new ParametrosCajaControlador(App, ParametrosCajaControlador.VistaType.Fecha);
                pcaja.showAndWait();
                Map<String,Object> parametros = new HashMap<String,Object>();
                parametros.put("FechaInicio", pcaja.getFechaInicio());
                parametros.put("FechaFin", pcaja.getFechaFin()); 
                parametros.put("UsuarioLogueado", App.usuarioLogeado.getNombre()); 

                try {
                    ObservableList<Caja> cajas = cm.getPorFecha(pcaja.getFechaInicio(), pcaja.getFechaFin());
                    JasperPrint jp = reporteHelper.getJasperPrint("/Reportes/Caja/CajasporFecha.jasper", cajas, parametros, App.configuracion);
                    
                    //UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    
                    rviewer.cargarReporte("Reporte de cajas por fechas", jp);
                    App.agregarEscena("reporte", rviewer.thisStage.getScene());
                
                    /*JasperViewer jv = new JasperViewer(jp, false);
                    jv.setTitle("Reporte de cajas");
                    jv.setIconImage(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/ticketprint.png")).getImage());
                    jv.setVisible(true);*/
                } catch (Exception ex) { 
                    System.out.println(ex.getMessage()); 
                    Dialogo.mostrarError(ex.getMessage(), "Error al cargar el reporte", App.configuracion, ButtonType.OK);
                }
                
                /*ObservableList<Ticket> ticketsSeleccionados = new TicketManager().getTickets();
                JasperPrint jp = reporteHelper.getJasperPrintTicket(ticketsSeleccionados, App.configuracion);
                ReporteViewerControlador rview = new ReporteViewerControlador(App);
                rviewer.cargarReporte("Reporte de tickets generados", jp);
                */
                break;
            
            case "Detalles de caja":
                pcaja = new ParametrosCajaControlador(App, ParametrosCajaControlador.VistaType.Caja);
                pcaja.showAndWait();
                
                Map<String,Object> parametros2 = new HashMap<String,Object>();
                parametros2.put("UsuarioLogueado", App.usuarioLogeado.getNombre()); 

                try {
                    ObservableList<Caja> cajas = cm.getDetallesdeCaja(pcaja.getNumeroCaja());
                    JasperPrint jp = reporteHelper.getJasperPrint("/Reportes/Caja/DetallesdeCaja.jasper", cajas, parametros2, App.configuracion);
                    
                    rviewer.cargarReporte("Reporte de caja", jp);
                    App.agregarEscena("reporte", rviewer.thisStage.getScene());
                } catch (Exception ex) { 
                    System.out.println(ex.getMessage()); 
                    Dialogo.mostrarError(ex.getMessage(), "Error al cargar el reporte", App.configuracion, ButtonType.OK);
                }
                break;
            
            case "Vendidos":
                break;
                
            case "Sin Vender":
                break;
        }
    }
    
}
