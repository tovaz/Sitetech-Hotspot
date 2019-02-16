
package sitetech.hotspot.Controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sitetech.hotspot.Modelos.Paquete;
import sitetech.hotspot.Modelos.PaqueteManager;
import sitetech.hotspot.Modelos.RouterManager;

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
    private PaqueteManager pm;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
    public PaquetesController() {
        thisStage = new Stage();
        Util.util.cargarStage("/Vistas/Paquetes/Paquetes.fxml", "Gestion de Paquetes", thisStage, this, Modality.APPLICATION_MODAL);
        pm = new PaqueteManager();
        cargarTabla();
    }
    
    public void cargarTabla(){
        tvpaquetes.getColumns().get(0).setCellValueFactory( new PropertyValueFactory("Id") );
        tvpaquetes.getColumns().get(2).setCellValueFactory( new PropertyValueFactory("Nombre") );
        tvpaquetes.getColumns().get(3).setCellValueFactory( new PropertyValueFactory("Ip") );
        tvpaquetes.getColumns().get(4).setCellValueFactory( new PropertyValueFactory("puertoApi") );
        tvpaquetes.getColumns().get(5).setCellValueFactory( new PropertyValueFactory("lanInterface") );
        tvpaquetes.getColumns().get(6).setCellValueFactory( new PropertyValueFactory("apiActiva") );
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
        
    }

    @FXML
    private void showEditar(ActionEvent event) {
    }

    @FXML
    private void EliminarAction(ActionEvent event) {
    }
    
}
