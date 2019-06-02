/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sitetech.Helpers;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import javafx.collections.ObservableList;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.view.JasperViewer;
import sitetech.hotspot.MainApp;
import sitetech.hotspot.Modelos.Configuracion;
import sitetech.hotspot.Modelos.Ticket;

/**
 *
 * @author megan
 */
public class reporteHelper {
    public static void imprimirTickets(ObservableList<Ticket> listaTickets, Configuracion config){
        MainApp mainApp = new MainApp();
        Map<String,Object> parametros = new HashMap<String,Object>();
        parametros.put("dominio", config.getDominio()); // propiedad de configuracion
        parametros.put("iTicket", config.getImagenTicket()); // propiedad de configuracion
                
        try {
            JasperReport jp = (JasperReport) JRLoader.loadObject(mainApp.getClass().getResource("/Reportes/imprimirTicket.jasper"));
            JRBeanCollectionDataSource listaReporte = new JRBeanCollectionDataSource(listaTickets);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jp, (Map<String,Object>)parametros, listaReporte);

            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            JasperViewer jv = new JasperViewer(jasperPrint, false);
            jv.setTitle("imprimir Tickets");
            jv.setIconImage(new javax.swing.ImageIcon(mainApp.getClass().getResource("/Imagenes/ticketprint.png")).getImage());
            jv.setVisible(true);
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException | JRException ex) { 
            System.out.println(ex.getMessage()); 
        }
    }
    
    public static JasperPrint getJasperPrintTicket(ObservableList<Ticket> listaTickets,Configuracion config){
        MainApp mainApp = new MainApp();
        Map<String,Object> parametros = new HashMap<String,Object>();
        parametros.put("dominio", config.getDominio()); // propiedad de configuracion
        parametros.put("iTicket", config.getImagenTicket()); // propiedad de configuracion
        parametros.put("mostrarImagen", config.isImagenVisible()); // propiedad de configuracion
        parametros.put("mostrarCodigo", config.isCodigoBarraVisible()); // propiedad de configuracion
        parametros.put( JRParameter.REPORT_LOCALE, config.getRegionLocal().getLocale() );
        
        try {
            JasperReport jp = (JasperReport) JRLoader.loadObject(mainApp.getClass().getResource("/Reportes/imprimirTicket.jasper"));
            JRBeanCollectionDataSource listaReporte = new JRBeanCollectionDataSource(listaTickets);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jp, (Map<String,Object>)parametros, listaReporte);

            return jasperPrint;
        } catch (JRException ex) { 
            System.out.println(ex.getMessage()); 
        }
        
        return null;
    }
    
    public static JasperPrint getJasperPrint(String reporte_nombre, ObservableList<?> lista, Map<String,Object> parametros, Configuracion config ){
        MainApp mainApp = new MainApp();
        parametros.put( JRParameter.REPORT_LOCALE, config.getRegionLocal().getLocale() );
        parametros.put( "HIBERNATE_SESSION", dbManager.configureSessionFactory().openSession() );
                
        try {
            JasperReport jp = (JasperReport) JRLoader.loadObject(mainApp.getClass().getResource(reporte_nombre));
            JasperPrint jasperPrint;
            if (lista != null){
                JRBeanCollectionDataSource listaReporte = new JRBeanCollectionDataSource(lista);
                jasperPrint = JasperFillManager.fillReport(jp, (Map<String,Object>)parametros, listaReporte);
            }
            else{
                jasperPrint = JasperFillManager.fillReport(jp, parametros);
            }
            
            return jasperPrint;
        } catch (JRException ex) { 
            System.out.println(ex.getMessage()); 
        }
        
        return null;
    }
    // ***********************************************
    // Export Utilities
    // ***********************************************
    /**
     * Choose the right export method for each file extension
     * @param file File
     * @param extension File extension
     */
    public static void exportarA(File file, String extension, JasperPrint reporte) {
        switch (extension) {
            case "*.pdf":
                exportToPdf(file, reporte);
                break;
            case "*.html":
                exportToHtml(file, reporte);
                break;
            case "*.xml":
                exportToXml(file, reporte);
                break;
            case "*.xls":
                exportToXls(file, reporte);
                break;
            case "*.xlsx":
                exportToXlsx(file, reporte);
                break;
            default:
                exportToPdf(file, reporte);
        }
    }

    /**
     * Export report to html file
     */
    public static void exportToHtml(File file, JasperPrint reporte) {
        try {
            JasperExportManager.exportReportToHtmlFile(reporte, file.getPath());
        } catch (JRException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Export report to Pdf file
     */
    public static void exportToPdf(File file, JasperPrint reporte) {
        try {
            JasperExportManager.exportReportToPdfFile(reporte, file.getPath());
        } catch (JRException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Export report to old Microsoft Excel file
     */
    public static void exportToXls(File file, JasperPrint reporte) {
        try {
            JRXlsExporter exporter = new JRXlsExporter();
            exporter.setExporterInput(new SimpleExporterInput(reporte));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(file));
            exporter.exportReport();
        } catch (JRException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Export report to Microsoft Excel file
     */
    public static void exportToXlsx(File file, JasperPrint reporte) {
        try {
            JRXlsxExporter exporter = new JRXlsxExporter();
            exporter.setExporterInput(new SimpleExporterInput(reporte));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(file));
            exporter.exportReport();
        } catch (JRException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Export report to XML file
     */
    public static void exportToXml(File file, JasperPrint reporte) {
        try {
            JasperExportManager.exportReportToXmlFile(reporte, file.getPath(), false);
        } catch (JRException ex) {
            ex.printStackTrace();
        }
    }
}
