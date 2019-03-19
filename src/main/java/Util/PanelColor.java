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
          panel.setPrefSize(160, 30);
          panel.setMaxSize(USE_PREF_SIZE, USE_PREF_SIZE);
          //panel.setPadding(new Insets(5,0,0,5));
          
          Label lb = new Label();
          lb.setPadding(new Insets(5,0,0,5));
          lb.textAlignmentProperty().setValue(TextAlignment.CENTER);
          
          if(item != null){
            lb.setText(item.getNombre());
            lb.setStyle("-fx-text-fill: #eee");
            panel.setStyle("-fx-background-color: #" + item.getColor() + "; " + 
                          "-fx-border-color: derive(#" + item.getColor() + ", 50%);");
            
            panel.getChildren().addAll(lb);
              setGraphic(panel);
          }
    }
}