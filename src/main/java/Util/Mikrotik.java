/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import Util.claseRetorno.estadoTipo;
import java.util.List;
import java.util.Map;
import me.legrange.mikrotik.ApiConnection;
import me.legrange.mikrotik.MikrotikApiException;
import sitetech.hotspot.Modelos.Paquete;

/**
 *
 * @author willi
 */
public class Mikrotik {
    private String ip, usuario, contraseña;
    private ApiConnection conexion;
    
    public Mikrotik(String ip, String usuario, String contraseña){
        this.ip = ip;
        this.usuario = usuario;
        this.contraseña = contraseña;
    }
    
    
    public  claseRetorno conectar(){
        try {
            conexion = ApiConnection.connect(ip);
            conexion.login(usuario,contraseña);
            return new claseRetorno("Conexion establecida correctamente.", estadoTipo.Exito, true);
        } catch (MikrotikApiException ex) {
            System.out.println(ex.getMessage());
            if (ex.getMessage().contains("invalid user name"))
                return new claseRetorno("Nombre de usuario o contraseña incorrecto.", estadoTipo.Error, false);
            else
                return new claseRetorno(ex.getMessage(), estadoTipo.Error, false);
        }
    }
    
    public boolean Conectar()
    {
        try {
            conexion = ApiConnection.connect(ip);
            conexion.login(usuario,contraseña);
            return true;
        } catch (MikrotikApiException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }
    
    public boolean enviarComando(String comando){
        try {
            if (this.Conectar()){
                conexion.execute(comando);
                conexion.close();
                return true;
            }
        } catch (MikrotikApiException ex) {
                System.out.println(ex.getMessage());
        }
        return false;
    }
    
    public List<Map<String, String>> leerDatos(String comando){
        try {
            if ((boolean)this.conectar().getDato()){
                List<Map<String, String>> rs = conexion.execute(comando);
                for (Map<String,String> r : rs) {
                  System.out.println(r);
                }
                return rs;
            }
        } catch (MikrotikApiException ex) {
                System.out.println(ex.getMessage());
        }
        return null;
    }
    
    public boolean agregarHotspotUsuario(String usuario, String password, Paquete paq ){
        int limiteDescarga = (int)( (paq.getGigasDescarga() * 1024) + paq.getMegasDescarga() );
        int limiteSubida = (int)( (paq.getGigasDescarga() * 1024) + paq.getMegasDescarga() );
        String limiteTiempo = paq.getDias() + "d" + paq.getHoras() + ":" + paq.getMinutos() + ":00";
        String profile = "default";

        String comando = "/ip/hotspot/user/add name=" + usuario + 
                " password=" + password + " profile=" + profile + 
                " limit-uptime=" + limiteTiempo + " limit-bytes-out=" + limiteSubida +
                "M limit-bytes-in=" + limiteDescarga + "M";

        if ( this.enviarComando(comando) )
            return true;

        return false;
        
    }
}
