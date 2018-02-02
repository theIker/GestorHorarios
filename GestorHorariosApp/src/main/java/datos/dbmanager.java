package datos;

import entity.Funciones;
import entity.Jornadas;
import entity.Solicitudes;
import entity.Turnos;
import entity.Usuarios;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import models.Funcion;
import models.Jornada;
import models.Solicitud;
import models.Turno;
import models.Usuario;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 * 
 * @author Iker Iglesias
 */



public class dbmanager{

	private Session session;

	public void openConnection() throws Exception {
		SessionFactory sesion=HibernateUtil.getSessionFactory();
		session=sesion.openSession();
	}
	private void closeConnection() throws Exception {
		session.close();
	}
	
	
        
        
            //Metodo para pruebas
             public void prueba() throws Exception{
            this.openConnection();
              
		List<Usuarios> results = session.createQuery("FROM Usuarios where dni='12345678s' and hashPass='1'").list();	
                Iterator<Usuarios> l= results.iterator();
             
                while(l.hasNext()){
                    Usuarios u=l.next();
                    System.out.println(u.getNombre()+" "+u.getDni());
                    
                }
                this.closeConnection();

        }
        
             
             
        /**
         * Metodo de creacion de un nuevo usuario
         * @param usuario el usuario con todos sus datos
         * @param pass la contraseña generada a ese usuario
         * @throws Exception 
         */
        public void crearUsuario(Usuario usuario,String pass) throws Exception{
            
            this.openConnection();
            
            Transaction tx=session.beginTransaction();
            Usuarios u=new Usuarios();
             u.setDni(usuario.getDNI());
             u.setEmail(usuario.getEmail());
             u.setNombre(usuario.getNombre());
             u.setApellido1(usuario.getApellido1());
             u.setApellido2(usuario.getApellido2());
             u.setPerfil(usuario.getPerfil());
             //u.setJornadases((Set) usuario.getJornadas());
             u.setSolicitudeses(setSolicitudes(usuario.getSolicitudes()));
             u.setHashPass(pass);
              session.save(u);
	     tx.commit();
             
            this.closeConnection();
        }
        
        private void crearSolicitud(Usuario usuario, Jornada jornada,String estado) throws Exception{
            
            this.openConnection();
            Transaction tx=session.beginTransaction();
            Solicitudes s=new Solicitudes();
             s.setEstado(estado);
             s.setJornadaSolicita(jornada.getID());
             s.setUsuarioSolicita(usuario.getDNI());
             session.save(s);
	     tx.commit();
            
            this.closeConnection();
            asignarSolicitudUsuario(s);
        }
        
        private void asignarSolicitudUsuario(Solicitudes solicitud) throws Exception {
             this.openConnection();
              Transaction tx=session.beginTransaction();
              List  <Usuarios> result =  session.createQuery("FROM Usuarios where dni='"+solicitud.getUsuarioSolicita()+"'").list();
              Iterator<Usuarios> l= result.iterator();
             
             
              Usuarios u=l.next();
                   u.getSolicitudeses().add(solicitud);
                   session.update(u);
                   tx.commit();
              this.closeConnection();
        }
        
        /**
         * Asigna las solicitudes al crear el usuario
         * @param solicitudes solicitudes de ese usuario
         * @return 
         */
        private Set setSolicitudes(Collection<Solicitud> solicitudes) {
                 Set<Solicitudes> sol=new HashSet<Solicitudes>();;
                 
		 Solicitudes aux;
               
		for(Solicitud s : solicitudes) {
			aux=new Solicitudes();
                        
                        aux.setEstado(s.getEstado());
			//aux.setId(s.getID());
                        aux.setJornadaAcepta(s.getJornadaAcepta());
                        aux.setJornadaSolicita(s.getJornadaSolicita());
                        aux.setUsuarioAcepta(s.getUsuarioAcepta());
                        aux.setUsuarioSolicita(s.getUsuarioSolicita());
                        aux.setUsuarioValida(s.getUsuarioValida());
                        
			sol.add(aux);
		}
                 
                 return sol;
       }
       
       /**
        * Devuelve todos los usuarios
        * @return coleccion de todos los usuarios
        * @throws Exception 
        */      
       public ArrayList<Usuario> getUsuarios() throws Exception{
           
            ArrayList<Usuario>usu=new ArrayList<>();
             Usuario aux;
             this.openConnection();
             List  <Usuarios> result =  session.createQuery("FROM Usuarios").list();
             Iterator<Usuarios> l= result.iterator();
             
             while(l.hasNext()){
                Usuarios u=l.next();
                aux=new Usuario();
                    aux.setDNI(u.getDni());
                    aux.setEmail(u.getEmail());
                    aux.setNombre(u.getNombre());
                    aux.setApellido1(u.getApellido1());
                    aux.setApellido2(u.getApellido2());
                    aux.setJornadas(getJornadas(u.getJornadases()));
                    aux.setSolicitudes(getSolicitudes(u.getSolicitudeses()));
                    aux.setPerfil(u.getPerfil());
                    usu.add(aux);
             
             }
             
             this.closeConnection();
             return usu;
       }     
             
       /**
        * Devuelve un usuario a partir de su dni
        * @param usuario recibe el objeto usuario
        * @return todo el usuario
        */   
        public Usuario getUsuario(Usuario usuario){
            Usuario usu=new Usuario();
            
              List  <Usuarios> result =  session.createQuery("FROM Usuarios where dni='"+usuario.getDNI()+"'").list();
              Iterator<Usuarios> l= result.iterator();
             
              Usuarios u=l.next();
              
              usu.setDNI(u.getDni());
              usu.setEmail(u.getEmail());
              usu.setNombre(u.getNombre());
              usu.setApellido1(u.getApellido1());
              usu.setApellido2(u.getApellido2());
              usu.setJornadas(getJornadas(u.getJornadases()));
              usu.setSolicitudes(getSolicitudes(u.getSolicitudeses()));
              usu.setPerfil(u.getPerfil());
              
              
            return usu;
        }     
             
        
        /**
         * Metodo para cambiar de perfil al usuario :gerente, encargado o empleado
         * sera para accender o degradar a los trabajadores
         * @param usuario recibe el objeto del usuario que quieres modificar
         * @throws Exception 
         */
        public void modificarPerfilUsuario(Usuario usuario) throws Exception{
            this.openConnection();
            
              Transaction tx=session.beginTransaction();
              List  <Usuarios> result =  session.createQuery("FROM Usuarios where dni='"+usuario.getDNI()+"'").list();
              Iterator<Usuarios> l= result.iterator();
             
              Usuarios u=l.next();
              
              u.setPerfil(usuario.getPerfil());
              
                session.update(u);
                tx.commit();
            
            this.closeConnection();
        }     
             
             
        /**
         * Metodo que recibe objeto usuario y modifica sus datos email,nombre,apellido1
         * y apellido2.
         * @param usuario objeto usuario con todos sus datos
         * @throws Exception 
         */
        public void modificarDatos(Usuario usuario) throws Exception{
            
            this.openConnection();
            
              Transaction tx=session.beginTransaction();
              List  <Usuarios> result =  session.createQuery("FROM Usuarios where dni='"+usuario.getDNI()+"'").list();
              Iterator<Usuarios> l= result.iterator();
             
              Usuarios u=l.next();
              
              u.setEmail(usuario.getEmail());
              u.setNombre(usuario.getNombre());
              u.setApellido1(usuario.getApellido1());
              u.setApellido2(usuario.getApellido2());
              
                session.update(u);
                tx.commit();
            
            this.closeConnection();
            
        }
        /**
         * Metodo que recive objeto usuario y cambia su contraseá
         * @param usuario el usuario
         * @param pass nueva contraseña
         * @throws Exception 
         */
        public void modificarPass(Usuario usuario,String pass) throws Exception{
            this.openConnection();
            
              Transaction tx=session.beginTransaction();
              List  <Usuarios> result =  session.createQuery("FROM Usuarios where dni='"+usuario.getDNI()+"'").list();
              Iterator<Usuarios> l= result.iterator();
             
              Usuarios u=l.next();
            
              u.setHashPass(pass);
              session.update(u);
              tx.commit();
              
            this.closeConnection();
        }
        
             
        /**
         * Metodo que comprueba si existe un usuario mediante el DNI y la contraseña
         * se utilizara para el login y recibir todos los datos de dicho usuario
         * @param dni dni de inicio de sesion
         * @param pass pass de inicio de sesion
         * @return devuelve al usuario con todos sus datos
         * @throws Exception 
         */
        public Usuario validarUsuario(String dni, String pass) throws Exception{
           Usuario usu =new Usuario();
            
             this.openConnection();
             
              List  <Usuarios> result =  session.createQuery("FROM Usuarios where dni='"+dni+"' and hashPass='"+pass+"'").list();
              Iterator<Usuarios> l= result.iterator();
             
              Usuarios u=l.next();
              
              usu.setDNI(u.getDni());
              usu.setEmail(u.getEmail());
              usu.setNombre(u.getNombre());
              usu.setApellido1(u.getApellido1());
              usu.setApellido2(u.getApellido2());
              usu.setJornadas(getJornadas(u.getJornadases()));
              usu.setSolicitudes(getSolicitudes(u.getSolicitudeses()));
              usu.setPerfil(u.getPerfil());
            
           this.closeConnection();
           
           return usu;
        }
        
        
        /**
         * Metodo que devuelve todas las solicitudes de un usuario
         * @param solicitudes
         * @return solicitudes
         */
       private ArrayList<Solicitud> getSolicitudes(Set<Solicitudes> solicitudes) {
		
		ArrayList<Solicitud> sol=new ArrayList<>();
		Solicitud aux;
		for(Solicitudes s : solicitudes) {
			aux=new Solicitud();
                        
                        aux.setEstado(s.getEstado());
			aux.setID(s.getId());
                        aux.setJornadaAcepta(s.getJornadaAcepta());
                        aux.setJornadaSolicita(s.getJornadaSolicita());
                        aux.setUsuarioAcepta(s.getUsuarioAcepta());
                        aux.setUsuarioSolicita(s.getUsuarioSolicita());
                        aux.setUsuarioValida(s.getUsuarioValida());
                        
			sol.add(aux);
		}
                
		return sol;
        }
       /**
        * Metodo que devuelve todas las jornadas del usuario
        * @param jornadas 
        * @return jornadas
        */
         private ArrayList<Jornada> getJornadas(Set<Jornadas> jornadas) {
		
		ArrayList<Jornada> jor=new ArrayList<>();
		Jornada aux;
		for(Jornadas j : jornadas) {
			aux= new Jornada();
                        aux.setID(j.getId());
                        aux.setFecha(j.getFecha());
                        aux.setTurno(getTurno(j.getTurnos()));
                        jor.add(aux);
			
		}
                
		return jor;
        }
         
         /**
          * Metodo que devuelve el turno de un usuario a partir de una jornada
          * @param turnos
          * @return turno
          */
        private Turno getTurno(Turnos turnos){
            Turno turno = new Turno();
            
            turno.setHoraEntrada(turnos.getHoraEntrada());
            turno.setHoraSalida(turnos.getHoraSalida());
            turno.setID(turnos.getId());
            turno.setFunciones(getFunciones(turnos.getFuncioneses()));
            
            
            return turno;
        }
        
        /**
         * Metodo que devuelve las funciones de un turno de un usuario
         * @param funciones
         * @return funciones
         */
         private ArrayList<Funcion> getFunciones(Set<Funciones> funciones) {
		
		ArrayList<Funcion> jor=new ArrayList<>();
		Funcion aux;
		for(Funciones f : funciones) {
			aux= new Funcion();
                        aux.setID(f.getId());
                        aux.setNombre(f.getFuncion());
                        jor.add(aux);
			
		}
                
		return jor;
        }

   

  
	
	
}
