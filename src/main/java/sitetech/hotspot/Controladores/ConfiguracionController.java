package sitetech.hotspot.Controladores;

import sitetech.hotspot.Temas;
import Util.Archivo;
import Util.Dialogo;
import Util.MiLocale;
import sitetech.hotspot.ThemeColor;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXToggleButton;
import com.jfoenix.controls.JFXTextField;
import javafx.scene.shape.Rectangle;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
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
import sitetech.hotspot.Modelos.ConfiguracionManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import sitetech.Helpers.dbHelper;

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
    @FXML private ImageView iticket;
    @FXML private JFXComboBox<ThemeColor> cbenfasis;
    
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
        cargarColores();
        
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
        
        cbmoneda.setValue(new MiLocale(conf.getrLocale(), conf.getrLocale().getDisplayCountry()));
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
        
        conf.setEmpresa(tempresa.getText());
        conf.setPais(cbpais.getValue());
        conf.setEstado(testado.getText());
        conf.setCiudad(tciudad.getText());
        conf.setDireccion(tdireccion.getText());
        conf.setImagenTicket(timagen.getText());
        conf.setDominio(tdominio.getText());
        conf.setCodigoBarraVisible(tgmostrarBarras.isSelected());
        conf.setColorEnfasis(cbenfasis.getValue().getNombre());
        
        NumberFormat nf = NumberFormat.getCurrencyInstance(cbmoneda.getValue().getLocale());
        conf.setMoneda(nf.getCurrency());
        conf.setrLocale(cbmoneda.getValue().getLocale());

        if (conf.getId() == 777)
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
        ObservableList<String> enfasis = cbenfasis.getValue().getCssList();
        Stage primaryStage = (Stage)thisStage.getUserData();
        thisStage.getScene().getRoot().getStylesheets().setAll(enfasis);
        
        primaryStage.getScene().getRoot().getStylesheets().setAll(enfasis);
        System.out.println("ENFASIS: #" + enfasis.get(0));
    }
    
    private void cargarColores(){
        ObservableList<ThemeColor> colores = Temas.getTemas(this);
        cbenfasis.setItems(colores);
        for (ThemeColor tc : cbenfasis.getItems()){
            if ( tc.getNombre().equals(ConfiguracionManager.getConfiguracion(new dbHelper()).getColorEnfasis() ) )
                cbenfasis.getSelectionModel().select(tc);
        }
        javafx.util.Callback<ListView<ThemeColor>, ListCell<ThemeColor>> factory = new javafx.util.Callback<ListView<ThemeColor>, ListCell<ThemeColor>>() {
            @Override
            public ListCell<ThemeColor> call(ListView<ThemeColor> list) {
                return new ColorRectCell();
            }
        };

        cbenfasis.setCellFactory(factory);
        cbenfasis.setButtonCell(factory.call(null));
    }
    
    static class ColorRectCell extends ListCell<ThemeColor>{
      @Override
      public void updateItem(ThemeColor item, boolean empty){
          super.updateItem(item, empty);
          AnchorPane panel = new AnchorPane();
          panel.setPrefSize(150, 35);
          Label lb = new Label();
          lb.setPadding(new Insets(10,10,10,10));
          
          lb.textAlignmentProperty().setValue(TextAlignment.CENTER);
          
          Rectangle rect = new Rectangle(120,18);
          if(item != null){
            lb.setText(item.getNombre());
            panel.setStyle("-fx-background-color: #" + item.getColor() + "");
            panel.getChildren().addAll(lb);
              setGraphic(panel);
          }
    }
    }   
    
}