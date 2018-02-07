package com.gestorhorarios.datos.entity;
// Generated 31-ene-2018 9:47:29 by Hibernate Tools 4.3.1


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 
 * @author Iker Iglesias
 */
public class Jornadas  implements java.io.Serializable {


     private int id;
     private Turnos turnos;
     private Date fecha;
     private Set usuarioses = new HashSet(0);

    public Jornadas() {
    }

	
    public Jornadas(int id, Turnos turnos) {
        this.id = id;
        this.turnos = turnos;
    }
    public Jornadas(int id, Turnos turnos, Date fecha, Set usuarioses) {
       this.id = id;
       this.turnos = turnos;
       this.fecha = fecha;
      this.usuarioses = usuarioses;
    }
   
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    public Turnos getTurnos() {
        return this.turnos;
    }
    
    public void setTurnos(Turnos turnos) {
        this.turnos = turnos;
    }
    public Date getFecha() {
        return this.fecha;
    }
    
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    
    public Set getUsuarioses() {
        return this.usuarioses;
    }
    
    public void setUsuarioses(Set usuarioses) {
        this.usuarioses = usuarioses;
    }




}


