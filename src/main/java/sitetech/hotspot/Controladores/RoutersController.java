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
import javafx.scene.Parent;
import javafx.scene.Scene;
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
    
    private RouterManager ur;

    // ************************************************************
    @FXML
    private TextField tnombre;

    @FXML
    private TextField tip;

    @FXML
    private TextField tusuario;

    @FXML
    private TextField tcontraseña;

    @FXML
    private TextField tpuertoApi;

    @FXML
    private TextField tpuertoWeb;

    @FXML
    private TextField tlan;

    @FXML
    private TextField twlan;

    @FXML
    private TextArea trangos;

    @FXML
    void agregarRouterAction(ActionEvent event) {

    }

    @FXML
    void cancelarAction(ActionEvent event) {

    }
    // ************************************************************
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
        
    }
    
    public void cargarTabla(){
        ur = new RouterManager();
        tvrouters.getColumns().get(1).setCellValueFactory( new PropertyValueFactory("Id") );
        tvrouters.getColumns().get(2).setCellValueFactory( new PropertyValueFactory("Nombre") );
        tvrouters.getColumns().get(3).setCellValueFactory( new PropertyValueFactory("Ip") );
        tvrouters.getColumns().get(4).setCellValueFactory( new PropertyValueFactory("IsApiActiva") );
        tvrouters.setItems(ur.listaRouters);
    }
    
    

    @FXML
    private void editarRouterTablaAction(MouseEvent event) {
    }

    @FXML
    private void showAgregarRouter(ActionEvent event) {
        Util.util.mostrarStage("/Vistas/Routers/adeRouters.fxml", "Agregar Router", null, this, Modality.APPLICATION_MODAL);
    }

    @FXML
    private void showEditarRouter(ActionEvent event) {
    }

    @FXML
    private void EliminarRouterAction(ActionEvent event) {
    }
    
    public void showStage() {
        thisStage.showAndWait();
    }

}
