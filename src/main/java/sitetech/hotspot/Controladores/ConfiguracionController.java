/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sitetech.hotspot.Controladores;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.Currency;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sitetech.hotspot.Modelos.Configuracion;
import sitetech.hotspot.Modelos.configuracionManager;

/**
 * FXML Controller class
 *
 * @author Aura Marina Choxon
 */
public class ConfiguracionController implements Initializable {

    @FXML private JFXTextField tempresa;
    @FXML private JFXComboBox<String> cbpais;
    @FXML private JFXTextField testado;
    @FXML private JFXTextField tciudad;
    @FXML private JFXTextField tdireccion;
    @FXML private JFXTextField timagen;
    @FXML private JFXTextField tdominio;
    @FXML  private JFXComboBox<Locale> cbpais2;
    @FXML private JFXComboBox<Currency> cbmoneda;

    private configuracionManager cm;
    private final Stage thisStage;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    

    
    public ConfiguracionController() {
        thisStage = new Stage();
        Util.util.cargarStage("/Vistas/Configuracions/Configuracion.fxml", "Configuracion de hotspot", thisStage, this, Modality.APPLICATION_MODAL);
        
        cm = new configuracionManager();
        cargarDatos();
    }
    
    private  void cargarDatos(){
        Configuracion conf = cm.getConfiguracion();
        tempresa.setText(conf.getEmpresa());
        cbpais.setValue(conf.getPais());
        testado.setText(conf.getEstado());
        tciudad.setText(conf.getCiudad());
        tdireccion.setText(conf.getDireccion());
        timagen.setText(conf.getImagenTicket());
        tdominio.setText(conf.getDominio());
        cbmoneda.setValue(conf.getMoneda());
        cbpais2.setValue(conf.getRegionLocal());
    }
    
    public void showStage() {
        thisStage.showAndWait();
    }
    
    @FXML
    void guardarAction(ActionEvent event) {
        Configuracion conf = cm.getConfiguracion();
        if (conf == null)
            conf = new Configuracion();
        
        conf.setEmpresa(tempresa.getText());
        conf.setPais(cbpais.getValue());
        conf.setEstado(testado.getText());
        conf.setCiudad(tciudad.getText());
        conf.setDireccion(tdireccion.getText());
        conf.setImagenTicket(timagen.getText());
        conf.setDominio(tdominio.getText());

        conf.setMoneda(cbmoneda.getValue());
        conf.setRegionLocal(cbpais2.getValue());

        if (conf == null)
            cm.Agregar(conf);
        else
            cm.Editar(conf);
    }
    
    @FXML
    void cancelarAction(ActionEvent event) {

    }

    
    
}
