/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sitetech.hotspot.Controladores;

import Util.Dialogo;
import animatefx.animation.FadeIn;
import animatefx.animation.FadeOut;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXSpinner;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.io.IOException;
import static java.lang.Thread.sleep;
import java.net.URI;
import java.net.URL;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.util.Duration;
import org.codehaus.groovy.tools.FileSystemCompiler;
import sitetech.Helpers.DateTimeHelper;
import sitetech.License.CryptoKey;
import sitetech.License.Licencia;
import sitetech.License.Licencia.tipoType;
import sitetech.hotspot.MainApp;

/**
 * FXML Controller class
 *
 * @author megan
 */
public class LicenciaController extends MiControlador {

    @FXML private VBox plicencia;
    @FXML private JFXTextField tnegocio, tpais, tapp, tversion, tfechaVencimiento, tlicencia, tfechaCreacion;
    @FXML private JFXComboBox<Licencia.tipoType> ttlicencia;
    @FXML private JFXButton bcopiar;
    @FXML private JFXButton bactivar;
    @FXML private TextArea tclientId;
    @FXML private JFXButton babrir;
    @FXML private AnchorPane psinlicencia;
    @FXML private Text lerror;
    @FXML private AnchorPane perror;
    @FXML private AnchorPane pprincipal;
    @FXML private JFXButton babrir1;
    @FXML private JFXSpinner spactivando;
    
    Licencia licencia;
    CopyOption[] opciones = new CopyOption[]{
                StandardCopyOption.REPLACE_EXISTING,
                StandardCopyOption.COPY_ATTRIBUTES
        };
    public LicenciaController(MainApp _app) {
        cargarEscena("/Vistas/Licencia/Licencia.fxml", "Activacion del programa", Modality.WINDOW_MODAL, _app);
        thisStage.resizableProperty().set(false);
        ttlicencia.getItems().setAll(Licencia.tipoType.values());
        
        generarLicenciaLocal();
    }

    public void generarLicenciaLocal(){
        String uuid = CryptoKey.getUID();
        licencia = new Licencia(uuid, "1.0", "Sitetech Hotspot", Licencia.tipoType.FreeWare, 0);
        if (licencia != null){
            if (licencia.getEncriptedUID() != null){
                tclientId.setText(licencia.getEncriptedUID());
            }
        }
    }
    
    public void showLicencia(){
        psinlicencia.setVisible(true);
        plicencia.setVisible(false);
        perror.setVisible(false);
        if (App.Splash != null) App.Splash.thisStage.close();
        
        showAndWait();
    }
    
    public boolean checkLicencia(){
        String licFile = System.getProperty("user.dir") + "\\license.lic";
        String licFileTemp = System.getProperty("user.dir") + "\\license.lic_";
        File file = null;
        
        //if (true) return true; // PARA DESACTIVAR LA COMPROBACION DE LICENCIA
        
        if (Files.exists(Paths.get(licFile))){ //Copiamos la licencia a un archivo temporal, para no bloquearla al abrirla
            try {
                Files.deleteIfExists(Paths.get(licFileTemp));
                Files.copy(Paths.get(licFile), Paths.get(licFileTemp), opciones);
                file = new File(licFileTemp);
            }catch (IOException ex){ System.err.println(ex.getMessage());}
        }
        else
            file = new File(licFile);
        
        if (file.exists()){
            System.out.println("Archivo no es nulo.");
            boolean check = checkFileLicense(file);
            if (!check)
                showLicencia();
            return check;
        }
        else
            showLicencia();
        
        return false;
    }
    
    public void loadInfo(Licencia lic){
        String fechaCreacion = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(lic.getFechaCreacion());
        String fechaVencimiento = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(lic.getFechaVencimiento());
    
        tnegocio.setText(lic.getNegocio());
        tpais.setText(lic.getPais()); 
        tapp.setText(lic.getNombreApp());
        tversion.setText(lic.getVersion());
        ttlicencia.getSelectionModel().select(lic.getTipoLicencia());
        tlicencia.setText(lic.getLicencia());
        //tclientId.setText(lic.getEncriptedUID());
        tfechaCreacion.setText(fechaCreacion);
        tfechaVencimiento.setText(fechaVencimiento);
        licencia = lic;
    }
    
    @FXML
    private void oncopiar(ActionEvent event) {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent content = new ClipboardContent();
        content.putString(tclientId.getText());
        clipboard.setContent(content);
    }
    
    @FXML
    private void onactivar(ActionEvent event) throws InterruptedException {
        String dir = System.getProperty("user.dir");
        System.out.println("Directorio app:" + dir );
        
        spactivando.setVisible(true);
        bactivar.setDisable(true);
        try {
            Files.deleteIfExists(Paths.get(dir + "\\license.lic"));
            Files.copy(Paths.get(archivo.toURI()), Paths.get(dir + "\\license.lic"), opciones);
            Dialogo.mostrarInformacion("El programa se activo exitosamente.", "Activacion correcta", App.configuracion, ButtonType.OK);
            
            //thisStage.hide();
            App.loginScene(null);  // CARGAMOS LA ESCENA DEL LOGIN
        }
        catch (IOException ex){
            Dialogo.mostrarError("Error al activar el programa, no se pudo copiar la licencia. \n" + ex.getMessage(), "Error de activacion.", App.configuracion, ButtonType.OK);
            System.err.println(ex.getCause() + " -- \n" + ex.getMessage());
        }
        
        new FadeOut(spactivando).setDelay(Duration.millis(500));
        bactivar.setDisable(false);
    }

    @FXML
    private void oncerrar(ActionEvent event) {
        thisStage.close();
    }
    
    File archivo;
    @FXML
    private void onabrir(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Archivos de licencia (*.lic)", "*.lic");
        
        fileChooser.setTitle("Abrir archivo de licencia");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(this.thisStage);
        if (file != null){
            Licencia licenciaFile = CryptoKey.leerLicencia(file.getAbsolutePath());
            if (checkFileLicense(file)){
                perror.setVisible(false);
                psinlicencia.setVisible(false);
                plicencia.setVisible(true);
                pprincipal.getStyleClass().remove("fondoApp2");
                pprincipal.setStyle("-fx-background-color: -colorFondo2");
                loadInfo(licenciaFile);
            }
            else
                perror.setVisible(true);
        }
    }
    
    public boolean checkFileLicense(File file){
        Licencia licenciaFile = CryptoKey.leerLicencia(file.getAbsolutePath());
        archivo = file;
        if (licenciaFile != null){
            licencia = licenciaFile;
            System.out.println("FECHA VE: " + licencia.getFechaVencimiento());
            System.out.println("FECHA CRE: " + licencia.getFechaCreacion());
            System.out.println("FECHA LIC: " + licencia.getFechaLicencia());
            System.out.println("TIPO: " + licencia.getTipoLicencia().name());
            checkDate(licencia);
            if (licencia.getEncriptedUID().equals(licenciaFile.getEncriptedUID())) {// LA LICENCIA TIENE EL IDENTIFICADOR PARA EL CLIENTE ID CORRECTO.
                if (licencia.getTipoLicencia() == tipoType.Completa)
                    return true;
                if (licencia.getTipoLicencia() == tipoType.Prueba)
                    return checkDate(licencia);
                if (licencia.getTipoLicencia() == tipoType.FreeWare)
                    return true;
            }
        }
        else{
            lerror.setText("El archivo de licencia es invalido, comunicate con el proveedor de licencias.");
            perror.setVisible(true);
            Dialogo.mostrarError("El archivo de licencia es invalido, comunicate con el proveedor de licencias.", "Licencia invalida", App.configuracion, ButtonType.OK);
        }
        
        return false;
    }

    private boolean checkDate(Licencia _lic){
        Date dateNTP = DateTimeHelper.getNTPDate();
        long dias = DateTimeHelper.getDifferenceDays(dateNTP, _lic.getFechaVencimiento());
        System.out.println("DIAS: " + dias);
        if ( dias > 0 )
            return true;
        else 
            if (_lic.getTipoLicencia() == tipoType.Prueba)
                Dialogo.mostrarError("La licencia ya vencio, porfavor compra una nueva licencia.", "Licencia de prueba", App.configuracion, ButtonType.OK);

        return false;
    }
    
}
