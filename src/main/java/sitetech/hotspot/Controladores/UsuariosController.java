package sitetech.hotspot.Controladores;

import Util.Dialogo;
import Util.StageManager;
import Util.columnIndex;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sitetech.hotspot.MainApp;
import sitetech.hotspot.Modelos.Usuario;
import sitetech.hotspot.Modelos.usuarioManager;

/**
 * FXML Controller class
 *
 * @author megan
 */
public class UsuariosController implements Initializable {

    private final Stage thisStage;
    private MainApp App;
    @FXML
    public AnchorPane AnchorPane;
    
    @FXML
    private TableView<Usuario> tvusuarios;
    
    private usuarioManager um;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarTabla();
    }    
    
    public UsuariosController (MainApp _app){
        App = _app;
        thisStage = new Stage();
        StageManager.cargarStage("/Vistas/Usuarios/UsuariosVista.fxml", "Agregar Usuario", thisStage, this, Modality.APPLICATION_MODAL, App.configuracion);
        App.agregarEscena("scene_usuarios", thisStage.getScene());
    }
    
    public void cargarTabla(){
        um = new usuarioManager();
        //tvusuarios.getColumns().get(1).setCellValueFactory( new PropertyValueFactory("id") );
        tvusuarios.getColumns().get(1).setCellFactory(new columnIndex());
        tvusuarios.getColumns().get(2).setCellValueFactory( new PropertyValueFactory("Nombre") );
        tvusuarios.getColumns().get(3).setCellValueFactory( new PropertyValueFactory("Privilegios") );
        tvusuarios.getColumns().get(4).setCellValueFactory( new PropertyValueFactory("Activo") );
        //
        
        tvusuarios.setItems(um.listaUsuarios);
    }
    
    public void showStage() {
        thisStage.showAndWait();
    }
    
    public void AgregarUsuario(Usuario ux)
    {
        um.AgregarUsuario(ux);
        cargarTabla();
    }
    
    public void editarUsuario(Usuario ux)
    {
        um.EditarUsuario(ux);
        cargarTabla();
    }
    
    public Usuario uSeleccionado = null;
    @FXML
    void agregarUsuarioAction(ActionEvent event) {
        adUsuarioController adUsuario = new adUsuarioController(App);
        adUsuario.showAgregar(this);
    }
    
    @FXML
    void editarUsuarioAction(ActionEvent event) {
        uSeleccionado = tvusuarios.getSelectionModel().getSelectedItem();
        if (uSeleccionado != null) {
            adUsuarioController adUsuario = new adUsuarioController(App);
            adUsuario.showEditar(uSeleccionado, this);
        }
        else
            Dialogo.mostrarAlerta("Debe de seleccionar un usuario para poder editarlo.", "No a seleccionado ningun usuario", App.configuracion, ButtonType.OK);
    }
    
     @FXML
    void onEliminarAction(ActionEvent event) {
        uSeleccionado = tvusuarios.getSelectionModel().getSelectedItem();
        if (uSeleccionado != null) {
            if (!uSeleccionado.getNombre().equals("admin")){
                ButtonType btn = Dialogo.mostrarConfirmacion("¿Desea realmente eliminar al usuario \" " + uSeleccionado.getNombre() + "\" ?", "Eliminar Usuario", App.configuracion, ButtonType.YES, ButtonType.NO);
                if ( btn == ButtonType.YES) {
                    um.EliminarUsuario(uSeleccionado);
                    um.getUsuarios();
                }
            }
            else
                Dialogo.mostrarError("No puede eliminar el usuario admin.", "Error al eliminar usuario", App.configuracion, ButtonType.OK);
        }
        else
            Dialogo.mostrarAlerta("Debe de seleccionar un usuario para poder eliminarlo.", "Eliminar Usuario", App.configuracion, ButtonType.OK);
    }
    
    @FXML
    void editarUsuarioTablaAction(MouseEvent event) {
        if (event.getClickCount() == 2 ) {
            editarUsuarioAction(new ActionEvent());
        }
    }
}
