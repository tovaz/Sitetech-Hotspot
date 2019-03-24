package sitetech.hotspot;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sitetech.Helpers.dbHelper;
import sitetech.hotspot.Controladores.LoginController;
import sitetech.hotspot.Controladores.MainController;
import sitetech.hotspot.Modelos.Caja;
import sitetech.hotspot.Modelos.CajaManager;
import sitetech.hotspot.Modelos.Configuracion;
import sitetech.hotspot.Modelos.ConfiguracionManager2;
import sitetech.hotspot.Modelos.Usuario;
import sitetech.hotspot.Modelos.usuarioManager;

public class MainApp extends Application {

    public static Stage primaryStage;
    public static Map<String, Scene> escenas = new HashMap<String, Scene>();
    
    private LoginController logc;
    private MainController mainControlador;
    private CajaManager cm;
    
    public Usuario usuarioLogeado = null;
    public Caja cajaAbierta;
    public Configuracion configuracion;
    
    @Override
    public void start(Stage stage) throws Exception {
        //primaryStage = new Stage();
        cm = new CajaManager();
        configuracion = ConfiguracionManager2.getConfiguracion(new dbHelper());
        Locale.setDefault(configuracion.getRegionLocal().getLocale());
        
        //setUserAgentStylesheet();
        //primaryStage.getIcons().add(new Image(MainApp.class.getResourceAsStream( "/Imagenes/icon.png" )));
        
        checkLogin("tovaz", "correr");
        
        //********************************************
        //setUserAgentStylesheet(STYLESHEET_MODENA);
        //setUserAgentStylesheet(STYLESHEET_CASPIAN);
        //AquaFx.style();
        //********************************************
        
    }
    
    public void ActualizarConfiguracion(Configuracion conf){
        configuracion = conf;
        Locale.setDefault(conf.getRegionLocal().getLocale());
    }
    
    public void agregarEscena(String key, Scene scene){
        escenas.put(key, scene);
        //primaryStage.setUserData(escenas);
    }
    
    public static void main(String[] args) {
        launch(args);
    }

    public void loginScene() {
        logc = new LoginController(this);
        logc.showStage();
    }

    public void mainScene() {
        mainControlador = new MainController(this);
        cajaAbierta = cm.getCajaAbierta(usuarioLogeado);
        mainControlador.showStage();
    }

    public boolean checkLogin(String usuario, String contraseña){
        usuarioManager um = new usuarioManager();
        boolean login = um.checkLogin(usuario, contraseña);
        
        if (login){
            this.usuarioLogeado = um.usuarioLogeado;
            this.mainScene();                       // ABRE LA ESCENA CENTRAL ( MAIN SCENE )
            
            //agregarVariable("usuarioLogeado", usuarioLogeado);
            mainControlador.cargarInfo(usuarioLogeado);
            return true;
        }
        else return false;
    }
    
    //public static Stage getStage(){ return primaryStage; }

    
}
