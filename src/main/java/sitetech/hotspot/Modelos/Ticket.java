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
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author megan
 */
@Entity
@Table(name = "Ticket")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name = "idPaquete")
    Paquete paquete;

    @ManyToOne
    @JoinColumn(name = "idRouter")
    Router router;
    
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
    private Date fechaVenta;
    private Date fechaActualizacion;

    private boolean eliminado = false;

    public static enum EstadosType {
        Todos, Activo, Desactivado, En_Uso, Eliminado, Consumido, Generado, Error, Vendido
    };
    
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

    public Ticket(int id, String nombre, String contraseña, EstadosType estado, Paquete paquete, Router _router) {
        this.id = id;
        this.paquete = paquete;
        this.usuario = nombre;
        this.contraseña = contraseña;
        this.eliminado = false;
        this.estado = estado;
        this.router = _router;
        
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

    public Router getRouter() {
        return router;
    }

    public void setRouter(Router router) {
        this.router = router;
    }

    public Date getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(Date fechaVenta) {
        this.fechaVenta = fechaVenta;
    }
    
    
    //********************* FUNCIONES ADICIONALES ***************************
    @Transient public String Duracion;
    @Transient public String InternetConsumido;
    @Transient public String LimiteInternetDown;
    @Transient public String LimiteInternetUp;
    private String estadoS;
    
    public String getEstadoS(){
        return estadoS;
    }
    
    public void setEstadoS(String st){
        estadoS = st;
    }
    
    
    public String getInternetConsumido(){
        return Math.round(getMegasConsumidosDown())  + " Mb ↓ y " + Math.round(getMegasConsumidosUp()) + " Mb ↑ ";
    }
    
    public Double getPorcentajeDescarga(){
        return (getmegasConsumidoDown()*100)/(getLimiteMegasDown() + (getLimiteGigasDown()*1024) )/100;
    }
    
    public Double getPorcentajeSubida(){
        return (getmegasConsumidoUp()*100)/(getLimiteMegasUp() + (getLimiteGigasUp()*1024) )/100;
    }
    
    public Double getPorcentaje(double consumido, double limite){
        return consumido / limite;
    }
    
    public Double getmegasConsumidoDown(){
        Double _return = (gigasConsumidosDown*1024) + megasConsumidosDown;
        _return = Math.round(_return * 100.0) / 100.0;
        return _return;
    }
    
    public Double getmegasConsumidoUp(){
        Double _return = (gigasConsumidosUp*1024) + megasConsumidosUp;
        _return = Math.round(_return * 100.0) / 100.0;
        return _return;
    }
    
    public String getLimiteInternetDown(){
        String lreturn = "";
        if (limiteGigasDown == 0 && limiteMegasDown == 0) lreturn = "Sin limite";
        else if (limiteGigasDown > 0) {
            if (limiteMegasDown > 0) lreturn = limiteGigasDown + " Gb + " + limiteMegasDown + " Mb";
            else lreturn = limiteGigasDown + " Gb";
        }
        else if (limiteMegasDown > 0) lreturn = limiteMegasDown + " Mb";
        return lreturn;
    }
    
    public String getLimiteInternetUp(){
        if (limiteGigasUp == 0 && limiteMegasUp == 0) return "Sin limite";
        return limiteGigasUp + " Gb + " + limiteMegasUp + " Mb";
    }
    
    public String getDuracion() {
        return paquete.getDuracion();
    }
    
    public String getDuracionConsumida() {
        String _duracion = "";
        if (diasConsumidos > 0)
            if (horasConsumidas > 0)
                if (minutosConsumidos > 0)
                    _duracion = String.valueOf(diasConsumidos) + " dias y " + String.valueOf(horasConsumidas) + " hr " + String.valueOf(minutosConsumidos) + " min";
                else if (horasConsumidas == 1)
                    _duracion = String.valueOf(diasConsumidos) + " dias y " + String.valueOf(horasConsumidas) + " hora";
                else
                    _duracion = String.valueOf(diasConsumidos) + " dias y " + String.valueOf(horasConsumidas) + " horas";
            else if (minutosConsumidos > 0)
                _duracion = String.valueOf(diasConsumidos) + " dias y "  + String.valueOf(minutosConsumidos) + " minutos";
            else
                _duracion = String.valueOf(diasConsumidos) + " dias";
        else{
            if (horasConsumidas > 0)
                if (minutosConsumidos > 0)
                    _duracion = String.valueOf(horasConsumidas) + " hr " + String.valueOf(minutosConsumidos) + " min";
                else if (horasConsumidas == 1)
                    _duracion = String.valueOf(horasConsumidas) + " hora ";
                else
                    _duracion = String.valueOf(horasConsumidas) + " horas ";
            else if (minutosConsumidos > 0 )
                _duracion = String.valueOf(minutosConsumidos) + " minutos";
            else
                return "Aun sin consumir";
        }
        
        if (diasConsumidos == paquete.getDias() && horasConsumidas == paquete.getHoras() && minutosConsumidos == paquete.getMinutos())
            _duracion += " ** Llego al limite **";
        
        return _duracion;
    }
    
    
    // FUNCIONES DE PAQUETE - para los reportes
    @Transient private String precio;
    @Transient private String limiteInternet;
    @Transient private String paqueteS;
    public String getPrecio() {
        return paquete.getPrecioFormateado();
    }
    
    public String getLimiteInternet(){
        return paquete.getLimiteInternet();
    }
    
    public String getPaqueteS(){
        return paquete.getNombre();
    }
    
}
