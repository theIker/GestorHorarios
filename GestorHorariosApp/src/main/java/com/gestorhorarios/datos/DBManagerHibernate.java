package com.gestorhorarios.datos;

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
import java.util.logging.Logger;

import com.gestorhorarios.logic.models.Funcion;
import com.gestorhorarios.logic.models.Jornada;
import com.gestorhorarios.logic.models.Solicitud;
import com.gestorhorarios.logic.models.Turno;
import com.gestorhorarios.logic.models.Usuario;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 * 
 * @author Iker Iglesias
 */
public class DBManagerHibernate implements DataBaseInterface{
    
    private Session session;
    private static final Logger LOGGER=Logger.getLogger("GestorHorarios");

	private void openConnection() throws Exception {
		SessionFactory sesion=HibernateUtil.getSessionFactory();
		session=sesion.openSession();
	}
	private void closeConnection() throws Exception {
		session.close();
	}

                    
             
        /**
         * Metodo de creacion de un nuevo usuario
         * @param usuario el usuario con todos sus datos
         * @param pass la contraseña generada a ese usuario
         * @throws Exception 
         */
        @Override
        public void crearUsuario(Usuario usuario,String pass) throws Exception{
            
            this.openConnection();
            LOGGER.info("DBManagerHibernate: creando usuario");
            Transaction tx=session.beginTransaction();
            Usuarios u=new Usuarios();
             u.setDni(usuario.getDNI());
             u.setEmail(usuario.getEmail());
             u.setNombre(usuario.getNombre());
             u.setApellido1(usuario.getApellido1());
             u.setApellido2(usuario.getApellido2());
             u.setPerfil(usuario.getPerfil());
             //u.setJornadases(setJornadas(usuario.getJornadas()));
             
             u.setHashPass(pass);
              session.save(u);
	     tx.commit();
             LOGGER.info("DBManagerHibernate: guardando usuario");
            this.closeConnection();
        }
        
        /**
         * Asingna jornadas al nuevo usuario
         * @param jornadas las jornadas 
         * @return jornadas
         */
        private Set<Jornadas> setJornadas(Collection<Jornada> jornadas) {
          Set<Jornadas> j= new HashSet<>();
               Jornadas aux;
               for(Jornada jor:jornadas){
                    aux= new Jornadas();
                    aux.setFecha(jor.getFecha());
                    aux.setId(jor.getID());
                    aux.setTurnos(setTurno(jor.getTurno()));
                    j.add(aux);
               }
               LOGGER.info("DBManagerHibernate: añadiendo jornadas");
          
          return j;
        }
        
        /**
         * Asigna el turno a cada jornada del usuario
         * @param turno el turno
         * @return el turno
         */
        private Turnos setTurno(Turno turno) {
               Turnos tur = new Turnos();
               tur.setHoraEntrada(turno.getHoraEntrada());
               tur.setHoraSalida(turno.getHoraSalida());
               tur.setId(turno.getID());
               tur.setFuncioneses(setFunciones(turno.getFunciones()));
               LOGGER.info("DBManagerHibernate: añadiento turnos");
               return tur;
        }
       
        /**
         * Asigna funciones a cada turno del usuario
         * @param funciones
         * @return las funciones
         */
        private Set<Funciones> setFunciones(Collection<Funcion> funciones) {
           Set <Funciones> fun= new HashSet<>();
            Funciones aux;
               for(Funcion f:funciones){
                   aux=new Funciones();
                   aux.setFuncion(f.getNombre());
                   aux.setId(f.getID());
                   
                   fun.add(aux);
               }
               LOGGER.info("DBManagerHibernate: añadiendo funciones");
               
           return fun;
         }

        
     /**
      * Metodo que valida finalmente el cambio de jornada
      * @param usuario el usuario que valida ese cambio de jornada
      * @param solicitud La solicitud que va a ser validada
      * @param estado el estado final denegada o validada, si es validada la 
      * jornada se cambiara entre los dos usuarios 
      * @throws Exception 
      */
     @Override
    public void validarSolicitud(Usuario usuario, Solicitud solicitud, String estado) throws Exception {
             this.openConnection();
              Transaction tx=session.beginTransaction();
              List  <Solicitudes> result =  session.createQuery("FROM Solicitudes where id="+solicitud.getID()).list();
              Iterator<Solicitudes> l= result.iterator();
             
                   Solicitudes s=l.next();
                    s.setEstado(estado);
                    s.setUsuarioValida(usuario.getDNI());
                    session.update(s);
                    tx.commit();
                    LOGGER.info("DBManagerHibernate: validando solicitud");
              this.closeConnection();
              if(estado.equals("validado"))
                  cambiarJornadaUsuario(solicitud);
    }
    
    /**
     * Cuando se valida la solicitud entra en este metodo que cambia la jornada
     * entre el usuario solicitante y el aceptante
     * @param solicitud se envia la solicitud validada
     * @throws Exception 
     */
     private void cambiarJornadaUsuario(Solicitud solicitud) throws Exception {
             
             this.openConnection();
              Transaction tx=session.beginTransaction();
              
              //usuario solicitante
              List  <Usuarios> result =  session.createQuery("FROM Usuarios where dni="+solicitud.getUsuarioSolicita()).list();
              Iterator<Usuarios> l= result.iterator();
              Usuarios u1=l.next();
              //usuario aceptante
              List  <Usuarios> result2 =  session.createQuery("FROM Usuarios where dni="+solicitud.getUsuarioAcepta()).list();
              Iterator<Usuarios> l2= result2.iterator();
              Usuarios u2=l2.next();
              
             
              List  <Jornadas> result3 =  session.createQuery("FROM Jornadas where id="+solicitud.getJornadaSolicita()).list();
              Iterator<Jornadas> l3= result3.iterator();
              Jornadas j1=l3.next();
              
              List  <Jornadas> result4 =  session.createQuery("FROM Jornadas where id="+solicitud.getJornadaAcepta()).list();
              Iterator<Jornadas> l4= result4.iterator();
              Jornadas j2=l4.next();
              
              //usuario solicitante
              u1.addJornada(j2);
              u1.eliminarJornada(j1);
              session.update(u1);
              //usuario aceptante
              u2.addJornada(j1);
              u2.eliminarJornada(j2);
              session.update(u2);
              
              tx.commit();
              LOGGER.info("DBManagerHibernate: cambiando jornada despues de ser validado");
              
         this.closeConnection();
         
     }
        
   /**
    * Otro usuario ve la solicitud y la acepta a la espera de ser validada
    * @param usuario se envia el usuario que la acepta
    * @param solicitud la solicitud que quiere aceptar
    * @param jornada la jornada que va a intercambiar
    * @throws Exception 
    */ 
    @Override
    public void aceptarSolicitud(Usuario usuario, Solicitud solicitud,Jornada jornada) throws Exception {
            this.openConnection();
              Transaction tx=session.beginTransaction();
              List  <Solicitudes> result =  session.createQuery("FROM Solicitudes where id="+solicitud.getID()).list();
              Iterator<Solicitudes> l= result.iterator();
             
                   Solicitudes s=l.next();
                    s.setEstado("aceptado");
                    s.setUsuarioAcepta(usuario.getDNI());
                    s.setJornadaAcepta(jornada.getID());
                    session.update(s);
                    tx.commit();
                    LOGGER.info("DBManagerHibernate: aceptando solicitud");
              this.closeConnection();
        
    }
        
        /**
         * Metodo para crear una solicitud de cambio de jornada
         * @param usuario el usuario que solicita
         * @param jornada la jornada que se quiere cambiar
         * @throws Exception 
         */
        @Override
        public void crearSolicitud(Usuario usuario, Jornada jornada) throws Exception{
            
            this.openConnection();
            Transaction tx=session.beginTransaction();
            Solicitudes s=new Solicitudes();
             s.setEstado("pendiente");
             s.setJornadaSolicita(jornada.getID());
             s.setUsuarioSolicita(usuario.getDNI());
             session.save(s);
	     tx.commit();
            
            this.closeConnection();
            LOGGER.info("DBManagerHibernate: creando solicitud");
            asignarSolicitudUsuario(s);
        }
        
        /**
         * Tras realizarse esa solicitud, es asignada al usuario que la ha solicitado
         * @param solicitud se envia la solicitud realizada ñpor el usuario
         * @throws Exception 
         */
        private void asignarSolicitudUsuario(Solicitudes solicitud) throws Exception {
             this.openConnection();
              Transaction tx=session.beginTransaction();
              List  <Usuarios> result =  session.createQuery("FROM Usuarios where dni='"+solicitud.getUsuarioSolicita()+"'").list();
              Iterator<Usuarios> l= result.iterator();
             
                   Usuarios u=l.next();
                   u.addSolicitud(solicitud);
                   session.update(u);
                   tx.commit();
                   LOGGER.info("DBManagerHibernate: asignando solicitud al usuario");
              this.closeConnection();
        }
        
      
        
       
       /**
        * Devuelve todos los usuarios
        * @return coleccion de todos los usuarios
        * @throws Exception 
        */ 
        @Override
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
             LOGGER.info("DBManagerHibernate: devolviendo usuarios");
             this.closeConnection();
             return usu;
       }     
             
       /**
        * Devuelve un usuario a partir de su dni
        * @param dni recibe el objeto usuario
        * @return todo el usuario
        * @throws java.lang.Exception
        */ 
       @Override
        public Usuario getUsuario(String dni) throws Exception{
            Usuario usu=new Usuario();
             this.openConnection();
             
              List  <Usuarios> result =  session.createQuery("FROM Usuarios where dni='"+dni+"'").list();
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
              LOGGER.info("DBManagerHibernate: devolviendo usuario por dni");
            return usu;
        }     
             
        
        /**
         * Metodo para cambiar de perfil al usuario :gerente, encargado o empleado
         * sera para accender o degradar a los trabajadores
         * @param usuario recibe el objeto del usuario que quieres modificar
         * @throws Exception 
         */
        @Override
        public void modificarPerfilUsuario(Usuario usuario) throws Exception{
            this.openConnection();
            
              Transaction tx=session.beginTransaction();
              List  <Usuarios> result =  session.createQuery("FROM Usuarios where dni='"+usuario.getDNI()+"'").list();
              Iterator<Usuarios> l= result.iterator();
             
              Usuarios u=l.next();
              
              u.setPerfil(usuario.getPerfil());
              
                session.update(u);
                tx.commit();
                LOGGER.info("DBManagerHibernate: modificando perfil usuario");
            
            this.closeConnection();
        }     
             
             
        /**
         * Metodo que recibe objeto usuario y modifica sus datos email,nombre,apellido1,
         * apellido2. y sus jornadas
         * @param usuario objeto usuario con todos sus datos
         * @throws Exception 
         */
        @Override
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
              u.setPerfil(usuario.getPerfil().toLowerCase());
              System.out.println("eeeeeee<: "+usuario.getPerfil());
              //u.setJornadases(setJornadas(usuario.getJornadas()));
              
                session.update(u);
                tx.commit();
                LOGGER.info("DBManagerHibernate: modificando datos usuario");
            
            this.closeConnection();
            
        }
        /**
         * Metodo que recive objeto usuario y cambia su contraseá
         * @param usuario el usuario
         * @param pass nueva contraseña
         * @throws Exception 
         */
        @Override
        public void modificarPass(Usuario usuario,String pass) throws Exception{
            this.openConnection();
            
              Transaction tx=session.beginTransaction();
              List  <Usuarios> result =  session.createQuery("FROM Usuarios where dni='"+usuario.getDNI()+"'").list();
              Iterator<Usuarios> l= result.iterator();
             
              Usuarios u=l.next();
            
              u.setHashPass(pass);
              session.update(u);
              tx.commit();
              LOGGER.info("DBManagerHibernate: modificando contraseña usuario");
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
        @Override
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
              
              LOGGER.info("DBManagerHibernate: validando existencia del usuario");
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
                LOGGER.info("DBManagerHibernate: devolviendo solicitudes del usuario");
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
                LOGGER.info("DBManagerHibernate: devolviendo jornadas del usuario");
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
            
            LOGGER.info("DBManagerHibernate: devolviendo el turno de las jornadas del usuario");
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
                LOGGER.info("DBManagerHibernate: devolviendo las funciones de cada turno");
		return jor;
        }
    
    /**
     * Se borra un usuario con todas sus jornadas y solicitudes
     * @param usuario el usuario
     * @throws Exception 
     */
    @Override
    public void borrarUsuario(Usuario usuario) throws Exception {
        this.openConnection();
        Transaction tx=session.beginTransaction();
        Usuarios u = (Usuarios) session.get(Usuarios.class, usuario.getDNI());
        session.delete(u);
        
        tx.commit();
        LOGGER.info("DBManagerHibernate: Borrando usuario");
        this.closeConnection();
    }

    /**
     * Devuelve las solicitudes por ser validadas
     * @return las solicitudes por validar
     * @throws Exception 
     */
    @Override
    public ArrayList<Solicitud> getSolicitudesPorValidar() throws Exception {
         
        ArrayList<Solicitud> s=new ArrayList<>();
        Solicitud aux;
        this.openConnection();
        
        List  <Solicitudes> result =  session.createQuery("FROM Solicitudes where estado='aceptado'").list();
        Iterator<Solicitudes> l= result.iterator();
         
        while(l.hasNext()){
              Solicitudes sol=l.next();
              aux= new Solicitud();
              aux.setEstado(sol.getEstado());
              aux.setID(sol.getId());
              aux.setJornadaAcepta(sol.getJornadaAcepta());
              aux.setJornadaSolicita(sol.getJornadaSolicita());
              aux.setUsuarioSolicita(sol.getUsuarioSolicita());
              aux.setUsuarioAcepta(sol.getUsuarioSolicita());
             
              s.add(aux);
        }
        LOGGER.info("DBManagerHibernate: devolviendo solicitudes por validar");
        this.closeConnection();
        
        return s;
    }

    /**
     * Metodo que devuelve todos los turnos con sus funciones
     * @return todos los turnos
     * @throws Exception 
     */
    @Override
    public ArrayList<Turno> getTurnos() throws Exception{
        ArrayList<Turno> turnos = new ArrayList<>();
        
        this.openConnection();
        
         List  <Turnos> result =  session.createQuery("FROM Turnos").list();
        Iterator<Turnos> l= result.iterator();
        Turno aux;
         
        while(l.hasNext()){
              Turnos tur=l.next();
              aux=new Turno();
              aux.setID(tur.getId());
              aux.setHoraEntrada(tur.getHoraEntrada());
              aux.setHoraSalida(tur.getHoraSalida());
              aux.setFunciones(getFunciones(tur.getFuncioneses()));
              turnos.add(aux);
        }
        
        this.closeConnection();
        
        return turnos;
    }

     /**
     * Metodo que crea jornadas
     * @param usuario
     * @param jornadas lista de jornadas
     * @throws Exception 
     */
    @Override
    public void crearJornada(Usuario usuario,ArrayList<Jornada> jornadas) throws Exception {
            this.openConnection();
            LOGGER.info("DBManagerHibernate: creando jornadas");
            
                
            for(int i=0;i<jornadas.size();i++){
                Transaction tx=session.beginTransaction();
                Jornadas j=new Jornadas();
                    j.setFecha(jornadas.get(i).getFecha());
                    j.setTurnos(setTurno(jornadas.get(i).getTurno()));
                   
                       session.save(j);
                     
                       tx.commit();
                     asignarJornada(usuario.getDNI(),j);
            }
            
             
            this.closeConnection();
    }
    
        /**
     * Metodo que asigna jornada a un usuario
     * @param dni dni al que se le asigna
     * @param j jornada que se asigna
     */
    @Override
    public void asignarJornada(String dni, Jornadas j) throws Exception {
        
         Transaction tx=session.beginTransaction();
              List  <Usuarios> result =  session.createQuery("FROM Usuarios where dni='"+dni+"'").list();
              Iterator<Usuarios> l= result.iterator();
                          
                   Usuarios u=l.next();
                   u.addJornada(j);
                   session.update(u);
                   tx.commit();
                   LOGGER.info("DBManagerHibernate: asignando jornada al usuario");
          
    }

    /**
   * Metdo de devolver jornada por ID
   * @param id id de la jornada
   * @return la jornada
   * @throws Exception 
   */
    @Override
    public Jornada getJornadaById(int id) throws Exception {
       Jornada jor= new Jornada();
         this.openConnection();
             List  <Jornadas> result =  session.createQuery("FROM Jornadas where id="+id).list();
              Iterator<Jornadas> l= result.iterator();
             
              Jornadas j=l.next();
              jor.setID(j.getId());
              jor.setFecha(j.getFecha());
              jor.setTurno(getTurno(j.getTurnos()));
              
              LOGGER.info("DBManagerHibernate: asignando jornada al usuario");
         this.closeConnection();
       
       return jor;
    }


    /**
     * Metodo que devuelve todas las jornadas
     * @return Una colección de jordanas
     * @throws Exception 
     */
    @Override
    public ArrayList<Jornada> getAllJornadas() throws Exception {
        ArrayList<Jornada>jors=new ArrayList<>();
        Jornada aux;
        this.openConnection();
         List  <Jornadas> result =  session.createQuery("FROM Jornadas").list();
              Iterator<Jornadas> l= result.iterator();
              while(l.hasNext()){
                  aux=new Jornada();
                  Jornadas j=l.next();
                  aux.setFecha(j.getFecha());
                  aux.setID(j.getId());
                  aux.setTurno(getTurno(j.getTurnos()));
                  jors.add(aux);
              }
              LOGGER.info("DBManagerHibernate: recibiendo todas las jornadas");
        this.closeConnection();
        
        return jors;
    }
    
}