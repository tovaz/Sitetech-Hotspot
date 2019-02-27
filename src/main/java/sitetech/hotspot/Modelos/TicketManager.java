/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sitetech.hotspot.Modelos;

import javafx.collections.FXCollections;
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
}
