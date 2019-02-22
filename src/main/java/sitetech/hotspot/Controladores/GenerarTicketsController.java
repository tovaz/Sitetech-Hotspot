/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sitetech.hotspot.Controladores;

import Util.Dialogo;
import Util.Mikrotik;
import Util.cadenaAletoria;
import Util.claseRetorno;
import Util.util;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXSpinner;
import java.net.URL;
import java.util.ResourceBundle;
import static javafx.collections.FXCollections.observableArrayList;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
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

    @FXML private ComboBox<Paquete> cbpaquetes;
    @FXML private ComboBox<Router> cbrouters;
    @FXML private ComboBox<String> cbusuario;
    @FXML private ComboBox<String> cbcontraseña;
    @FXML private TableView<Ticket> tvtickets;
    @FXML private JFXCheckBox checkNumeros;
    @FXML private TextField tcantidad;
    @FXML private Label ltrabajando;
    @FXML private JFXSpinner sptrabajando;
    @FXML private HBox pbotones;
    
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
    private void guardarAction(ActionEvent event) throws InterruptedException {
        mensaje("Espere mientras se crean los tickets en el router.");
        sptrabajando.setVisible(true);
        pbotones.setDisable(true);
        
        ctickets = 0;
        Thread thread = new Thread(){
            @Override
            public void run(){      
                ctickets = checkRouteryGuardar();
                while (true) {
                  if (Thread.interrupted()) {
                    hiloTermino(ctickets);
                  }
                  // Continue to do nothing
                }
              
            }
        };

        thread.start();
        //thread.join();
        //hiloTermino(ctickets);
        //new Thread( () -> checkRouteryGuardar() ).start();
        //checkRouteryGuardar();
    }

    int ctickets;
    private int checkRouteryGuardar() {
        TicketManager tm = new TicketManager();
        Router rx = cbrouters.getValue();
        Mikrotik mk = new Mikrotik (rx.getIp(), rx.getUsuario(), rx.getPassword());
        ctickets = 0;

        for (int i=0; i<listaTickets.size(); i++){
            Ticket tc = listaTickets.get(i);
            tc.setEstado(Ticket.EstadosType.Activo);
            claseRetorno cr = mk.agregarHotspotUsuario(tc.getUsuario(), tc.getContraseña(), tc.getPaquete());
            if ((boolean)cr.getDato()){
                tm.AgregarTicket(tc);
                ctickets++;
            }
            else tc.setEstado( Ticket.EstadosType.Generado );
        }
        
        return ctickets;
    }
    
    private void hiloTermino(int ctickets){
        sptrabajando.setVisible(false);
        pbotones.setDisable(false);
        System.out.println(ctickets);
        
        if (ctickets == listaTickets.size())
            Dialogo.mostrarInformacion("Se generaron "+ ctickets + " tickets correctamente.", "Todos los tickets se guardaron.", ButtonType.OK);
        else
            Dialogo.mostrarError("Se generaron "+ ctickets + " tickets correctamente.", "No se guardaron todos los tickets.", ButtonType.OK);
        mensaje("Se generaron "+ ctickets + " tickets correctamente.");
    }
    
    private void mensaje(String msg){
        ltrabajando.setText(msg);
    }
    
    @FXML
    private void imprimirAction(ActionEvent event) {
    }
    
}
