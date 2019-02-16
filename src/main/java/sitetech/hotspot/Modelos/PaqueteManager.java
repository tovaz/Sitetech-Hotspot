/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sitetech.hotspot.Modelos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sitetech.Helpers.dbHelper;
import sitetech.hotspot.Modelos.Paquete;

/**
 *
 * @author willi
 */
public class PaqueteManager {
    private dbHelper DbHelper;
    public ObservableList<Paquete> listaPaquetes = FXCollections.observableArrayList();
    
    public PaqueteManager() {
        DbHelper = new dbHelper();
        listaPaquetes = this.getPaquetes();
    }
    
    public ObservableList<Paquete> getPaquetes(){
        return listaPaquetes = (ObservableList<Paquete>) 
        DbHelper.Select("FROM Paquete WHERE eliminado=false");
    }
    
    public void AgregarPaquete(Paquete pack)
    {
        DbHelper.Agregar(pack);
    }
    
    public void EditarPaquete(Paquete pack)
    {
        DbHelper.Editar(pack);
    }
    
    public void EliminarPaquete(Paquete pack)
    {
        pack.setEliminado(true);
        DbHelper.Editar(pack);
    }
}
