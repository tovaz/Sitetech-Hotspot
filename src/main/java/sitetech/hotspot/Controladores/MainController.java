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
    private Stage thisStage;
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
    
    public MainController (){
        thisStage = new Stage();
        /*try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/Vistas/mainScene.fxml"));
            loader.setController(this);

            thisStage.initModality(Modality.APPLICATION_MODAL);
            Scene scene = new Scene((Parent)loader.load());
            scene.getStylesheets().add("../../resources/styles/Styles.css");
            thisStage.setScene(scene);
            thisStage.setTitle("Hotspot 1.0");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }*/
    }
    
    public void showStage() {
        thisStage.show();
    }
    
    @FXML
    public void loadUsuarios () throws IOException {
        adUsuarioController adUsuario = new adUsuarioController();
        adUsuario.showAgregar(new UsuariosController());
    }  
    
    @FXML
    private void onMenuAction(ActionEvent event) throws IOException {
        MenuItem mi = (MenuItem) event.getSource();
        System.out.println(mi.getText());
    
        switch (mi.getText()){
            case "Usuarios":
                UsuariosController uvController = new UsuariosController();
                uvController.showStage();
                break;
                
            case "Routers":
                RoutersController rController = new RoutersController();
                rController.showStage();
                break;
                
            case "Paquetes de Internet":
                PaquetesController pController = new PaquetesController();
                pController.showStage();
                break;
                
        }
        
    }
    
}
