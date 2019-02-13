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
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author megan
 */
public class RoutersController implements Initializable {

    @FXML
    private Label titulo;
    @FXML
    private TableView<?> tvrouters;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void editarRouterTablaAction(MouseEvent event) {
    }

    @FXML
    private void agregarRouterAction(ActionEvent event) {
    }

    @FXML
    private void editarRouterAction(ActionEvent event) {
    }

    @FXML
    private void EliminarRouterAction(ActionEvent event) {
    }

}
