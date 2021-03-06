
package sitetech.hotspot.Controladores;

import Util.Dialogo;
import Util.StageManager;
import Util.columnIndex;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
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
import sitetech.hotspot.Modelos.Paquete;
import sitetech.hotspot.Modelos.PaqueteManager;

/**
 * FXML Controller class
 *
 * @author willi
 */
public class PaquetesController implements Initializable {

    @FXML
    private Label titulo;
    @FXML
    private TableView<Paquete> tvpaquetes;

    private final Stage thisStage;
    private MainApp App;
    private PaqueteManager pm;
    private Paquete pSeleccionado;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
    public PaquetesController(MainApp _app) {
        App = _app;
        thisStage = new Stage();
        StageManager.cargarStage("/Vistas/Paquetes/Paquetes.fxml", "Gestion de Paquetes", thisStage, this, Modality.APPLICATION_MODAL, App.configuracion);
        App.agregarEscena("scene_paquetes", thisStage.getScene());
        
        pm = new PaqueteManager();
        cargarTabla();
    }
    
    public void cargarTabla(){
        tvpaquetes.getColumns().get(1).setCellFactory(new columnIndex());
        tvpaquetes.getColumns().get(2).setCellValueFactory( new PropertyValueFactory("Nombre") );
        tvpaquetes.getColumns().get(3).setCellValueFactory( new PropertyValueFactory("PrecioFormateado") );
        tvpaquetes.getColumns().get(4).setCellValueFactory( new PropertyValueFactory("Duracion") );
        tvpaquetes.getColumns().get(5).setCellValueFactory( new PropertyValueFactory("LimiteInternet") );
        tvpaquetes.setItems( pm.getPaquetes() );
    }
    
    public void showStage() {
        thisStage.showAndWait();
    }

    @FXML
    private void editarPaqueteTablaAction(MouseEvent event) {
        if (event.getClickCount() == 2 ) {
            showEditar(new ActionEvent());
        }
    }

    @FXML
    private void showAgregar(ActionEvent event) {
        adePaqueteController agregarController = new adePaqueteController(App);
        agregarController.showStage();
        cargarTabla();
    }

    @FXML
    private void showEditar(ActionEvent event) {
        pSeleccionado = tvpaquetes.getSelectionModel().getSelectedItem();
        if (pSeleccionado != null) {
            adePaqueteController agregarController = new adePaqueteController(App);
            agregarController.cargarInfo(pSeleccionado);
            agregarController.showStage();
            cargarTabla();
        }
        else
            Dialogo.mostrarAlerta("Debe de seleccionar un paquete para poder editarlo.", "No a seleccionado ningun paquete", App.configuracion, ButtonType.OK);
    }

    @FXML
    private void EliminarAction(ActionEvent event) {
        pSeleccionado = tvpaquetes.getSelectionModel().getSelectedItem();
        if (pSeleccionado != null) {
            ButtonType btn = Dialogo.mostrarConfirmacion("¿Desea realmente eliminar al paquete \" " + pSeleccionado.getNombre() + "\" ?", "Eliminar Paquete", App.configuracion, ButtonType.YES, ButtonType.NO);
            if ( btn == ButtonType.YES) {
                pm.EliminarPaquete(pSeleccionado);
                cargarTabla();
            }
        }
        else
            Dialogo.mostrarAlerta("Debe de seleccionar un paquete para poder eliminarlo.", "Eliminar Paquete", App.configuracion, ButtonType.OK);
    }
    
}
