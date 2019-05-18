/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sitetech.hotspot.Modelos;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author megan
 */
@Entity
@Table(name = "Detalle_Caja")
public class detalleCaja {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    
    @ManyToOne @JoinColumn(name = "idCaja")
    private Caja caja;
    
    @OneToOne @JoinColumn(name = "idTicket")
    private Ticket ticket;
    
    public enum TipoDetalle { Venta_Articulo, Ingreso, Egreso, Venta_Ticket };
    private TipoDetalle tipo;
    public enum EstadoDetalle { Correcto, anulado };
    private EstadoDetalle estado;
    
    private String comentario;
    private BigDecimal monto = BigDecimal.ZERO;
    private Date fechaCreacion;
    @PrePersist protected void onCreate() {
        this.fechaCreacion = new Date();
    }

    public detalleCaja() {
        
    }

    public detalleCaja(Caja caja, Ticket ticket, TipoDetalle tipo, String comentario, BigDecimal monto, EstadoDetalle _estado) {
        this.estado = _estado;
        this.caja = caja;
        this.ticket = ticket;
        this.tipo = tipo;
        this.comentario = comentario;
        this.monto = monto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Caja getCaja() {
        return caja;
    }

    public void setCaja(Caja caja) {
        this.caja = caja;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public TipoDetalle getTipo() {
        return tipo;
    }

    public void setTipo(TipoDetalle tipo) {
        this.tipo = tipo;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public EstadoDetalle getEstado() {
        return estado;
    }

    public void setEstado(EstadoDetalle estado) {
        this.estado = estado;
    }
    
    //************************** UTILIZADO PARA LOS REPORTES *******************
    @Transient String idCaja;
    @Transient String tipoNombre;
    @Transient String ticketNombre;
    @Transient String ticketContraseña;
    @Transient String ticketPrecio;
    @Transient String ticketPaquete;
    @Transient Date fechaApertura;
    @Transient Date fechaCierre;
    @Transient String usuarioCierre;

    public String getIdCaja(){
        return String.valueOf(caja.getId());
    }
    
    public String getTipoNombre() {
        return tipo.name();
    }
    
    public String getTicketNombre() {
        return ticket.getUsuario();
    }

    public String getTicketContraseña() {
        return ticket.getContraseña();
    }

    public String getTicketPrecio() {
        return ticket.getPaquete().getPrecioFormateado();
    }

    public String getTicketPaquete() {
        return ticket.getPaqueteS();
    }

    public Date getFechaApertura() {
        return caja.getFechaApertura();
    }

    public Date getFechaCierre() {
        return caja.getFechaCierre();
    }
    
    public String getUsuarioCierre() {
        return caja.getUsuarioCierre().getNombre();
    }
    
    
}
