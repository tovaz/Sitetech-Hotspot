package sitetech.hotspot;


import sitetech.hotspot.ThemeColor;
import java.net.URL;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import sitetech.Helpers.dbHelper;
import sitetech.hotspot.MainApp;
import sitetech.hotspot.Modelos.Configuracion;
import sitetech.hotspot.Modelos.ConfiguracionManager;

/**
 *
 * @author willi
 */
public class Temas {
    public static ObservableList<ThemeColor> getTemas(Object obj){
        
        ObservableList<String> indigo = FXCollections.observableArrayList( "/styles/Temas/Tema-Indigo.css", "/styles/botones.css", "/styles/Styles.css"  );
        ObservableList<String> turquesa = FXCollections.observableArrayList(  "/styles/Temas/Tema-Turquesa.css", "/styles/botones.css", "/styles/Styles.css"  );
        ObservableList<String> rosado = FXCollections.observableArrayList(  "/styles/Temas/Tema-Rosado.css", "/styles/botones.css", "/styles/Styles.css"  );
        ObservableList<String> verde = FXCollections.observableArrayList( "/styles/Temas/Tema-Verde.css", "/styles/botones.css", "/styles/Styles.css" );
        
        ThemeColor tci = new ThemeColor("Indigo", "304FFE", indigo);
        ThemeColor tct = new ThemeColor("Turquesa", "00BFA5", turquesa);
        ThemeColor tcr = new ThemeColor("Rosado", "F50057", rosado);
        ThemeColor tcv = new ThemeColor("Verde", "0F9D58", verde);
        return FXCollections.observableArrayList(tci, tct, tcr, tcv);
    }
    
    public static ThemeColor getThemebyName(String nombre, Object obj){
        ObservableList<ThemeColor> temas = getTemas(obj);
        for (ThemeColor tc : temas){
            if (tc.getNombre().equals(nombre))
                return tc;
        }
        return null;
    }
    
    public static void aplicarTema(String tema, Scene escena){
        Configuracion conf = ConfiguracionManager.getConfiguracion(new dbHelper());
        ThemeColor tc = getThemebyName(tema, escena);
        escena.getStylesheets().setAll(tc.getCssList());
    }
    
    public static void aplicarTema(Scene escena){
        Configuracion conf = ConfiguracionManager.getConfiguracion(new dbHelper());
        ThemeColor tc = getThemebyName(conf.getColorEnfasis(), escena);
        
        System.out.println(tc.getCssList());
        escena.getRoot().getStylesheets().setAll(tc.getCssList());
    }
}