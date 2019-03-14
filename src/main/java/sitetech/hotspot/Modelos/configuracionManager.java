/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sitetech.hotspot.Modelos;

import sitetech.Helpers.dbHelper;

/**
 *
 * @author Willi
 */
public class configuracionManager {
    private dbHelper DbHelper;
    
    public configuracionManager() {
        DbHelper = new dbHelper();
    }
    
    public Configuracion getConfiguracion(){
        return (Configuracion) DbHelper.Select("FROM Configuracion WHERE id=1");
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
