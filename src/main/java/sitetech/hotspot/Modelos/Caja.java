/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sitetech.hotspot.Modelos;

import java.io.Serializable;
import java.util.Currency;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Willi
 */
@Entity
@Table(name = "Caja")
public class Caja {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    
    @ManyToOne
    @JoinColumn(name = "usuarioApertura")
    private Usuario UsuarioApertura;
    
    @ManyToOne
    @JoinColumn(name = "usuarioCierre")
    private Usuario UsuarioCierre;
    
    private Currency cajaInicial;
    
    private Currency total;
    private Currency totalIngreso;
    private Currency totalEgreso;
    
}
