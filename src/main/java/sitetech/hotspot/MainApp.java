package sitetech.hotspot;

import java.io.IOException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sitetech.hotspot.Controladores.LoginController;
import sitetech.hotspot.Controladores.MainController;
import sitetech.hotspot.Modelos.Usuario;


public class MainApp extends Application {
    public Usuario usuarioLogeado = null;
    private Stage primaryStage;
    
    @Override
    public void start(Stage stage) throws Exception {
        /*Parent root = FXMLLoader.load(getClass().getResource("/Vistas/mainScene.fxml"));
        
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");
        
        stage.setTitle("JavaFX and Maven");
        stage.setScene(scene);
        stage.show();*/
        
        primaryStage = new Stage();
        loginScene();
        
        //********************************************
        //setUserAgentStylesheet(STYLESHEET_MODENA);
        //setUserAgentStylesheet(STYLESHEET_CASPIAN);
        //AquaFx.style();
        //********************************************
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
        public void loginScene()
    {
        try {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("/Vistas/login.fxml"));

        //AnchorPane rootLayout = (AnchorPane) loader.load();
        VBox rootLayout = (VBox) loader.load();
        Scene scene = new Scene(rootLayout);
        primaryStage.setScene(scene);
        
        LoginController controladorx = loader.getController();
        controladorx.pasarStage(this, primaryStage);
        
        primaryStage.show();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
    
    public void mainScene() throws IOException
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("/Vistas/mainScene.fxml"));
        
        AnchorPane rootLayout = (AnchorPane) loader.load();
        Scene scene = new Scene(rootLayout);
        primaryStage.setScene(scene);
        
        MainController controladorx = loader.getController();
        controladorx.pasarStage(this, primaryStage);
        
        //primaryStage.getIcons().add(new Image(this.getClass().getResource("icon.png").toString()) );
        primaryStage.show();
    }

}
