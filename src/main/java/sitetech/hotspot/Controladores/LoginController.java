/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sitetech.hotspot.Controladores;

import Util.StageManager;
import Util.Validar;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sitetech.hotspot.Modelos.usuarioManager;
import sitetech.hotspot.MainApp;

/**
 * FXML Controller class
 *
 * @author megan
 */
public class LoginController implements Initializable {

    @FXML
    private Label ltusuario;
    @FXML
    private Label ltcontraseña;
    
    @FXML
    private JFXTextField  tusuario;
    @FXML
    private JFXTextField  tcontraseña;
    @FXML
    private Pane plogin;
    
    private MainApp App;
    public final Stage thisStage;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    public LoginController(MainApp _app) {
        App = _app;
        thisStage = new Stage();
        StageManager.cargarStage("/Vistas/login.fxml", "Iniciar Sesion", thisStage, this, Modality.APPLICATION_MODAL, App.configuracion);
        App.agregarEscena("scene_login", thisStage.getScene());
    }
    
    
    
    @FXML
    public void testAction(ActionEvent event) throws IOException
    {
        
    }
    
    @FXML
    public void loginAction (ActionEvent event) throws IOException{
        if (camposValidos()) {
            if (App.checkLogin(tusuario.getText(), tcontraseña.getText()))
                thisStage.close();
            else
                plogin.setVisible(true);
        }
    }
    
    private boolean camposValidos(){
        if (Validar.esTextfieldVacio(tusuario, ltusuario, "Debe ingresar un usuario.")) return false;
        if (Validar.esTextfieldVacio(tcontraseña, ltcontraseña, "Debe ingresar una contraseña.")) return false;
        
        
        return true;
    }
    
    @FXML
    public void onKeyPress(KeyEvent key) throws IOException {
        if (key.getCode().equals(KeyCode.ENTER))
            loginAction(new ActionEvent());
        else
            plogin.setVisible(false);
    }
            
    public void showStage() {
        thisStage.setAlwaysOnTop(true);
        thisStage.setResizable(false);
        thisStage.setTitle("Sitetech :: Hotspot - Login");
        thisStage.showAndWait();
    }
    
    public void pasarStage (MainApp _app, Stage _stage)
    {
        
        //this.thisStage = _stage;
        
        
    }
}
