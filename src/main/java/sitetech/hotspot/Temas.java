package sitetech.hotspot;

import java.util.HashMap;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.HBox;
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
        
        ObservableList<String> indigo = FXCollections.observableArrayList( "/styles/validation.css", "/styles/botones.css", "/styles/Temas/Tema-Indigo.css", "/styles/Styles.css"  );
        ObservableList<String> turquesa = FXCollections.observableArrayList(  "/styles/validation.css", "/styles/botones.css", "/styles/Temas/Tema-Turquesa.css", "/styles/Styles.css"  );
        ObservableList<String> rosado = FXCollections.observableArrayList(  "/styles/validation.css", "/styles/botones.css", "/styles/Temas/Tema-Rosado.css", "/styles/Styles.css" );
        ObservableList<String> azul = FXCollections.observableArrayList( "/styles/validation.css", "/styles/botones.css", "/styles/Temas/Tema-Azul.css", "/styles/Styles.css" );
        ObservableList<String> celeste = FXCollections.observableArrayList( "/styles/validation.css", "/styles/botones.css", "/styles/Temas/Tema-Celeste.css", "/styles/Styles.css" );
        ObservableList<String> lima = FXCollections.observableArrayList( "/styles/validation.css", "/styles/botones.css", "/styles/Temas/Tema-Lima.css", "/styles/Styles.css" );
        ObservableList<String> light = FXCollections.observableArrayList( "/styles/validation.css", "/styles/botones.css", "/styles/Temas/Tema-Light.css", "/styles/Styles.css" );
        ObservableList<String> dark = FXCollections.observableArrayList( "/styles/validation.css", "/styles/botones.css", "/styles/Temas/Tema-Dark.css", "/styles/Styles.css" );
        ObservableList<String> naranja = FXCollections.observableArrayList( "/styles/validation.css", "/styles/botones.css", "/styles/Temas/Tema-Naranja.css", "/styles/Styles.css" );
        ObservableList<String> verde = FXCollections.observableArrayList( "/styles/validation.css", "/styles/botones.css", "/styles/Temas/Tema-Verde.css", "/styles/Styles.css" );
        
        
        ThemeColor tc1 = new ThemeColor("Indigo", "304FFE", indigo);
        ThemeColor tc2 = new ThemeColor("Azul", "2962FF", azul);
        ThemeColor tc3 = new ThemeColor("Celeste", "0091EA", celeste);
        ThemeColor tc4 = new ThemeColor("Turquesa", "00BFA5", turquesa);
        ThemeColor tc5 = new ThemeColor("Lima", "64DD17", lima);
        ThemeColor tc6 = new ThemeColor("Verde", "00b25b", verde);
        ThemeColor tc7 = new ThemeColor("Rosado", "C51162", rosado);
        ThemeColor tc8 = new ThemeColor("Naranja", "FF3D00", naranja);
        ThemeColor tc9 = new ThemeColor("Light", "999", light);
        ThemeColor tc10 = new ThemeColor("Dark", "333", dark);
        
        return FXCollections.observableArrayList(tc1,tc2,tc3,tc4,tc5,tc6,tc7,tc8,tc9,tc10);
    }
    
    public static Map<String, ThemeColor> getTemasMap(){
        Map<String, ThemeColor> TemasMap =   new HashMap<String, ThemeColor>();
        for (ThemeColor tx : getTemas())
            TemasMap.put(tx.getNombre(), tx);
        return TemasMap;
    }
    
    public static ObservableList<ThemeColor> getTemas(){
        ObservableList<String> claro = FXCollections.observableArrayList( "/styles/Temas/Tema-Claro.css" );
        ObservableList<String> obscuro = FXCollections.observableArrayList(  "/styles/Temas/Tema-Obscuro.css" );
        
        ThemeColor tc = new ThemeColor("Claro", "999", claro);
        ThemeColor to = new ThemeColor("Obscuro", "333", obscuro);
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
    
   
    public static void aplicarTema(Scene escena, Configuracion config){
        ThemeColor enfasis = getCssporNombre(config.getColorEnfasis(), Temas.getEnfasis());
        ThemeColor tema = getCssporNombre(config.getColorTema(), Temas.getTemas());
        
        aplicarTema(enfasis, tema, escena);
        colorearBarras(escena, config.isColorMenu(), config.isColorToolbar());
        
        System.out.println("ENFASIS : #" + enfasis.getCssList());
    }
    
    public static ObservableList<String> getTemaStyleSheet(){
        Configuracion conf = ConfiguracionManager2.getConfiguracion(new dbHelper());
        ThemeColor enfasis = getCssporNombre(conf.getColorEnfasis(), Temas.getEnfasis());
        ThemeColor tema = getCssporNombre(conf.getColorTema(), Temas.getTemas());
        
        ObservableList<String> lista = enfasis.getCssList();
        lista.addAll(tema.getCssList());
        
        return lista;
    }
    
    
    public static void colorearBarras(Scene escena, boolean colorMenu, boolean colorToolbar)
    {
        ToolBar toolbar = (ToolBar) escena.lookup("#ticketToolbar");
        HBox panelMenu = (HBox) escena.lookup("#panelMenu");
        
        if (panelMenu != null) {
            MenuBar menu = (MenuBar) panelMenu.lookup("#menuBar");
            ObservableList<Menu> mi = menu.getMenus();
            if (colorMenu){
                for (Menu mx : mi){
                    panelMenu.getStyleClass().remove("menuSinEnfasis");
                    panelMenu.getStyleClass().add("menuEnfasis");
                }
                panelMenu.setStyle("-fx-background-color: -fx-accent;");
            }
            else{
                for (Menu mx : mi){
                    panelMenu.getStyleClass().remove("menuEnfasis");
                    panelMenu.getStyleClass().add("menuSinEnfasis");
                }
                panelMenu.setStyle("-fx-background-color: -colorFondo2;");
            }
        }
        
        if (toolbar != null) { 
            Label lticket = (Label) toolbar.lookup("#lTickets");
            System.err.println("----f Ticket: " + lticket);
            if (colorToolbar){
                toolbar.setStyle("-fx-background-color: -fx-accent;");
                if (lticket != null) lticket.setStyle("-fx-text-fill: #eee22");
            }else{
                toolbar.setStyle("-fx-background-color: -colorFondo2;");
                if (lticket != null) lticket.setStyle("-fx-text-fill: #00022");
            }
        }
    }
}