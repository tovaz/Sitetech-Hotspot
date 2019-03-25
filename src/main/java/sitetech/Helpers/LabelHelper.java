package sitetech.Helpers;
import javafx.geometry.Bounds;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class LabelHelper{
  public static boolean necesitaTooltip(Font font, Label label){
    Text text = new Text(label.getText());
    text.setFont(font);
    Bounds tb = text.getBoundsInLocal();
    Rectangle stencil = new Rectangle(
            tb.getMinX(), tb.getMinY(), tb.getWidth(), tb.getHeight()
    );

    Shape intersection = Shape.intersect(text, stencil);

    Bounds ib = intersection.getBoundsInLocal();
    return ib.getWidth() > label.getPrefWidth();
  }
  
  public static void asignarTexto(Label label, String texto){
      label.setText(texto);
      if (necesitaTooltip(label.getFont(), label)){
          Tooltip tp = new Tooltip(texto);
          
        label.setTooltip(tp);
      }
  }
}
