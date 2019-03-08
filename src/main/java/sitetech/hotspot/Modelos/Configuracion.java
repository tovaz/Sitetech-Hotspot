/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sitetech.hotspot.Modelos;

import java.util.Currency;
import java.util.Locale;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author megan
 */
@Entity
@Table(name = "Configuracion")
public class Configuracion {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    
    private String Dominio="http://st.cklogin/";
    private String imagenTicket="";
    private String Empresa="Sitetech";
    private String direccion="0 Calle D11B";
    private String Pais = "Guatemala";
    private String Estado = "Guatemala";
    private String Ciudad = "Guatemala";
    private Locale RegionLocal = new Locale("es", "GT");
    private Currency Moneda = Currency.getInstance("QGT");

    public Configuracion() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDominio() {
        return Dominio;
    }

    public void setDominio(String Dominio) {
        this.Dominio = Dominio;
    }

    public String getImagenTicket() {
        return imagenTicket;
    }

    public void setImagenTicket(String imagenTicket) {
        this.imagenTicket = imagenTicket;
    }

    public String getEmpresa() {
        return Empresa;
    }

    public void setEmpresa(String Empresa) {
        this.Empresa = Empresa;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getPais() {
        return Pais;
    }

    public void setPais(String Pais) {
        this.Pais = Pais;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String estado) {
        this.Estado = estado;
    }

    public String getCiudad() {
        return Ciudad;
    }

    public void setCiudad(String Ciudad) {
        this.Ciudad = Ciudad;
    }

    public Locale getRegionLocal() {
        return RegionLocal;
    }

    public void setRegionLocal(Locale RegionLocal) {
        this.RegionLocal = RegionLocal;
    }

    public Currency getMoneda() {
        return Moneda;
    }

    public void setMoneda(Currency Moneda) {
        this.Moneda = Moneda;
    }
    
    
    
}
