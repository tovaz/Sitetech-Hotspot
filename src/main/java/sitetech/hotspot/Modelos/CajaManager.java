/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sitetech.hotspot.Modelos;

import java.math.BigDecimal;
import javafx.collections.FXCollections;
import static javafx.collections.FXCollections.observableArrayList;
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
        if (lista.isEmpty())
            return null;
        else
            return lista;
    }
    
    public Caja getCajaAbierta(Usuario usuarioLogeado){
        ObservableList<Caja> lista =(ObservableList<Caja>)DbHelper.Select("FROM Caja WHERE UsuarioCierre = null");
        if (lista.isEmpty()){
            Caja nuevaCaja = new Caja(usuarioLogeado, new BigDecimal(0), new BigDecimal(0));
            crearCaja(nuevaCaja);
            return nuevaCaja;
        }
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
    public ObservableList<detalleCaja> getDetalleCaja(Caja caja){
        ObservableList<detalleCaja> lista =   (ObservableList<detalleCaja>)DbHelper.Select("FROM detalleCaja WHERE idCaja = " + caja.getId());
        if (lista.isEmpty())
            return null;
        else
            return lista;
    }
    
    public ObservableList<detalleCaja> getTicketsdeCaja(Caja caja){
        ObservableList<detalleCaja> listaDetalles = getDetalleCaja(caja);
        ObservableList<detalleCaja> listaRetorno = observableArrayList();
        
        for (int i=0; i<listaDetalles.size(); i++){
            if (listaDetalles.get(i).getTicket() != null)
                listaRetorno.add(listaDetalles.get(i));
        }
        
        return listaRetorno;
    }
    
    public Caja agregarDetalle(detalleCaja detalle){
        Caja caja = detalle.getCaja();
        
        if ( DbHelper.Agregar(detalle) ){
            switch (detalle.getTipo().name()){
                case "Venta_Ticket":
                    caja.setTotal( caja.getTotal().add(detalle.getMonto()) );
                    break;
                
                case "Ingreso":
                    caja.setTotalIngreso(caja.getTotalIngreso().add(detalle.getMonto()) );
                    caja.setTotal( caja.getTotal().add(detalle.getMonto()) );
                    break;
                    
                case "Egreso":
                    caja.setTotalEgreso(caja.getTotalEgreso().add(detalle.getMonto()) );
                    caja.setTotal( caja.getTotal().subtract(detalle.getMonto()) );
                    break;
                    
                default: break;
            }
            DbHelper.Editar(caja);
            return caja;
        }
        
        return null;
    }
    
    public void Editar(detalleCaja detalle)
    {
        DbHelper.Editar(detalle);
    }
}
