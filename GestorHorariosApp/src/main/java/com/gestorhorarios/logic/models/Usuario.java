/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestorhorarios.logic.models;

import java.util.Collection;


/**
 *
 * Class that contains detailed information of each employee
 * ({@link model.models.Usuario})
 * <br><br>
 * Contains a dni ({@link dni}) that identifies it, the name of the employee with
 * it's surnames({@link nombre} {@link apellido1} {@link apellido2}), the profile
 * of the employee, it's workdays ({@link jornadas}) and the request of workday
 * swapping ({@link solicitudes})
 * @author Pedro
 * 
 */
public class Usuario {
    
    private String dni;
    private String email;
    private String nombre;
    private String apellido1;
    private String apellido2;
    private String perfil;
    private Collection <Jornada> jornadas;
    private Collection <Solicitud> solicitudes;
    
    /**
     * 
     * Empty constructor. Makes a new empty {@link model.models.Usuario} object.
     * 
     */
    public Usuario () {
        
        //Empty
        
    }
    
    /**
     * 
     * Detailed constructor. Makes a new {@link model.models.Usuario} and fills
     * it with the data that's received on the method's call
     * 
     * @param dni           Identifies the employee {@link model.models.Usuario}
     * @param email         The personal mailing account of the employee
     * @param nombre        The name of the employee
     * @param apellido1     The first surname of the employee
     * @param apellido2     The second surname of the employee
     * @param perfil        The profile of the employee
     * @param jornadas      The workdays ({@link model.models.Jornada}) with the
     *                      worktimes ({@link model.models.Turno}) of the
     *                      employee ({@link model.models.Usuario})
     * @param solicitudes   The requests for workday ({@link model.models.Jornada}
     *                      swapping ({@link model.models.Solicitud})
     */
    public Usuario (String dni,
                      String email,
                      String nombre,
                      String apellido1,
                      String apellido2,
                      String perfil,
                      Collection <Jornada> jornadas,
                      Collection <Solicitud> solicitudes) {
        
        this.dni = dni;
        this.email = email;
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.perfil = perfil;
        this.jornadas = jornadas;
        this.solicitudes = solicitudes;
        
    }
    
    public String getDNI () {
        return this.dni;
    }
    
    public void setDNI (String dni) {
        this.dni = dni;
    }
    
    public String getEmail () {
        return this.email;
    }
    
    public void setEmail (String email) {
        this.email = email;
    }
    
    public String getNombre () {
        return this.nombre;
    }
    
    public void setNombre (String nombre) {
        this.nombre = nombre;
    }
    
    public String getApellido1 () {
        return this.apellido1;
    }
    
    public void setApellido1 (String apellido1) {
        this.apellido1 = apellido1;
    }
    
    public String getApellido2 () {
        return this.apellido2;
    }
    
    public void setApellido2 (String apellido2) {
        this.apellido2 = apellido2;
    }
    
    public String getPerfil () {
        return this.perfil;
    }
    
    public void setPerfil (String perfil) {
        
        this.perfil=perfil;
    }
    
    public Collection <Jornada> getJornadas () {
        return this.jornadas;
    }
     
    public void setJornadas (Collection <Jornada> jornadas) {
        this.jornadas = jornadas;
    }
    
    public Collection <Solicitud> getSolicitudes () {
        return this.solicitudes;
    }
    
    public void setSolicitudes (Collection <Solicitud> solicitudes) {
        this.solicitudes = solicitudes;
    }
    
}
