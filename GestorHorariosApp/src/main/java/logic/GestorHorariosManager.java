/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.util.ArrayList;
import models.Jornada;
import models.Solicitud;
import models.Usuario;

/**
 *
 * @author Iker Iglesias
 */
public interface GestorHorariosManager {
     public ArrayList<Usuario> getUsuarioByTurnoAndFecha(Jornada jornada);
     public boolean validarEmail(String email);
     public boolean validarPass(String pass);
     public void borrarUsuario(Usuario usuario);
     public void crearUsuario(Usuario usuario,String pass);
     public void validarSolicitud(Usuario usuario, Solicitud solicitud, String estado);
     public void aceptarSolicitud(Usuario usuario, Solicitud solicitud,Jornada jornada);
     public void crearSolicitud(Usuario usuario, Jornada jornada);
     public ArrayList<Usuario> getUsuarios();
     public Usuario getUsuario(Usuario usuario);
     public void modificarPerfilUsuario(Usuario usuario);
     public void modificarDatos(Usuario usuario);
     public void modificarPass(Usuario usuario,String pass);
     public Usuario validarUsuario(String dni, String pass);
     public ArrayList<Solicitud> getSolicitudesPorValidar();
     
}
