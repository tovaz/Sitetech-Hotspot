/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sitetech.hotspot.Controladores;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sitetech.hotspot.Modelos.Paquete;
import sitetech.hotspot.Modelos.PaqueteManager;
import slider.control.CircularSlider;

/**
 * FXML Controller class
 *
 * @author megan
 */
public class AdePaqueteController implements Initializable {

    @FXML
    private Label ltitulo;
    @FXML
    private ImageView img;
    @FXML
    private TextField tnombre;
    @FXML
    private TextField tprecio;
    @FXML
    private JFXTextField tdias;
    @FXML
    private JFXSlider sldias;
    @FXML
    private CircularSlider slhoras;
    @FXML
    private CircularSlider slminutos;
    @FXML
    private CircularSlider slmegasDown;
    @FXML
    private CircularSlider slgigasDown;
    @FXML
    private CircularSlider slmegasUp;
    @FXML
    private CircularSlider slgigasUp;
    @FXML
    private JFXButton bAgregar;

    private final Stage thisStage;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    public AdePaqueteController() {
        thisStage = new Stage();
        Util.util.cargarStage("/Vistas/Paquetes/adePaquete.fxml", "Agregar Paquete de Internet", thisStage, this, Modality.APPLICATION_MODAL);
    }
    
    public void showStage() {
        thisStage.showAndWait();
    }
    
    Paquete Pseleccionado;
    public void cargarInfo(Paquete px) // EDITAR ROUTER CARGAR INFORACION DEL ROUTER
    {
        img.setImage( new Image(getClass().getResourceAsStream("/Imagenes/paqueteedit.png")) ); 
        bAgregar.setText("Guardar Paquete");
        thisStage.setTitle("Editar Paquete de Internet");
        Pseleccionado = px;
        
        tnombre.setText(px.getNombre());
        tprecio.setText( String.valueOf(px.getPrecio()) );
        
        sldias.setValue(px.getDias());
        slhoras.setValue(px.getHoras());
        slminutos.setValue(px.getMinutos());
        slmegasDown.setValue(px.getMegasDescarga());
        slgigasDown.setValue(px.getGigasDescarga());
        slmegasUp.setValue(px.getMegasSubida());
        slgigasUp.setValue(px.getGigasSubida());
    }
    
    
    @FXML
    private void onKeypresstDias(KeyEvent event) {
        double x = Double.valueOf(tdias.getText());
        
        if ( x >= sldias.getMin() && x <= sldias.getMax() )
            sldias.setValue( Double.valueOf(tdias.getText()));
        else if ( x > sldias.getMax() ) { x = sldias.getMax(); }
        else if ( x < sldias.getMin() ) { x = sldias.getMin(); }
        
        tdias.setText(String.valueOf(x));
    }

    @FXML
    private void onDragslDias(DragEvent event) {
        tdias.setText(String.valueOf(sldias.getValue()));
    }

    @FXML
    private void cancelarAction(ActionEvent event) {
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }

    //int id, String nombre, Double precio, boolean eliminado, int cantidadTickets, int dias, int horas, int minutos, double megasDescarga, double gigasDescarga, double megasSubida, double gigasSubida
    @FXML
    private void agregarAction(ActionEvent event) {
        PaqueteManager pM = new PaqueteManager();
        if (bAgregar.getText().equals("Agregar Paquete")) {
            Paquete px = new Paquete(0, tnombre.getText(), Double.valueOf(tprecio.getText()), false, 0, (int)sldias.getValue(),
            (int)slhoras.getValue(), (int)slminutos.getValue(), slmegasDown.getValue(), slgigasDown.getValue(), slmegasUp.getValue(), 
            slgigasUp.getValue());
            
            pM.AgregarPaquete(px);
        }
        else{
            editarRegistro(pM);
        }
        
        cancelarAction(event);
    }
    
    void editarRegistro(PaqueteManager rM)
    {
        Pseleccionado.setNombre(tnombre.getText());
        Pseleccionado.setPrecio(Double.valueOf( tprecio.getText()) );
        Pseleccionado.setDias((int)sldias.getValue());
        Pseleccionado.setHoras((int)slhoras.getValue());
        Pseleccionado.setMinutos((int)slminutos.getValue());
        
        Pseleccionado.setMegasDescarga(slmegasDown.getValue());
        Pseleccionado.setGigasDescarga(slgigasDown.getValue());
        Pseleccionado.setMegasSubida(slmegasUp.getValue());
        Pseleccionado.setGigasSubida(slgigasUp.getValue());
        
        rM.EditarPaquete(Pseleccionado);
    }
    
}
