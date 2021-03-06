package com.gestorhorarios.datos.entity;
// Generated 31-ene-2018 9:47:29 by Hibernate Tools 4.3.1



import java.util.HashSet;
import java.util.Set;

/**
 * 
 * @author Iker Iglesias
 */
public class Solicitudes  implements java.io.Serializable {


     private Integer id;
     private String estado;
     private String usuarioSolicita;
     private String usuarioAcepta;
     private String usuarioValida;
     private Integer jornadaSolicita;
     private Integer jornadaAcepta;
     private Set usuarioses = new HashSet(0);

    public Solicitudes() {
    }

    public Solicitudes(String estado, String usuarioSolicita, String usuarioAcepta, String usuarioValida, Integer jornadaSolicita, Integer jornadaAcepta, Set usuarioses) {
       this.estado = estado;
       this.usuarioSolicita = usuarioSolicita;
       this.usuarioAcepta = usuarioAcepta;
       this.usuarioValida = usuarioValida;
       this.jornadaSolicita = jornadaSolicita;
       this.jornadaAcepta = jornadaAcepta;
       this.usuarioses = usuarioses;
    }
   
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    public String getEstado() {
        return this.estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }
    public String getUsuarioSolicita() {
        return this.usuarioSolicita;
    }
    
    public void setUsuarioSolicita(String usuarioSolicita) {
        this.usuarioSolicita = usuarioSolicita;
    }
    public String getUsuarioAcepta() {
        return this.usuarioAcepta;
    }
    
    public void setUsuarioAcepta(String usuarioAcepta) {
        this.usuarioAcepta = usuarioAcepta;
    }
    public String getUsuarioValida() {
        return this.usuarioValida;
    }
    
    public void setUsuarioValida(String usuarioValida) {
        this.usuarioValida = usuarioValida;
    }
    public Integer getJornadaSolicita() {
        return this.jornadaSolicita;
    }
    
    public void setJornadaSolicita(Integer jornadaSolicita) {
        this.jornadaSolicita = jornadaSolicita;
    }
    public Integer getJornadaAcepta() {
        return this.jornadaAcepta;
    }
    
    public void setJornadaAcepta(Integer jornadaAcepta) {
        this.jornadaAcepta = jornadaAcepta;
    }
    
    
    public Set getUsuarioses() {
        return this.usuarioses;
    }
    
    public void setUsuarioses(Set usuarioses) {
        this.usuarioses = usuarioses;
    }

}


