package sitetech.hotspot.Modelos;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

@Entity
@Table(name = "Paquete")
public class Paquete {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
    
    private String nombre;
    private Double precio;
    private boolean eliminado;
    private int cantidadTickets;
    
    private String limiteTiempo;
    private String limiteSubida;
    private String limiteBajada;
        
    private Date fechaCreacion;
    private Date fechaActualizacion;
    
    @PrePersist
    protected void onCreate() {
      this.fechaCreacion = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
      this.fechaActualizacion = new Date();
    }

    public Paquete(int id, String nombre, Double precio, boolean eliminado, String limiteTiempo, String limiteSubida, String limiteBajada) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.eliminado = eliminado;
        this.limiteTiempo = limiteTiempo;
        this.limiteSubida = limiteSubida;
        this.limiteBajada = limiteBajada;
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

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
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

    public String getLimiteTiempo() {
        return limiteTiempo;
    }

    public void setLimiteTiempo(String limiteTiempo) {
        this.limiteTiempo = limiteTiempo;
    }

    public String getLimiteSubida() {
        return limiteSubida;
    }

    public void setLimiteSubida(String limiteSubida) {
        this.limiteSubida = limiteSubida;
    }

    public String getLimiteBajada() {
        return limiteBajada;
    }

    public void setLimiteBajada(String limiteBajada) {
        this.limiteBajada = limiteBajada;
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
    
}
