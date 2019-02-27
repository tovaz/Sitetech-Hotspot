/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sitetech.hotspot.Controladores;

import Util.ArrastrarScene;
import Util.util;
import com.jfoenix.controls.JFXComboBox;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import static javafx.collections.FXCollections.observableArrayList;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sitetech.hotspot.Modelos.Ticket;
import sitetech.hotspot.Modelos.TicketManager;

/**
 * FXML Controller class
 *
 * @author willi
 */
public class TicketsController implements Initializable, ArrastrarScene {

    private final Stage thisStage;

    @FXML
    private AnchorPane ticketsPanel;

    @FXML
    private TextField tnombre;

    @FXML
    private JFXComboBox<?> cbestado;

    @FXML
    private JFXComboBox<?> cbpaquetes;

    @FXML
    private TableView<Ticket> tvtickets;

    @FXML
    private AnchorPane panelTicket;

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
        tvtickets.setItems(listaTickets);
    }

    //******************************** FUNCIONES PRINCPALES *********************/
    @FXML
    void onBuscarAction(ActionEvent event) {

    }

    @FXML
    void onEliminarAction(ActionEvent event) {

    }

    @FXML
    void onGenerarAction(ActionEvent event) {
        GenerarTicketsController genTicket = new GenerarTicketsController();
        genTicket.showStage();
    }

    @FXML
    void onImprimirAction(ActionEvent event) {

    }

    @FXML
    void onVenderAction(ActionEvent event) {

    }
}
