package sitetech.hotspot.Controladores;

import Util.ArrastrarScene;
import Util.Dialogo;
import Util.Moneda;
import sitetech.hotspot.Temas;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.effect.MotionBlur;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sitetech.hotspot.MainApp;
import sitetech.hotspot.Modelos.Caja;
import sitetech.hotspot.Modelos.Usuario;

public class MainController implements Initializable, ArrastrarScene {

    public MainApp App;
    public Stage thisStage;
    
    @FXML private AnchorPane panelPrincipal;
    @FXML private AnchorPane panelTitulo;
    @FXML private Label ltitulo;
    @FXML private TextField tprueba;
    @FXML private Label lusuario;
    @FXML private Label lcaja;
    @FXML private Label lcajaTotal;

    TicketsController tc;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //this.ArrastrarScene(panelTitulo);
        tc = new TicketsController(App);
        try {
            tc.cargarPanel(panelPrincipal);
            //Temas.aplicarTema(tc.nodo.getScene(), App.configuracion);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    protected void finalize() throws Throwable {
        App.stop();
        super.finalize(); //To change body of generated methods, choose Tools | Templates.
    }

    
    @FXML
    void testAction(ActionEvent event) throws IOException {

    }

    public void actualizarInfo(Usuario user, Caja caja){
        lusuario.setText(user.getNombre());
        lcaja.setText( String.valueOf(caja.getId() ));
        lcajaTotal.setText( Moneda.Formatear(caja.getTotal(), Locale.getDefault()));
    }
    
    public MainController(MainApp _mainApp) {
        App = _mainApp;
        thisStage = new Stage();
        //util.cargarStage("/Vistas/mainScene.fxml", "Hotspot", thisStage, this, Modality.APPLICATION_MODAL);
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/Vistas/mainScene.fxml"));
            loader.setController(this);
            
            thisStage.initStyle(StageStyle.DECORATED);
            Scene scene = new Scene((Parent) loader.load());

            Temas.aplicarTema(scene, App.configuracion);
            thisStage.setScene(scene);
            thisStage.setTitle("Hotspot 1.0");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        
        App.agregarEscena("scene_main", thisStage.getScene());
    }

    public void showStage() {
        panelPrincipal.setEffect(null);
        thisStage.show();
    }

    @FXML
    public void loadUsuarios() throws IOException {
        adUsuarioController adUsuario = new adUsuarioController(App);
        adUsuario.showAgregar(new UsuariosController(App));
    }

    UsuariosController uvController;
    RoutersController rController;
    PaquetesController pController;
    @FXML
    private void onMenuAction(ActionEvent event) throws IOException {
        MenuItem mi = (MenuItem) event.getSource();
        System.out.println(mi.getText());

        
        switch (mi.getText()) {
            //*************** ADMIN MENU ******************//
            case "Usuarios":
                uvController = new UsuariosController(App);
                uvController.showStage();
                break;

            case "Routers":
                rController = new RoutersController(App);
                rController.showStage();
                break;

            case "Paquetes de Internet":
                pController = new PaquetesController(App);
                pController.showStage();
                break;
            
            case "Configuracion":
                ConfiguracionController pConfiguracion = new ConfiguracionController(App);
                ObservableList<Scene> scenes = FXCollections.observableArrayList();
                scenes.add(tc.thisStage.getScene());
                scenes.add(thisStage.getScene());
                
                pConfiguracion.thisStage.setUserData(scenes);
                pConfiguracion.showStage();
                break;
                
            //*************** TICKET MENU ******************//
            //*************** CAJA MENU ******************//
            case "Consultar caja": case "Cerrar caja":
                DetalleCajaControlador detallesC = new DetalleCajaControlador(App);
                detallesC.showStage();
                break;
        }

    }
    
    private void CerrarCaja(){
        
    }    

    @FXML
    void cerrarAction(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    void minimizarAction(ActionEvent event) {
        thisStage.setIconified(true);
    }

    private boolean isMaximized = false;
    private double ancho, alto, x, y;

    @FXML
    void maximizarAction(ActionEvent event) {

        Screen screen = Screen.getPrimary();
        javafx.geometry.Rectangle2D bounds = screen.getVisualBounds();

        if (!isMaximized) {
            x = thisStage.getX();
            y = thisStage.getY();
            ancho = thisStage.getWidth();
            alto = thisStage.getHeight();

            thisStage.setX(bounds.getMinX());
            thisStage.setY(bounds.getMinY());
            thisStage.setWidth(bounds.getWidth());
            thisStage.setHeight(bounds.getHeight());
        } else {
            thisStage.setX(x);
            thisStage.setY(y);
            thisStage.setWidth(ancho);
            thisStage.setHeight(alto);
        }
        isMaximized = !isMaximized;
    }
    
    @FXML
    void cerrarSesionAction(ActionEvent event) throws Exception {
        ButtonType btn = Dialogo.mostrarConfirmacion("¿Desea realmente cerrar sesion?", "Cerrar Sesion", App.configuracion, ButtonType.YES, ButtonType.NO);
        
        if (btn == ButtonType.YES){
            LoginController logc = new LoginController(App);
            App.usuarioLogeado = null;
            lusuario.setText("");
            
            MotionBlur mb = new MotionBlur();
            mb.setRadius(15.0f);
            mb.setAngle(45.0f);

            panelPrincipal.setEffect(mb);
            logc.showStage();

            if (App.usuarioLogeado == null){
                thisStage.close();
            }
        }
    }
}
