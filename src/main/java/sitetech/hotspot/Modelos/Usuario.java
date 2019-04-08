/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sitetech.hotspot.Modelos;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import Util.util;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

/**
 *
 * @author megan
 */
@Entity
@Table(name = "Usuario")
public class Usuario{
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
    private String nombre;
    private String contraseña;
    private String privilegios;
    private boolean eliminado;
    private boolean activo=true;
    
    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = util.getMD5(contraseña);
    }
    

    public Usuario()
    {
        
    }
    
    public Usuario (int _id, String _nombre, String _password, String _privilegios, boolean _eliminado, boolean _activo)
    {
        this.setId(_id);
        this.setNombre(_nombre);
        this.contraseña = _password;
        this.setPrivilegios(_privilegios);
        this.setEliminado(_eliminado);
        this.setActivo(_activo);
    }
    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the privilegios
     */
    public String getPrivilegios() {
        return privilegios;
    }

    /**
     * @param privilegios the privilegios to set
     */
    public void setPrivilegios(String privilegios) {
        this.privilegios = privilegios;
    }

    /**
     * @return the eliminado
     */
    public boolean getEliminado() {
        return eliminado;
    }

    /**
     * @param eliminado the eliminado to set
     */
    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }

    /**
     * @return the deshabilitad
     */
    public boolean getActivo() {
        return activo;
    }

    /**
     * @param deshabilitad the deshabilitad to set
     */
    public void setActivo(boolean _activo) {
        this.activo = _activo;
    }
    
    public String toString()
    {
        return "id: " + id + " ** nombre: " + nombre + " ** contraseña: " + contraseña + " ** privilegios: " + privilegios + 
                " ** eliminado: " + eliminado + " ** deshabilitado: " + activo;
    }
    
}
