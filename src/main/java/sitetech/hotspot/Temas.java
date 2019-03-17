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
        //String[] css = new String[]{"/styles/botones.css", "/styles/Styles.css", "/styles/validation.css"};
        
        ObservableList<String> indigo = FXCollections.observableArrayList( "/styles/Styles.css", "/styles/validation.css", "/styles/botones.css", "/styles/Temas/Tema-Indigo.css"  );
        ObservableList<String> turquesa = FXCollections.observableArrayList(  "/styles/Styles.css", "/styles/validation.css", "/styles/botones.css", "/styles/Temas/Tema-Turquesa.css"  );
        ObservableList<String> rosado = FXCollections.observableArrayList(  "/styles/Styles.css", "/styles/validation.css", "/styles/botones.css", "/styles/Temas/Tema-Rosado.css"  );
        ObservableList<String> azul = FXCollections.observableArrayList( "/styles/Styles.css", "/styles/validation.css", "/styles/botones.css", "/styles/Temas/Tema-Azul.css" );
        ObservableList<String> celeste = FXCollections.observableArrayList( "/styles/Styles.css", "/styles/validation.css", "/styles/botones.css", "/styles/Temas/Tema-Celeste.css" );
        ObservableList<String> lima = FXCollections.observableArrayList( "/styles/Styles.css", "/styles/validation.css", "/styles/botones.css", "/styles/Temas/Tema-Lima.css" );
        ObservableList<String> light = FXCollections.observableArrayList( "/styles/Styles.css", "/styles/validation.css", "/styles/botones.css", "/styles/Temas/Tema-Light.css" );
        ObservableList<String> dark = FXCollections.observableArrayList( "/styles/Styles.css", "/styles/validation.css", "/styles/botones.css", "/styles/Temas/Tema-Dark.css" );
        ObservableList<String> naranja = FXCollections.observableArrayList( "/styles/Styles.css", "/styles/validation.css", "/styles/botones.css", "/styles/Temas/Tema-Naranja.css" );
        ObservableList<String> verde = FXCollections.observableArrayList( "/styles/Styles.css", "/styles/validation.css", "/styles/botones.css", "/styles/Temas/Tema-Verde.css" );
        
        
        ThemeColor tc1 = new ThemeColor("Indigo", "304FFE", indigo);
        ThemeColor tc2 = new ThemeColor("Azul", "2962FF", azul);
        ThemeColor tc3 = new ThemeColor("Celeste", "0091EA", celeste);
        ThemeColor tc4 = new ThemeColor("Turquesa", "00BFA5", turquesa);
        ThemeColor tc5 = new ThemeColor("Lima", "64DD17", lima);
        ThemeColor tc6 = new ThemeColor("Verde", "00b25b", verde);
        ThemeColor tc7 = new ThemeColor("Rosado", "C51162", rosado);
        ThemeColor tc8 = new ThemeColor("Naranja", "FF3D00", naranja);
        ThemeColor tc9 = new ThemeColor("Light", "bbb", light);
        ThemeColor tc10 = new ThemeColor("Dark", "555", dark);
        
        return FXCollections.observableArrayList(tc1,tc2,tc3,tc4,tc5,tc6,tc7,tc8,tc9,tc10);
    }
    
    public static ObservableList<ThemeColor> getTemas(){
        ObservableList<String> claro = FXCollections.observableArrayList( "/styles/Temas/Tema-Claro.css" );
        ObservableList<String> obscuro = FXCollections.observableArrayList(  "/styles/Temas/Tema-Obscuro.css" );
        
        ThemeColor tc = new ThemeColor("Claro", "bbb", claro);
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
        escena.getRoot().getStylesheets().removeAll(Temas.getStringColors(getEnfasis()));
        escena.getRoot().getStylesheets().removeAll(Temas.getStringColors(getTemas()));
        
        
        escena.getRoot().getStylesheets().addAll(tema.getCssList());
        escena.getRoot().getStylesheets().addAll(enfasis.getCssList());
        
        
        System.out.println("ENFASIS : #" + enfasis.getCssList());
    }
    
    public static void aplicarTema(Scene escena){
        Configuracion conf = ConfiguracionManager2.getConfiguracion(new dbHelper());
        ThemeColor enfasis = getCssporNombre(conf.getColorEnfasis(), Temas.getEnfasis());
        ThemeColor tema = getCssporNombre(conf.getColorTema(), Temas.getTemas());
        
        
        escena.getRoot().getStylesheets().removeAll(Temas.getStringColors(getTemas()));
        escena.getRoot().getStylesheets().removeAll(Temas.getStringColors(getEnfasis()));
        
        escena.getRoot().getStylesheets().addAll(tema.getCssList());
        escena.getRoot().getStylesheets().addAll(enfasis.getCssList());
        
        
        System.out.println("ENFASIS : #" + enfasis.getCssList());
    }
}