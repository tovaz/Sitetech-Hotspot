/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sitetech.hotspot.Modelos;

import Util.MiLocale;
import java.util.Currency;
import java.util.Locale;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author megan
 */
@Entity
@Table(name = "Configuracion")
public class Configuracion {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id=777;
    
    private String Dominio="http://st.cklogin/";
    private String imagenTicket=getClass().getResource("/Imagenes/icon.png").toString();
    private String Empresa="Sitetech";
    private String direccion="0 Calle D11B";
    private String Pais = "Guatemala";
    private String Estado = "Guatemala";
    private String Ciudad = "Guatemala";
    
    private String FormatoMoneda = "GT";
    private String Idioma = "Spanish";
    
    @Transient
    private Currency Moneda;

    private String colorEnfasis="Azul";
    private String colorTema="Claro";
    private boolean colorToolbar = false;
    private boolean colorMenu = true;
    
    //************ OPCIONES CON TICKETS Y SINCRONIZACION *************
    @Column(name = "imagenVisible", columnDefinition = "boolean default true", nullable = false)
    private boolean imagenVisible = true;
    @Column(name = "codigoBarraVisible", columnDefinition = "boolean default false", nullable = false)
    private boolean codigoBarraVisible = false;
    @Column(name = "sincronizarConsumo", columnDefinition = "boolean default true", nullable = false)
    private boolean sincronizarConsumo = true;
    @Column(name = "sincronizarVenta", columnDefinition = "boolean default false", nullable = false)
    private boolean sincronizarVenta = false;
    
    //************ OPCIONES DE BACKUP BASE DE DATOS *************
    
    private String dirBackup = "C:\\hotspot_back\\";
    @Column(name = "hacerBackup", columnDefinition = "boolean default true", nullable = false)
    private boolean hacerBackup = true;
    
    private String defaultUsername = "Usuario";
    
    
    @Transient
    private MiLocale RegionLocal; 
    public Configuracion() {
    }
    
    public Configuracion(boolean x) {
        FormatoMoneda = "GT";
        Idioma = "es";
        RegionLocal = new MiLocale(new Locale(Idioma, FormatoMoneda), FormatoMoneda);
        this.Moneda = Currency.getInstance(RegionLocal.getLocale());
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

    public MiLocale getRegionLocal() {
        return new MiLocale(new Locale(getIdioma(), FormatoMoneda), FormatoMoneda);
    }

    public Currency getMoneda() {
        return Currency.getInstance(new Locale(getIdioma(), FormatoMoneda));
    }
    
    public boolean isCodigoBarraVisible() {
        return codigoBarraVisible;
    }

    public void setCodigoBarraVisible(boolean codigoBarraVisible) {
        this.codigoBarraVisible = codigoBarraVisible;
    }

    public String getColorEnfasis() {
        return colorEnfasis;
    }

    public void setColorEnfasis(String colorEnfasis) {
        this.colorEnfasis = colorEnfasis;
    }

    public String getColorTema() {
        return colorTema;
    }

    public void setColorTema(String colorTema) {
        this.colorTema = colorTema;
    }

    public String getFormatoMoneda() {
        return FormatoMoneda;
    }

    public void setFormatoMoneda(String FormatoMoneda) {
        this.FormatoMoneda = FormatoMoneda;
    }

    public String getIdioma() {
        if (Idioma != null)
            return Idioma;
        else return "sp";
    }

    public void setIdioma(String Idioma) {
        this.Idioma = Idioma;
    }

    public boolean isColorToolbar() {
        return colorToolbar;
    }

    public void setColorToolbar(boolean colorToolbar) {
        this.colorToolbar = colorToolbar;
    }

    public boolean isColorMenu() {
        return colorMenu;
    }

    public void setColorMenu(boolean colorMenu) {
        this.colorMenu = colorMenu;
    }

    public boolean isImagenVisible() {
        return imagenVisible;
    }

    public void setImagenVisible(boolean imagenVisible) {
        this.imagenVisible = imagenVisible;
    }

    public String getDefaultUsername() {
        return defaultUsername;
    }

    public void setDefaultUsername(String defaultUsername) {
        this.defaultUsername = defaultUsername;
    }

    public boolean isSincronizarConsumo() {
        return sincronizarConsumo;
    }

    public void setSincronizarConsumo(boolean sincronizarConsumo) {
        this.sincronizarConsumo = sincronizarConsumo;
    }

    public boolean isSincronizarVenta() {
        return sincronizarVenta;
    }

    public void setSincronizarVenta(boolean sincronizarVenta) {
        this.sincronizarVenta = sincronizarVenta;
    }

    public String getDirBackup() {
        return dirBackup;
    }

    public void setDirBackup(String dirBackup) {
        this.dirBackup = dirBackup;
    }

    public boolean isHacerBackup() {
        return hacerBackup;
    }

    public void setHacerBackup(boolean hacerBackup) {
        this.hacerBackup = hacerBackup;
    }

    
    
    
    
}
