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
import sitetech.Helpers.dbHelper;
import sitetech.Helpers.dbManager;

/**
 *
 * @author willi
 */
public class RouterManager {
    private dbHelper DbHelper;
    public ObservableList<Router> listaRouters = FXCollections.observableArrayList();

    public RouterManager() {
        DbHelper = new dbHelper();
        listaRouters = this.getRouters();
    }
    
    public void AgregarRouter(Router rt)
    {
        DbHelper.Agregar(rt);
    }
    
    public void EditarRouter(Router rt)
    {
        DbHelper.Editar(rt);
    }
    
    public void EliminarRouter(Router rt)
    {
        rt.setEliminado(true);
        DbHelper.Editar(rt);
    }
    
    
    public ObservableList<Router> getRouters(){
        return listaRouters = (ObservableList<Router>) 
        DbHelper.Select("FROM Router WHERE eliminado=false");
    }
    
}
