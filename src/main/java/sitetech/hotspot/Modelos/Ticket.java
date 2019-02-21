/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sitetech.hotspot.Modelos;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

/**
 *
 * @author megan
 */
@Entity
@Table(name = "Ticket")
public class Ticket {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
    
    @ManyToOne
    @JoinColumn(name="idPaquete")
    Paquete paquete;
    
    private String usuario;
    private String contraseña;
    private String ip;
    private String mac;
    
    // ******************** LIMITES **************
    private double limiteMegasDown;
    private double limiteGigasDown;
    private double limiteMegasUp;
    private double limiteGigasUp;
    
    private int limiteDias;
    private int limiteHoras;
    private int limiteMinutos;
    
    // ******************** CONSUMO **************
    
    private double megasConsumidosDown;
    private double megasConsumidosUp;
    private double gigasConsumidosDown;
    private double gigasConsumidosUp;
    
    private int diasConsumidos;
    private int horasConsumidas;
    private int minutosConsumidos;
    
    private Date fechaCreacion;
    private Date fechaActualizacion;
    
    private boolean eliminado;
    
    public static enum EstadosType {  Activo, Desactivado, En_Uso, Eliminado, Consumido, Generado, Error };
    private EstadosType estado;
    
    
    @PrePersist
    protected void onCreate() {
      this.fechaCreacion = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
      this.fechaActualizacion = new Date();
    }

    public Ticket() {
    }

    
    public Ticket(int id, String nombre, String contraseña, EstadosType estado, Paquete paquete) {
        this.id = id;
        this.paquete = paquete;
        this.usuario = nombre;
        this.contraseña = contraseña;
        this.eliminado = false;
        this.estado = estado;
        
        // limites
        this.limiteMegasDown = paquete.getMegasDescarga();
        this.limiteGigasDown = paquete.getGigasDescarga();
        this.limiteMegasUp = paquete.getMegasSubida();
        this.limiteGigasUp = paquete.getGigasSubida();
        this.limiteDias = paquete.getDias();
        this.limiteHoras = paquete.getHoras();
        this.limiteMinutos = paquete.getMinutos();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Paquete getPaquete() {
        return paquete;
    }

    public void setPaquete(Paquete paquete) {
        this.paquete = paquete;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String nombre) {
        this.usuario = nombre;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public double getLimiteMegasDown() {
        return limiteMegasDown;
    }

    public void setLimiteMegasDown(double limiteMegasDown) {
        this.limiteMegasDown = limiteMegasDown;
    }

    public double getLimiteGigasDown() {
        return limiteGigasDown;
    }

    public void setLimiteGigasDown(double limiteGigasDown) {
        this.limiteGigasDown = limiteGigasDown;
    }

    public double getLimiteMegasUp() {
        return limiteMegasUp;
    }

    public void setLimiteMegasUp(double limiteMegasUp) {
        this.limiteMegasUp = limiteMegasUp;
    }

    public double getLimiteGigasUp() {
        return limiteGigasUp;
    }

    public void setLimiteGigasUp(double limiteGigasUp) {
        this.limiteGigasUp = limiteGigasUp;
    }

    public int getLimiteDias() {
        return limiteDias;
    }

    public void setLimiteDias(int limiteDias) {
        this.limiteDias = limiteDias;
    }

    public int getLimiteHoras() {
        return limiteHoras;
    }

    public void setLimiteHoras(int limiteHoras) {
        this.limiteHoras = limiteHoras;
    }

    public int getLimiteMinutos() {
        return limiteMinutos;
    }

    public void setLimiteMinutos(int limiteMinutos) {
        this.limiteMinutos = limiteMinutos;
    }

    public double getMegasConsumidosDown() {
        return megasConsumidosDown;
    }

    public void setMegasConsumidosDown(double megasConsumidosDown) {
        this.megasConsumidosDown = megasConsumidosDown;
    }

    public double getMegasConsumidosUp() {
        return megasConsumidosUp;
    }

    public void setMegasConsumidosUp(double megasConsumidosUp) {
        this.megasConsumidosUp = megasConsumidosUp;
    }

    public double getGigasConsumidosDown() {
        return gigasConsumidosDown;
    }

    public void setGigasConsumidosDown(double gigasConsumidosDown) {
        this.gigasConsumidosDown = gigasConsumidosDown;
    }

    public double getGigasConsumidosUp() {
        return gigasConsumidosUp;
    }

    public void setGigasConsumidosUp(double gigasConsumidosUp) {
        this.gigasConsumidosUp = gigasConsumidosUp;
    }

    public int getDiasConsumidos() {
        return diasConsumidos;
    }

    public void setDiasConsumidos(int diasConsumidos) {
        this.diasConsumidos = diasConsumidos;
    }

    public int getHorasConsumidas() {
        return horasConsumidas;
    }

    public void setHorasConsumidas(int horasConsumidas) {
        this.horasConsumidas = horasConsumidas;
    }

    public int getMinutosConsumidos() {
        return minutosConsumidos;
    }

    public void setMinutosConsumidos(int minutosConsumidos) {
        this.minutosConsumidos = minutosConsumidos;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(Date fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    public boolean isEliminado() {
        return eliminado;
    }

    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }

    public EstadosType getEstado() {
        return estado;
    }

    public void setEstado(EstadosType estado) {
        this.estado = estado;
    }

    
    
}
