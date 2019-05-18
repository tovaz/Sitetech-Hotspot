package sitetech.hotspot.Controladores;

import sitetech.hotspot.Temas;
import Util.Archivo;
import Util.Dialogo;
import Util.MiLocale;
import Util.Moneda;
import Util.StageManager;
import sitetech.hotspot.ThemeColor;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXToggleButton;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.text.NumberFormat;
import java.util.Iterator;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import sitetech.hotspot.Modelos.Configuracion;
import sitetech.hotspot.Modelos.ConfiguracionManager2;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import org.springframework.util.StringUtils;
import sitetech.hotspot.MainApp;

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
    @FXML private JFXComboBox<String> cbidioma;
    @FXML private Label lemoneda;
    @FXML private JFXToggleButton tgmostrarBarras;
    @FXML private ImageView iticket;
    @FXML private JFXComboBox<ThemeColor> cbenfasis;
    @FXML private JFXComboBox<ThemeColor> cbtema;
    @FXML private JFXToggleButton tbtoolbar;
    @FXML private JFXToggleButton tbmenu;
    
    private ConfiguracionManager2 cm;
    private MainApp App;
    public final Stage thisStage;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    

    
    public ConfiguracionController(MainApp _app) {
        thisStage = new Stage();
        App = _app;
        StageManager.cargarStage("/Vistas/Configuraciones/Configuracion.fxml", "Configuracion de hotspot", thisStage, this, Modality.APPLICATION_MODAL, App.configuracion);
        
        App.agregarEscena("scene_configuraciones", thisStage.getScene());
        cm = new ConfiguracionManager2();
        cargarDatos();
    }
    
    private Configuracion conf;
    private  void cargarDatos(){
        conf = cm.getConfiguracion();
        
        cargarPaises();
        cargarIdiomas();
        cargarColores(cbenfasis, Temas.getEnfasis(), true);
        cargarColores(cbtema, Temas.getTemas(), false);
        
        System.out.println("****************** EMPRESA: " + conf.getEmpresa());
        tempresa.setText(conf.getEmpresa());
        cbpais.setValue(conf.getPais());
        testado.setText(conf.getEstado());
        tciudad.setText(conf.getCiudad());
        tdireccion.setText(conf.getDireccion());
        timagen.setText(conf.getImagenTicket());
        tdominio.setText(conf.getDominio());
        tgmostrarBarras.setSelected(conf.isCodigoBarraVisible());
        tbmenu.setSelected(conf.isColorMenu());
        tbtoolbar.setSelected(conf.isColorToolbar());
        
        File fimg = new File(conf.getImagenTicket());
        iticket.setImage(new Image( fimg.toURI().toString()) );
    }
    
    private void cargarIdiomas(){ 
        String[] _idiomas = Locale.getISOLanguages();
        ObservableList<String> idiomas = FXCollections.observableArrayList();
        
        for (String lenguaje : _idiomas) {
            String len = StringUtils.capitalize(new Locale(lenguaje).getDisplayLanguage());
            idiomas.add(len);
            if (len.toLowerCase().startsWith(conf.getIdioma().toLowerCase() ) )
                cbidioma.getSelectionModel().select(len);
	} 
        
        FXCollections.sort(idiomas);
        cbidioma.setItems(idiomas);
    }
    private void cargarPaises(){
        String[] paises = Locale.getISOCountries();
        ObservableList<MiLocale> locales = FXCollections.observableArrayList();
       
        if (conf.getIdioma() != null){
            for (String countryCode : paises) {
                Locale obj = new Locale(conf.getIdioma(), countryCode);
                cbpais.getItems().add(obj.getDisplayCountry());

                MiLocale ml = new MiLocale(obj, obj.getDisplayCountry());
                locales.add(ml);
                if (ml.getLocale().getCountry().equals(conf.getFormatoMoneda()))
                    cbmoneda.getSelectionModel().select(ml);
            }
        }
        
        FXCollections.sort(locales);
        cbmoneda.setItems(locales); 
        
        lemoneda.setText(Moneda.Formatear( new BigDecimal(5100.50), conf.getRegionLocal().getLocale() ) );
        cbmoneda.valueProperty().addListener(new ChangeListener<MiLocale>() {
            @Override
            public void changed(ObservableValue<? extends MiLocale> observable, MiLocale oldValue, MiLocale newValue) {
                lemoneda.setText(Moneda.Formatear( new BigDecimal(5100.50), newValue.getLocale() ) );
            }
        });
        System.err.println("********* IDIOMA: " + conf.getIdioma());
    }
    
    public void showStage() {
        thisStage.showAndWait();
    }
    
    @FXML
    void guardarAction(ActionEvent event) {
        if (!cbpais.getValue().isEmpty())
            conf.setPais(cbpais.getValue());
        
        conf.setEmpresa(tempresa.getText());
        conf.setEstado(testado.getText());
        conf.setCiudad(tciudad.getText());
        conf.setDireccion(tdireccion.getText());
        conf.setImagenTicket(timagen.getText());
        conf.setDominio(tdominio.getText());
        conf.setCodigoBarraVisible(tgmostrarBarras.isSelected());
        
        // CONFIGURACION DE APARIENCIA
        conf.setColorEnfasis(cbenfasis.getValue().getNombre());
        conf.setColorTema(cbtema.getValue().getNombre());
        conf.setColorMenu(tbmenu.isSelected());
        conf.setColorToolbar(tbtoolbar.isSelected());
        
        conf.setIdioma(cbidioma.getValue());
        conf.setFormatoMoneda(cbmoneda.getValue().getLocale().getCountry());

        if (conf.getId() == 777)
            cm.Agregar(conf);
        else
            cm.Editar(conf);
        
        App.ActualizarConfiguracion(conf);
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
                Dialogo.mostrarError("Error al copiar el archivo, verifica que existe y que tiene permisos para copiar en el directorio de destino.", "Error al copiar el archivo.", 
                        conf, ButtonType.OK);
        }
    }
    
    @FXML
    void enfasisAction(ActionEvent event) {
        ThemeColor enfasisSeleccionado = cbenfasis.getValue();
        ThemeColor temaSeleccionado = cbtema.getValue();
        
        Iterator it = App.escenas.keySet().iterator();
        while(it.hasNext()){
          String key = (String)it.next();
          Scene sx = (Scene) App.escenas.get(key);
          if (sx != null){
                Temas.aplicarTema(enfasisSeleccionado, temaSeleccionado, sx);
                Temas.colorearBarras(sx, tbmenu.isSelected(), tbtoolbar.isSelected());
          }
          
          System.out.println("Escena: " + key );
        }
        
        Temas.aplicarTema(enfasisSeleccionado, temaSeleccionado, thisStage.getScene());
    }
    
    private void cargarColores(JFXComboBox<ThemeColor> cb, ObservableList<ThemeColor> colores, boolean enfasis){
        cb.setItems(colores);
        for (ThemeColor tc : cb.getItems()){
            if ( tc.getNombre().equals(conf.getColorEnfasis() ) && enfasis )
                cb.getSelectionModel().select(tc);
            else if ( tc.getNombre().equals(conf.getColorTema()) && !enfasis )
                cb.getSelectionModel().select(tc);
        }
        
        javafx.util.Callback<ListView<ThemeColor>, ListCell<ThemeColor>> factory = new javafx.util.Callback<ListView<ThemeColor>, ListCell<ThemeColor>>() {
            @Override
            public ListCell<ThemeColor> call(ListView<ThemeColor> list) {
                return new Util.PanelColor();
            }
        };
        
        cb.setCellFactory(factory);
        cb.setButtonCell(factory.call(null));
   }   
}