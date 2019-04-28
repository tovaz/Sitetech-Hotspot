/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sitetech.Helpers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author megan
 */
public class DateTimeHelper {
    public static String getFechayHoraHoy(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date fecha = new Date();
        return dateFormat.format(fecha);
    }
    
            
    public static TiempoDividido dividirTiempo(String tiempo){
        Pattern pdias = Pattern.compile("(\\d?\\d)[d].*");
        Pattern phoras = Pattern.compile(".*?[d?]?(\\d?\\d)[h].*");
        Pattern pminutos = Pattern.compile(".*?[h?]?(\\d?\\d)[m].*");
        Pattern psegundos = Pattern.compile(".*?[m?]?(\\d?\\d)[s]");
        
        Matcher matchdias = pdias.matcher(tiempo);
        Matcher matchhoras = phoras.matcher(tiempo);
        Matcher matchminutos = pminutos.matcher(tiempo);
        Matcher matchsegundos = psegundos.matcher(tiempo);
        
        TiempoDividido td = new TiempoDividido();
        
        if (matchdias.matches())  td.Dias = Integer.parseInt(matchdias.group(1).replace("d", ""));
        if (matchhoras.matches())  td.Horas = Integer.parseInt(matchhoras.group(1).replace("h", ""));
        if (matchminutos.matches())  td.Minutos = Integer.parseInt(matchminutos.group(1).replace("m", ""));
        if (matchsegundos.matches())  td.Segundos = Integer.parseInt(matchsegundos.group(1).replace("s", ""));
        
        //System.out.println("TIEMPO: " + tiempo + " -- " + td.toString());
        return td;
    }
    
    
    public static class TiempoDividido{
        public int Dias=0;
        public int Horas=0;
        public int Minutos=0;
        public int Segundos=0;
        
        public TiempoDividido(){}
        public TiempoDividido(int dias, int horas, int minutos, int segundos){
            Dias = dias;
            Horas = horas;
            Minutos = minutos;
            Segundos = segundos;
        }        

        @Override
        public String toString() {
            return "D: " + Dias + " H: " + Horas + " M: " + Minutos + " S: " + Segundos;
        }
        
        
    }
}


