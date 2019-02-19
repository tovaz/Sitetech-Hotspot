/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sitetech.hotspot.Controladores;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author willi
 */
public class TicketsController implements Initializable {

    private final Stage thisStage;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    public TicketsController() {
        thisStage = new Stage();
        //Util.util.cargarStage("/Vistas/Tickets/Tickets.fxml", "Tickets", thisStage, this, Modality.APPLICATION_MODAL);
    }
    
    public void cargarPanel(AnchorPane panel) throws IOException {
        AnchorPane newLoadedPane =  FXMLLoader.load(getClass().getResource("/Vistas/Tickets/Tickets.fxml"));
        panel.getChildren().setAll( newLoadedPane );
    }
    
    public void showStage(AnchorPane panel) {
        thisStage.showAndWait();
    }
}
