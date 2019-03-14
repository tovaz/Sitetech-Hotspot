/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sitetech.hotspot.Modelos;

import javafx.collections.ObservableList;
import sitetech.Helpers.dbHelper;

/**
 *
 * @author Willi
 */
public class ConfiguracionManager2 {
    private dbHelper DbHelper;
    
    public ConfiguracionManager2() {
        DbHelper = new dbHelper();
    }
    
    public Configuracion getConfiguracion(){
        ObservableList<Configuracion> lista =   (ObservableList<Configuracion>)DbHelper.Select("FROM Configuracion");
        if (lista.size() == 0)
            return new Configuracion(true);
        else
            return (Configuracion)lista.get(0);
    }
    
    public static Configuracion getConfiguracion(dbHelper db){
        ObservableList<Configuracion> lista =   (ObservableList<Configuracion>)db.Select("FROM Configuracion");
        if (lista.size() == 0)
            return new Configuracion(true);
        else
            return (Configuracion)lista.get(0);
    }
    
    public void Agregar(Configuracion conf)
    {
        DbHelper.Agregar(conf);
    }
    
    public void Editar(Configuracion conf)
    {
        DbHelper.Editar(conf);
    }
}
