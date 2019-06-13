
package sitetech.Helpers;

import Util.Dialogo;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.ButtonType;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
/**
 *
 * @author megan
 */
public class dbManager {
    private static SessionFactory sessionFactory = null;  
    public static SessionFactory configureSessionFactory() throws HibernateException {  
        Configuration configuration = new Configuration();  
        configuration.configure();  
         
        Properties properties = configuration.getProperties();
                 
        sessionFactory = configuration.buildSessionFactory();  
         
        return sessionFactory;  
    }
    
    public Session session;
    Transaction tx;
    public boolean conectarHb(){
        configureSessionFactory();
        session = null;
        tx=null;
        
        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();
            System.out.println("Conectado a la base de datos.");
            return true;
        } catch (Exception ex) {
            System.err.println("No se ha podido conectar a la base de datos\n"+ex.getMessage());
            tx.rollback();
        }
        return false;
    }
    
    public boolean Flush()
    {
        try {
            session.flush();
            tx.commit();
            return true;
        } catch (Exception e) { System.out.println("ERROR AL GUARDAR: " + e.getMessage()); }
        return false;
    }
    
    public boolean Cerrar()
    {
        try {
            if(session != null)
                session.close();
            return true;
        } catch (Exception e) { System.out.println("ERROR AL CERRAR: " + e.getMessage()); }
        return false;
    }
    
    public static Connection getConnection(){
        try{
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            String userDir = System.getProperty("user.dir") + "\\hotspot\\";
            return (Connection)DriverManager.getConnection("jdbc:derby:" + userDir);
        }
        catch (Exception ex) {
            Dialogo.mostrarError(ex.getMessage(), "Error al conectar a la base de datos.", ButtonType.OK);
        }
        
        return null;
    }
    
    public static Connection restoreDb(String ruta){
        try{
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            String userDir = System.getProperty("user.dir") + "\\hotspot";
            Connection cn = DriverManager.getConnection("jdbc:derby:" + userDir + ";createFrom=" + ruta + ";logDevice=" + ruta + "\\log");
            return cn;
        }
        catch (Exception ex) {
            Dialogo.mostrarError(ex.getMessage(), "Error al restaurar.", ButtonType.OK);
        }
        
        return null;
    }
    
}
