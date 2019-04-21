/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sitetech.Helpers;

import de.jensd.fx.glyphs.GlyphsBuilder;
import de.jensd.fx.glyphs.GlyphsStack;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconName;
import javafx.scene.layout.Region;

/**
 *
 * @author megan
 */
public class imagenHelper {
    public static Region getIcono(FontAwesomeIconName icono, String tamaño, String estilo){
        return GlyphsStack.create().add(GlyphsBuilder.create(FontAwesomeIcon.class)
                .icon (icono).size(tamaño).style(estilo).build());
    }
}
