
package sitetech.Helpers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    public String url = "src\\hotspot.db";
    public Connection conexion;
    
    private static SessionFactory sessionFactory = null;  
       
    private static SessionFactory configureSessionFactory() throws HibernateException {  
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
    
    public void conectar(){
        try {
            conexion = DriverManager.getConnection("jdbc:sqlite:" + url);
            if (conexion!=null) {
                System.out.println("Conectado a la base de datos.");
            }
        }catch (SQLException ex) {
            System.err.println("No se ha podido conectar a la base de datos\n"+ex.getMessage());
        }
    }
    public void cerrar(){
           try {
               conexion.close();
           } catch (SQLException ex) {
               Logger.getLogger(dbManager.class.getName()).log(Level.SEVERE, null, ex);
           }
    }
    
    public void createDb(){
        ResultSet result = null;
        try {
            this.conectar();
            PreparedStatement st = conexion.prepareStatement("select * from alumnos");
            result = st.executeQuery();
            while (result.next()) {
                System.out.print("ID: ");
                System.out.println(result.getInt("id"));
            }
            
            this.cerrar();
        }catch (SQLException ex) {
            Logger.getLogger(dbManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
}
