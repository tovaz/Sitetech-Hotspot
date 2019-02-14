/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sitetech.hotspot.Modelos;

import java.sql.ResultSet;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sitetech.hotspot.dbManager;

/**
 *
 * @author willi
 */
public class RouterManager {
    private dbManager db;
    public ObservableList<Router> listaRouters = FXCollections.observableArrayList();

    public RouterManager() {
        db = new dbManager();
        listaRouters = this.getRouters();
    }
    
    public void crearRouter(Router rt){
        try {
            if (db.conectarHb()) {
                db.session.save(rt);
                db.Flush();
                db.Cerrar();
                
                System.out.println("Router agregado exitosamente.");
                this.getRouters();
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    public void editarRouter(Router rt){
        try {
            if (db.conectarHb()) {
                db.session.update(rt);
                db.Flush();
                db.Cerrar();
                
                System.out.println("Router editado exitosamente.");
                this.getRouters();
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    public void eliminarRouter(Router rx){
        rx.setEliminado(true);
        try {
            if (db.conectarHb()) {
                db.session.update(rx);
                db.Flush();
                db.Cerrar();
                
                System.out.println("Router eliminado exitosamente.");
                this.getRouters();
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    
    //int _id, String _nombre, String _password, String _privilegios, int _eliminado, int _deshabilitado
    public ObservableList<Router> getRouters()
    {
        ResultSet result = null;
        ObservableList<Router> ltemporal= FXCollections.observableArrayList();;
        try {
            if (db.conectarHb()){
                List<Router> lrouters = db.session.createQuery("FROM Router WHERE eliminado=false").list();
                System.out.println(" ** Obteniendo ROUTERS ** ");

                for (Router rt : lrouters) {
                    System.out.println("Id: " + rt.getId() + " | Name:"  + rt.getNombre() + " | Email:" + rt.isApiActiva());
                    ltemporal.add(rt);
                }
            }
            db.Cerrar();
            System.out.println("=======================");
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        
        return listaRouters = ltemporal;
    }
    
}
