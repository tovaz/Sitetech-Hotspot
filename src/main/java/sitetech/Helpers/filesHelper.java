/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sitetech.Helpers;

import java.io.File;

/**
 *
 * @author megan
 */
public class filesHelper {
    public static boolean Exist(String ruta){
        File ff = new File(ruta);
        return ff.exists();
    }
}
