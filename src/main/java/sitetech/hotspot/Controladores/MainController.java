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
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sitetech.hotspot.MainApp;

public class MainController implements Initializable {
    
    private MainApp App;
    private Stage stage;
    @FXML
    private AnchorPane panelPrincipal;

    @FXML
    private Label label;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
    @FXML
    void testAction(ActionEvent event) throws IOException {
        this.loadUsuarios();
    }
    
    @FXML
    public void loadUsuarios () throws IOException {
        UsuariosController uvController = new UsuariosController();
        uvController.showStage();
    }  

    public void pasarStage (MainApp _app, Stage _stage)
    {
        this.App = _app;
        this.stage = _stage;
        
        stage.setAlwaysOnTop(false);
        stage.setResizable(true);
        stage.setTitle("Sitetech :: Hotspot");
    }
    
    @FXML
    private void onMenuAction(ActionEvent event) throws IOException {
        MenuItem mi = (MenuItem) event.getSource();
        System.out.println(mi.getText());
        
        if (mi.getText().equals("Usuarios"))
        {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/Vistas/Usuarios/UsuariosVista.fxml"));
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                //stage.initStyle(StageStyle.UNDECORATED);
                stage.setScene(new Scene(root));  
                stage.show();
            } catch(Exception e) {
                System.out.println(e.getMessage());
            }
        }
        
    }  
}
