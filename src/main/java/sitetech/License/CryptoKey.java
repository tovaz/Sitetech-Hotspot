package sitetech.License;

import Util.Dialogo;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javafx.scene.control.ButtonType;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.SealedObject;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
/**
 *
 * @author megan
 */
public class CryptoKey {
    public static String AppId = "$#xdP58-txZVo10=";
    
    public static String getUID(){ // GET UUID PARA LINUX Y WINDOWS
        String OS = System.getProperty("os.name").toLowerCase();
        String machineId = null;
        if (OS.indexOf("win") >= 0) {
            StringBuffer output = new StringBuffer();
            Process process;
            String[] cmd = {"reg", "query", "HKEY_LOCAL_MACHINE\\SOFTWARE\\Microsoft\\Cryptography", "/v", "MachineGuid"};  
            try {
                process = Runtime.getRuntime().exec(cmd);
                process.waitFor();
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line = "";
                while ((line = reader.readLine()) != null) {
                    output.append(line);
                }
                
                return getUUID(output.toString());
            } catch (Exception e) {
                Dialogo.mostrarError(e.getMessage(), "Error al intentar obtener el identificador de la aplicacion", ButtonType.OK);
            }
        } else if (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0) {
            StringBuffer output = new StringBuffer();
            Process process;
            String[] cmd = {"/bin/sh", "-c", "echo <password for superuser> | sudo -S cat /sys/class/dmi/id/product_uuid"};
            try {
                process = Runtime.getRuntime().exec(cmd);
                process.waitFor();
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line = "";
                while ((line = reader.readLine()) != null) {
                    output.append(line);
                }
                
                System.out.println(output.toString());
                return getUUID(output.toString());
            } catch (Exception e) {
                Util.Dialogo.mostrarError(e.getMessage(), "Error al intentar obtener el identificador de la aplicacion", ButtonType.OK);
            }
        }
        return null;
    }
    
    public static String getUUID(String cadena){ // GET UUID
        cadena = cadena.replace("HKEY_LOCAL_MACHINE\\SOFTWARE\\Microsoft\\Cryptography", "").replace("MachineGuid", "").replace("REG_SZ", "").replace(" ", "");
        return cadena.toUpperCase();
    }
    
    
    //************************ ENCRIPTAR Y DESENCRIPTAR LICENCIA **************************//
    public static void guardarLicencia(Licencia licencia, String fileName){
        SecretKey key64 = new SecretKeySpec( AppId.getBytes(), "Blowfish" );
        
        try {
            Cipher cipher = Cipher.getInstance( "Blowfish" );
            cipher.init( Cipher.ENCRYPT_MODE, key64 );
            SealedObject sealedObject = new SealedObject(licencia, cipher);
            CipherOutputStream cipherOutputStream = new CipherOutputStream( new BufferedOutputStream( new FileOutputStream( fileName ) ), cipher );
            ObjectOutputStream outputStream = new ObjectOutputStream( cipherOutputStream );
            outputStream.writeObject( sealedObject );
            outputStream.close();
        }catch (Exception ex){
            Dialogo.mostrarError(ex.getMessage(), "Error al intentar guardar la licencia.", ButtonType.OK);
        }
        
    }
    
    public static Licencia leerLicencia(String file){
        SecretKey key64 = new SecretKeySpec( AppId.getBytes(), "Blowfish" );
        
        try {
            Cipher cipher = Cipher.getInstance( "Blowfish" );
            cipher.init( Cipher.DECRYPT_MODE, key64 );
            CipherInputStream cipherInputStream = new CipherInputStream( new BufferedInputStream( new FileInputStream( file ) ), cipher );
            ObjectInputStream inputStream = new ObjectInputStream( cipherInputStream );
            SealedObject sealedObject = (SealedObject) inputStream.readObject();
            
            System.err.println("Licencia obtenida");
            return (Licencia) sealedObject.getObject( cipher );
        }
        catch (Exception ex){
            System.err.println("ERROR al obtener la licencia");
            System.err.println(ex.getMessage());
            //Dialogo.mostrarError(ex.getMessage(), "Error al intentar leer la licencia.", ButtonType.OK);
        }
        return null;
    }
    
    public static String getMD5(String input, String appCode) {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(appCode.getBytes());
            byte[] bytes = md.digest(input.getBytes());
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            
            generatedPassword = sb.toString().toUpperCase();
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }
}
