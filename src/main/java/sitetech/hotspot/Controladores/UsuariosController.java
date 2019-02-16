package sitetech.hotspot.Controladores;

import Util.ActionButtonTableCell;
import static com.sun.xml.internal.fastinfoset.alphabet.BuiltInRestrictedAlphabets.table;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
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
    
    public UsuariosController (){
        thisStage = new Stage();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/Usuarios/UsuariosVista.fxml"));
            loader.setController(this);

            thisStage.initModality(Modality.APPLICATION_MODAL);
            Scene thisScene = new Scene((Parent)loader.load());
            //thisScene.getStylesheets().add("/styles/Styles.css");
            thisStage.setScene(thisScene);
            thisStage.setTitle("Gestion de Usuario");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void cargarTabla(){
        um = new usuarioManager();
        //tvusuarios.getColumns().get(1).setCellValueFactory( new PropertyValueFactory("id") );
        tvusuarios.getColumns().get(1).setCellValueFactory( new PropertyValueFactory("Id") );
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
        adUsuarioController adUsuario = new adUsuarioController();
        adUsuario.showAgregar(this);
    }
    
    @FXML
    void editarUsuarioAction(ActionEvent event) {
        uSeleccionado = tvusuarios.getSelectionModel().getSelectedItem();
        if (uSeleccionado != null) {
            adUsuarioController adUsuario = new adUsuarioController();
            adUsuario.showEditar(uSeleccionado, this);
            
        }
        else
            Util.util.mostrarAlerta("Debe de seleccionar un usuario para poder editarlo.", "No a seleccionado ningun usuario", ButtonType.OK);
    }
    
     @FXML
    void onEliminarAction(ActionEvent event) {
        uSeleccionado = tvusuarios.getSelectionModel().getSelectedItem();
        if (uSeleccionado != null) {
            ButtonType btn = Util.util.mostrarAlerta("¿Desea realmente eliminar al usuario \" " + uSeleccionado.getNombre() + "\" ?", "Eliminar Usuario", ButtonType.YES, ButtonType.NO);
            if ( btn == ButtonType.YES) {
                um.EliminarUsuario(uSeleccionado);
                um.getUsuarios();
            }
        }
        else
            Util.util.mostrarAlerta("Debe de seleccionar un usuario para poder eliminarlo.", "Eliminar Usuario", ButtonType.OK);
    }
    
    @FXML
    void editarUsuarioTablaAction(MouseEvent event) {
        if (event.getClickCount() == 2 ) {
            editarUsuarioAction(new ActionEvent());
        }
    }
}
