/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sitetech.hotspot.MainApp;

/**
 *
 * @author megan
 */
public class util {
    public static String getMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String hashtext = number.toString(16);

            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    
    public static ButtonType mostrarAlerta(String mensaje, String titulo, ButtonType... btn)
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, mensaje, btn);
        alert.setHeaderText(titulo);
        alert.showAndWait();
        return alert.getResult();
    }
    
    public static Object mostrarStage(String vista, String titulo, Stage main, Object Controlador, Modality modalidad)
    {
        Stage nuevaStage;
        if (main == null)
            nuevaStage = new Stage();
        else
            nuevaStage = main;
        
        try {
            FXMLLoader loader = new FXMLLoader(Controlador.getClass().getResource(vista));
            loader.setController(Controlador);

            nuevaStage.initModality(modalidad);
            Scene thisScene = new Scene((Parent)loader.load());
            //thisScene.getStylesheets().add("../../resources/styles/Styles.css");
            nuevaStage.setScene(thisScene);
            nuevaStage.setTitle(titulo);
            
            if (modalidad == Modality.NONE)
                nuevaStage.show();
            else
                nuevaStage.showAndWait();
            return Controlador;
            
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    
    public static Object cargarStage(String vista, String titulo, Stage main, Object Controlador, Modality modalidad)
    {
        try {
            FXMLLoader loader = new FXMLLoader(Controlador.getClass().getResource(vista));
            loader.setController(Controlador);

            main.initModality(modalidad);
            Scene thisScene = new Scene((Parent)loader.load());
            //thisScene.getStylesheets().add("../../resources/styles/Styles.css");
            main.setScene(thisScene);
            main.setTitle(titulo);
            return Controlador;
            
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    
    public static Object cargarSceneEnPanel(String vista, String titulo, Object Controlador)
    {
        FXMLLoader loader = new FXMLLoader(Controlador.getClass().getResource(vista));
        loader.setController(Controlador);
        Object control = loader.getRoot();
        return control;
    }
    
    //IP TEXT REGEX
    public static String makePartialIPRegex() {
        String partialBlock = "(([01]?[0-9]{0,2})|(2[0-4][0-9])|(25[0-5]))" ;
        String subsequentPartialBlock = "(\\."+partialBlock+")" ;
        String ipAddress = partialBlock+"?"+subsequentPartialBlock+"{0,3}";
        return "^"+ipAddress ;
    }
}
