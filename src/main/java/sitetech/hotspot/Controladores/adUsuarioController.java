package sitetech.hotspot.Controladores;

import Util.Validar;
import com.jfoenix.controls.JFXToggleButton;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sitetech.hotspot.Modelos.Usuario;

/**
 * FXML Controller class
 *
 * @author megan
 */
public class adUsuarioController implements Initializable {

    final Stage thisStage;
    
    @FXML
    private TextField tusuario;

    @FXML
    private PasswordField tcontraseña;

    @FXML
    private PasswordField tcontraseña2;

    @FXML private Label lnota;
    
    @FXML private Label ltitulo;
    
    @FXML private Label ltusuario;
    @FXML private Label ltcontraseña;
    @FXML private Label ltconfirmar;
    @FXML private Label lcbprivilegios;
    
    @FXML
    private ComboBox<String> cbprivilegios;

    @FXML
    private VBox pguardar;

    @FXML
    private JFXToggleButton checkDeshabilitado;

    @FXML
    private HBox pagregar;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
    
    public adUsuarioController (){
        /*thisStage = new Stage();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/Usuarios/adUsuario.fxml"));
            loader.setController(this);

            thisStage.initModality(Modality.APPLICATION_MODAL);
            Scene thisScene = new Scene((Parent)loader.load());
            //thisScene.getStylesheets().add("/styles/Styles.css");
            thisStage.setScene(thisScene);
            thisStage.setTitle("Agregar Usuario");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        */
        thisStage = new Stage();
        Util.util.cargarStage("/Vistas/Usuarios/adUsuario.fxml", "Agregar Usuario", thisStage, this, Modality.APPLICATION_MODAL);
    }
    
    public void showStage() {
        thisStage.setResizable(false);
        thisStage.showAndWait();
    }
    
    @FXML
    void agregarAction(ActionEvent event) throws Throwable {
        if (camposValidos(true)){
            Usuario ux = new Usuario(0, tusuario.getText(), "123", cbprivilegios.getValue(), false, checkDeshabilitado.isSelected());
            ux.setContraseña(tcontraseña.getText());

            uvController.AgregarUsuario(ux);
            bcancelar(event);
        }
    }

    @FXML
    void bcancelar(ActionEvent event) throws Throwable {
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }
    
    UsuariosController uvController;
    public void showAgregar(UsuariosController _uvController)
    {
        uvController = _uvController;
        pagregar.setVisible(true);
        pguardar.setVisible(false);
        lnota.setVisible(false);
        
        this.showStage();
    }
    
    Usuario selUsuario;
    public void showEditar(Usuario usuario, UsuariosController _uvController)
    {
        uvController = _uvController;
        selUsuario = usuario;
        
        pguardar.setVisible(true);
        pagregar.setVisible(false);
        tusuario.setText(usuario.getNombre());
        cbprivilegios.setValue(usuario.getPrivilegios());
        checkDeshabilitado.setSelected(usuario.getActivo());
        
        thisStage.setTitle("Editar Usuario");
        ltitulo.setText("Editar Usuario");
        this.showStage();
    }
    
    private boolean camposValidos(boolean todos){
        boolean t2=true, t3=true; 
        
        boolean t1 = !Validar.esTextfieldVacio(tusuario, ltusuario, "Debe ingresar un usuario.") && 
                Validar.esComboboxCorrecto(cbprivilegios, lcbprivilegios, "Debe de seleccionar un privilegio");
        
        if (todos){
            t2 =  Validar.VerificarContraseña(tcontraseña, tcontraseña2, ltconfirmar, "Las contraseñas no coinciden.") &&
                  !Validar.esTextfieldVacio(tcontraseña, ltcontraseña, "Debe de ingresar una contraseña.");
            
        }
        if (!tcontraseña.getText().isEmpty())
            t3 = Validar.VerificarContraseña(tcontraseña, tcontraseña2, ltconfirmar, "Las contraseñas no coinciden.");
        
        return (t1 && t2 && t3);
    }

    @FXML
    void guardarAction(ActionEvent event) throws Throwable {
         if (camposValidos(false)){
            selUsuario.setNombre(tusuario.getText());
            selUsuario.setPrivilegios(cbprivilegios.getValue());
            selUsuario.setActivo(checkDeshabilitado.isSelected());

            if (!tcontraseña.getText().equals(""))
                selUsuario.setContraseña(tcontraseña.getText());

            uvController.editarUsuario(selUsuario);
            this.bcancelar(event);
         }
    }

    

}