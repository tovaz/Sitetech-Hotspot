package sitetech.hotspot.Controladores;

import Util.StageManager;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sitetech.hotspot.MainApp;
import sitetech.hotspot.Modelos.Router;
import sitetech.hotspot.Modelos.RouterManager;

public class RoutersController implements Initializable {

    private final Stage thisStage;
    
    @FXML
    private Label titulo;
    @FXML
    private TableView<Router> tvrouters;
    private MainApp App;
    
    private RouterManager rm;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public RoutersController(MainApp _app) {
        App = _app;
        thisStage = new Stage();
        StageManager.cargarStage("/Vistas/Routers/Routers.fxml", "Gestion de Routers", thisStage, this, Modality.APPLICATION_MODAL, App.configuracion);
        App.agregarEscena("scene_routers", thisStage.getScene());
        
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
        adeRouterController agregarRouterC = new adeRouterController(App);
        agregarRouterC.showStage();
        cargarTabla();
    }

    @FXML
    private void showEditarRouter(ActionEvent event) {
        rSeleccionado = tvrouters.getSelectionModel().getSelectedItem();
        if (rSeleccionado != null) {
            adeRouterController editarRouterC = new adeRouterController(App);
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
                rm.EliminarRouter(rSeleccionado);
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
