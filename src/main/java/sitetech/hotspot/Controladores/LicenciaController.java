/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sitetech.hotspot.Controladores;

import Util.Dialogo;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import sitetech.License.CryptoKey;
import sitetech.License.Licencia;
import sitetech.hotspot.MainApp;

/**
 * FXML Controller class
 *
 * @author megan
 */
public class LicenciaController extends MiControlador {

    @FXML private VBox plicencia;
    @FXML private JFXTextField tnegocio;
    @FXML private JFXTextField tpais;
    @FXML private JFXTextField tapp;
    @FXML private JFXTextField tversion;
    @FXML private JFXComboBox<Licencia.tipoType> ttlicencia;
    @FXML private JFXTextField tlicencia;
    @FXML private JFXTextField tfechaCreacion;
    @FXML private JFXButton bactivar;
    @FXML private TextArea tclientId;
    @FXML private JFXButton babrir;
    @FXML private AnchorPane psinlicencia;
    @FXML private AnchorPane pprincipal;
    @FXML private JFXButton babrir1;

    Licencia licencia;
    public LicenciaController(MainApp _app) {
        cargarEscena("/Vistas/Licencia/Licencia.fxml", "Activacion del programa", Modality.APPLICATION_MODAL, _app);
        thisStage.resizableProperty().set(false);
        
    }

    public boolean showLicencia(){
        if (!checkLicencia()){
            psinlicencia.setVisible(true);
            plicencia.setVisible(false);
            if (App.Splash != null) App.Splash.thisStage.close();
            showAndWait();
        }
        return checkLicencia();
    }
    
    public boolean checkLicencia(){
        return false;
    }
    
    public void loadInfo(Licencia lic){
        String fechaCreacion = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(lic.getFechaCreacion());
       
        tnegocio.setText(lic.getNegocio());
        tpais.setText(lic.getPais()); 
        tapp.setText(lic.getNombreApp());
        tversion.setText(lic.getVersion());
        ttlicencia.getSelectionModel().select(lic.getTipoLicencia());
        tlicencia.setText(lic.getLicencia());
        tclientId.setText(lic.getEncriptedUID());
        tfechaCreacion.setText(fechaCreacion);
        
        licencia = lic;
    }
    
    @FXML
    private void onactivar(ActionEvent event) {
    }

    @FXML
    private void oncerrar(ActionEvent event) {
    }
    
    @FXML
    private void onabrir(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Archivos de licencia (*.lic)", "*.lic");
        
        fileChooser.setTitle("Abrir archivo de licencia");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(this.thisStage);
        if (file != null){
            Licencia licencia = CryptoKey.leerLicencia(file.getAbsolutePath());
            if (licencia != null){
                psinlicencia.setVisible(false);
                plicencia.setVisible(true);
                pprincipal.getStyleClass().remove("fondoApp2");
                loadInfo(licencia);
            }else
                Dialogo.mostrarError("El archivo de licencia es invalido, comunicate con el proveedor de licencias.", "Licencia invalida", App.configuracion, ButtonType.OK);
        }
    }
    
}
