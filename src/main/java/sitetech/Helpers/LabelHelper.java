/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sitetech.Helpers;

import com.sun.javafx.scene.control.skin.LabeledText;
import com.sun.javafx.scene.text.TextLayout;
import com.sun.javafx.tk.Toolkit;
import java.awt.FontMetrics;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;

//@SuppressWarnings("restriction")
public class LabelHelper{
  public static boolean checkWith(Label label, String texto){
      FontMetrics fm;
      int width = fm.stringWidth(texto);
      return width > label.getWidth();
  }
  
  public static void asignarTexto(Label label, String texto){
      
      label.setText(texto);
      
      LabeledText tx = (LabeledText ) label.getClip();
      System.err.println("TEXTO: " + tx);
      if ()
    //if (label.isNeedsLayout())
        label.setTooltip(new Tooltip(texto));
  }
}
