/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sitetech.hotspot.Controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author megan
 */
public class DetalleCajaControlador implements Initializable {

    @FXML private Label lcaja;
    @FXML private Label ltickets;
    @FXML private Label lingresos;
    @FXML private Label legresos;
    @FXML private Label ltotal;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void onCancelar(ActionEvent event) {
        
    }

    @FXML
    private void onCerrarCaja(ActionEvent event) {
    }
    
}
