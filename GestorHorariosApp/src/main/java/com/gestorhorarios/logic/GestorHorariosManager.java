
package com.gestorhorarios.logic;

import java.util.ArrayList;
import com.gestorhorarios.logic.models.Jornada;
import com.gestorhorarios.logic.models.Solicitud;
import com.gestorhorarios.logic.models.Turno;
import com.gestorhorarios.logic.models.Usuario;
import entity.Jornadas;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author Miguel Axier Lafuente Peñas, Iker Iglesias
 */
public interface GestorHorariosManager {
    public ArrayList<Usuario> getUsuarioByTurnoAndFecha(Jornada jornada);
    public boolean validarEmail(String email);
    public boolean validarPass(String pass);
    public void borrarUsuario(Usuario usuario);
    public void crearUsuario(Usuario usuario,String pass);
    public void validarSolicitud(Usuario usuario, Solicitud solicitud, String estado);
    public void aceptarSolicitud(Usuario usuario, Solicitud solicitud,Jornada jornada);
    public void crearSolicitud(Usuario usuario, Jornada jornada, Jornada acepta);
    public ArrayList<Usuario> getUsuarios();
    public Usuario getUsuario(String dni);
    public void modificarPerfilUsuario(Usuario usuario);
    public void modificarDatos(Usuario usuario);
    public void modificarPass(String dni,String pass);
    public Usuario validarUsuario(String dni, String pass);
    
    public List<Solicitud> getSolicitudesByUsuario(Usuario usuario, String estado);
    
    public ArrayList<Turno> getTurnos();
    public void crearJornada(Usuario usuario,ArrayList<Jornada> jornadas);
    public String getGenPassHash(Usuario usuario);
    public void  encryptMailData();
    public Jornada getJornadaById(int id);
    public ArrayList <Jornada> getJornadasByFecha(Usuario usuario, LocalDate fecha);
    public void setUsuarioLogin(Usuario usuario);
    public Usuario getUsuarioLogin();
    public ArrayList<Jornada>getAllJornadas();
    
    public String getPassHash(String pass);
    public List<Solicitud> getSolicitudesByEstado(String estado);
    
}