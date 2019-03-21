package sitetech.hotspot;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.stage.Stage;
import sitetech.hotspot.Controladores.LoginController;
import sitetech.hotspot.Controladores.MainController;
import sitetech.hotspot.Modelos.Usuario;
import sitetech.hotspot.Modelos.usuarioManager;

public class MainApp extends Application {

    public Usuario usuarioLogeado = null;
    public static Stage primaryStage;

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = new Stage();
        //primaryStage.getIcons().add(new Image(MainApp.class.getResourceAsStream( "/Imagenes/icon.png" )));
        //loginScene();
        
        checkLogin("tovaz", "correr");
        //********************************************
        //setUserAgentStylesheet(STYLESHEET_MODENA);
        //setUserAgentStylesheet(STYLESHEET_CASPIAN);
        //AquaFx.style();
        //********************************************
    }

    public static void main(String[] args) {
        launch(args);
    }

    LoginController logc;
    public void loginScene() {
        logc = new LoginController(this);
        logc.showStage();
    }

    public void mainScene2(Stage stagePrincipal) {
        
    }

    MainController mainControlador;
    public void mainScene() {
        mainControlador = new MainController();
        mainControlador.showStage();
    }

    public boolean checkLogin(String usuario, String contraseña){
        usuarioManager um = new usuarioManager();
        boolean login = um.checkLogin(usuario, contraseña);
        
        if (login){
            this.usuarioLogeado = um.usuarioLogeado;
            this.mainScene();
            
            mainControlador.logearUsuario(usuarioLogeado);
            return true;
        }
        else return false;
    }
    
    public static Stage getStage(){ return primaryStage; }
}
