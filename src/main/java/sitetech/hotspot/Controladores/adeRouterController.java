
package sitetech.hotspot.Controladores;
import Util.Validar;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sitetech.hotspot.Modelos.Router;
import sitetech.hotspot.Modelos.RouterManager;

public class adeRouterController implements Initializable {
    @FXML
    private TextField tnombre;

    @FXML
    private TextField tip;

    @FXML
    private TextField tusuario;

    @FXML
    private TextField tcontraseña;

    @FXML
    private TextField tpuertoApi;

    @FXML
    private TextField tpuertoWeb;

    @FXML
    private CheckBox checkApi;

    @FXML
    private TextField tlan;

    @FXML
    private TextField twlan;

    @FXML
    private TextArea trangos;

    @FXML
    private Button bAgregar;
    
    @FXML
    private Label ltitulo;

    @FXML
    private ImageView img;
    
    @FXML private Label ltnombre;
    @FXML private Label ltip;
    @FXML private Label ltusuario;
    @FXML private Label ltcontraseña;
    @FXML private Label ltpuertoApi;
    @FXML private Label ltpuertoWeb;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String regex = Util.util.makePartialIPRegex();
        final UnaryOperator<Change> ipAddressFilter = c -> {
            String text = c.getControlNewText();
            if  (text.matches(regex)) {
                return c ;
            } else {
                return null ;
            }
        };
        tip.setTextFormatter(new TextFormatter<>(ipAddressFilter));
    }

    private final Stage thisStage;
    /**
     *
     * @author megan
     */
    public adeRouterController (){
        thisStage = new Stage();
        Util.util.cargarStage("/Vistas/Routers/adeRouters.fxml", "Agregar Router", thisStage, this, Modality.APPLICATION_MODAL);
    }

    public void showStage() {
        thisStage.showAndWait();
    }
    
    Router Rseleccionado;
    public void cargarRouterInfo(Router rx) // EDITAR ROUTER CARGAR INFORACION DEL ROUTER
    {
        img.setImage( new Image(getClass().getResourceAsStream("/Imagenes/routeredit.png")) ); 
        bAgregar.setText("Guardar Router");
        thisStage.setTitle("Editar Router");
        Rseleccionado = rx;
        
        tnombre.setText(rx.getNombre());
        tip.setText(rx.getIp());
        tusuario.setText(rx.getUsuario());
        tcontraseña.setText(rx.getPassword());
        tpuertoApi.setText( String.valueOf(rx.getPuertoApi()) );
        tpuertoWeb.setText( String.valueOf(rx.getPuertoWeb()) );
        checkApi.setSelected(rx.isApiActiva());
        tlan.setText(rx.getLanInterface());
        twlan.setText(rx.getWlanInterface());
        trangos.setText(rx.getRangosIp());
    }
    
    @FXML
    void agregarRouterAction(ActionEvent event) {
        if (camposValidos()) {
            RouterManager rM = new RouterManager();
            if (bAgregar.getText().equals("Agregar Router")) {
                Router rt = new Router(0, tnombre.getText(), tip.getText(), tusuario.getText(), tcontraseña.getText(), Integer.parseInt(tpuertoApi.getText()), 
                        Integer.parseInt(tpuertoWeb.getText()), tlan.getText()+"", twlan.getText() + "", trangos.getText() + "", checkApi.isSelected(), false);
                rM.AgregarRouter(rt);
            }
            else{
                editarRouter(rM);
            }

            cancelarAction(event);
        }
    }

    void editarRouter(RouterManager rM)
    {
        Rseleccionado.setNombre(tnombre.getText());
        Rseleccionado.setIp(tip.getText());
        Rseleccionado.setUsuario(tusuario.getText());
        Rseleccionado.setUsuario(tusuario.getText());
        Rseleccionado.setPassword(tcontraseña.getText());
        Rseleccionado.setPuertoApi( Integer.parseInt(tpuertoApi.getText()) );
        Rseleccionado.setPuertoWeb( Integer.parseInt(tpuertoWeb.getText()) );
        
        Rseleccionado.setApiActiva(checkApi.isSelected());
        Rseleccionado.setLanInterface(tlan.getText());
        Rseleccionado.setWlanInterface(twlan.getText());
        Rseleccionado.setRangosIp(trangos.getText());
        
        rM.EditarRouter(Rseleccionado);
    }
    
    private boolean camposValidos(){
        boolean t1 = !Validar.esTextfieldVacio(tnombre, ltnombre, "Un nombres es requerido.");
        boolean t2 = !Validar.esTextfieldVacio(tip, ltip, "Se requiere una direccion ip.");
        boolean t3 = !Validar.esTextfieldVacio(tusuario, ltusuario, "Es requerido el nombre de usuario.");
        boolean t4 = !Validar.esTextfieldVacio(tcontraseña, ltcontraseña, "Contraseña es requerida.");
        boolean t5 = !Validar.esTextfieldNumero(tpuertoApi, ltpuertoApi, "Numero de puerto es requerido.", false);
        boolean t6 = !Validar.esTextfieldNumero(tpuertoWeb, ltpuertoWeb, "Numero de puerto requerido.", false);
        return t1 && t2 && t3 && t4 && t5 && t6;
    } 
    
    @FXML
    void cancelarAction(ActionEvent event) {
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }


}

