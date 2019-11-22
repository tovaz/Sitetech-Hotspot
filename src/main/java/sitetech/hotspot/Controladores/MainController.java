package sitetech.hotspot.Controladores;

import Util.Dialogo;
import com.jfoenix.controls.JFXSpinner;
import sitetech.hotspot.Temas;
import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.effect.MotionBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sitetech.Helpers.LabelHelper;
import sitetech.hotspot.MainApp;
import sitetech.hotspot.Modelos.Caja;
import sitetech.hotspot.Modelos.CajaManager;
import sitetech.hotspot.Modelos.Router;
import sitetech.hotspot.Modelos.RouterManager;
import sitetech.hotspot.Modelos.Ticket;
import sitetech.hotspot.Modelos.TicketManager;
import sitetech.hotspot.Modelos.Usuario;
import sitetech.hotspot.Modelos.detalleCaja;

public class MainController implements Initializable {

    public MainApp App;
    public Stage thisStage;
    
    @FXML private AnchorPane panelPrincipal;
    @FXML private AnchorPane panelTitulo;
    @FXML private Label ltitulo;
    @FXML private Label lusuario;
    @FXML private Label lcaja;
    @FXML private Label lcajaTotal;
    @FXML private Label lsincronizando;
    @FXML private JFXSpinner spsincronizando;
    @FXML private MenuItem meliminar;
    @FXML private MenuItem mgenerar;
    @FXML private Menu madministrar;
    
    TicketsController tc;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //this.ArrastrarScene(panelTitulo);
        tc = new TicketsController(App);
        try {
            tc.cargarPanel(panelPrincipal);
            aplicarPermisos();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
        
    }

    @Override
    protected void finalize() throws Throwable {
        App.stop();
        super.finalize(); //To change body of generated methods, choose Tools | Templates.
    }

    public void actualizarInfo(Usuario user, Caja caja){
        try {
            lusuario.setText(user.getNombre());
            lcaja.setText( String.valueOf(caja.getId() ));
            lcajaTotal.setText( caja.getTotalF() );
        }
        catch (Exception e){
            System.err.println(e.getMessage());
        }
    }
    
    public Timer  timer;
    public TimerTask timerTask;
    public MainController(MainApp _mainApp) {
        App = _mainApp;
        thisStage = new Stage();
        
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/Vistas/mainScene.fxml"));
            loader.setController(this);
            
            thisStage.initStyle(StageStyle.DECORATED);
            Scene scene = new Scene((Parent) loader.load());

            Temas.aplicarTema(scene, App.configuracion);
            
            thisStage.getIcons().add(App.iconoApp); // ICONO DE LA APP
            
            thisStage.setScene(scene);
            thisStage.setTitle("Hotspot 1.1");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        
        App.agregarEscena("scene_main", thisStage.getScene());
        
        SincronizarTickets();
    }

    public void SincronizarTickets(){// Sincronizar los tickest y programar la tarea cada 30 segundos ... TASK
        spsincronizando.setVisible(false);
        if (App.configuracion.isSincronizarConsumo() || App.configuracion.isSincronizarVenta()){
            spsincronizando.setVisible(true);
            LabelHelper.asignarTexto(lsincronizando, "Sincronizando tickets ...");
            timer = new Timer ();
            if (sync == null) sync = new syncTicket();
            timer.schedule(sync, 10000, 300000);
        }else
            LabelHelper.asignarTexto(lsincronizando, "Sincronizacion deshabilitada.");
    }
    
    public void showStage() {
        panelPrincipal.setEffect(null);
        thisStage.show();
    }

    @FXML
    public void loadUsuarios() throws IOException {
        adUsuarioController adUsuario = new adUsuarioController(App);
        adUsuario.showAgregar(new UsuariosController(App));
    }

    UsuariosController uvController;
    RoutersController rController;
    PaquetesController pController;
    ReportesControlador reportesControlador;
    IeCajaController ieCaja;
    @FXML
    private void onMenuAction(ActionEvent event) throws IOException {
        MenuItem mi = (MenuItem) event.getSource();
        System.out.println(mi.getText());

        
        switch (mi.getText()) {
            //*************** ADMIN MENU ******************//
            case "Usuarios":
                uvController = new UsuariosController(App);
                uvController.showStage();
                break;

            case "Routers":
                rController = new RoutersController(App);
                rController.showStage();
                break;

            case "Paquetes de Internet":
                pController = new PaquetesController(App);
                pController.showStage();
                break;
            
            case "Reportes":
                reportesControlador = new ReportesControlador(App);
                reportesControlador.show();
                break;
            
            case "Configuracion":
                ConfiguracionController pConfiguracion = new ConfiguracionController(App);
                ObservableList<Scene> scenes = FXCollections.observableArrayList();
                scenes.add(tc.thisStage.getScene());
                scenes.add(thisStage.getScene());
                
                pConfiguracion.thisStage.setUserData(scenes);
                pConfiguracion.showStage();
                break;
                
            //*************** TICKET MENU ******************//
            case "Vender Ticket":
                tc.onVenderAction(event);
            break;
            
            case "Generar tickets":
                tc.onGenerarAction(event);
            break;
            
            case "Imprimir tickets":
                tc.onImprimirAction(event);
            break;
            
            case "Eliminar ticket":
                tc.onEliminarAction(event);
            break;
            
            //*************** CAJA MENU ******************//
            case "Consultar caja": 
                reportesControlador = new ReportesControlador(App);
                reportesControlador.show();
                reportesControlador.showDetalleCaja(App.cajaAbierta.getId());
                break;
                
            case "Ingreso":
                ieCaja = new IeCajaController(App);
                ieCaja.mostrarIngreso();
                break;
                
            case "Egreso":
                ieCaja = new IeCajaController(App);
                ieCaja.mostrarEgreso();
                break;
                
            case "Cerrar caja":
                DetalleCajaControlador detallesC = new DetalleCajaControlador(App);
                detallesC.showStage();
                break;
                
            default: break;
        }

    }
    
    private void CerrarCaja(){
        
    }    

    @FXML
    void onclickCaja(MouseEvent event) {
        reportesControlador = new ReportesControlador(App);
        reportesControlador.show();
        reportesControlador.showDetalleCaja(App.cajaAbierta.getId());
    }
    
    @FXML
    void cerrarAction(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    void minimizarAction(ActionEvent event) {
    }

    @FXML
    void maximizarAction(ActionEvent event) {
    }
    
    @FXML
    void cerrarSesionAction(ActionEvent event) throws Exception {
        ButtonType btn = Dialogo.mostrarConfirmacion("¿Desea realmente cerrar sesion?", "Cerrar Sesion", App.configuracion, ButtonType.YES, ButtonType.NO);
        
        if (btn == ButtonType.YES){
            LoginController logc = new LoginController(App);
            App.usuarioLogeado = null;
            lusuario.setText("");
            
            MotionBlur mb = new MotionBlur();
            mb.setRadius(15.0f);
            mb.setAngle(45.0f);

            panelPrincipal.setEffect(mb);
            logc.showStage();

            if (App.usuarioLogeado == null){
                thisStage.close();
            }
        }
    }
    
    public void aplicarPermisos(){
        tc.aplicarPermisos(); // Aplicar permisos en ticket manager
        
        switch (App.usuarioLogeado.getPrivilegios()){
            case "Usuario":
                meliminar.setDisable(true);
                
            break;
            
            case "Administrador":
                meliminar.setDisable(false);
                madministrar.setDisable(false);
                mgenerar.setDisable(false);
            break;
            
            case "Cajero":
                meliminar.setDisable(true);
            break;
        }
        
    }
    
    //**************************************** SINCRONIZACION DE INFORMACION TICKETS DB Y TICKETS EN ROUTER
    public syncTicket sync;
    @FXML
    void onsincronizar(ActionEvent event) {
        if (App.configuracion.isSincronizarConsumo() || App.configuracion.isSincronizarVenta()){
            spsincronizando.setVisible(true);
            LabelHelper.asignarTexto(lsincronizando, "Sincronizando tickets ...");

            if (sync == null)
               sync = new syncTicket();

            sync.run();
        }
    }
    
    ObservableList<Ticket> ticketsActualizados;
    public void  actualizarTickets(){
        RouterManager rm = new RouterManager();
        TicketManager tm = new TicketManager();
        ObservableList<Ticket> listaDb = tm.getTickets();
        ObservableList<Router> lrouters = rm.getRouters();
        ticketsActualizados = FXCollections.observableArrayList();
        ticketsActualizados.clear();
        
        for (Router rx : lrouters){ // Recorremos cada router, buscando sus tickets, luego los comparamos con los tickets de la base de datos.
            ObservableList<Ticket> listaRouter = tm.getticketsRouter(rx);
            
            if (listaRouter != null){
                for (Ticket tcRouter : listaRouter){
                    for (Ticket tc : listaDb){
                        boolean editar = false;
                        if (tc.getUsuario().equals(tcRouter.getUsuario())){
                            System.out.println("Internet: " + tc.getInternetConsumido() + " -ROUTER- " + tcRouter.getInternetConsumido());
                            
                            boolean actualizar = !tc.getInternetConsumido().equals(tcRouter.getInternetConsumido());
                            
                             if (App.configuracion.isSincronizarConsumo() && actualizar){ //Sincronizamos el consumo
                                tc.setDiasConsumidos( tcRouter.getDiasConsumidos() );
                                tc.setHorasConsumidas(tcRouter.getHorasConsumidas());
                                tc.setMinutosConsumidos(tcRouter.getMinutosConsumidos());

                                tc.setGigasConsumidosDown(tcRouter.getGigasConsumidosDown());
                                tc.setGigasConsumidosUp(tcRouter.getGigasConsumidosUp());

                                tc.setMegasConsumidosDown(tcRouter.getMegasConsumidosDown());
                                tc.setMegasConsumidosUp(tcRouter.getMegasConsumidosUp());
                                editar = true;
                            }
                             
                            if (!tc.getDuracionConsumida().equals("Aun sin consumir") && tc.getEstado() == Ticket.EstadosType.Activo){ //Vendemos el ticket y lo agregamos a la caja actual
                                if (App.configuracion.isSincronizarVenta()){
                                    CajaManager cm = new CajaManager();
                                    detalleCaja dt = new detalleCaja(App.cajaAbierta, tc, detalleCaja.TipoDetalle.Venta_Ticket, "", tc.getPaquete().getPrecio(), detalleCaja.EstadoDetalle.Correcto);

                                    Caja caja = cm.agregarDetalle(dt);
                                    if ( caja != null){
                                        tc.setFechaVenta(new Date());
                                        tc.setEstado(Ticket.EstadosType.Vendido);
                                        //tm.EditarTicket(tc);
                                        App.actualizarCaja(caja);
                                    }
                                    editar = true;
                                }
                            }
                            
                            if (editar){
                                //tm.EditarTicket(tc); //Actualizamos el ticket en la DB
                                ticketsActualizados.add(tc);
                            }
                        }
                    }
                }
            }
        }
        
        Platform.runLater(new Runnable() {
            @Override
            public void run(){
                guardarTickets();
            }
        });
    }
    
    private void guardarTickets(){ // Actualizamos el ticket en la lista que vemos
        TicketManager tm = new TicketManager();
        for (Ticket tx : ticketsActualizados){
            tm.EditarTicket(tx);
            for (int i=0; i<tc.listaTickets.size(); i++){
                if (tc.listaTickets.get(i).getId() == tx.getId())
                    tc.listaTickets.set(i, tx); 
            }
        }
        
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        if (ticketsActualizados.isEmpty())
            LabelHelper.asignarTexto(lsincronizando, "No se actualizo ningun ticket, ultima sync | " + java.time.LocalTime.now().format(dtf) + " |");
        else
            LabelHelper.asignarTexto(lsincronizando, "Se actualizaron " + ticketsActualizados.size() + " tickets. <" + java.time.LocalTime.now().format(dtf) + ">");
        
        spsincronizando.setVisible(false);
    }
    
    public class syncTicket extends TimerTask {
        Thread th;
        @Override
        public void run() {
            spsincronizando.setVisible(true);
            lsincronizando.setText("Sincronizando tickets ...");
            th = new Thread(() -> actualizarTickets());
            //System.out.println("PRIORIDAD THREAD: " + th.getPriority());
            th.setPriority(1);
            th.start();
        }

        @Override
        public boolean cancel() {
            th.interrupt();
            return super.cancel(); //To change body of generated methods, choose Tools | Templates.
        }
        
        
    }
}
