/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sitetech.hotspot.Controladores;

import Util.ArrastrarScene;
import Util.Dialogo;
import Util.Mikrotik;
import Util.Moneda;
import Util.StageManager;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXSpinner;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
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
import sitetech.hotspot.MainApp;
import sitetech.hotspot.Modelos.Paquete;
import sitetech.hotspot.Modelos.PaqueteManager;
import sitetech.hotspot.Modelos.Router;
import sitetech.hotspot.Modelos.RouterManager;
import sitetech.hotspot.Modelos.Ticket;
import sitetech.hotspot.Modelos.TicketManager;
import sitetech.hotspot.Temas;

/**
 * FXML Controller class
 *
 * @author willi
 */
public class TicketsController implements Initializable, ArrastrarScene {

    public final Stage thisStage;

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
    
    @FXML private Label lusuario;
    @FXML private Label lcontraseña;
    @FXML private Label lip;
    @FXML private Label lmac;
    @FXML private Label lestado;
    @FXML private Label ltiempoConsumido;
    @FXML private Label lconsumidoDown;
    @FXML private JFXSpinner spConsumidoDown;
    @FXML private Label lconsumidoUp;
    @FXML private JFXSpinner spConsumidoUp;
    @FXML private Label lpaquete;
    @FXML private Label lprecio;
    @FXML private Label llimiteDescarga;
    @FXML private Label llimiteSubida;
    @FXML private Label llimiteTiempo;
    
    private MainApp App;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //this.ArrastrarScene(panelTicket);
    }

    public ObservableList<Ticket> listaTickets;
    private TicketManager tm;

    public TicketsController(MainApp _app) {
        thisStage = new Stage();
        App = _app;
        listaTickets = observableArrayList();
        tm = new TicketManager();

        listaTickets = tm.getTickets();
    }

    public void cargarPanel(AnchorPane panel) throws IOException {
        Node nodo = (Node) StageManager.cargarEscenaEnPanel("/Vistas/Tickets/Tickets.fxml", "Tickets", this);
        AnchorPane.setTopAnchor(nodo, 0.0);

        AnchorPane.setLeftAnchor(nodo, 0.0);
        AnchorPane.setRightAnchor(nodo, 0.0);
        AnchorPane.setBottomAnchor(nodo, 0.0);
        panel.getChildren().setAll(nodo);

        actualizarTabla();
        cargarDatos();
        
        tvtickets.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> { llenarDetalles(newSelection); });
    }

    public void showStage() {
        thisStage.show();
    }

    public void actualizarTabla() {
        tvtickets.getColumns().get(0).setCellValueFactory(new PropertyValueFactory("Id"));
        tvtickets.getColumns().get(1).setCellValueFactory(new PropertyValueFactory("Usuario"));
        
        //TableColumn paqueteColum = tvtickets.getColumns().get(2);
        //paqueteColum.setCellValueFactory(cellData -> ((Ticket) cellData).getPaquete().getNombre() );
        tvtickets.getColumns().get(3).setCellValueFactory(new PropertyValueFactory("Paquete.getNombre()"));
        tvtickets.getColumns().get(3).setCellValueFactory(new PropertyValueFactory("Estado"));
        
        tvtickets.getColumns().get(4).setCellValueFactory(new PropertyValueFactory("fechaCreacion"));
        tvtickets.getColumns().get(5).setCellValueFactory(new PropertyValueFactory("duracion"));
        tvtickets.getColumns().get(6).setCellValueFactory(new PropertyValueFactory("LimiteInternetDown"));
        tvtickets.getColumns().get(7).setCellValueFactory(new PropertyValueFactory("LimiteInternetUp"));

    
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

    //****************************************************************************************
    //<editor-fold desc="ACCIONES AL ELIMINAR UN TICKET">  **************************
    //****************************************************************************************
    //****************************************************************************************
    Thread th;
    @FXML
    void onEliminarAction(ActionEvent event) {
         ObservableList<Ticket> ticketsSeleccionados = tvtickets.getSelectionModel().getSelectedItems();
         
        if (ticketsSeleccionados != null) {
            Router rt = ticketsSeleccionados.get(0).getRouter();
            Mikrotik mk = new Mikrotik(rt.getIp(), rt.getUsuario(), rt.getPassword());
            ButtonType btn;
            
            if (ticketsSeleccionados.size() == 1)
                btn = Dialogo.mostrarInformacion("¿Desea realmente eliminar el ticket con usuario = \" " + ticketsSeleccionados.get(0).getUsuario()  + " \" ?, se eliminara del router tambien.", "Eliminar Ticket", App.configuracion.getColorTema(), ButtonType.YES, ButtonType.NO);
            else 
                btn = Dialogo.mostrarInformacion("¿Desea realmente eliminar " + ticketsSeleccionados.size() + " ? tickets, se eliminaran del router tambien.", "Eliminar Tickets", App.configuracion.getColorTema(), ButtonType.YES, ButtonType.NO);
            
            if ( btn == ButtonType.YES) {
                if (ticketsSeleccionados.size() == 1)
                    ltrabajando.setText("Espere mientras se eliminan el ticket \"" + ticketsSeleccionados.get(0).getUsuario()  + "\" .");
                else
                    ltrabajando.setText("Espere mientras se eliminan los tickets.");
                
                ptrabajando.setVisible(true);
                beliminar.setDisable(true);
                th = new Thread(() -> eliminarVariosTickets(ticketsSeleccionados, mk));
                th.start();
            }
        }
        else
            Dialogo.mostrarAlerta("Debe de seleccionar algun ticket para poder eliminarlo.", "Eliminar Ticket", App.configuracion.getColorTema(), ButtonType.OK);
    }
    
    private void eliminarVariosTickets(ObservableList<Ticket> ticketsSeleccinados, Mikrotik mk){
        int ticketsEliminados = 0;
        int errores=0;
        for (int i = 0; i < ticketsSeleccinados.size(); i++) {
            if (errores == 3) { th.interrupt(); break; }
            
            Ticket tc = ticketsSeleccinados.get(i);
            if (mk.eliminarHotspotUsuario(tc.getUsuario())){
                tm.EliminarTicket(tc);
                listaTickets.remove(tc);
                ticketsEliminados ++;
            }
            else errores++;
        }
        
        final int tickets = ticketsEliminados;
        Platform.runLater(() -> hiloTermino(tickets, ticketsSeleccinados));
    }

    private void hiloTermino(int ticketsEliminados, ObservableList<Ticket> ticketsSeleccinados) {
        ltrabajando.setText("Se eleminaron " + ticketsEliminados + " correctamente.");
        
        if (ticketsEliminados == ticketsSeleccinados.size()) {
            Dialogo.mostrarInformacion("Se eliminaron " + ticketsEliminados + " tickets correctamente.", "Todos los tickets se eliminaron.", App.configuracion.getColorTema(), ButtonType.OK);
        } else {
            Dialogo.mostrarError("Se eliminaron solamente " + ticketsEliminados + " tickets correctamente.", "No se eliminaron todos los tickets.", App.configuracion.getColorTema(), ButtonType.OK);
        }
        
        beliminar.setDisable(false);
        ptrabajando.setVisible(false);
    }
        
    //****************************************************************************************
    //</editor-fold>
    //****************************************************************************************
    
    GenerarTicketsController genTicket;
    @FXML
    void onGenerarAction(ActionEvent event) {
        if (genTicket == null)
            genTicket = new GenerarTicketsController(this, App);
        
        genTicket.showStage();
    }

    @FXML
    void onImprimirAction(ActionEvent event) {
        ObservableList<Ticket> ticketsSeleccionados = tvtickets.getSelectionModel().getSelectedItems();
        if (ticketsSeleccionados != null)
            reporteHelper.imprimirTickets(ticketsSeleccionados);
        else 
            Util.util.mostrarAlerta("Debe de seleccionar uno o mas tickets para imprimir ", "No hay tickets seleccionados para imprimir", ButtonType.OK);
    }

    @FXML
    void onVenderAction(ActionEvent event) {
        ObservableList<Ticket> ticketsSeleccionados = tvtickets.getSelectionModel().getSelectedItems();
         
        if (ticketsSeleccionados != null) {
            if (ticketsSeleccionados.size() == 1){
                VenderTicketController vtc = new VenderTicketController(this, App);
                System.out.println(ticketsSeleccionados.get(0));
                vtc.cargarDatos(ticketsSeleccionados.get(0));
                vtc.showStage();
            }
            else 
                Dialogo.mostrarError("Solamente puede seleccionar un ticket para vender. ", "Seleccione unicamente un ticket.", App.configuracion.getColorTema(), ButtonType.OK);
        }
        else
            Dialogo.mostrarError("Debe de seleccionar un ticket para vender.", "No a seleccionado ningun ticket", App.configuracion.getColorTema(), ButtonType.OK);
    }
    
    void llenarDetalles(Ticket tc) {
        if (tc != null){
            lusuario.setText(tc.getUsuario());
            lcontraseña.setText(tc.getContraseña());
            lip.setText(tc.getIp());
            lmac.setText(tc.getMac());
            lestado.setText(tc.getEstado().name());

            ltiempoConsumido.setText(tc.getDuracionConsumida());
            lconsumidoDown.setText(tc.getmegasConsumidoDown().toString() + " Mb ");
            lconsumidoUp.setText(tc.getmegasConsumidoUp().toString() + " Mb ");

            spConsumidoDown.setProgress( tc.getmegasConsumidoDown() / ( (tc.getLimiteGigasDown() * 1024) + tc.getLimiteMegasDown() ) );
            spConsumidoUp.setProgress(tc.getPorcentaje(tc.getmegasConsumidoUp(), (tc.getLimiteGigasUp() * 1024) + tc.getLimiteMegasUp()) );

            lpaquete.setText(tc.getPaquete().getNombre());
            lprecio.setText(Moneda.Formatear(tc.getPaquete().getPrecio(), App.configuracion.getRegionLocal().getLocale()));
            llimiteTiempo.setText(tc.getPaquete().getDuracion());
            llimiteDescarga.setText(tc.getPaquete().getLimiteDescargaS());
            llimiteSubida.setText(tc.getPaquete().getLimiteSubidaS());
        }
        else {
            lusuario.setText("");
            lcontraseña.setText("");
            lip.setText("");
            lmac.setText("");
            lestado.setText("");

            ltiempoConsumido.setText("");
            lconsumidoDown.setText("");
            lconsumidoUp.setText("");

            spConsumidoDown.setProgress(0);
            spConsumidoUp.setProgress(0);

            lpaquete.setText("");
            lprecio.setText(Moneda.Formatear(new BigDecimal(0.00), App.configuracion.getRegionLocal().getLocale()));
            llimiteTiempo.setText("");
            llimiteDescarga.setText("");
        }
    }
}
