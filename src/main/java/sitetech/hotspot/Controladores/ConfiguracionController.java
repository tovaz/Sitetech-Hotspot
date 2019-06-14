package sitetech.hotspot.Controladores;

import sitetech.hotspot.Temas;
import Util.Archivo;
import Util.Dialogo;
import Util.MiLocale;
import Util.Moneda;
import Util.StageManager;
import com.jfoenix.controls.JFXButton;
import sitetech.hotspot.ThemeColor;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXSpinner;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXToggleButton;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.Iterator;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.application.Platform;
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
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.springframework.util.StringUtils;
import sitetech.Helpers.backupHelper;
import sitetech.hotspot.MainApp;

/**
 * FXML Controller class
 *
 * @author Willi
 */
public class ConfiguracionController implements Initializable {

    @FXML private JFXComboBox<String> cbpais;
    @FXML private JFXTextField testado, tciudad, tdireccion, timagen, tdominio, tempresa, tusername, tcarpeta;
    @FXML private JFXComboBox<MiLocale> cbmoneda;
    @FXML private JFXComboBox<String> cbidioma;
    @FXML private Label lemoneda;
    @FXML private Text ttrabajando;
    @FXML private JFXToggleButton tgmostrarBarras;
    @FXML private ImageView iticket;
    @FXML private JFXComboBox<ThemeColor> cbenfasis, cbtema;
    @FXML private JFXToggleButton tbtoolbar, tbmenu, tgmostrarImagen, tgSincronizarConsumo, tgRegistrarVenta, tbbackup;
    @FXML private JFXTabPane tabs;
    @FXML private Tab tabtickets, tabnegocio, tabformatos, tabsincronizacion, tabdb, tabapariencia;
    @FXML private JFXButton bnegocio;
    @FXML private JFXSpinner sptrabajando;
        
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
        System.err.println(cbenfasis);
        System.err.println(cbtema);
        
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
        
        tbmenu.setSelected(conf.isColorMenu());
        tbtoolbar.setSelected(conf.isColorToolbar());
        
        //***************** CONFIGURACIONES DE TICKETS Y SINCRONIZACION **************
        tusername.setText(conf.getDefaultUsername());
        tgSincronizarConsumo.setSelected(conf.isSincronizarConsumo());
        tgRegistrarVenta.setSelected(conf.isSincronizarVenta());
        tgmostrarBarras.setSelected(conf.isCodigoBarraVisible());
        tgmostrarImagen.setSelected(conf.isImagenVisible());
        
        //********************* CONFIGURACION DE BACKUP BASE DE DATOS *****************
        dbMensaje(false, "");
        tcarpeta.setText(conf.getDirBackup());
        tbbackup.setSelected(conf.isHacerBackup());
        
        
        File fimg = new File(conf.getImagenTicket());
        iticket.setImage(new Image( fimg.toURI().toString()) );
        bmenuAnterior = bnegocio;
        
    }
    
    JFXButton bmenuAnterior = null;
    @FXML
    void onclickMenu(ActionEvent event) {
        JFXButton btn = (JFXButton)event.getTarget();
        SingleSelectionModel<Tab> selectionModel = tabs.getSelectionModel();
        
        bmenuAnterior.getStyleClass().remove("activo");
        btn.getStyleClass().add("activo");
        bmenuAnterior = btn;
        switch (btn.getText()){
            case "Negocio": selectionModel.select(tabnegocio); break;
            case "Ticket": selectionModel.select(tabtickets); break;
            case "Sincronizacion": selectionModel.select(tabsincronizacion); break;            
            case "Formatos": selectionModel.select(tabformatos); break;
            case "Apariencia": selectionModel.select(tabapariencia); break;
            case "Bases de Datos": selectionModel.select(tabdb); break;
        }
    }
    
    private void agregarTab(String texto, Node iconPath) {
        for (Tab tx : tabs.getTabs()){
            tx.setGraphic(new Group(new Label(texto, iconPath)));
        }
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
        
        // CONFIGURACION DE APARIENCIA
        conf.setColorEnfasis(cbenfasis.getValue().getNombre());
        conf.setColorTema(cbtema.getValue().getNombre());
        conf.setColorMenu(tbmenu.isSelected());
        conf.setColorToolbar(tbtoolbar.isSelected());
        
        conf.setIdioma(cbidioma.getValue());
        conf.setFormatoMoneda(cbmoneda.getValue().getLocale().getCountry());

        // CONFIGURACION DE TICKET Y SINCRONIZACION
        conf.setCodigoBarraVisible(tgmostrarBarras.isSelected());
        conf.setImagenVisible(tgmostrarImagen.isSelected());
        conf.setSincronizarConsumo(tgSincronizarConsumo.isSelected());
        conf.setSincronizarVenta(tgRegistrarVenta.isSelected());
        conf.setDefaultUsername(tusername.getText());
        
        //********************* CONFIGURACION DE BACKUP BASE DE DATOS *****************
        conf.setDirBackup(tcarpeta.getText());
        conf.setHacerBackup(tbbackup.isSelected());
        
        
        if (conf.getId() == 777)
            cm.Agregar(conf);
        else
            cm.Editar(conf);
        
        App.ActualizarConfiguracion(conf);
        cancelarAction(event);
    }
    
    @FXML
    void cancelarAction(ActionEvent event) {
        ThemeColor tema = Temas.getTemasMap().get(App.configuracion.getColorTema());
        ThemeColor enfasis = Temas.getCssporNombre(App.configuracion.getColorEnfasis(), Temas.getEnfasis());
        aplicarTema(tema, enfasis);
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
        aplicarTema(enfasisSeleccionado, temaSeleccionado);
        
    }
    
    public void aplicarTema(ThemeColor enfasisSeleccionado, ThemeColor temaSeleccionado){
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
        Temas.colorearBarras(thisStage.getScene(), tbmenu.isSelected(), tbtoolbar.isSelected());
    }
        
    private void cargarColores(JFXComboBox<ThemeColor> cb, ObservableList<ThemeColor> colores, boolean enfasis){
        for (ThemeColor x : colores)
            System.out.println(x.getNombre());
        
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
    
    /************************* BACKUP BASES DE DATOS ***********************/
    /***********************************************************************/
    Runnable hiloTerminado = new Runnable() { @Override public void run(){ dbMensaje(false, ""); } };
    boolean errImport = false;
    private String openDir(){
        DirectoryChooser seldir = new DirectoryChooser();
        seldir.setTitle("Selecciona una carpeta para guardar el Backup");
        File file = seldir.showDialog(thisStage);
        if (file != null) return file.getPath() + "\\";
        
        return null;
    }
    
    @FXML
    void onexaminarCarpeta(ActionEvent event) {
        String dir = openDir();
        if (dir != null)
            tcarpeta.setText(dir);
    }
    
    @FXML
    void onExportar(ActionEvent event) {
        String dir = openDir();
        if (dir != null){
            dbMensaje(true, "Exportando base de datos, espere porfavor.");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    backupHelper.exportar(dir);
                    Platform.runLater( hiloTerminado );
                }
            }).start();
        }
    }

    @FXML
    void onImportar(ActionEvent event) {
        String dir = openDir();
        System.out.println("Restaurando desde backup: " + dir);
        if (dir != null){
            ButtonType bconfirmar = Dialogo.mostrarConfirmacion("¿Desea realmente eliminar los datos actuales y reemplazarlos por los datos del backup ?", "Restaurar desde Backup", App.configuracion, ButtonType.YES, ButtonType.NO);
            if (bconfirmar == ButtonType.YES){
                dbMensaje(true, "Restaurando desde backup: " + dir);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        errImport = !backupHelper.restoreDb(dir);
                        Platform.runLater( hiloTerminado );
                    }
                }).start();
            }
        }
    }
    
    @FXML
    void onBackup(ActionEvent event) {
        dbMensaje(true, "Realizando Copia de Seguridad ...");
        new Thread(new Runnable() {
            @Override
            public void run() {
                backupHelper.exportar(tcarpeta.getText());
                Platform.runLater( hiloTerminado );
            }
        }).start();
    }
    
    private void dbMensaje(boolean estado, String mensaje){
        sptrabajando.setVisible(estado);
        ttrabajando.setVisible(estado);
        ttrabajando.setText(mensaje);
        if (errImport)  
            Dialogo.mostrarAlerta("Error al intentar restablecer la base de datos desde el directorio seleccionado, intenta seleccionar otro directorio o subcarpeta.", "Error al restaurar", App.configuracion, ButtonType.OK);
        
        errImport = false;
    }
}