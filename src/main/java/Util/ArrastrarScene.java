/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import java.util.concurrent.atomic.AtomicReference;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author willi
 */
public interface  ArrastrarScene {
    default void ArrastrarScene(AnchorPane panelFather) {
        AtomicReference<Double> xOffset = new AtomicReference<>((double) 0);
        AtomicReference<Double> yOffset = new AtomicReference<>((double) 0);

        panelFather.setOnMousePressed(e -> {
            Stage stage = (Stage) panelFather.getScene().getWindow();
            xOffset.set(stage.getX() - e.getScreenX());
            yOffset.set(stage.getY() - e.getScreenY());

        });

        panelFather.setOnMouseDragged(e -> {
            Stage stage = (Stage) panelFather.getScene().getWindow();
            stage.setX(e.getScreenX() + xOffset.get());
            stage.setY(e.getScreenY() + yOffset.get());
            panelFather.setStyle("-fx-cursor: CLOSED_HAND;");
        });

        panelFather.setOnMouseReleased(e-> panelFather.setStyle("-fx-cursor: DEFAULT;"));


    }
}
