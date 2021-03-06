/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sitetech.Helpers;

import Util.Dialogo;
import java.sql.ResultSet;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.hibernate.query.Query;
import org.hibernate.type.TimestampType;

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
        try {
            if (this.conectarHb()){
                List<Object> queryList = this.session.createQuery(query).list();
                ObservableList<Object> ltemporal= FXCollections.observableArrayList(queryList);
                
                this.Cerrar();
                return ltemporal;
            }
        } catch (Exception ex) {
            Dialogo.mostrar(ex.getMessage(), "Error al seleccionar: ( '" + query + "' )", Alert.AlertType.ERROR, ButtonType.OK);
            System.err.println(ex.getMessage());
            return null;
        }
        
        return null;
    }
    
    public ObservableList<?> Select(String query, HashMap<String, Object> parametros)
    {
        try {
            if (this.conectarHb()){
                Query queryList = this.session.createQuery(query);
                if (parametros != null){
                    for (Map.Entry<String, Object> parametro : parametros.entrySet()) {
                        System.out.println("clave=" + parametro.getKey() + ", valor=" + parametro.getValue());
                        if ((parametro.getValue() instanceof Date)){
                            Date fecha = (Date) parametro.getValue();
                            queryList.setParameter(parametro.getKey(), fecha, TimestampType.INSTANCE );
                        }
                        else
                            queryList.setParameter( parametro.getKey(), parametro.getValue() );
                    }
                }
                
                ObservableList<Object> ltemporal= FXCollections.observableArrayList(queryList.list());
                
                this.Cerrar();
                System.out.println("=======================");
                return ltemporal;
            }
        } catch (Exception ex) {
            Dialogo.mostrar(ex.getMessage(), "Error al seleccionar: ( '" + query + "' )", Alert.AlertType.ERROR, ButtonType.OK);
            System.err.println(ex.getMessage());
            return null;
        }
        
        return null;
    }
}
