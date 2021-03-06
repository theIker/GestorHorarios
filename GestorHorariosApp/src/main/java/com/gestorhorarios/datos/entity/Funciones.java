package com.gestorhorarios.datos.entity;
// Generated 31-ene-2018 9:47:29 by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * 
 * @author Iker Iglesias
 */
public class Funciones  implements java.io.Serializable {


     private int id;
     private String funcion;
     private Set turnoses = new HashSet(0);

    public Funciones() {
    }

	
    public Funciones(int id) {
        this.id = id;
    }
    public Funciones(int id, String funcion, Set turnoses) {
       this.id = id;
       this.funcion = funcion;
       this.turnoses = turnoses;
    }
   
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    public String getFuncion() {
        return this.funcion;
    }
    
    public void setFuncion(String funcion) {
        this.funcion = funcion;
    }
    
    public Set getTurnoses() {
        return this.turnoses;
    }
    
    public void setTurnoses(Set turnoses) {
        this.turnoses = turnoses;
    }

}


