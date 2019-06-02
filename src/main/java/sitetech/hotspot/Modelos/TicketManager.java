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
import sitetech.Helpers.DateTimeHelper;
import sitetech.Helpers.DateTimeHelper.TiempoDividido;
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
        tq.setEstadoS(tq.getEstado().name());
        DbHelper.Agregar(tq);
    }

    public void EditarTicket(Ticket tq) {
        tq.setEstadoS(tq.getEstado().name());
        DbHelper.Editar(tq);
    }

    public void EliminarTicket(Ticket tq) {
        tq.setEliminado(true);
        tq.setEstado(Ticket.EstadosType.Eliminado);
        tq.setEstadoS(tq.getEstado().name());
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
            Ticket tc = new Ticket(0, map.get("name"), map.get("password"), Ticket.EstadosType.Vendido, new Paquete(), rt);
            
            // LIMITES CONSUMIDOS
            TiempoDividido td = DateTimeHelper.dividirTiempo(map.get("uptime")); // OBTENEMOS EL TIEMPO CONSUMIDO
            tc.setDiasConsumidos(td.Dias);
            tc.setHorasConsumidas(td.Horas);
            tc.setMinutosConsumidos(td.Minutos);
            
            Double MegasConsumidosDown = Double.parseDouble(map.get("bytes-out")); // OBTENEMOS LOS MEGAS CONSUMIDOS DE DESCARGA
            if (MegasConsumidosDown > 0) MegasConsumidosDown = ( (MegasConsumidosDown / 1024) / 1024 ) / 1024;
            tc.setMegasConsumidosDown( (10 * MegasConsumidosDown - 10 * MegasConsumidosDown.intValue()) * 100);
            tc.setGigasConsumidosDown(MegasConsumidosDown.intValue());
            
            Double MegasConsumidosUp = Double.parseDouble(map.get("bytes-in")); // OBTENEMOS LOS MEGAS CONSUMIDOS DE SUBIDA
            if (MegasConsumidosUp > 0) MegasConsumidosUp = ( (MegasConsumidosUp / 1024) / 1024 ) / 1024;
            tc.setMegasConsumidosUp( (10 * MegasConsumidosUp - 10 * MegasConsumidosUp.intValue()) * 100);
            tc.setGigasConsumidosUp(MegasConsumidosUp.intValue());
            
            _listaTicketsRt.add(tc);
            System.out.println( map.get("name") + " - "  + map.get("password"));
        }

        return _listaTicketsRt;
    }
    
    
}
