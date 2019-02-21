/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sitetech.hotspot.Controladores;

import Util.Mikrotik;
import Util.cadenaAletoria;
import Util.claseRetorno;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXSpinner;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import static javafx.collections.FXCollections.observableArrayList;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import sitetech.hotspot.Modelos.Paquete;
import sitetech.hotspot.Modelos.PaqueteManager;
import sitetech.hotspot.Modelos.Router;
import sitetech.hotspot.Modelos.RouterManager;
import sitetech.hotspot.Modelos.Ticket;
import sitetech.hotspot.Modelos.TicketManager;
import sitetech.hotspot.Modelos.usuarioManager;

/**
 * FXML Controller class
 *
 * @author megan
 */
public class GenerarTicketsController implements Initializable {

    @FXML private ComboBox<Paquete> cbpaquetes;
    @FXML private ComboBox<Router> cbrouters;
    @FXML private ComboBox<String> cbusuario;
    @FXML private ComboBox<String> cbcontraseña;
    @FXML private TableView<Ticket> tvtickets;
    @FXML private JFXCheckBox checkNumeros;
    @FXML private TextField tcantidad;
    @FXML private Label lmensaje;
    @FXML private JFXSpinner sptrabajando;
    
    private final Stage thisStage;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    public GenerarTicketsController() {
        thisStage = new Stage();
        Util.util.cargarStage("/Vistas/Tickets/generarTickets.fxml", "Generador de tickets", thisStage, this, Modality.NONE);
    }

    public void showStage() {
        this.cargarDatos();
        thisStage.showAndWait();
    }
    
    public void cargarDatos(){
        cbusuario.getSelectionModel().select(5);
        cbcontraseña.getSelectionModel().select(4);
        
        PaqueteManager pm = new PaqueteManager();
        RouterManager rm = new RouterManager();
        cbpaquetes.getItems().addAll(pm.listaPaquetes);
        cbrouters.getItems().addAll(rm.listaRouters);
        
        this.actualizarTabla();
    }
    
    @FXML
    private void generarAction(ActionEvent event) {
        int longusuario = Integer.valueOf(cbusuario.getSelectionModel().getSelectedItem().toString());
        int longcontraseña = Integer.valueOf(cbcontraseña.getSelectionModel().getSelectedItem().toString());
        int cantidad = Integer.valueOf(tcantidad.getText());
        
        this.generarTickets(longusuario, longcontraseña, cantidad);
    }

    
    
    private ObservableList<Ticket> listaTickets;
    public void generarTickets(int longusuario, int longcontraseña, int cantidad)
    {
        listaTickets = observableArrayList();
        for (int i=1; i<=cantidad; i++){
            String contraseña;
            if (checkNumeros.isSelected())
                contraseña = cadenaAletoria.generarNumeros(longcontraseña);
            else
                contraseña = cadenaAletoria.generarCadena(longcontraseña);
            
            String usuario = crearUsuario(longusuario, listaTickets, listaTickets);
            Ticket tx = new Ticket(i, usuario, contraseña, Ticket.EstadosType.Generado, cbpaquetes.getValue());
            listaTickets.add(tx);
        }
        tvtickets.setItems(listaTickets);
    }
    
    public String crearUsuario(int longusuario, ObservableList<Ticket> lista1, ObservableList<Ticket> lista2){
        String usuario = cadenaAletoria.generarCadena(longusuario);
        for(Ticket tx : lista1)
            if ( tx.getUsuario().equals( usuario ) )
                crearUsuario(longusuario, lista1, lista2);
            
        for(Ticket tx : lista2) 
            if ( tx.getUsuario().equals( usuario ) )
                crearUsuario(longusuario, lista1, lista2);

        System.out.println("Usuario creado: " + usuario);
        return usuario;
    }
    
    public void actualizarTabla(){
        tvtickets.getColumns().get(0).setCellValueFactory( new PropertyValueFactory("Id") );
        tvtickets.getColumns().get(1).setCellValueFactory( new PropertyValueFactory("Usuario") );
        tvtickets.getColumns().get(2).setCellValueFactory( new PropertyValueFactory("Contraseña") );
        tvtickets.getColumns().get(3).setCellValueFactory( new PropertyValueFactory("Paquete") );
        tvtickets.getColumns().get(4).setCellValueFactory( new PropertyValueFactory("Estado") );
    }
    
    
    @FXML
    private void guardarAction(ActionEvent event) {
        lmensaje.setText("Espere mientras se guardan los usuarios en el router.");
        sptrabajando.setVisible(true);
        new Thread( () -> checkRouteryGuardar() ).start();
        //checkRouteryGuardar();
    }

    private void checkRouteryGuardar() {
        TicketManager tm = new TicketManager();
        Router rx = cbrouters.getValue();
        Mikrotik mk = new Mikrotik (rx.getIp(), rx.getUsuario(), rx.getPassword());
        int contadorUsuarios = 0;
        
        if ((boolean)mk.conectar().getDato()){
            for (Ticket tc : listaTickets){
                tc.setEstado(Ticket.EstadosType.Activo);
                claseRetorno cr = mk.agregarHotspotUsuario(tc.getUsuario(), tc.getContraseña(), tc.getPaquete());
                if (!(boolean)cr.getDato())
                    tc.setEstado(Ticket.EstadosType.Generado);
                else { 
                    tm.AgregarTicket(tc);
                    contadorUsuarios ++;
                }
            }
        }
        lmensaje.setText(contadorUsuarios + " tickets creados...");
        sptrabajando.setVisible(false);
    }
    
    @FXML
    private void imprimirAction(ActionEvent event) {
    }
    
}
