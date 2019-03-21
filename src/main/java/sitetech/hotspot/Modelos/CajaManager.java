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
 * @author megan
 */
public class CajaManager {
    private dbHelper DbHelper;
    public CajaManager() {
        DbHelper = new dbHelper();
    }
    
    public ObservableList<Caja> getCajas(){
        ObservableList<Caja> lista =   (ObservableList<Caja>)DbHelper.Select("FROM Caja");
        if (lista.size() == 0)
            return null;
        else
            return lista;
    }
    
    public static Caja getCajaAbierta(dbHelper db){
        ObservableList<Caja> lista =   (ObservableList<Caja>)db.Select("FROM Caja WHERE UsuarioCierre = null");
        if (lista.size() == 0)
            return null;
        else
            return lista.get(0);
    }
    
    public void crearCaja(Caja caja)
    {
        DbHelper.Agregar(caja);
    }
    
    public void Editar(Caja caja)
    {
        DbHelper.Editar(caja);
    }
    
    
    //******************************* DETALLE CAJA *****************************/
    public ObservableList<Caja> getDetalleCaja(Caja caja){
        ObservableList<Caja> lista =   (ObservableList<Caja>)DbHelper.Select("FROM Detalle_Caja WHERE idCaja = " + caja.getId());
        if (lista.size() == 0)
            return null;
        else
            return lista;
    }
    
    public void agregarDetalle(detalleCaja detalle){
        DbHelper.Agregar(detalle);
    }
    
    public void Editar(detalleCaja detalle)
    {
        DbHelper.Editar(detalle);
    }
}
