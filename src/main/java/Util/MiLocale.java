/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import java.text.NumberFormat;
import java.util.Locale;

/**
 *
 * @author megan
 */
public class  MiLocale  implements Comparable{
    private Locale locale;
    private String nombre;

    public MiLocale(Locale locale, String nombre) {
        this.locale = locale;
        this.nombre = nombre;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale local) {
        this.locale = local;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public int compareTo(Object o) {
        return this.nombre.compareTo(((MiLocale)o).getNombre());
    }
    
    @Override
    public String toString() {
        return nombre + " - " + NumberFormat.getCurrencyInstance(locale).getCurrency();
    }
    
    
    
}
