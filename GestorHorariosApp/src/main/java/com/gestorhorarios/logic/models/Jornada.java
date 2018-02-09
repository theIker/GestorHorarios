/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestorhorarios.logic.models;

import com.gluonhq.impl.charm.a.b.b.f;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 *
 * Class that contains the relation between the workdays
 * ({@link model.models.Jornada}) and it's worktimes ({@link model.models.Turno})
 * <br><br>
 * Contains an id ({@link id}) that identifies it, the date of the workday
 * ({@link fecha}) and one worktime {@link model.models.Turno} that happens that
 * workday
 * 
 * @author Pedro
 * 
 */
public class Jornada {

    private int id;
    private Date fecha;
    private Turno turno;
    
    /**
     * 
     * Empty constructor. Makes a new empty {@link model.models.Jornada} object.
     * 
     */
    public Jornada () {
        
        //Empty
        
    }
    
    /**
     * 
     * Detailed constructor. Makes a new {@link model.models.Jornada} object and
     * fills it with the data that's received on the method's call.
     * 
     * @param id        Identifies the workday ({@link model.models.Jornada})
     * @param fecha     The date of the workday ({@link model.models.Jornada})
     * @param turno     The worktime ({@link model.models.Turno}) that will have
     *                  to be done in the specified date
     */
    public Jornada (int id,
                      Date fecha,
                      Turno turno) {
        
        this.id = id;
        this.fecha = fecha;
        this.turno = turno;
        
    }

    
    
    public int getID () {
        return this.id;
    }
    
    public void setID (int id) {
        this.id = id;
    }
    
    public Date getFecha () {
        return this.fecha;
    }
    
    public void setFecha (Date fecha) {
        this.fecha = fecha;
    }
    
    public Turno getTurno () {
        return this.turno;
    }
    
    public void setTurno (Turno turno) {
        this.turno = turno;
    }
    
    public int compare (Jornada j1, Jornada j2) {
        
        int comparation;
        
        if (j1.getFecha().before(j2.getFecha())) {
            
            comparation = 1;
            
        } else if (j1.getFecha().after(j2.getFecha())) {
            
            comparation = -1;
            
        } else {
            
            comparation = 0;
            
        }
        
        return comparation;
        
    }
    
    @Override
    public String toString() {
      
        String data = this.fecha + "\n";
        
        Collection <Funcion> fs= this.getTurno().getFunciones();
        
        for (Funcion f : fs ) {
            data += f.getNombre() + ",";
        }
        data += "\n";
        data += this.turno.getHoraEntrada() + " - " + this.turno.getHoraSalida();
        
        return data;
        
    }
    
}
