package com.gestorhorarios.datos.entity;
// Generated 31-ene-2018 9:47:29 by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * 
 * @author Iker Iglesias
 */
public class Turnos  implements java.io.Serializable {


     private String id;
     private String horaEntrada;
     private String horaSalida;
     private Set funcioneses = new HashSet(0);
     private Set jornadases = new HashSet(0);

    public Turnos() {
    }

	
    public Turnos(String id, String horaEntrada, String horaSalida) {
       this.id = id;
       this.id = id;
       this.horaEntrada = horaEntrada;
       this.horaSalida = horaSalida;
    }
    public Turnos(String id, String horaEntrada, String horaSalida, Set funcioneses, Set jornadases) {
       this.id = id;
       this.horaEntrada = horaEntrada;
       this.horaSalida = horaSalida;
       this.funcioneses = funcioneses;
       this.jornadases = jornadases;
    }
   
    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    public String getHoraEntrada() {
        return this.horaEntrada;
    }
    
    public void setHoraEntrada(String horaEntrada) {
        this.horaEntrada = horaEntrada;
    }
    public String getHoraSalida() {
        return this.horaSalida;
    }
    
    public void setHoraSalida(String horaSalida) {
        this.horaSalida = horaSalida;
    }
    public Set getFuncioneses() {
        return this.funcioneses;
    }
    
    public void setFuncioneses(Set funcioneses) {
        this.funcioneses = funcioneses;
    }
    
    public Set getJornadases() {
        return this.jornadases;
    }
    
    public void setJornadases(Set jornadases) {
        this.jornadases = jornadases;
    }





}


