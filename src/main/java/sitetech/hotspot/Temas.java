package sitetech.hotspot;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import sitetech.Helpers.dbHelper;
import sitetech.hotspot.Modelos.Configuracion;
import sitetech.hotspot.Modelos.ConfiguracionManager2;

/**
 *
 * @author willi
 */
public class Temas {
    public static ObservableList<ThemeColor> getEnfasis(){
        ObservableList<String> indigo = FXCollections.observableArrayList( "/styles/Temas/Tema-Indigo.css", "/styles/botones.css", "/styles/Styles.css", "/styles/validation.css"  );
        ObservableList<String> turquesa = FXCollections.observableArrayList(  "/styles/Temas/Tema-Turquesa.css", "/styles/botones.css", "/styles/Styles.css", "/styles/validation.css"  );
        ObservableList<String> rosado = FXCollections.observableArrayList(  "/styles/Temas/Tema-Rosado.css", "/styles/botones.css", "/styles/Styles.css", "/styles/validation.css"  );
        ObservableList<String> verde = FXCollections.observableArrayList( "/styles/Temas/Tema-Verde.css", "/styles/botones.css", "/styles/Styles.css", "/styles/validation.css" );
        
        ThemeColor tci = new ThemeColor("Indigo", "304FFE", indigo);
        ThemeColor tct = new ThemeColor("Turquesa", "00BFA5", turquesa);
        ThemeColor tcr = new ThemeColor("Rosado", "F50057", rosado);
        ThemeColor tcv = new ThemeColor("Verde", "0F9D58", verde);
        return FXCollections.observableArrayList(tci, tct, tcr, tcv);
    }
    
    public static ObservableList<ThemeColor> getTemas(){
        ObservableList<String> claro = FXCollections.observableArrayList( "/styles/Temas/Tema-Claro.css" );
        ObservableList<String> obscuro = FXCollections.observableArrayList(  "/styles/Temas/Tema-Obscuro.css" );
        
        ThemeColor tc = new ThemeColor("Claro", "ccc", claro);
        ThemeColor to = new ThemeColor("Obscuro", "555", obscuro);
        return FXCollections.observableArrayList(tc, to);
    }
    
    public static ObservableList<String> getStringColors(ObservableList<ThemeColor> colores){
        ObservableList<String> temasString = FXCollections.observableArrayList();
        for (ThemeColor tc : colores){
            for (String st : tc.getCssList()){
                if (!temasString.contains(st))
                    temasString.add(st);
            }
        }
        return temasString;
    }
    
    public static ThemeColor getCssporNombre(String nombre, ObservableList<ThemeColor> colores){
        for (ThemeColor tc : colores){
            if (tc.getNombre().equals(nombre))
                return tc;
        }
        return null;
    }
    
    public static void aplicarTema(ThemeColor enfasis, ThemeColor tema, Scene escena){
        Configuracion conf = ConfiguracionManager2.getConfiguracion(new dbHelper());
        
        escena.getRoot().getStylesheets().removeAll(Temas.getStringColors(getEnfasis()));
        escena.getRoot().getStylesheets().removeAll(Temas.getStringColors(getTemas()));
        escena.getRoot().getStylesheets().addAll(enfasis.getCssList());
        escena.getRoot().getStylesheets().addAll(tema.getCssList());
        System.out.println("ENFASIS : #" + enfasis.getCssList());
    }
    
    public static void aplicarTema(Scene escena){
        Configuracion conf = ConfiguracionManager2.getConfiguracion(new dbHelper());
        ThemeColor enfasis = getCssporNombre(conf.getColorEnfasis(), Temas.getEnfasis());
        ThemeColor tema = getCssporNombre(conf.getColorTema(), Temas.getTemas());
        
        escena.getRoot().getStylesheets().removeAll(Temas.getStringColors(getEnfasis()));
        escena.getRoot().getStylesheets().removeAll(Temas.getStringColors(getTemas()));
        escena.getRoot().getStylesheets().addAll(enfasis.getCssList());
        escena.getRoot().getStylesheets().addAll(tema.getCssList());
        System.out.println("ENFASIS : #" + enfasis.getCssList());
    }
}