/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestorhorarios.datos;


import java.util.ArrayList;
import com.gestorhorarios.logic.models.Jornada;
import com.gestorhorarios.logic.models.Solicitud;
import com.gestorhorarios.logic.models.Turno;
import com.gestorhorarios.logic.models.Usuario;
import entity.Jornadas;


/**
 *
 * @author Iker Iglesias
 */
public interface DataBaseInterface {               
        public void crearUsuario(Usuario usuario,String pass) throws Exception;
        public Usuario getUsuario(String dni) throws Exception;
        public ArrayList<Usuario> getUsuarios() throws Exception;
        public void modificarPerfilUsuario(Usuario usuario) throws Exception;
        public void modificarDatos(Usuario usuario) throws Exception;
        public void modificarPass(String dni,String pass) throws Exception;
        public Usuario validarUsuario(String dni, String pass) throws Exception;
        public void crearSolicitud(Usuario usuario, Jornada jornada, Jornada acepta) throws Exception;
        public void borrarUsuario(Usuario usuario) throws Exception;
        public void aceptarSolicitud(Usuario usuario, Solicitud solicitud,Jornada jornada) throws Exception;
        public void validarSolicitud(Usuario usuario,Solicitud solicitud,String estado) throws Exception;
        public ArrayList<Solicitud> getSolicitudes() throws Exception;
        public ArrayList<Turno> getTurnos()throws Exception;
        public void crearJornada(Usuario usuario,ArrayList<Jornada> jornadas) throws Exception;
        public Jornada getJornadaById(int id) throws Exception;
        public void asignarJornada(String dni, Jornadas j) throws Exception;
        public ArrayList<Jornada>getAllJornadas() throws Exception;
        public String getDniByEmail(String email) throws Exception;
}