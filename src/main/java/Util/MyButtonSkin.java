/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import com.sun.javafx.scene.control.skin.ButtonSkin;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.FillTransition;
import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class MyButtonSkin extends ButtonSkin {

    public MyButtonSkin(Button control) {
        super(control);
        
        final FadeTransition fadeIn = new FadeTransition(Duration.millis(50));
        fadeIn.setNode(control);
        fadeIn.setToValue(1.0);
        control.setOnMouseEntered(e -> fadeIn.playFromStart());

        final FadeTransition fadeOut = new FadeTransition(Duration.millis(300));
        fadeOut.setNode(control);
        fadeOut.setToValue(0.8);
        control.setOnMouseExited(e -> fadeOut.playFromStart());

        control.setOpacity(0.8);
    }

}