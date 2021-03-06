package sitetech.hotspot;

import Util.Dialogo;
import Util.StageManager;
import Util.backupCron;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import sitetech.Helpers.dbHelper;
import sitetech.Helpers.dbManager;
import sitetech.hotspot.Controladores.LicenciaController;
import sitetech.hotspot.Controladores.LoginController;
import sitetech.hotspot.Controladores.MainController;
import sitetech.hotspot.Controladores.SplashControlador;
import sitetech.hotspot.Modelos.Caja;
import sitetech.hotspot.Modelos.CajaManager;
import sitetech.hotspot.Modelos.Configuracion;
import sitetech.hotspot.Modelos.ConfiguracionManager2;
import sitetech.hotspot.Modelos.Usuario;
import sitetech.hotspot.Modelos.usuarioManager;

@SpringBootApplication

public class MainApp extends Application {

    public ConfigurableApplicationContext springContext;
    public static Map<String, Scene> escenas = new HashMap<String, Scene>();
    
    private LoginController logc;
    private MainController mainControlador;
    private CajaManager cm;
    
    public Usuario usuarioLogeado = null;
    public Caja cajaAbierta;
    public Configuracion configuracion;
    public static Image iconoApp = new Image(MainApp.class.getResourceAsStream( "/Imagenes/ico.png" ));
    
    public static Stage mainStage;
    private backupCron backups;
    
    @Override
    public void init() throws Exception {
        springContext = SpringApplication.run(MainApp.class);
    }

    @Override
    public void stop(){
        try {
            Platform.exit();
            System.exit(0);
        } catch (Exception ex){ System.err.println(ex.getMessage()); }
        springContext.stop();
    }
    
    
    SplashControlador sc;
    @Override
    public void start(Stage stage) throws Exception {
        mainStage = new Stage();
        sc = new SplashControlador(this);
        sc.mostrar();
        //StageManager.SplashScreen(this);
    }
    
    public void cargarApp() throws Exception{
        cm = new CajaManager();
        //sc.lmensaje.setText("Cargando clases ...");
        
        Locale.setDefault(Locale.forLanguageTag("es"));
        configuracion = ConfiguracionManager2.getConfiguracion(new dbHelper());
        checkConeccion();
        
        //sc.lmensaje.setText("Revisando Base de Datos ...");
        Locale.setDefault(configuracion.getRegionLocal().getLocale());
        configurarBackup();
    }
    
    private void configurarBackup(){ // REALIZAR COPIA DE SEGURIDAD DB, SI ESTA ACTIVADA LA OPCION
        if (configuracion.isHacerBackup()){
            backupCron backups = new backupCron("jdbc:derby:hotspot", configuracion.getDirBackup()); // INICIA AL BACKUP A LA 11 PM
            backups.run();
            backups.start(23);
        }
    }
    public void checkConeccion() throws Exception{
        dbManager dbm = new dbManager();
        if (!dbm.conectarHb()){
            Dialogo.mostrarError("Error al intentar comunicarse con la base de datos.", "Error conexion de base de datos.", configuracion, ButtonType.OK);
            stop();
        }
    }
    
    public void ActualizarConfiguracion(Configuracion conf){
        configuracion = conf;
        Locale.setDefault(conf.getRegionLocal().getLocale());
        actualizarCaja(cajaAbierta);
    }
    
    public void agregarEscena(String key, Scene scene){
        escenas.put(key, scene);
    }
    
    public static void main(String[] args) {
        launch(args);
    }

    public SplashControlador Splash;
    public void loginScene(SplashControlador _splash) {
        Splash = _splash;
        if (checkLicencia()){ // Chequeamos si la licencia es valida
            logc = new LoginController(this);        
            if (_splash != null) _splash.thisStage.close();
            logc.showStage();
        }
    }

    public void mainScene() {
        cajaAbierta = cm.getCajaAbierta(usuarioLogeado);
        if (lic != null) lic.thisStage.hide();
        if (mainControlador == null)
            mainControlador = new MainController(this);
        
        mainControlador.showStage();
    }
    
    public void actualizarCaja(Caja caja){
        this.cajaAbierta = caja;
        mainControlador.actualizarInfo(usuarioLogeado, cajaAbierta);
    }

    public LicenciaController lic = null;
    public boolean checkLicencia(){
        try {
            sc.lmensaje.setText("Revisando informacion de licencia");
            lic = new LicenciaController(this);
            return lic.checkLicencia();
        }
        catch (Exception ex){
            System.err.println(ex.getMessage());
        }
        
        return false;
    }
    
    public boolean checkLogin(String usuario, String contraseña, SplashControlador splash){
        usuarioManager um = new usuarioManager();
        boolean login = um.checkLogin(usuario, contraseña);
        
        if (login){
            this.usuarioLogeado = um.usuarioLogeado;
            this.mainScene();                       // ABRE LA ESCENA CENTRAL ( MAIN SCENE )
            
            //agregarVariable("usuarioLogeado", usuarioLogeado);
            mainControlador.aplicarPermisos();
            mainControlador.actualizarInfo(usuarioLogeado, cajaAbierta);
            
            if (splash != null)
                splash.thisStage.close();
            return true;
        }
        else return false;
    }
    
    //public static Stage getStage(){ return primaryStage; }

    
}
