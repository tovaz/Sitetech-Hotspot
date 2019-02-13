/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

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
}
