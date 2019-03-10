/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sitetech.hotspot;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author willi
 */
public class ThemeColor {
    String nombre;
    ObservableList<String> cssList = FXCollections.observableArrayList();
    String color;

    public ThemeColor(String nombre, String color, ObservableList<String> _cssList) {
        this.nombre = nombre;
        this.color = color;
        this.cssList = _cssList;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ObservableList<String> getCssList() {
        return cssList;
    }

    public void setCssList(ObservableList<String> cssList) {
        this.cssList = cssList;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
    
}
