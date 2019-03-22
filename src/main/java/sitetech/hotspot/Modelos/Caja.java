/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sitetech.hotspot.Modelos;

import Util.Moneda;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Currency;
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
 * @author Willi
 */
@Entity
@Table(name = "Caja")
public class Caja {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    
    @ManyToOne
    @JoinColumn(name = "usuarioApertura")
    private Usuario UsuarioApertura;
    
    @ManyToOne
    @JoinColumn(name = "usuarioCierre")
    private Usuario UsuarioCierre;
    
    private BigDecimal cajaInicial;
    
    private BigDecimal total = new BigDecimal(0);
    private BigDecimal totalIngreso = new BigDecimal(0);
    private BigDecimal totalEgreso = new BigDecimal(0);
    
    private Date fechaApertura;
    private Date fechaCierre;
    
    private Date fechaCreacion;
    private Date fechaActualizacion;
    private Date fechaImpresion;
    
    @PrePersist
    protected void onCreate() {
        this.fechaCreacion = new Date();
        this.fechaApertura = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        this.fechaActualizacion = new Date();
    }

    public Caja(Usuario UsuarioApertura, BigDecimal cajaInicial, BigDecimal total) {
        this.UsuarioApertura = UsuarioApertura;
        this.cajaInicial = cajaInicial;
        this.total = total;
    }

    
    public Caja(Usuario UsuarioApertura, BigDecimal cajaInicial, BigDecimal totalIngreso, BigDecimal totalEgreso) {
        this.UsuarioApertura = UsuarioApertura;
        this.cajaInicial = cajaInicial;
        this.totalIngreso = totalIngreso;
        this.totalEgreso = totalEgreso;
        this.total.add(totalIngreso);
        this.total.add(totalEgreso);
    }

    public Caja() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Usuario getUsuarioApertura() {
        return UsuarioApertura;
    }

    public void setUsuarioApertura(Usuario UsuarioApertura) {
        this.UsuarioApertura = UsuarioApertura;
    }

    public Usuario getUsuarioCierre() {
        return UsuarioCierre;
    }

    public void setUsuarioCierre(Usuario UsuarioCierre) {
        this.UsuarioCierre = UsuarioCierre;
    }

    public BigDecimal getCajaInicial() {
        return cajaInicial;
    }

    public void setCajaInicial(BigDecimal cajaInicial) {
        this.cajaInicial = cajaInicial;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getTotalIngreso() {
        return totalIngreso;
    }

    public void setTotalIngreso(BigDecimal totalIngreso) {
        this.totalIngreso = totalIngreso;
    }

    public BigDecimal getTotalEgreso() {
        return totalEgreso;
    }

    public void setTotalEgreso(BigDecimal totalEgreso) {
        this.totalEgreso = totalEgreso;
    }

    public Date getFechaApertura() {
        return fechaApertura;
    }

    public void setFechaApertura(Date fechaApertura) {
        this.fechaApertura = fechaApertura;
    }

    public Date getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(Date fechaCierre) {
        this.fechaCierre = fechaCierre;
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

    public Date getFechaImpresion() {
        return fechaImpresion;
    }

    public void setFechaImpresion(Date fechaImpresion) {
        this.fechaImpresion = fechaImpresion;
    }
    
    public String getTotalF(){
        return Moneda.Formatear(this.total);
    }
    
    public String getTotalIngresoF(){
        return Moneda.Formatear(this.totalIngreso);
    }
    
    public String getTotalEgresoF(){
        return Moneda.Formatear(this.totalEgreso);
    }
}
