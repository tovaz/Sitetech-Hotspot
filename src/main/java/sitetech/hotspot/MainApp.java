package sitetech.hotspot;

import java.io.IOException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sitetech.hotspot.Controladores.LoginController;
import sitetech.hotspot.Controladores.MainController;
import sitetech.hotspot.Modelos.Usuario;

public class MainApp extends Application {

    public Usuario usuarioLogeado = null;
    public static Stage primaryStage;

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = new Stage();
        //primaryStage.getIcons().add(new Image(MainApp.class.getResourceAsStream( "/Imagenes/icon.png" )));
        mainScene();

        //********************************************
        //setUserAgentStylesheet(STYLESHEET_MODENA);
        //setUserAgentStylesheet(STYLESHEET_CASPIAN);
        //AquaFx.style();
        //********************************************
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void loginScene() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/Vistas/login.fxml"));

            //AnchorPane rootLayout = (AnchorPane) loader.load();
            VBox rootLayout = (VBox) loader.load();
            Scene scene = new Scene(rootLayout);
            scene.getStylesheets().add("../../resources/styles/Styles.css");
            primaryStage.setScene(scene);

            LoginController controladorx = loader.getController();
            controladorx.pasarStage(this, primaryStage);

            primaryStage.show();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void mainScene2(Stage stagePrincipal) {
        
    }

    public void mainScene() {
        MainController mainControlador = new MainController();
        mainControlador.showStage();
    }

    public void cambiarTema(ThemeColor tm){
        primaryStage.getScene().getRoot().getStylesheets().addAll(tm.getCssList());
    }
    
    public static Stage getStage(){ return primaryStage; }
}
