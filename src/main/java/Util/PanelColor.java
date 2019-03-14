/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;
import sitetech.hotspot.ThemeColor;

/**
 *
 * @author willi
 */
public class PanelColor extends ListCell<ThemeColor>{
      @Override
      public void updateItem(ThemeColor item, boolean empty){
          super.updateItem(item, empty);
          AnchorPane panel = new AnchorPane();
          panel.setPrefSize(150, 35);
          Label lb = new Label();
          lb.setPadding(new Insets(10,10,10,10));
          
          lb.textAlignmentProperty().setValue(TextAlignment.CENTER);
          
          Rectangle rect = new Rectangle(120,18);
          if(item != null){
            lb.setText(item.getNombre());
            lb.setStyle("-fx-text-fill: #fff");
            panel.setStyle("-fx-background-color: #" + item.getColor() + "");
            panel.getChildren().addAll(lb);
              setGraphic(panel);
          }
    }
}