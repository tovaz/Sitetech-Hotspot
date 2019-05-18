/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sitetech.hotspot.Modelos;

import com.google.gson.Gson;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
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
        ObservableList<Caja> lista =  (ObservableList<Caja>)DbHelper.Select("FROM Caja");
        if (lista.isEmpty())
            return null;
        else
            return lista;
    }
    
    public ObservableList<Caja> getPorFecha(Date FechaInicio, Date FechaFin){
        HashMap<String, Object> parametros = new HashMap<String, Object>();
        parametros.put("fechaI", FechaInicio);
        parametros.put("fechaF", FechaFin);
        Object l = DbHelper.Select("FROM Caja WHERE fechaCierre BETWEEN :fechaI AND :fechaF ORDER BY fechaCierre", parametros);
        ObservableList<Caja> lista = (ObservableList<Caja>)l;
        
        //System.out.println("LISTA DE CAJAS *****");
        //Gson gson = new Gson();
        //System.out.println(gson.toJson(lista));
        
        if (lista.isEmpty())
            return null;
        else
            return lista;
    }
    
    public ObservableList<Caja> getDetallesdeCaja(int NumeroCaja){
        HashMap<String, Object> parametros = new HashMap<String, Object>();
        parametros.put("cajax", NumeroCaja);
        Object l = DbHelper.Select("FROM detalleCaja WHERE caja.id = :cajax ORDER BY tipo asc, fechaCreacion asc", parametros);
        ObservableList<Caja> lista = (ObservableList<Caja>)l;
        
        //System.out.println("LISTA DE CAJAS *****");
        //Gson gson = new Gson();
        //System.out.println(gson.toJson(lista));
        
        if (lista.isEmpty())
            return null;
        else
            return lista;
    }
    
    public Caja getCajaAbierta(Usuario usuarioLogeado){
        ObservableList<Caja> lista =(ObservableList<Caja>)DbHelper.Select("FROM Caja WHERE UsuarioCierre = null");
        if (lista.isEmpty()){
            Caja nuevaCaja = new Caja(usuarioLogeado, BigDecimal.ZERO, BigDecimal.ZERO);
            crearCaja(nuevaCaja);
            return nuevaCaja;
        }
        else
            return lista.get(0);
    }
    
    public Caja nuevaCajaconSaldo(Usuario usuarioLogeado, BigDecimal saldo){
        Caja nuevaCaja = new Caja(usuarioLogeado, BigDecimal.ZERO, BigDecimal.ZERO);
        if (saldo != BigDecimal.ZERO){ 
            nuevaCaja.setCajaInicial(saldo);
            nuevaCaja.setTotal(saldo);
        }
        
        crearCaja(nuevaCaja);
        return nuevaCaja;
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
        
        if (listaDetalles != null){
            for (int i=0; i<listaDetalles.size(); i++){
                if (listaDetalles.get(i).getTicket() != null)
                    listaRetorno.add(listaDetalles.get(i));
            }
        }
        
        return listaRetorno;
    }
    
    public Caja agregarDetalle(detalleCaja detalle){
        Caja caja = detalle.getCaja();
        
        if ( DbHelper.Agregar(detalle) ){
            switch (detalle.getTipo().name()){
                case "Venta_Ticket":
                    caja.setTotalIngreso(caja.getTotalIngreso().add(detalle.getMonto()) );
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
