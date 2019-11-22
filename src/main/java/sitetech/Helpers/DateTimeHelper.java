/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sitetech.Helpers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.net.InetAddress;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

//Importamos las librerias de Apache Commons
import org.apache.commons.net.ntp.NTPUDPClient;
import org.apache.commons.net.ntp.TimeInfo;

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
    
    public static String getFecha(){
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
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
    
    
    public static Date getNTPDate() {
        String servidor = "0.north-america.pool.ntp.org";
        Date fechaRecibida = null;
        NTPUDPClient cliente = new NTPUDPClient();
        cliente.setDefaultTimeout(5000);
        try {
            InetAddress hostAddr = InetAddress.getByName(servidor);
            TimeInfo fecha = cliente.getTime(hostAddr);
            
            //Recibimos y convertimos la fecha a formato DATE
            fechaRecibida = new Date(fecha.getMessage().getTransmitTimeStamp().getTime());
        } catch (Exception e) {
            System.err.println("Error "+e.getMessage());
        }
        
        cliente.close();
        return fechaRecibida == null ? new Date() : fechaRecibida ;
    }
    
    public static long getDifferenceDays(Date d1, Date d2) {
        long diff = d2.getTime() - d1.getTime();
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }
    
    public static Date addDays(Date date, int days)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days); //minus number would decrement the days
        return cal.getTime();
    }
}


