package sitetech.hotspot.Modelos;
import Util.Moneda;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;
import javafx.beans.property.StringProperty;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Transient;
import sitetech.Helpers.dbHelper;

@Entity
@Table(name = "Paquete")
public class Paquete {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
    
    private String nombre;
    private BigDecimal precio;
    private boolean eliminado;
    private int cantidadTickets;
    
    private int dias;
    private int horas;
    private int minutos;
    private double megasDescarga;
    private double gigasDescarga;
    private double megasSubida;
    private double gigasSubida;
        
    private Date fechaCreacion;
    private Date fechaActualizacion;
    
    @Transient
    public boolean  Combobox_default;
    
    @Transient
    public String  precioFormateado;
    
    @PrePersist
    protected void onCreate() {
      this.fechaCreacion = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
      this.fechaActualizacion = new Date();
    }

    public Paquete() {
    }

    public Paquete(boolean _cb) {
        Combobox_default = _cb;
    }
    
    public Paquete(int id, String nombre, BigDecimal precio, boolean eliminado, int cantidadTickets, int dias, int horas, int minutos, double megasDescarga, double gigasDescarga, double megasSubida, double gigasSubida) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.eliminado = eliminado;
        this.cantidadTickets = cantidadTickets;
        this.dias = dias;
        this.horas = horas;
        this.minutos = minutos;
        this.megasDescarga = megasDescarga;
        this.gigasDescarga = gigasDescarga;
        this.megasSubida = megasSubida;
        this.gigasSubida = gigasSubida;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public boolean isEliminado() {
        return eliminado;
    }

    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }

    public int getCantidadTickets() {
        return cantidadTickets;
    }

    public void setCantidadTickets(int cantidadTickets) {
        this.cantidadTickets = cantidadTickets;
    }

    public int getDias() {
        return dias;
    }

    public void setDias(int dias) {
        this.dias = dias;
    }

    public int getHoras() {
        return horas;
    }

    public void setHoras(int horas) {
        this.horas = horas;
    }

    public int getMinutos() {
        return minutos;
    }

    public void setMinutos(int minutos) {
        this.minutos = minutos;
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

    public double getMegasDescarga() {
        return megasDescarga;
    }

    public void setMegasDescarga(double megasDescarga) {
        this.megasDescarga = megasDescarga;
    }

    public double getGigasDescarga() {
        return gigasDescarga;
    }

    public void setGigasDescarga(double gigasDescarga) {
        this.gigasDescarga = gigasDescarga;
    }

    public double getMegasSubida() {
        return megasSubida;
    }

    public void setMegasSubida(double megasSubida) {
        this.megasSubida = megasSubida;
    }

    public double getGigasSubida() {
        return gigasSubida;
    }

    public void setGigasSubida(double gigasSubida) {
        this.gigasSubida = gigasSubida;
    }
    
    @Transient
    public String Duracion;
    @Transient
    public String LimiteInternet;
    
    public String getDuracion() {
        String duracion = "";
        if (dias > 0)
            if (horas > 0)
                if (minutos > 0)
                    duracion = String.valueOf(dias) + " dias y " + String.valueOf(horas) + " hr " + String.valueOf(minutos) + " min";
                else if (horas == 1)
                    duracion = String.valueOf(dias) + " dias y " + String.valueOf(horas) + " hora";
                else
                    duracion = String.valueOf(dias) + " dias y " + String.valueOf(horas) + " horas";
            else if (minutos > 0)
                duracion = String.valueOf(dias) + " dias y "  + String.valueOf(minutos) + " minutos";
            else
                duracion = String.valueOf(dias) + " dias";
        else{
            if (horas > 0)
                if (minutos > 0)
                    duracion = String.valueOf(horas) + " hr " + String.valueOf(minutos) + " min";
                else if (horas == 1)
                    duracion = String.valueOf(horas) + " hora ";
                else
                    duracion = String.valueOf(horas) + " horas ";
            else if (minutos > 0 )
                duracion = String.valueOf(minutos) + " minutos";
            else
                return "Sin limite";
        }
        
        return duracion;
    }

    public void setDuracion(String Duracion) {
        this.Duracion = Duracion;
    }

    
    public String getLimiteSubidaS() {
        return  String.valueOf(gigasSubida) + " Gb " + " + " + String.valueOf(megasSubida) + " Mb ";
    }
    
    public String getLimiteDescargaS() {
        return  String.valueOf(gigasDescarga) + " Gb " + " + " + String.valueOf(megasDescarga) + " Mb ";
    }
    
    public String getLimiteInternet() {
        DecimalFormat f = new DecimalFormat("##.00");
        String limite = "";
                if (megasDescarga == 0 && gigasDescarga == 0) limite += " Sin limite. ";
                else if (megasDescarga == 0) 
                    limite = String.valueOf(f.format(gigasDescarga)) + " Gb " + " / " + String.valueOf( f.format((megasSubida/1024) + gigasSubida)) + " Gb ";
                else
                    limite = String.valueOf(f.format((megasDescarga/1024) + gigasDescarga)) + " Gb " + " / " + String.valueOf( f.format((megasSubida/1024) + gigasSubida)) + " Gb ";
                
                
                /*limite += " + ";
                if (gigasDescarga == 0) limite += "Sin limite. ";
                else limite += String.valueOf(gigasDescarga) + " Gb " + " / " + String.valueOf(gigasSubida) + " Gb ";
                */
        return limite;
    }

    public void setLimiteInternet(String LimiteInternet) {
        this.LimiteInternet = LimiteInternet;
    }
    
    public String getPrecioFormateado(){
        return Moneda.Formatear(precio);
    }
    
    
    @Override
    public String toString() { 
        if (!Combobox_default)
            return nombre + " | " + Moneda.Formatear(precio, Locale.getDefault()) + " | Limite: " + dias + "d " + horas + ":" + minutos + " | ( " + megasDescarga + " Mb + " + gigasDescarga + " Gb )"; 
        else
            return "Todos";
    }
    
    
}