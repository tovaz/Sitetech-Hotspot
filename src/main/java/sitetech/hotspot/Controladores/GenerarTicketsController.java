/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sitetech.hotspot.Controladores;

import Util.Dialogo;
import Util.Mikrotik;
import Util.StageManager;
import Util.Validar;
import Util.cadenaAletoria;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXSpinner;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import static javafx.collections.FXCollections.observableArrayList;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.swing.UnsupportedLookAndFeelException;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import sitetech.Helpers.reporteHelper;
import sitetech.hotspot.MainApp;
import sitetech.hotspot.Modelos.Paquete;
import sitetech.hotspot.Modelos.PaqueteManager;
import sitetech.hotspot.Modelos.Router;
import sitetech.hotspot.Modelos.RouterManager;
import sitetech.hotspot.Modelos.Ticket;
import sitetech.hotspot.Modelos.TicketManager;

/**
 * FXML Controller class
 *
 * @author megan
 */
public class GenerarTicketsController implements Initializable {
    @FXML private JFXComboBox<Paquete> cbpaquetes;
    @FXML private JFXComboBox<Router> cbrouters;
    @FXML private JFXComboBox<String> cbusuario;
    @FXML private JFXComboBox<String> cbcontraseña;
    @FXML private TableView<Ticket> tvtickets;
    @FXML private JFXCheckBox checkNumeros;
    @FXML private JFXCheckBox checkCodigos;
    
    @FXML private TextField tcantidad;
    @FXML private Label ltrabajando;
    @FXML private JFXSpinner sptrabajando;
    @FXML private HBox pbotones;

    @FXML private JFXButton bgenerar;
    @FXML private JFXButton bguardar;
    @FXML private JFXButton bimprimir;

    @FXML private Label lecantidad;
    @FXML private Label lepaquete;
    @FXML private Label lerouter;
    
    public final Stage thisStage;
    private TicketManager tm;
    private MainApp App;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    private TicketsController tc;
    public GenerarTicketsController(TicketsController _tc, MainApp _app) {
        tc = _tc;
        App = _app;
        thisStage = new Stage();
        StageManager.cargarStage("/Vistas/Tickets/generarTickets.fxml", "Generador de tickets", thisStage, this, Modality.NONE, App.configuracion);
        
        App.agregarEscena("scene_generarTickets", thisStage.getScene());
    }

    public void showStage() {
        this.cargarDatos();
        thisStage.showAndWait();
    }

    public void cargarDatos() {
        tm = new TicketManager();
        cbusuario.getSelectionModel().select(5);
        cbcontraseña.getSelectionModel().select(4);

        PaqueteManager pm = new PaqueteManager();
        RouterManager rm = new RouterManager();
        cbpaquetes.getItems().setAll(pm.listaPaquetes);
        cbrouters.getItems().setAll(rm.listaRouters);

        bguardar.setDisable(true);
        bimprimir.setDisable(true);
        this.actualizarTabla();
    }

    @FXML
    private void generarAction(ActionEvent event) {
        if ( validarCampos() ){
            int longusuario = Integer.valueOf(cbusuario.getSelectionModel().getSelectedItem().toString());
            int longcontraseña = Integer.valueOf(cbcontraseña.getSelectionModel().getSelectedItem().toString());
            int cantidad = Integer.valueOf(tcantidad.getText());

        
            if (listaTickets == null )
                this.generarTickets(longusuario, longcontraseña, cantidad);
            else{
                if (Dialogo.mostrarConfirmacion("Se perderan los tickets que actualmente se generaron y no se guardaron.", "¿Desea generar mas Tickets?", 
                        App.configuracion, ButtonType.YES, ButtonType.NO) == ButtonType.YES)
                    this.generarTickets(longusuario, longcontraseña, cantidad);
            }
        }
    }
    
    private boolean validarCampos()
    {
        return !Validar.esTextfieldVacio(tcantidad, lecantidad, "Escriba un numero.") &&
        Validar.esComboboxCorrecto(cbpaquetes, lepaquete, "Debe seleccionar un paquete.") &&
        Validar.esComboboxCorrecto(cbrouters, lerouter, "Debe seleccionar un router.");
    }

    private ObservableList<Ticket> listaTickets;
    public void generarTickets(int longusuario, int longcontraseña, int cantidad) {
        listaTickets = observableArrayList();
        ObservableList<Ticket> listaTicketsRt = tm.getticketsRouter(cbrouters.getValue()); // Obtener lista de usuarios del router
        bguardar.setDisable(true);
        
        for (int i = 1; i <= cantidad; i++) {
            if (listaTicketsRt == null) {
                Dialogo.mostrarError("Porfavor verifica la coneccion con el router y vuelve a intentar.", "Error al comunicarse con el router.", 
                        App.configuracion, ButtonType.OK); 
                break; 
            }
            
            String contraseña = generarContraseña(longcontraseña);
            if (checkCodigos.isSelected())
                contraseña= App.configuracion.getDefaultUsername();
            
            String usuario = crearUsuario(longusuario, listaTicketsRt, listaTickets);
            Ticket tx = new Ticket(i, usuario, contraseña, Ticket.EstadosType.Generado, cbpaquetes.getValue(), cbrouters.getValue());
            listaTickets.add(tx);
        }
        
        
        tvtickets.setItems(listaTickets);
        bimprimir.setDisable(true);
        
        if (listaTickets.size() == Integer.valueOf(tcantidad.getText()) )
            bguardar.setDisable(false);
        else 
            bguardar.setDisable(true);
    }

    public String crearUsuario(int longusuario, ObservableList<Ticket> lista1, ObservableList<Ticket> lista2) {
        String usuario = cadenaAletoria.generarCadena(longusuario);
        
        for (Ticket tx : lista1) {
            if (tx.getUsuario().equals(usuario)) {
                crearUsuario(longusuario, lista1, lista2);
                System.err.println("Usuario repetido: " + usuario);
            }
        }

        for (Ticket tx : lista2) {
            if (tx.getUsuario().equals(usuario)) {
                crearUsuario(longusuario, lista1, lista2);
                System.err.println("Usuario repetido: " + usuario);
            }
        }

        System.out.println("Usuario creado: " + usuario);
        return usuario;
    }
    
    public String generarContraseña(int longcontraseña){
        if (checkNumeros.isSelected()) {
            return cadenaAletoria.generarNumeros(longcontraseña);
        } else {
            return cadenaAletoria.generarCadena(longcontraseña);
        }
    }
    

    public void actualizarTabla() {
        tvtickets.getColumns().get(0).setCellValueFactory(new PropertyValueFactory("Id"));
        tvtickets.getColumns().get(1).setCellValueFactory(new PropertyValueFactory("Usuario"));
        tvtickets.getColumns().get(2).setCellValueFactory(new PropertyValueFactory("Contraseña"));
        tvtickets.getColumns().get(3).setCellValueFactory(new PropertyValueFactory("Paquete"));
        tvtickets.getColumns().get(4).setCellValueFactory(new PropertyValueFactory("Estado"));
    }

    Thread th;
    @FXML
    private void guardarAction(ActionEvent event) throws InterruptedException {
        ltrabajando.setText("Espere mientras se crean los tickets en el router.");
        sptrabajando.setVisible(true);
        pbotones.setDisable(true);

        th = new Thread(() -> guardarTickets());

        th.start();
    }

    private void guardarTickets() {
        
        Router rx = cbrouters.getValue();
        Mikrotik mk = new Mikrotik(rx.getIp(), rx.getUsuario(), rx.getPassword());
        int ctickets = 0;
        int errores = 0;
        for (int i = 0; i < listaTickets.size(); i++) {
            if (errores == 3){
                th.interrupt();
                break;
            }
            
            Ticket tc = listaTickets.get(i);
            tc.setEstado(Ticket.EstadosType.Activo);
            boolean agreagadoExitoso = mk.agregarHotspotUsuario(tc.getUsuario(), tc.getContraseña(), tc.getPaquete());
            if (agreagadoExitoso) {
                tm.AgregarTicket(tc);
                ctickets++;
                listaTickets.set(i, tc);
            } else {
                tc.setEstado(Ticket.EstadosType.Generado);
                errores ++;
            }
        }

        final int cxtickets = ctickets;
        Platform.runLater(() -> hiloTermino(cxtickets));
    }

    private void hiloTermino(int ctickets) {
        sptrabajando.setVisible(false);
        pbotones.setDisable(false);
        System.out.println(ctickets);

        ltrabajando.setText("Se generaron " + ctickets + " tickets correctamente.");
        if (ctickets == listaTickets.size()) {
            Dialogo.mostrarInformacion("Se guardaron " + ctickets + " tickets correctamente.", "Todos los tickets se guardaron.", 
                    App.configuracion, ButtonType.OK);
        } else {
            Dialogo.mostrarError("Se guardaron solamente " + ctickets + " tickets correctamente.", "No se guardaron todos los tickets.", 
                    App.configuracion, ButtonType.OK);
        }

        
        if (ctickets > 0) { bimprimir.setDisable(false); }
        
        tvtickets.getItems().removeAll();
        tvtickets.setItems(listaTickets);
        tc.listaTickets.addAll(listaTickets);
    }

    @FXML
    private void imprimirAction(ActionEvent event) throws IOException, JRException, ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
        JasperPrint jp = reporteHelper.getJasperPrintTicket(listaTickets, App.configuracion);
        if (jp != null){
            ReporteViewerControlador rview = new ReporteViewerControlador(App);
            rview.mostrarReporte("Reporte de tickets generados", jp);
        }
        else
            Dialogo.mostrarError("Error no se pudo generar el reporte.", "Error al generar el reporte.", App.configuracion, ButtonType.OK);
        
        //reporteHelper.imprimirTickets(listaTickets, App.configuracion);
    }

}
