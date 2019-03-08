/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sitetech.hotspot.Modelos;

import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import sitetech.Helpers.dbHelper;

/**
 *
 * @author Willi
 */
public class ConfiguracionManager {
    private dbHelper DbHelper;
    
    public ConfiguracionManager() {
        DbHelper = new dbHelper();
    }
    
    public Configuracion getConfiguracion(){
        ObservableList<Configuracion> lista = (ObservableList<Configuracion>)DbHelper.Select("FROM Configuracion");
        if (lista.size()>0)
            return lista.get(0);
        else
            return null;
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
