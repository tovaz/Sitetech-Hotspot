/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

/**
 *
 * @author willi
 */
public class Archivo {
    public static boolean Copiar(File archivo) throws IOException{
        String destino = System.getProperty("user.dir") + "\\img\\";
        Path destinoPath = FileSystems.getDefault().getPath(destino + archivo.getName());
        Path origen = FileSystems.getDefault().getPath(archivo.getAbsolutePath());
        
        File directorio = new File(destino);
        if (!directorio.exists())
            directorio.mkdir();
        
        try {
            Files.copy(origen, destinoPath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Archivo copiado a: " + destinoPath.toString());
            return true; 
        } catch (IOException e) {
            System.err.println(e);
        }
        return false;
    }
}
