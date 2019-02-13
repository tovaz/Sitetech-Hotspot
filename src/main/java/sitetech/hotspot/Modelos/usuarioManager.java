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
import sitetech.hotspot.dbManager;

/**
 *
 * @author megan
 */
public class usuarioManager {
    private dbManager db;
    public ObservableList<Usuario> listaUsuarios = FXCollections.observableArrayList();
    public Usuario usuarioLogeado=null;
    
    public usuarioManager()
    {
        db = new dbManager();
        listaUsuarios = this.getUsuarios();
    }
    
    public void crearUsuario(Usuario usuario){
        try {
            if (db.conectarHb()) {
                db.session.save(usuario);
                db.Flush();
                db.Cerrar();
                
                System.out.println("Usuario agregado exitosamente.");
                this.getUsuarios();
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    public void editarUsuario(Usuario usuario){
        try {
            if (db.conectarHb()) {
                db.session.update(usuario);
                db.Flush();
                db.Cerrar();
                
                System.out.println("Usuario editado exitosamente.");
                this.getUsuarios();
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    public void eliminarUsuario(Usuario usuario){
        usuario.setEliminado(true);
        try {
            if (db.conectarHb()) {
                db.session.update(usuario);
                db.Flush();
                db.Cerrar();
                
                System.out.println("Usuario eliminado exitosamente.");
                this.getUsuarios();
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    
    //int _id, String _nombre, String _password, String _privilegios, int _eliminado, int _deshabilitado
    public ObservableList<Usuario> getUsuarios()
    {
        ResultSet result = null;
        ObservableList<Usuario> ltemporal= FXCollections.observableArrayList();;
        try {
            if (db.conectarHb()){
                List<Usuario> lusuarios = db.session.createQuery("FROM Usuario WHERE eliminado=false").list();
                System.out.println(" ** Obteniendo usuarios ** ");

                for (Usuario usuario : lusuarios) {
                    System.out.println("Id: " + usuario.getId() + " | Name:"  + usuario.getNombre() + " | Email:" + usuario.getPrivilegios());
                    ltemporal.add(usuario);
                }
            
            }
            db.Cerrar();
            System.out.println("=======================");
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        
        return listaUsuarios = ltemporal;
    }
    
    public Usuario getUsuariobyId(int id)
    {
        ResultSet result = null;
        try {
            db.conectar();
            PreparedStatement st = db.conexion.prepareStatement("SELECT * FROM usuario WHERE id = ?");
            result = st.executeQuery();
            System.out.print(" ****** Obteniendo usuario con id " + id + " ****** ");
            
            int registros = 0;
            if (result.last()) {
                registros = result.getRow();
                result.beforeFirst();
            }
            
            if (registros == 1){
                Usuario ux = new Usuario(result.getInt("id"), 
                        result.getString("nombre"), 
                        result.getString("password"), 
                        result.getString("privilegios"), 
                        Boolean.parseBoolean( result.getString(  "eliminado") ),
                        Boolean.parseBoolean ( result.getString("activo") )  );
                
                System.out.println("Usuario encontrado ...");
                System.out.println(ux.toString());
                db.cerrar();
                return ux;
            }
            
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            return null;
        }
        
        return null;
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
