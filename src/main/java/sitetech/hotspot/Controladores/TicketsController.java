/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sitetech.hotspot.Controladores;

import Util.ArrastrarScene;
import Util.Dialogo;
import Util.Mikrotik;
import Util.util;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXSpinner;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import static javafx.collections.FXCollections.observableArrayList;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sitetech.Helpers.reporteHelper;
import sitetech.hotspot.Modelos.Paquete;
import sitetech.hotspot.Modelos.PaqueteManager;
import sitetech.hotspot.Modelos.Router;
import sitetech.hotspot.Modelos.RouterManager;
import sitetech.hotspot.Modelos.Ticket;
import sitetech.hotspot.Modelos.TicketManager;

/**
 * FXML Controller class
 *
 * @author willi
 */
public class TicketsController implements Initializable, ArrastrarScene {

    private final Stage thisStage;

    @FXML private AnchorPane ticketsPanel;
    @FXML private TextField tnombre;
    @FXML private JFXComboBox<Ticket.EstadosType> cbestado;
    @FXML private JFXComboBox<Paquete> cbpaquetes;
    @FXML private TableView<Ticket> tvtickets;
    @FXML private AnchorPane panelTicket;
    @FXML private JFXButton beliminar;
    @FXML private VBox ptrabajando;
    @FXML private Label ltrabajando;
    @FXML private JFXSpinner sptrabajando;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //this.ArrastrarScene(panelTicket);
    }

    private ObservableList<Ticket> listaTickets;
    private TicketManager tm;

    public TicketsController() {
        thisStage = new Stage();

        listaTickets = observableArrayList();
        tm = new TicketManager();

        listaTickets = tm.getTickets();

        //Util.util.cargarStage("/Vistas/Tickets/Tickets.fxml", "Tickets", thisStage, this, Modality.APPLICATION_MODAL);
    }

    public void cargarPanel(AnchorPane panel) throws IOException {
        //FXMLLoader fl =  FXMLLoader.load(getClass().getResource("/Vistas/Tickets/Tickets.fxml"));
        //fl.setController(this);
        Node nodo = (Node) util.cargarEscenaEnPanel("/Vistas/Tickets/Tickets.fxml", "Tickets", this);
        AnchorPane.setTopAnchor(nodo, 0.0);

        AnchorPane.setLeftAnchor(nodo, 0.0);
        AnchorPane.setRightAnchor(nodo, 0.0);
        AnchorPane.setBottomAnchor(nodo, 0.0);
        panel.getChildren().setAll(nodo);

        actualizarTabla();
        cargarDatos();
    }

    public void showStage() {
        thisStage.show();
    }

    public void actualizarTabla() {
        tvtickets.getColumns().get(0).setCellValueFactory(new PropertyValueFactory("Id"));
        tvtickets.getColumns().get(1).setCellValueFactory(new PropertyValueFactory("Usuario"));
        tvtickets.getColumns().get(2).setCellValueFactory(new PropertyValueFactory("Contraseña"));
        tvtickets.getColumns().get(3).setCellValueFactory(new PropertyValueFactory("Paquete"));
        tvtickets.getColumns().get(4).setCellValueFactory(new PropertyValueFactory("Estado"));
        
        tvtickets.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tvtickets.setItems(listaTickets);
    }

    public void cargarDatos() {
        PaqueteManager pm = new PaqueteManager();
        RouterManager rm = new RouterManager();
        cbpaquetes.getItems().addAll(pm.listaPaquetes);
        cbpaquetes.getItems().add(0, new Paquete(true));
        
        cbestado.getItems().setAll(Ticket.EstadosType.values());
        
        cbpaquetes.getSelectionModel().select(0);
        cbestado.getSelectionModel().select(0);

        ptrabajando.setVisible(false);
        this.actualizarTabla();
    }
    
    //******************************** FUNCIONES PRINCPALES *********************/
    @FXML
    void onBuscarAction(ActionEvent event) {
        listaTickets = tm.buscarTickets(tnombre.getText(), cbpaquetes.getValue(), cbestado.getValue());
        tvtickets.setItems(listaTickets);
    }

    @FXML
    void onEliminarAction(ActionEvent event) {
         ObservableList<Ticket> ticketsSeleccionados = tvtickets.getSelectionModel().getSelectedItems();
         
        if (ticketsSeleccionados != null) {
            Router rt = ticketsSeleccionados.get(0).getRouter();
            Mikrotik mk = new Mikrotik(rt.getIp(), rt.getUsuario(), rt.getPassword());
            ButtonType btn;
            
            if (ticketsSeleccionados.size() == 1)
                btn = Util.util.mostrarAlerta("¿Desea realmente eliminar el ticket con usuario = \" " + ticketsSeleccionados.get(0).getUsuario()  + " \" ?, se eliminara del router tambien.", "Eliminar Ticket", ButtonType.YES, ButtonType.NO);
            else 
                btn = Util.util.mostrarAlerta("¿Desea realmente eliminar " + ticketsSeleccionados.size() + " ? tickets, se eliminaran del router tambien.", "Eliminar Tickets", ButtonType.YES, ButtonType.NO);
            if ( btn == ButtonType.YES) {
                if (ticketsSeleccionados.size() == 1){
                    if (mk.Conectar()){
                        if (mk.eliminarHotspotUsuario(ticketsSeleccionados.get(0).getUsuario()))
                            tm.EliminarTicket(ticketsSeleccionados.get(0));
                    }
                    else
                        Util.util.mostrarAlerta("Error al eliminar el ticket en el router, verifica la coneccion y vuelve a intentar.", "Error al eliminar ticket", ButtonType.OK);
                }else {
                    ptrabajando.setVisible(true);
                    beliminar.setDisable(true);
                    ltrabajando.setText("Espere mientras se eliminan los tickets.");
                    Thread th = new Thread(() -> eliminarVariosTickets(ticketsSeleccionados, mk));
                    th.start();
                }
            }
        }
        else
            Util.util.mostrarAlerta("Debe de seleccionar algun ticket para poder eliminarlo.", "Eliminar Ticket", ButtonType.OK);
    }
    
    private void eliminarVariosTickets(ObservableList<Ticket> ticketsSeleccinados, Mikrotik mk){
        int ticketsEliminados = 0;
        for (int i = 0; i < ticketsSeleccinados.size(); i++) {
            Ticket tc = ticketsSeleccinados.get(i);
            if (mk.eliminarHotspotUsuario(tc.getUsuario())){
                tm.EliminarTicket(tc);
                ticketsEliminados ++;
            }
        }
        
        final int tickets = ticketsEliminados;
        Platform.runLater(() -> hiloTermino(tickets, ticketsSeleccinados));
    }

    private void hiloTermino(int ticketsEliminados, ObservableList<Ticket> ticketsSeleccinados) {
        ltrabajando.setText("Se eleminaron " + ticketsEliminados + " correctamente.");
        
        if (ticketsEliminados == ticketsSeleccinados.size()) {
            Dialogo.mostrarInformacion("Se eliminaron " + ticketsEliminados + " tickets correctamente.", "Todos los tickets se eliminaron.", ButtonType.OK);
        } else {
            Dialogo.mostrarError("Se eliminaron solamente " + ticketsEliminados + " tickets correctamente.", "No se eliminaron todos los tickets.", ButtonType.OK);
        }
        
        beliminar.setDisable(false);
        ptrabajando.setVisible(false);
    }
        
    @FXML
    void onGenerarAction(ActionEvent event) {
        GenerarTicketsController genTicket = new GenerarTicketsController();
        genTicket.showStage();
    }

    @FXML
    void onImprimirAction(ActionEvent event) {
        ObservableList<Ticket> ticketsSeleccionados = tvtickets.getSelectionModel().getSelectedItems();
        reporteHelper.imprimirTickets(ticketsSeleccionados);
    }

    @FXML
    void onVenderAction(ActionEvent event) {

    }
}
