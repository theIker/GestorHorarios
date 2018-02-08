/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestorhorarios.logic;

import com.gestorhorarios.datos.DBManagerHibernate;
import com.gestorhorarios.datos.DataBaseInterface;
import java.util.ArrayList;
import com.gestorhorarios.logic.models.Usuario;
import com.gestorhorarios.logic.models.Solicitud;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

/**
 *
 * Class which has all the methods that are used in this app to send mails. It
 * contains a private method which sends the mails, and some public methods to
 * invoque the private one with data adjusted to the type of mail.
 * 
 * @author Pedro
 * 
 */
public class Mail {
    
    private final Crypto crypto;
    private final DataBaseInterface db;
    
    /**
     * 
     * Empty constructor. Makes a new empty {@link logic.Mail} object and initialize
     * a crypto field used to decrypt the data used to send an email
     * 
     */
    public Mail () {
        
        crypto = new Crypto();
        db = new DBManagerHibernate();
        
        
    }
    
    /**
     * 
     * Method that sends a mail to an employee that has been registered for the first
     * time in the app. It creates a custom message that is sent to the employee's
     * mail, containing the user's Username and Password.
     * 
     * @param usuario       Employee ({@link model.models.Usuario}) that is created 
     * @param contraseña    Password of the employee ({@link model.models.Usuario}),
     *                      this is the only time that the program will handle it
     *                      as a string (besides the password recovery)
     */
    public void sendMailRegistro ( Usuario usuario, String contraseña ) {
        
        String asunto       = "Registro de un nuevo usuario";
        String mensaje      = "Bienvenido a la aplicación de gestión de horarios "
                              + usuario.getNombre() + ". Puede iniciar sesión "
                              + "con los datos mostrados a continuación:\n\n"
                              + "Usuario: " + usuario.getDNI() + "\n"
                              + "Contraseña: " + contraseña + "\n"
                              + "\nEs aconsejable cambiar la contraseña por una "
                              + "que le vaya a ser más fácil recordar, algo que "
                              + "puede realizar desde la aplicación en cualquier "
                              + "momento."
                              + "\nSin embargo, el nombre de usuario será su DNI "
                              + "y este no podrá ser modificado."
                              + "\n\nUn cordial saludo,\nel equipo de gestión";
        String destinatario = usuario.getEmail();
        
        sendMail(asunto, mensaje, destinatario);
        
    }
    
    /**
     * 
     * Method that sends a mail to an employee that has asked for a new password because
     * he forgot the last one. It creates a custom message that is sent to the
     * employee's mail, containing the new automatically generated password.
     * 
     * @param email      Mail of the employee({@link model.models.Usuario}) that
     *                   has asked for the new password
     * @param contraseña Password of the employee ({@link model.models.Usuario}),
     *                   this is the only time that the program will handle it
     *                   as a string (besides when it is generated the first time)
     */
    public void sendMailRecuperación ( String email, String contraseña) {
        
        String asunto       = "Recuperación de contraseña";
        String mensaje      = "Hemos recibido un aviso de que usted ha olvidado "
                              + "su contraseña. El sistema ha generado una nueva "
                              + "que se le mostrará a continuación:\n\n"
                              + "Contraseña: " + contraseña + "\n\n"
                              + "Puede iniciar sesión con la nueva contraseña y "
                              + "su usuario (su DNI) de forma inmediata.\n\n"
                              + "Un cordial saludo,\nel equipo de gestión";
        String destinatario = email;
        
        sendMail( asunto, mensaje, destinatario);
        
    }
    
    /**
     * 
     * Method that sends a mail to two employees when a workday swap that the first
     * employee requested is accepted by the second, and validated by a third one. It
     * sends the mail to both first employees, the ones that want to swap the workday, and gives
     * them the all the information they need to know.
     * 
     * @param solicitud The workday ({@link model.models.Jornada}) that contains all
     *                  the information of the request ({@link model.models.Solicitud})
     */
    public void sendMailResolución ( Solicitud solicitud ) {
        
        try {
            Usuario usuarioSolicita = db.getUsuario(solicitud.getUsuarioSolicita());
            Usuario usuarioAcepta = db.getUsuario(solicitud.getUsuarioAcepta());
            Usuario usuarioValida= db.getUsuario(solicitud.getUsuarioValida());
            
            String asunto       = "Cambio de jornada laboral";
            String mensaje      = "La solicitud de cambio de jornada solicitada por "
                    + usuarioSolicita.getNombre() + " "
                    + usuarioSolicita.getApellido1() + " "
                    + usuarioSolicita.getApellido2() + " "
                    + "fue aceptada por "
                    + usuarioAcepta.getNombre() + " "
                    + usuarioAcepta.getApellido1() + " "
                    + usuarioAcepta.getApellido2() + " "
                    + "revisada por "
                    + usuarioValida.getNombre() + " "
                    + usuarioValida.getApellido1() + " "
                    + usuarioValida.getApellido2() + ".\n\n"
                    + "Jornadas:\n"
                    /*+ "   " + "" + "\n"
                    + "   " + solicitud.getJornadaSolicita().getFecha().toString()
                    + "/" + solicitud.getJornadaSolicita().getTurno().getHoraEntrada()
                    + "-" + solicitud.getJornadaSolicita().getTurno().getHoraSalida() + "\n"
                    + "   " + solicitud.getUsuarioAcepta().getNombre() + "\n"
                    + "   " + solicitud.getJornadaAcepta().getFecha().toString()
                    + "/" + solicitud.getJornadaAcepta().getTurno().getHoraEntrada()
                    + "-" + solicitud.getJornadaAcepta().getTurno().getHoraSalida() + "\n"
                    + "\n El estado de la solicitud es: " + solicitud.getEstado()*/;
            
            
            sendMail( asunto, mensaje, usuarioSolicita.getEmail());
            sendMail( asunto, mensaje, usuarioAcepta.getEmail());
        } catch (Exception ex) {
            Logger.getLogger(Mail.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    /**
     * 
     * Method that sends an email. It just sends and email using the specified data.
     * 
     * @param asunto        Subject of the mail
     * @param mensaje       The message itself
     * @param destinatario  The addressee of the mail
     */
    private void sendMail ( String asunto, String mensaje, String destinatario ) {
        
        ArrayList <String> data = crypto.decryptMailData();
        String user = data.get(0);
        String password = data.get(1);
        
        try {
            
            Email email = new SimpleEmail();
            email.setHostName("smtp.googlemail.com");
            email.setSmtpPort(465);
            DefaultAuthenticator da = new DefaultAuthenticator( user , password );
            email.setAuthenticator(da);
            email.setSSLOnConnect(true);
            email.setFrom(user + "@gmail.com");
            email.setSubject(asunto);
            email.setMsg(mensaje);
            email.addTo(destinatario);
            email.send();
            
        } catch (EmailException ex) {
            
            // Tratar la excepción con un mensaje de error en la app
            
        }
        
    }
    
    
    
}
