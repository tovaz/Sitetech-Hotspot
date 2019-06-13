/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sitetech.Helpers;

import Util.Dialogo;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javafx.scene.control.ButtonType;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author megan
 */
public class backupHelper {
    public static boolean exportar(String ruta){
        java.text.SimpleDateFormat todaysDate =  new java.text.SimpleDateFormat("yyyy-MM-dd");
        ruta += todaysDate.format((java.util.Calendar.getInstance()).getTime());
        
        try {
            Connection conn = dbManager.getConnection();
            CallableStatement cs = conn.prepareCall("CALL SYSCS_UTIL.SYSCS_BACKUP_DATABASE(?)"); 
            cs.setString(1, ruta);
            cs.execute(); 
            cs.close();
            System.out.println("Backup realizado en: "+ ruta);
            return true;
        }
        catch (Exception ex){
            Dialogo.mostrarError(ex.getMessage(), "Error al realizar el backup.", ButtonType.OK);
        }
        
        return false;
    }
    
    public static boolean restoreDb(String ruta){
        String userDir = System.getProperty("user.dir") + "\\hotspot";
        try {
            DriverManager.getConnection("jdbc:derby:" + userDir + ";shutdown=true");
            System.out.println("PASO del try");
        }
        catch (Exception ex) {
            System.out.println("ERROR TRY: " + ex.getMessage());
        }
        
        try {
            Configuration configuration = new Configuration().configure();
            String value = configuration.getProperty("hibernate.connection.url");
            configuration.setProperty("hibernate.connection.url", value.replace(";create=true", "") + ";restoreFrom=" + ruta);
            SessionFactory sessionFactory = configuration.buildSessionFactory();
            return true;
        }
        catch (Exception ex) { 
            System.err.println(ex.getMessage());
            //Dialogo.mostrarError(ex.getMessage(), "Error al intentar restablecer desde el backup.", ButtonType.OK); 
        }
        return false;
    }

}
