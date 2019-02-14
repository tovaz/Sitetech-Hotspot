
package sitetech.hotspot.Controladores;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
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
        RouterManager rM = new RouterManager();
        if (bAgregar.getText().equals("Agregar Router")) {
            Router rt = new Router(0, tnombre.getText(), tip.getText(), tusuario.getText(), tcontraseña.getText(), Integer.parseInt(tpuertoApi.getText()), 
                    Integer.parseInt(tpuertoWeb.getText()), tlan.getText(), twlan.getText(), trangos.getText(), checkApi.isSelected(), false);
            rM.crearRouter(rt);
        }
        else{
            editarRouter(rM);
        }
        
        cancelarAction(event);
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
        
        rM.editarRouter(Rseleccionado);
    }
    
    @FXML
    void cancelarAction(ActionEvent event) {
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }


}

