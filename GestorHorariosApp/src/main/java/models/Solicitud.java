/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;


/**
 *
 * Class that contains detailed information of each workday
 * ({@link model.models.Jornada}) swap that is requested by the employees
 * ({@link model.models.Usuario})
 * <br><br>
 * Contains an id ({@link id}) that identifies it, the status of the request
 * ({@link estado}), the user that makes the request ({@link usuarioSolicita}),
 * the user that accepts the request ({@link usuarioAcepta} if some does), the
 * user that validates the request ({@link usuarioValida} once someone accepts
 * it) and both the asked workday that is requested to swap and the workday that
 * is swapped with it (if done)
 * 
 * @author Pedro
 * 
 */
public class Solicitud {

    private int id;
    private String estado;
    private String usuarioSolicita;
    private String usuarioAcepta;
    private String usuarioValida;
    private int jornadaSolicita;
    private int jornadaAcepta;
    
    /**
     * 
     * Empty constructor. Makes a new empty {@link model.models.Solicitud}
     * object.
     * 
     */
    public Solicitud () {
       
        //Empty
        
    }
    
    /**
     * 
     * Detailed constructor. Makes a new {@link model.models.Solicitud} object
     * and fills it with the data that's received on the method's call.
     * 
     * @param id                Identifies the request ({@link id})
     * @param estado            The actual status of the request ({@link estado})
     * @param usuarioSolicita   The employee ({@link model.models.Usuario}) that
     *                          asks for the change of workday
     *                          ({@link model.models.Jornada})
     * @param usuarioAcepta     The employee ({@link model.models.Usuario}) that
     *                          accepts the swap of workday
     *                          ({@link model.models.Jornada})
     * @param usuarioValida     The employee ({@link model.models.Usuario}) that
     *                          makes the last validation, letting the
     *                          {@link usuarioSolicita} and the
     *                          {@link usuarioAcepta} swap the workday
     *                          ({@link model.models.Jornada})
     * @param jornadaSolicita   The worktime ({@link jornadaSolicita}) of the employee
     *                          ({@link model.models.Usuario} as {@link usuarioSolicita}
     *                          that asks for a workday swap.
     * @param jornadaAcepta     The worktime ({@link jornadaAcepta}) of the employee
     *                          ({@link model.models.Usuario} as {@link usuarioAcepta}
     *                          that accepts to swap the workday.
     */
    public Solicitud (int id,
                        String estado,
                        String usuarioSolicita,
                        String usuarioAcepta,
                        String usuarioValida,
                        int jornadaSolicita,
                        int jornadaAcepta) {
        
        this.id = id;
        this.estado = estado;
        this.usuarioSolicita = usuarioSolicita;
        this.usuarioAcepta = usuarioAcepta;
        this.usuarioValida = usuarioValida;
        this.jornadaSolicita = jornadaSolicita;
        this.jornadaAcepta = jornadaAcepta;
        
    }
    
    public int getID () {
        return this.id;
    }
    
    public void setID (int id) {
        this.id = id;
    }
    
    public String getEstado () {
        return this.estado;
    }
    
    public void setEstado (String estado) {
        this.estado=estado;
    }
    
    public String getUsuarioSolicita () {
        return this.usuarioSolicita;
    }
    
    public void setUsuarioSolicita (String usuarioSolicita) {
        this.usuarioSolicita = usuarioSolicita;
    }
    
    public String getUsuarioAcepta () {
        return this.usuarioAcepta;
    }
    
    public void setUsuarioAcepta (String usuarioAcepta) {
        this.usuarioAcepta = usuarioAcepta;
    }
    
    public String getUsuarioValida () {
        return this.usuarioValida;
    }
    
    public void setUsuarioValida (String usuarioValida) {
        this.usuarioValida = usuarioValida;
    }
    
    public int getJornadaSolicita () {
        return this.jornadaSolicita;
    }
    
    public void setJornadaSolicita (int jornadaSolicita) {
        this.jornadaSolicita = jornadaSolicita;
    }
    
    public int getJornadaAcepta () {
        return this.jornadaAcepta;
    }
    
    public void setJornadaAcepta (int jornadaAcepta) {
        this.jornadaAcepta = jornadaAcepta;
    }   
    
}
