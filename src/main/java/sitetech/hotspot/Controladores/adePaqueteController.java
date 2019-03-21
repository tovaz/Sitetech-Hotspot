package sitetech.hotspot.Controladores;

import Util.Validar;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTextField;
import java.math.BigDecimal;
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
public class adePaqueteController implements Initializable {

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

    @FXML private Label ltnombre;
    @FXML private Label ltprecio;
    @FXML private Label ltdias;
    @FXML private Label lshoras;
    @FXML private Label lsminutos;
    
    @FXML private Label lsmegasDown;
    @FXML private Label lsgigasDown;
    
    private final Stage thisStage;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    public adePaqueteController() {
        thisStage = new Stage();
        Util.util.cargarStage("/Vistas/Paquetes/adePaquete.fxml", "Agregar Paquete de Internet", thisStage, this, Modality.APPLICATION_MODAL);
        sldias.setValue(0);
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
        
        if ( x > sldias.getMax() ) { x = sldias.getMax(); tdias.setText(String.valueOf((int)x)); }
        if ( x < sldias.getMin() ) { x = sldias.getMin(); tdias.setText(String.valueOf((int)x));}
        sldias.setValue( Double.valueOf(tdias.getText()));
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
        if (camposValidos()){
            PaqueteManager pM = new PaqueteManager();
            if (bAgregar.getText().equals("Agregar Paquete")) {
                Paquete px = new Paquete(0, tnombre.getText(), BigDecimal.valueOf( Double.valueOf(tprecio.getText()) ), false, 0, (int)sldias.getValue()+0,
                (int)slhoras.getValue()+0, (int)slminutos.getValue()+0, slmegasDown.getValue()+0, slgigasDown.getValue()+0, slmegasUp.getValue()+0, 
                slgigasUp.getValue()+0);

                pM.AgregarPaquete(px);
            }
            else{
                editarRegistro(pM);
            }

            cancelarAction(event);
        }
    }
    
    void editarRegistro(PaqueteManager rM)
    {
        if (camposValidos()){
            Pseleccionado.setNombre(tnombre.getText());
            Pseleccionado.setPrecio( BigDecimal.valueOf( Double.valueOf( tprecio.getText())  ) );
            Pseleccionado.setDias((int)sldias.getValue()+0);
            Pseleccionado.setHoras((int)slhoras.getValue()+0);
            Pseleccionado.setMinutos((int)slminutos.getValue()+0);

            Pseleccionado.setMegasDescarga(slmegasDown.getValue()+0);
            Pseleccionado.setGigasDescarga(slgigasDown.getValue()+0);
            Pseleccionado.setMegasSubida(slmegasUp.getValue()+0);
            Pseleccionado.setGigasSubida(slgigasUp.getValue()+0);

            rM.EditarPaquete(Pseleccionado);
        }
    }
    
    private boolean camposValidos(){
        boolean t1 = !Validar.esTextfieldVacio(tnombre, ltnombre, "Un nombres es requerido.");
        boolean t2 = !Validar.esTextfieldNumero(tprecio, ltprecio, "Se requiere un precio.", true);
        
        boolean t3 = !Validar.esTextfieldVacio(tdias, ltdias, "Requiere almenos un limite..");
        boolean t4 = Validar.esCircularSliderCorrecto(slhoras, lshoras, "Es requerido fijar un tiempo o un limite de megas.");
        boolean t5 = Validar.esCircularSliderCorrecto(slminutos, lsminutos, "Es requerido fijar un tiempo o un limite de megas.");
        
        boolean t6 = Validar.esCircularSliderCorrecto(slmegasDown, lsmegasDown, "Requiere almenos un limite Tiempo/Megas.");
        boolean t7 = Validar.esCircularSliderCorrecto(slgigasDown, lsgigasDown, "Requiere almenos un limite Tiempo/Megas.");
        return (t1 && t2) && ( (t3 || t4 || t5) || (t5 || t6) );
    }
    
}
