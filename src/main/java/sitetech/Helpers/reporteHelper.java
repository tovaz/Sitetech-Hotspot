/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sitetech.Helpers;

import java.util.HashMap;
import java.util.Map;
import javafx.collections.ObservableList;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import sitetech.hotspot.MainApp;
import sitetech.hotspot.Modelos.Ticket;

/**
 *
 * @author megan
 */
public class reporteHelper {
    public static void imprimirTickets(ObservableList<Ticket> listaTickets){
        MainApp mainApp = new MainApp();
        Map<String,Object> parametros = new HashMap<String,Object>();
        parametros.put("dominio",new String("st.com/")); // propiedad de configuracion
        parametros.put("iTicket",new String(mainApp.getClass().getResource("/Imagenes/icon.png").toString())); // propiedad de configuracion
                
        try {
            JasperReport jp = (JasperReport) JRLoader.loadObject(mainApp.getClass().getResource("/Reportes/imprimirTicket.jasper"));
            JRBeanCollectionDataSource listaReporte = new JRBeanCollectionDataSource(listaTickets);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jp, (Map<String,Object>)parametros, listaReporte);

            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            JasperViewer jv = new JasperViewer(jasperPrint, false);
            jv.setTitle("Tickets Generados");
            jv.setIconImage(new javax.swing.ImageIcon(mainApp.getClass().getResource("/Imagenes/ticketprint.png")).getImage());
            jv.setVisible(true);
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException | JRException ex) { 
            System.out.println(ex.getMessage()); 
        }
    }
}
