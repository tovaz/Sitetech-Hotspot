/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sitetech.Helpers;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.util.Properties;
import sitetech.hotspot.Modelos.Configuracion;
import sitetech.hotspot.Modelos.ConfiguracionManager2;

/**
 *
 * @author megan
 */
public class mailHelper {
    private String username = "meganetgt@outlook.com";
    private String password = "correr123";
    private static Session session;
    
    public mailHelper(){
        Properties prop = new Properties();
        prop.put("mail.smtp.auth", true);
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", "smtp-mail.outlook.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.ssl.trust", "smtp-mail.outlook.com");
        
        session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
    }
    
    public void enviarCorreo(String titulo, String contenido){
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("meganetgt@outlook.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("williamvaldez@outlook.com"));
            message.setSubject(titulo);

            String msg = contenido;

            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(msg, "text/html");

            MimeBodyPart attachmentBodyPart = new MimeBodyPart();
            attachmentBodyPart.attachFile(new File("pom.xml"));

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);
            multipart.addBodyPart(attachmentBodyPart);

            message.setContent(multipart);

            Transport.send(message);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void enviarCierreCaja(String contenido, File archivo){
        Configuracion conf = ConfiguracionManager2.getConfiguracion(new dbHelper());
       
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("meganetgt@outlook.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("williamvaldez@outlook.com"));
            
            if (conf.getEmpresa().isEmpty())
                message.setSubject("Cierre Caja - SiteTech * HOTSPOT *");
            else
                message.setSubject("Cierre Caja - " + conf.getEmpresa() + " * HOTSPOT *");

            String msg = contenido;

            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(msg, "text/html");
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);
            
            if (archivo.exists()){
                MimeBodyPart attachmentBodyPart = new MimeBodyPart();
                attachmentBodyPart.attachFile(archivo);
                multipart.addBodyPart(attachmentBodyPart);
            }
                
            message.setContent(multipart);
            Transport.send(message);
            System.out.println("Correo enviado con exito.");

        } catch (Exception e) {
            System.out.println(e.getMessage()); 
            e.printStackTrace();
        }
    }
}
