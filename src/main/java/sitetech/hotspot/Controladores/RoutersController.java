/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sitetech.hotspot.Controladores;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sitetech.hotspot.Modelos.Router;
import sitetech.hotspot.Modelos.RouterManager;

import javafx.scene.control.TextField;
import sitetech.hotspot.dbManager;
/**
 * FXML Controller class
 *
 * @author megan
 */
public class RoutersController implements Initializable {

    private final Stage thisStage;
    
    @FXML
    private Label titulo;
    @FXML
    private TableView<Router> tvrouters;
    
    private RouterManager rm;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public RoutersController() {
        
        thisStage = new Stage();
        Util.util.cargarStage("/Vistas/Routers/Routers.fxml", "Gestion de Routers", thisStage, this, Modality.APPLICATION_MODAL);
        rm = new RouterManager();
        cargarTabla();
        
    }
    
    public void cargarTabla(){
        tvrouters.getColumns().get(0).setCellValueFactory( new PropertyValueFactory("Id") );
        tvrouters.getColumns().get(2).setCellValueFactory( new PropertyValueFactory("Nombre") );
        tvrouters.getColumns().get(3).setCellValueFactory( new PropertyValueFactory("Ip") );
        tvrouters.getColumns().get(4).setCellValueFactory( new PropertyValueFactory("puertoApi") );
        tvrouters.getColumns().get(5).setCellValueFactory( new PropertyValueFactory("lanInterface") );
        tvrouters.getColumns().get(6).setCellValueFactory( new PropertyValueFactory("apiActiva") );
        tvrouters.setItems(rm.getRouters());
    }
    
    public void showStage() {
        thisStage.showAndWait();
    }

    Router rSeleccionado;
    @FXML
    private void editarRouterTablaAction(MouseEvent event) { // LISTA DE ROUTERS
        if (event.getClickCount() == 2 ) {
            showEditarRouter(new ActionEvent());
        }
    }
    
    @FXML
    private void showAgregarRouter(ActionEvent event) {
        adeRouterController agregarRouterC = new adeRouterController();
        agregarRouterC.showStage();
        cargarTabla();
    }

    @FXML
    private void showEditarRouter(ActionEvent event) {
        rSeleccionado = tvrouters.getSelectionModel().getSelectedItem();
        if (rSeleccionado != null) {
            adeRouterController editarRouterC = new adeRouterController();
            editarRouterC.cargarRouterInfo(rSeleccionado);
            editarRouterC.showStage();
            cargarTabla();
        }
        else
            Util.util.mostrarAlerta("Debe de seleccionar un router para poder editarlo.", "No a seleccionado ningun router", ButtonType.OK);
    }

    @FXML
    private void EliminarRouterAction(ActionEvent event) {
        rSeleccionado = tvrouters.getSelectionModel().getSelectedItem();
        if (rSeleccionado != null) {
            ButtonType btn = Util.util.mostrarAlerta("¿Desea realmente eliminar al router \" " + rSeleccionado.getNombre() + "\" ?", "Eliminar Router", ButtonType.YES, ButtonType.NO);
            if ( btn == ButtonType.YES) {
                rm.eliminarRouter(rSeleccionado);
                cargarTabla();
            }
        }
        else
            Util.util.mostrarAlerta("Debe de seleccionar un router para poder eliminarlo.", "Eliminar Router", ButtonType.OK);
    }
    
    
    @FXML
    void agregarRouterAction(ActionEvent event) {
        
    }

}
