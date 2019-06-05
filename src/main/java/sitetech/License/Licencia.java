package sitetech.License;

import Util.Dialogo;
import java.io.Serializable;
import javafx.scene.control.ButtonType;
import static sitetech.License.CryptoKey.getMD5;

/**
 *
 * @author megan
 */
public class Licencia implements Serializable{
    private String uid;
    private String version="";
    private String nombreApp= "";
    private String mensaje="";
    private String negocio="";
    private String pais= "";
    private java.sql.Timestamp fechaLicencia;
    private java.sql.Timestamp fechaVencimiento;
    private int diasValidos;
    private tipoType tipoLicencia;
    private java.sql.Timestamp fechaCreacion;
    
    private String appCode="abcd123z85=#ABDOZ0345197-+*/?=DAdafdjz,cviuydfpoqewrjkdslapie #";
    
    public enum tipoType { Completa, Prueba, FreeWare };
    
    public Licencia(String uuid) {
        this.uid = uuid;
    }
    
    public Licencia(String uid, boolean encriptado) {
        try {
            uid = uid.toLowerCase().replace("-", "").replace(" ", "");
            uid = AES.decrypt(Convert.HexToString(uid), appCode);
            this.uid = uid;
        }catch (Exception ex){
            this.uid = null;
        }
    }
    
    public Licencia(String uuid, String version, String nombreApp, tipoType tipo, int diasValidos) {
        this.uid = uuid;
        this.version = version;
        this.nombreApp = nombreApp;
        this.tipoLicencia = tipo;
        this.diasValidos = diasValidos;
        this.fechaCreacion = new java.sql.Timestamp(System.currentTimeMillis());
    }

    public String getEncriptedUID() {
        return EncriptarUID(uid);
    }

    public String EncriptarUID(String _uid) {
        String crypted = AES.encrypt(_uid, appCode);
        return format(Convert.StringToHex(crypted)).toUpperCase();
    }
    
    public boolean checkLicencia(String _uid){
        return getEncriptedUID().equals( EncriptarUID(_uid) );
    }

    public String getLicencia(){
        return getMD5(uid + version + nombreApp + diasValidos + tipoLicencia, appCode);
    }
            
    public String getUid() {
        return uid;
    }

    public void setUid(String uuid) {
        this.uid = uid;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getNombreApp() {
        return nombreApp;
    }

    public void setNombreApp(String nombreApp) {
        this.nombreApp = nombreApp;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
    
    public java.sql.Timestamp getFechaLicencia() {
        return fechaLicencia;
    }

    public void setFechaLicencia(java.sql.Timestamp fechaLicencia) {
        this.fechaLicencia = fechaLicencia;
    }

    public java.sql.Timestamp getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(java.sql.Timestamp fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public int getDiasValidos() {
        return diasValidos;
    }

    public void setDiasValidos(int diasValidos) {
        this.diasValidos = diasValidos;
    }

    public tipoType getTipoLicencia() {
        return tipoLicencia;
    }

    public void setTipoLicencia(tipoType tipoLicencia) {
        this.tipoLicencia = tipoLicencia;
    }

    public String getNegocio() {
        return negocio;
    }

    public void setNegocio(String negocio) {
        this.negocio = negocio;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }
    
    public static String format(String cadena){
        String nuevaCadena = "";
        for (int i=0; i<=cadena.length()-1; i++){
            if ((i % 6) == 0 && i>0)
                nuevaCadena += "-";
            nuevaCadena += cadena.charAt(i);
        }
        return nuevaCadena;
    }

    public java.sql.Timestamp getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(java.sql.Timestamp fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
    
    
    
}
