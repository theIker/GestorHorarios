/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestorhorarios.logic.models;

import java.util.Collection;

/**
 *
 * Class that contains detailed information of each type of worktime
 * ({@link model.models.Turno}) an employee ({@link model.models.Usuario}) can
 * have assigned
 * <br> <br>
 * Contains an id ({@link id}) that identifies it, the starting hour
 * ({@link horaEntrada}), the finishing hour ({@link horaSalida})and the implied
 * functions that have to be performed({@link model.models.Funcion} in 
 * {@link funciones})
 * 
 * @author Pedro
 * 
 */
public class Turno {

    private String id;
    private String horaEntrada;
    private String horaSalida;
    private Collection <Funcion> funciones;
    
    /**
     * 
     * Empty constructor. Makes a new empty {@link model.models.Turno} object.
     * 
     */
    public Turno () {
        
        //Empty
        
    }
    
    /**
     * 
     * Detailed constructor. Makes a new {@link model.models.Turno} object and
     * fills it with the data that's received on the method's call.
     * 
     * @param id            Identifies the worktime ({@link id})
     * @param horaEntrada   The hour when the employee's ({@link model.models.Usuario})
     *                      worktime ({@link model.models.Turno}) starts
     * @param horaSalida    The hour when the worker's (@link model.models.Usuario}) 
     *                      worktime ({@link model.models.Turno}) finishes
     * @param funciones     The different tasks ({@link model.models.Funcion})
     *                      the employee will be asked to perform during his
     *                      worktime ({@link model.models.Turno})
     * 
     */
    public Turno (String id,
                    String horaEntrada,
                    String horaSalida,
                    Collection <Funcion> funciones) {
        
        this.id = id;
        this.horaEntrada = horaEntrada;
        this.horaSalida = horaSalida;
        this.funciones = funciones;
        
    }
    
    public String getID () {
        return this.id;
    }
    
    public void setID (String id) {
        this.id = id;
    }
    
    public String getHoraEntrada () {
        return this.horaEntrada;
    }
    
    public void setHoraEntrada (String horaEntrada) {
        this.horaEntrada = horaEntrada;
    }
    
    public String getHoraSalida () {
        return this.horaSalida;
    }
    
    public void setHoraSalida (String horaSalida) {
        this.horaSalida = horaSalida;
    }
    
    public Collection <Funcion> getFunciones () {
        return this.funciones;
    }
    
    public void setFunciones (Collection <Funcion> funciones) {
        this.funciones = funciones;
    }
    
    
    @Override
        public String toString() {
            String he=this.horaEntrada.substring(0, 2)+":"+this.horaEntrada.substring(2, 4);
            String hs=this.horaSalida.substring(0,2)+":"+this.horaSalida.substring(2,4);
            
            return this.id.toUpperCase()+" "+he+"-"+hs;
    }
}
