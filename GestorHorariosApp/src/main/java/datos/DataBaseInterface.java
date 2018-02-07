/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datos;


import java.util.ArrayList;
import models.Jornada;
import models.Solicitud;
import models.Usuario;


/**
 *
 * @author Iker Iglesias
 */
public interface DataBaseInterface {               
        public void crearUsuario(Usuario usuario,String pass) throws Exception;
        public Usuario getUsuario(Usuario usuario) throws Exception;
        public ArrayList<Usuario> getUsuarios() throws Exception;
        public void modificarPerfilUsuario(Usuario usuario) throws Exception;
        public void modificarDatos(Usuario usuario) throws Exception;
        public void modificarPass(Usuario usuario,String pass) throws Exception;
        public Usuario validarUsuario(String dni, String pass) throws Exception;
        public void crearSolicitud(Usuario usuario, Jornada jornada) throws Exception;
        public void borrarUsuario(Usuario usuario) throws Exception;
        public void aceptarSolicitud(Usuario usuario, Solicitud solicitud,Jornada jornada) throws Exception;
        public void validarSolicitud(Usuario usuario,Solicitud solicitud,String estado) throws Exception;
        public ArrayList<Solicitud> getSolicitudesPorValidar() throws Exception;			
}