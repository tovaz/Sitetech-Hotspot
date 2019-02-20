/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sitetech.hotspot.Controladores;

import Util.ArrastrarScene;
import Util.util;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;


/**
 * FXML Controller class
 *
 * @author willi
 */
public class TicketsController implements Initializable,ArrastrarScene {

    private final Stage thisStage;
    public AnchorPane ticketsPanel;
    
    @FXML private AnchorPane panelTicket;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.ArrastrarScene(panelTicket);
    }    

    public TicketsController() {
        thisStage = new Stage();
        //Util.util.cargarStage("/Vistas/Tickets/Tickets.fxml", "Tickets", thisStage, this, Modality.APPLICATION_MODAL);
    }
    
    public void cargarPanel(AnchorPane panel) throws IOException {
        //FXMLLoader fl =  FXMLLoader.load(getClass().getResource("/Vistas/Tickets/Tickets.fxml"));
        //fl.setController(this);
        Node nodo = (Node)util.cargarEscenaEnPanel("/Vistas/Tickets/Tickets.fxml", "Tickets", this);
        AnchorPane.setTopAnchor(nodo, 0.0);
        
        AnchorPane.setLeftAnchor(nodo, 0.0);
        AnchorPane.setRightAnchor(nodo, 0.0);
        AnchorPane.setBottomAnchor(nodo, 0.0);
        panel.getChildren().setAll( nodo );
        
    }
    
    public void showStage() {
        thisStage.show();
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

    }

    @FXML
    void onImprimirAction(ActionEvent event) {

    }

    @FXML
    void onVenderAction(ActionEvent event) {

    }
}
