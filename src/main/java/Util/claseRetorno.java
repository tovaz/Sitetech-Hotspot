/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

/**
 *
 * @author willi
 */
public class claseRetorno {
    public enum estadoTipo { Error, Exito, Advertencia, Informacion };
    private String mensaje;
    private estadoTipo estado;
    private Object dato;

    public claseRetorno(String mensaje, estadoTipo estado, Object dato) {
        this.mensaje = mensaje;
        this.estado = estado;
        this.dato = dato;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public estadoTipo getEstado() {
        return estado;
    }

    public void setEstado(estadoTipo estado) {
        this.estado = estado;
    }

    public Object getDato() {
        return dato;
    }

    public void setDato(Object dato) {
        this.dato = dato;
    }
    
    
    
}
