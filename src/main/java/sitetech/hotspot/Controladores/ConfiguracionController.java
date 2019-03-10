package sitetech.hotspot.Controladores;

import Util.Archivo;
import Util.Dialogo;
import Util.MiLocale;
import com.jfoenix.controls.JFXColorPicker;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXToggleButton;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputMethodEvent;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sitetech.hotspot.Modelos.Configuracion;
import sitetech.hotspot.Modelos.ConfiguracionManager;

/**
 * FXML Controller class
 *
 * @author Willi
 */
public class ConfiguracionController implements Initializable {

    @FXML private JFXTextField tempresa;
    @FXML private JFXComboBox<String> cbpais;
    @FXML private JFXTextField testado;
    @FXML private JFXTextField tciudad;
    @FXML private JFXTextField tdireccion;
    @FXML private JFXTextField timagen;
    @FXML private JFXTextField tdominio;
    @FXML private JFXComboBox<MiLocale> cbmoneda;
    @FXML private Label lemoneda;
    @FXML private JFXToggleButton tgmostrarBarras;
    @FXML private JFXColorPicker cpenfasis;
    @FXML private ImageView iticket;
    
    private ConfiguracionManager cm;
    public final Stage thisStage;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    

    
    public ConfiguracionController() {
        thisStage = new Stage();
        Util.util.cargarStage("/Vistas/Configuraciones/Configuracion.fxml", "Configuracion de hotspot", thisStage, this, Modality.APPLICATION_MODAL);
        
        cm = new ConfiguracionManager();
        cargarDatos();
    }
    
    private  void cargarDatos(){
        cargarPaises();
        
        Configuracion conf = cm.getConfiguracion();
        if (conf == null)
            conf = new Configuracion(true);
        
        System.out.println("****************** EMPRESA: " + conf.getEmpresa());
        tempresa.setText(conf.getEmpresa());
        cbpais.setValue(conf.getPais());
        testado.setText(conf.getEstado());
        tciudad.setText(conf.getCiudad());
        tdireccion.setText(conf.getDireccion());
        timagen.setText(conf.getImagenTicket());
        tdominio.setText(conf.getDominio());
        tgmostrarBarras.setSelected(conf.isCodigoBarraVisible());
        
        File fimg = new File(conf.getImagenTicket());
        iticket.setImage(new Image( fimg.toURI().toString()) );
        
        cbmoneda.getSelectionModel().select(new MiLocale(conf.getrLocale(), conf.getrLocale().getDisplayCountry()));
        conf.setRegionLocal(new MiLocale(conf.getrLocale(), conf.getrLocale().getDisplayCountry()));
    }
    
    private void cargarPaises(){
        String[] paises = Locale.getISOCountries();
        ObservableList<MiLocale> locales = FXCollections.observableArrayList();
        for (String countryCode : paises) {
            Locale obj = new Locale("", countryCode);
            cbpais.getItems().add(obj.getDisplayCountry());
            
            locales.add(new MiLocale(obj, obj.getDisplayCountry()));
	}
        
        
        cbmoneda.setItems(locales);
        cbmoneda.valueProperty().addListener(new ChangeListener<MiLocale>() {
            @Override
            public void changed(ObservableValue<? extends MiLocale> observable, MiLocale oldValue, MiLocale newValue) {
                NumberFormat nf = NumberFormat.getCurrencyInstance(newValue.getLocale());
                lemoneda.setText(nf.format(5100.50));
            }
        });
    }
    
    public void showStage() {
        thisStage.showAndWait();
    }
    
    @FXML
    void guardarAction(ActionEvent event) {
        Configuracion conf = cm.getConfiguracion();
        boolean nueva = false;
        if (conf == null){
            conf = new Configuracion(true);
            conf.setId(0);
            nueva = true;
        }
        
        conf.setEmpresa(tempresa.getText());
        conf.setPais(cbpais.getValue());
        conf.setEstado(testado.getText());
        conf.setCiudad(tciudad.getText());
        conf.setDireccion(tdireccion.getText());
        conf.setImagenTicket(timagen.getText());
        conf.setDominio(tdominio.getText());
        conf.setCodigoBarraVisible(tgmostrarBarras.isSelected());
        
        NumberFormat nf = NumberFormat.getCurrencyInstance(cbmoneda.getValue().getLocale());
        conf.setMoneda(nf.getCurrency());
        conf.setrLocale(cbmoneda.getValue().getLocale());

        if (nueva)
            cm.Agregar(conf);
        else
            cm.Editar(conf);
        
        cancelarAction(event);
    }
    
    @FXML
    void cancelarAction(ActionEvent event) {
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }

    @FXML
    void examinarAction(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecciona una imagen.");
        File file = fileChooser.showOpenDialog(thisStage);
        
        String destino = System.getProperty("user.dir") + "\\img\\" + file.getName();
        File fimg = new File(destino);
        if (file != null){
            if (Archivo.Copiar(file)){
                timagen.setText(destino);
                iticket.setImage(new Image( fimg.toURI().toString()) );
            }
            else
                Dialogo.mostrarError("Error al copiar el archivo, verifica que existe y que tiene permisos para copiar en el directorio de destino.", "Error al copiar el archivo.", ButtonType.OK);
        }
    }
    
    @FXML
    void enfasisAction(ActionEvent event) {
        Stage primaryStage = (Stage)thisStage.getUserData();
        String enfasisColor = Integer.toHexString(cpenfasis.getValue().hashCode()).substring(0, 6).toUpperCase();
        thisStage.getScene().getRoot().getStyleClass().add("rootRosado");
        
        primaryStage.getScene().getRoot().getStyleClass().add("rootRosado");
        System.out.println("ENFASIS: #" + enfasisColor);
        
    }
}
