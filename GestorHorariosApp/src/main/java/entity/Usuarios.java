package entity;
// Generated 31-ene-2018 9:47:29 by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * 
 * @author Iker Iglesias
 */
public class Usuarios  implements java.io.Serializable {


     private String dni;
     private String email;
     private String nombre;
     private String apellido1;
     private String apellido2;
     private String perfil;
     private String hashPass;
     private Set jornadases = new HashSet(0);
     private Set solicitudeses = new HashSet(0);

    public Usuarios() {
    }

	
    public Usuarios(String dni, String email, String nombre, String apellido1, String apellido2, String perfil, String hashPass) {
       this.dni = dni;
       this.email = email;
       this.nombre = nombre;
       this.apellido1 = apellido1;
       this.apellido2 = apellido2;
       this.perfil = perfil;
       this.hashPass = hashPass;
        
    }
    public Usuarios(String dni, String email, String nombre, String apellido1, String apellido2, String perfil, String hashPass, Set jornadases, Set solicitudeses) {
       this.dni = dni;
       this.email = email;
       this.nombre = nombre;
       this.apellido1 = apellido1;
       this.apellido2 = apellido2;
       this.perfil = perfil;
       this.hashPass = hashPass;
       this.jornadases = jornadases;
       this.solicitudeses = solicitudeses;
    }
   
    public String getDni() {
        return this.dni;
    }
    
    public void setDni(String dni) {
        this.dni = dni;
    }
    public String getEmail() {
        return this.email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    public String getNombre() {
        return this.nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getApellido1() {
        return this.apellido1;
    }
    
    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }
    public String getApellido2() {
        return this.apellido2;
    }
    
    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }
    public String getPerfil() {
        return this.perfil;
    }
    
    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }
    public String getHashPass() {
        return this.hashPass;
    }
    
    public void setHashPass(String hashPass) {
        this.hashPass = hashPass;
    }
    public Set getJornadases() {
        return this.jornadases;
    }
    
    public void setJornadases(Set jornadases) {
        this.jornadases = jornadases;
    }
    public Set getSolicitudeses() {
        return this.solicitudeses;
    }
    
    public void setSolicitudeses(Set solicitudeses) {
        this.solicitudeses = solicitudeses;
    }

    public void addSolicitud(Solicitudes solicitud){
       this.solicitudeses.add(solicitud);
    }
    
    public void addJornada(Jornadas jornada){
        jornadases.add(jornada);
    }
    
    public void eliminarJornada(Jornadas jornada){
        jornadases.remove(jornada);
    }


}


