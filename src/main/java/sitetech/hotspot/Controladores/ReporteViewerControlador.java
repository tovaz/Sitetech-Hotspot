/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sitetech.hotspot.Controladores;

import Util.StageManager;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTextField;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.DragEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import sitetech.Helpers.reporteHelper;
import sitetech.hotspot.MainApp;

/**
 * FXML Controller class
 *
 * @author megan
 */
public class ReporteViewerControlador implements Initializable {

    @FXML private JFXButton bimprimir;
    @FXML private JFXButton bguardar;
    @FXML private JFXSlider slzoom;
    @FXML private JFXButton batraz2;
    @FXML private JFXButton batraz;
    @FXML private Label lpagina;
    @FXML private JFXButton badelante;
    @FXML private JFXButton badelante2;
    @FXML private StackPane stack;
    @FXML private ImageView ireporte;
    @FXML private JFXTextField tpagina;
    
    private JasperPrint jasperPrint;
    private SimpleIntegerProperty currentPage;
    private int imageHeight = 0; 
    private int imageWidth = 0;
    private int paginasReporte = 0;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    public static Stage thisStage;
    private MainApp App;
    public ReporteViewerControlador(MainApp _app) {
        App = _app;
        thisStage = new Stage();
        StageManager.cargarStage("/Reportes/ReporteViewer.fxml", "Reporte", thisStage, this, Modality.WINDOW_MODAL, App.configuracion);
        
        currentPage = new SimpleIntegerProperty(this, "currentPage", 1);
        botonesAction();
    }
    
    public void showStage() {
        thisStage.show();
    }

    
    private void botonesAction(){
        batraz.setOnAction(event -> renderPage(getCurrentPage() - 1));
        batraz2.setOnAction(event -> renderPage(1));
        badelante.setOnAction(event -> renderPage(getCurrentPage() + 1));
        badelante2.setOnAction(event -> renderPage(paginasReporte));
        
        slzoom.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                Double zoomVal = newValue.doubleValue();
                zoom(zoomVal/100);
            }
        });
    }
    
    @FXML
    private void onImprimir(ActionEvent event) {
        try {
            JasperPrintManager.printReport(jasperPrint, true);
            //thisStage.close();
        } catch (JRException ex) {
            ex.printStackTrace();
        }
    }
    
    @FXML
    void onExportar(ActionEvent event) {
        MenuItem mi = (MenuItem) event.getSource();
        
        FileChooser.ExtensionFilter ext = new FileChooser.ExtensionFilter(mi.getText(), "*." + mi.getText().toLowerCase());
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Guardar reporte como");
        chooser.getExtensionFilters().addAll(ext);
        chooser.setSelectedExtensionFilter(ext);

        File file = chooser.showSaveDialog(thisStage);

        if (file != null) {
            List<String> selectedExtension = chooser.getSelectedExtensionFilter().getExtensions();
            reporteHelper.exportarA(file, selectedExtension.get(0), jasperPrint);
        }
    }

    @FXML
    private void onGuardar(ActionEvent event) {
        FileChooser.ExtensionFilter pdf = new FileChooser.ExtensionFilter("PDF - Portable Document Format", "*.pdf");
        FileChooser.ExtensionFilter html = new FileChooser.ExtensionFilter("HTML - HyperText Markup Language", "*.html");
        FileChooser.ExtensionFilter xml = new FileChooser.ExtensionFilter("XML - Extensible Markup Language", "*.xml");
        FileChooser.ExtensionFilter xls = new FileChooser.ExtensionFilter("XLS - Microsoft Excel 2007", "*.xls");
        FileChooser.ExtensionFilter xlsx = new FileChooser.ExtensionFilter("XLSX - Microsoft Excel 2016", "*.xlsx");

        FileChooser chooser = new FileChooser();
        chooser.setTitle("Guardar reporte como");
        chooser.getExtensionFilters().addAll(pdf, html, xml, xls, xlsx);
        chooser.setSelectedExtensionFilter(pdf);

        File file = chooser.showSaveDialog(thisStage);

        if (file != null) {
            List<String> selectedExtension = chooser.getSelectedExtensionFilter().getExtensions();
            reporteHelper.exportarA(file, selectedExtension.get(0), jasperPrint);
        }
    }

    @FXML
    void onTextPagina(ActionEvent event) {
        Integer psel = Integer.parseInt(tpagina.getText());
        if (psel < 0) { psel = 0; tpagina.setText("0"); }
        
        if (psel > paginasReporte){
            psel = paginasReporte;
            tpagina.setText(String.valueOf(psel));
        }
            
        renderPage(psel);
    }
    
    
    // ***********************************************
    // Properties
    // ***********************************************

    /**
     * Set the currentPage property value
     * @param pageNumber Page number
     */
    public void setCurrentPage(int pageNumber) {
        currentPage.set(pageNumber);
    }

    /**
     * Get the currentPage property value
     * @return Current page value
     */
    public int getCurrentPage() {
        return currentPage.get();
    }

    /**
     * Get the currentPage property
     * @return currentPage property
     */
    public SimpleIntegerProperty currentPageProperty() {
        return currentPage;
    }
    
    
    //**************** FUNCIONES PRINCIPALES ****************/
    public void mostrarReporte(String titulo, JasperPrint jasperPrint) {
        this.jasperPrint = jasperPrint;

        imageHeight = jasperPrint.getPageHeight() + 284;
        imageWidth = jasperPrint.getPageWidth() + 201;
        paginasReporte = jasperPrint.getPages().size();
        lpagina.setText("1 de " + paginasReporte);

        if(paginasReporte > 0) {
            renderPage(1);
        }

        thisStage.setTitle(titulo);
        thisStage.show();
    }
    
    /**
     * Render specific page on screen
     * @param pageNumber
     */
    private void renderPage(int pageNumber) {
        setCurrentPage(pageNumber);
        desactivarBotones(pageNumber);
        tpagina.setText(Integer.toString(pageNumber));
        lpagina.setText(Integer.toString(pageNumber) + " de " + Integer.toString(paginasReporte) );
        ireporte.setImage(pageToImage(pageNumber));
    }

    /**
     * When the user reach first or last page he cannot go forward or backward
     * @param pageNumber Page number
     */
    private void desactivarBotones(int pageNumber) {
        boolean isFirstPage = (pageNumber == 1);
        boolean isLastPage = (pageNumber == paginasReporte);

        batraz.setDisable(isFirstPage);
        batraz2.setDisable(isFirstPage);
        badelante.setDisable(isLastPage);
        badelante2.setDisable(isLastPage);
    }
    
    /**
     * Scale image from ImageView
     * @param factor Zoom factor
     */
    public void zoom(double factor) {
        ireporte.setScaleX(factor);
        ireporte.setScaleY(factor);
        ireporte.setFitHeight(imageHeight + factor);
        ireporte.setFitWidth(imageWidth + factor);
    }
    
    /**
     * Renderize page to image
     * @param pageNumber Page number
     * @throws JRException
     */
    private Image pageToImage(int pageNumber) {
        try {
            //float zoom = (float) ( 1.33 + (slzoom.getValue()/100) );
            float zoom = (float) 1.33;
            BufferedImage image = (BufferedImage) JasperPrintManager.printPageToImage(jasperPrint, pageNumber - 1, zoom);
            WritableImage fxImage = new WritableImage(imageHeight, imageWidth);

            return SwingFXUtils.toFXImage(image, fxImage);
        } catch (JRException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}