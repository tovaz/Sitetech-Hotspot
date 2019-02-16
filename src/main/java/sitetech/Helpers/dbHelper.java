/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sitetech.Helpers;

import java.sql.ResultSet;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sitetech.hotspot.Modelos.Router;

/**
 *
 * @author willi
 */
public class dbHelper extends dbManager{
    //******************** QUERYS BASICAS *********************
    public boolean Agregar(Object clase){
        try {
            if (this.conectarHb()) {
                this.session.save(clase);
                this.Flush();
                this.Cerrar();
                
                System.out.println(clase.getClass().getName() + " agregado exitosamente.");
                return true;
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        return false;
    }
    
    public boolean Editar(Object clase){
        try {
            if (this.conectarHb()) {
                this.session.update(clase);
                this.Flush();
                this.Cerrar();
                
                System.out.println(clase.getClass().getName() + " editado exitosamente.");
                return true;
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        return false;
    }
    
    public ObservableList<?> Select(String query)
    {
        ResultSet result = null;
        try {
            if (this.conectarHb()){
                List<Object> queryList = this.session.createQuery(query).list();
                ObservableList<Object> ltemporal= FXCollections.observableArrayList(queryList);
                
                this.Cerrar();
                System.out.println("=======================");
                return ltemporal;
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        
        return null;
    }
}
