/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sitetech.hotspot.Modelos;

import Util.Mikrotik;
import java.util.List;
import java.util.Map;
import javafx.collections.FXCollections;
import static javafx.collections.FXCollections.observableArrayList;
import javafx.collections.ObservableList;
import sitetech.Helpers.dbHelper;

/**
 *
 * @author megan
 */
public class TicketManager {

    private dbHelper DbHelper;
    public ObservableList<Ticket> listaTickets = FXCollections.observableArrayList();

    public TicketManager() {
        DbHelper = new dbHelper();
        //listaTickets = this.getTickets();
    }

    public ObservableList<Ticket> getTickets() {
        return listaTickets = (ObservableList<Ticket>) DbHelper.Select("FROM Ticket WHERE eliminado=false");
    }
    
    public void AgregarTicket(Ticket tq) {
        DbHelper.Agregar(tq);
    }

    public void EditarTicket(Ticket tq) {
        DbHelper.Editar(tq);
    }

    public void EliminarTicket(Ticket tq) {
        tq.setEliminado(true);
        DbHelper.Editar(tq);
    }
    
    public ObservableList<Ticket> buscarTickets(String usuario, Paquete pq, Ticket.EstadosType estado) {
        String paquete = " ";
        String estad = " ";
        
        if (pq.toString() != "Todos") paquete = " paquete=" + pq.getId() + " "; else paquete = " paquete <> 0 ";
        if (estado != Ticket.EstadosType.Todos) estad = " AND estado=" + estado.ordinal() + " "; else estad = " AND estado <> 0 ";
        if (usuario.isEmpty()) usuario = "usuario like '%%' "; else usuario = " usuario like '%" + usuario + "%' ";
        
        return listaTickets = (ObservableList<Ticket>) DbHelper.Select("FROM Ticket WHERE eliminado=false AND " + usuario + " AND (" + paquete + estad + ")" );
    }

    public ObservableList<Ticket> getticketsRouter(Router rt){
        ObservableList<Ticket> _listaTicketsRt = observableArrayList();
        Mikrotik mk = new Mikrotik(rt.getIp(), rt.getUsuario(), rt.getPassword());
        List<Map<String, String>> lista = mk.leerDatos("/ip/hotspot/user/print");
        
        if (lista == null) return null;
        //public Ticket(int id, String nombre, String contraseña, EstadosType estado, Paquete paquete, Router _router) {
        for (Map<String, String> map : lista){
            Ticket tc = new Ticket(0, map.get("name"), map.get("password"), Ticket.EstadosType.Activo, new Paquete(), rt);
            _listaTicketsRt.add(tc);
            System.out.println( map.get("name") + " - "  + map.get("password"));
        }

        return _listaTicketsRt;
    }
    
    
}
