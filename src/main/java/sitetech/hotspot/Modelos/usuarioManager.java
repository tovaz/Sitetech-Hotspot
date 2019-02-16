/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sitetech.hotspot.Modelos;

import Util.util;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sitetech.Helpers.dbHelper;
import sitetech.Helpers.dbManager;

/**
 *
 * @author megan
 */
public class usuarioManager {
    private dbManager db;
    private dbHelper DbHelper;
    public ObservableList<Usuario> listaUsuarios = FXCollections.observableArrayList();
    public Usuario usuarioLogeado=null;
    
    public usuarioManager()
    {
        DbHelper = new dbHelper();
        db = new dbManager();
        listaUsuarios = this.getUsuarios();
    }
    
    public void AgregarUsuario(Usuario pack)
    {
        DbHelper.Agregar(pack);
    }
    
    public void EditarUsuario(Usuario pack)
    {
        DbHelper.Editar(pack);
    }
    
    public void EliminarUsuario(Usuario pack)
    {
        pack.setEliminado(true);
        DbHelper.Editar(pack);
    }
    
    
    public ObservableList<Usuario> getUsuarios(){
        return listaUsuarios = (ObservableList<Usuario>) 
        DbHelper.Select("FROM Usuario WHERE eliminado=false");
    }
    
    public boolean checkLogin(String nombre, String contraseña)
    {
        Usuario ux = null;
        for (int i=0; i<listaUsuarios.size(); i++)
            if (listaUsuarios.get(i).getNombre().equals(nombre) ) {
                ux = listaUsuarios.get(i);
                usuarioLogeado = ux;
            }
        if (ux != null)
            return ux.getContraseña().equals( util.getMD5(contraseña) );
        else return false;
    }
}
