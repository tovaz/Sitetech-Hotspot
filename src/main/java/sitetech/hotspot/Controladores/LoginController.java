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
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
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
    private TextField tusuario;
    @FXML
    private PasswordField tcontraseña;
    @FXML
    private Pane plogin;
    
    private MainApp App;
    private Stage stage;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
    
    @FXML
    public void testAction(ActionEvent event) throws IOException
    {
        
    }
    
    @FXML
    public void loginAction (ActionEvent event) throws IOException{
        usuarioManager um = new usuarioManager();
        boolean login = um.checkLogin(tusuario.getText(), tcontraseña.getText());
        if (login){
            App.usuarioLogeado = um.usuarioLogeado;
            App.mainScene();
        }
        else
            plogin.setVisible(true);
    }
    
    @FXML
    public void onKeyPress(KeyEvent key) throws IOException {
        if (key.getCode().equals(KeyCode.ENTER))
            loginAction(new ActionEvent());
        else
            plogin.setVisible(false);
    }
            
    public void pasarStage (MainApp _app, Stage _stage)
    {
        this.App = _app;
        this.stage = _stage;
        
        stage.setAlwaysOnTop(true);
        stage.setResizable(false);
        stage.setTitle("Sitetech :: Hotspot - Login");
    }
}
