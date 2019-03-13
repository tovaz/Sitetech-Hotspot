/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import sitetech.hotspot.Modelos.Configuracion;

/**
 *
 * @author megan
 */
public class Moneda {
    public static String Formatear(BigDecimal bd) {
        Configuracion conf = new Configuracion();
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(conf.getrLocale());
        //symbols.setDecimalSeparator(',');
        DecimalFormat df = new DecimalFormat("##.00", symbols);
        
        if(contieneDecimal(bd)) 
          return df.format(bd);
        else
          return bd.toString();
    }
    
     private static boolean contieneDecimal(BigDecimal bd) {
        return bd.toString().contains(".");
    }
}