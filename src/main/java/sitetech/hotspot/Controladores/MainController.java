package sitetech.hotspot.Controladores;

import Util.ArrastrarScene;
import Util.Moneda;
import sitetech.hotspot.Temas;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sitetech.hotspot.MainApp;
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
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @FXML
    void testAction(ActionEvent event) throws IOException {

    }

    public void cargarInfo(Usuario user){
        lusuario.setText(user.getNombre());
        lcaja.setText( String.valueOf(App.cajaAbierta.getId() ));
        lcajaTotal.setText( Moneda.Formatear(App.cajaAbierta.getTotal(), App.configuracion.getRegionLocal().getLocale()));
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

            Temas.aplicarTema(scene);
            thisStage.setScene(scene);
            thisStage.setTitle("Hotspot 1.0");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        
        App.agregarEscena("scene_main", thisStage.getScene());
    }

    public void showStage() {
        //ltitulo.setText("Hotspot - Sitetech");
        thisStage.show();
    }

    @FXML
    public void loadUsuarios() throws IOException {
        adUsuarioController adUsuario = new adUsuarioController();
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

        }

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
    void cerrarSesionAction(ActionEvent event) {
        
    }
}
