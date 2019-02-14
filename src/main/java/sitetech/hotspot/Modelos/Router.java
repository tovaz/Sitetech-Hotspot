/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sitetech.hotspot.Modelos;

import java.util.logging.Logger;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

/**
 *
 * @author willi
 */
@Entity
@Table(name = "Router")
public class Router {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
    
    private String nombre;
    private String ip;
    private String usuario;
    private String password;
    private int puertoApi;
    private int puertoWeb;
    private String lanInterface;
    private String wlanInterface;
    private String rangosIp;
    private boolean apiActiva;
    private boolean eliminado;

    
    public Router() { }
    
    public Router(int id, String nombre, String ip, String usuario, String password, int puertoApi, int puertoWeb, String lanInterface, String wlanInterface, String rangosIp, boolean habilitarApi, boolean _eliminado) {
        this.id = id;
        this.nombre = nombre;
        this.ip = ip;
        this.usuario = usuario;
        this.password = password;
        this.puertoApi = puertoApi;
        this.puertoWeb = puertoWeb;
        this.lanInterface = lanInterface;
        this.wlanInterface = wlanInterface;
        this.rangosIp = rangosIp;
        this.apiActiva = habilitarApi;
        this.eliminado = _eliminado;
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

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPuertoApi() {
        return puertoApi;
    }

    public void setPuertoApi(int puertoApi) {
        this.puertoApi = puertoApi;
    }

    public int getPuertoWeb() {
        return puertoWeb;
    }

    public void setPuertoWeb(int puertoWeb) {
        this.puertoWeb = puertoWeb;
    }

    public String getLanInterface() {
        return lanInterface;
    }

    public void setLanInterface(String lanInterface) {
        this.lanInterface = lanInterface;
    }

    public String getWlanInterface() {
        return wlanInterface;
    }

    public void setWlanInterface(String wlanInterface) {
        this.wlanInterface = wlanInterface;
    }

    public String getRangosIp() {
        return rangosIp;
    }

    public void setRangosIp(String rangosIp) {
        this.rangosIp = rangosIp;
    }

    public boolean isApiActiva() {
        return apiActiva;
    }

    public void setApiActiva(boolean habilitarApi) {
        this.apiActiva = habilitarApi;
    }
    
    public boolean isEliminado() {
        return eliminado;
    }

    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }
    
}
