/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestorhorarios.logic;

import com.gestorhorarios.datos.DBManagerHibernate;
import com.gestorhorarios.datos.DataBaseInterface;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.gestorhorarios.logic.models.Jornada;
import com.gestorhorarios.logic.models.Solicitud;
import com.gestorhorarios.logic.models.Turno;
import com.gestorhorarios.logic.models.Usuario;
import entity.Jornadas;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Iker Iglesias
 */
public class GestorHorariosManagerImplementation implements GestorHorariosManager{
   private static Usuario usuario = new Usuario();
   
   private static DataBaseInterface db= new DBManagerHibernate();
   private static Crypto cp= new Crypto();
   private static Mail mail= new Mail();
   private static final Logger LOGGER=Logger.getLogger("GestorHorarios");
   
   public GestorHorariosManagerImplementation(){     
       cp.encryptMailData();
   }
   
   @Override
   public void setUsuarioLogin(Usuario usuario){
      this.usuario = usuario;
   }
   @Override
   public Usuario getUsuarioLogin(){
       return usuario;
   }
    /**
     * Se envia la jornada y aparecen los usuarios con los que es posible
     * intercambiar la jornada
     * @param jornada la jornada que se solicita
     * @return arraylist de usuarios
     */
    @Override
    public ArrayList<Usuario> getUsuarioByTurnoAndFecha(Jornada jornada) {
        ArrayList<Usuario> usuarios= new ArrayList<>();
        ArrayList<Usuario> u= new ArrayList<>();
        
        try {
            usuarios=db.getUsuarios();
            
             for(int i=0;i<usuarios.size();i++){
                ArrayList<Jornada> jor=new ArrayList<>();
                jor=(ArrayList<Jornada>) usuarios.get(i).getJornadas();
                    for(int j=0;j<jor.size();j++){

                         if(jornada.getTurno().getID().contains(String.valueOf(jor.get(j).getTurno().getID().charAt(0)))
                                 && jornada.getFecha()==jor.get(j).getFecha()){
                             
                                        u.add(usuarios.get(i));
                         }
                    }
                    LOGGER.info("GestorHorariosManagerImplementation: añadiendo usuarios que cumplan el turno de esa jornada");
        }
        } catch (Exception ex) {
            LOGGER.severe("GestorHorariosManagerImplementation: error al añadir los usuarios");
            u=null;
        }
     
        return u;
    }
   
    /**
     * Se envia el email para comprobar si esta bien esrito
     * @param email el email para comprobar
     * @return true si esta bien y false si esta mal
     */
    @Override
    public boolean validarEmail(String email) {
       boolean e=false;
       
        if(email.matches("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\"
                + "x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@("
                + "?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-"
                + "5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:("
                + "?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])"))
            
                            e=true;
       LOGGER.info("GestorHorariosManagerImplementation: validando email");
       return e;
    }
    
    /**
     * Se valida la contraseña con todos estos criterios
     * -La longitud de la contraseña tiene que ser 8 o mas
     * -La contraseña tiene que tener una mayuscula o mas
     * -La contraseña tiene que tener una minuscula o mas
     * -La contraseña tiene que tener un numero o mas
     * -La contraseña tiene que tener un caracter especial
     * @param pass la contraseña
     * @return true si es valida y false si no es valida
     */
    @Override
    public boolean validarPass(String pass) {
          boolean e=false;
           if(pass.matches("(?=^.{8,}$)(?=.*\\d)(?=.*[!@#$%^&*]+)(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$"))
               
                  e=true;
           
           LOGGER.info("GestorHorariosManagerImplementation: validando contraseña");
          return e;
    }
  
    /**
     * Borra el usuario de la base de datos
     * @param usuario se envia el usuario
     */
    @Override
    public void borrarUsuario(Usuario usuario) {
          
        try {
            db.borrarUsuario(usuario);
            LOGGER.info("GestorHorariosManagerImplementation: usuario borrado");
        } catch (Exception ex) {
            LOGGER.severe("GestorHorariosManagerImplementation: error al borrar usuario");
        }
    }
  
    /**
     * Recibe el Usuario y la contraseña y crea al nuevo usuario
     * @param usuario usuario con sus datos 
     * @param pass contraseña encriptada
     * @return 
     */
    @Override
    public void crearUsuario(Usuario usuario, String pass) {
        try {
            db.crearUsuario(usuario, pass);
            LOGGER.info("GestorHorariosManagerImplementation: usuario creado");
        } catch (Exception ex) {
           
            LOGGER.severe("GestorHorariosManagerImplementation: error al crear usuario");
        }    
    }
   
    /**
     * Valida el cambio de jornada de dos usuarios, en el caso de validado
     * se cambia de jornada
     * @param usuario el usuario que valida
     * @param solicitud la solicitud que va a validar
     * @param estado si la pone en validado o denegrado
     */
    @Override
    public void validarSolicitud(Usuario usuario, Solicitud solicitud, String estado) {
         
        try {
            db.validarSolicitud(usuario, solicitud, estado);
            LOGGER.info("GestorHorariosManagerImplementation: solicitud validada");
        } catch (Exception ex) {
            LOGGER.severe("GestorHorariosManagerImplementation: error al validar solicitud");
        }
    }
   
    /**
     * Metodo que acepta la solicitud
     * @param usuario el usuario que acepta
     * @param solicitud la solicitud que acepta
     * @param jornada la jornada que se va a intercambiar
     */
    @Override
    public void aceptarSolicitud(Usuario usuario, Solicitud solicitud, Jornada jornada) {
          
        try {
            db.aceptarSolicitud(usuario, solicitud, jornada);
            LOGGER.info("GestorHorariosManagerImplementation: solicitud aceptada");
        } catch (Exception ex) {
             LOGGER.severe("GestorHorariosManagerImplementation: error al aceptar solicitud");
        }
        
    }
   /**
    * Se crea la solicitud de intercambio de jornada, y se le asigna la solicitud
    * al usuario
    * @param usuario el usuario
    * @param jornada  la jornada que se quiere intercambiar
    */
    @Override
    public void crearSolicitud(Usuario usuario, Jornada jornada) {
        
        try {
            db.crearSolicitud(usuario, jornada);
            LOGGER.info("GestorHorariosManagerImplementation: solicitud creada");
        } catch (Exception ex) {
             LOGGER.severe("GestorHorariosManagerImplementation: error al crear solicitud");
        }
        
    }
     
   /**
    * Metodo que devuelve todos los usuarios existentes empleados, encargados y gerente
    * @return todos los usuarios
    */
    @Override
    public ArrayList<Usuario> getUsuarios() {
       ArrayList<Usuario> u = new ArrayList<>();
       
        try {
            u=db.getUsuarios();
            LOGGER.info("GestorHorariosManagerImplementation: recibiendo todos los usuarios");
        } catch (Exception ex) {
            LOGGER.severe("GestorHorariosManagerImplementation: error al recibir todos los usuarios");
            u=null;
        }
        
       return u;
    }
   
    /**
     * Metodo que devuelve al usuario que se seleciona mediante el dni
     * @param usuario el usuario
     * @return el usuario
     */
    @Override
    public Usuario getUsuario(String dni) {
         Usuario u=new Usuario();
         
        try {
            u=db.getUsuario(dni);
            LOGGER.info("GestorHorariosManagerImplementation: recibiendo al usuario");
        } catch (Exception ex) {
            LOGGER.severe("GestorHorariosManagerImplementation: error al recibir el usuario");
            u=null;
        }
        return u;
    }
    /**
     * Metodo que cambi de perfil al usuario para ser empleado o encargado
     * @param usuario el usuario que se quiere modificar
     */
    @Override
    public void modificarPerfilUsuario(Usuario usuario) {
        
        try {
            db.modificarPerfilUsuario(usuario);
            LOGGER.info("GestorHorariosManagerImplementation: modificando perfil usuario");
        } catch (Exception ex) {
            LOGGER.severe("GestorHorariosManagerImplementation: error al modificar perfil usuario");
        }
    }

    /**
     * Se envia el usuario y se modifican sus datos, y sus jornadas
     * @param usuario se envia el usuario
     */
    @Override
    public void modificarDatos(Usuario usuario) {
         
        try {
            db.modificarDatos(usuario);
            LOGGER.info("GestorHorariosManagerImplementation: modificando usuario");
        } catch (Exception ex) {
            LOGGER.severe("GestorHorariosManagerImplementation: error al modificar usuario");
        }
    }

    
    /**
     * Metodo para cambiar la contraseña al usuario
     * @param usuario se envia el usuario
     * @param pass la nueva contraseña
     */
    @Override
    public void modificarPass(String dni, String pass) {
        
        try {
            db.modificarPass(dni, pass);
            LOGGER.info("GestorHorariosManagerImplementation: modificando password usuario");
        } catch (Exception ex) {
            LOGGER.severe("GestorHorariosManagerImplementation: error al modificar password usuario");
        }
    }
    
    /**
     *  Metodo para iniciar session y que te devuelva el usuario con todos sus datos
     * @param dni el dni 
     * @param pass la contraseña
     * @return el usuario si  existe con esa pass y dni
     */
    @Override
    public Usuario validarUsuario(String dni, String pass) {
         Usuario usuario= new Usuario();
         
        try {
            usuario=db.validarUsuario(dni, pass);
            LOGGER.info("GestorHorariosManagerImplementation: validando usuario");
        } catch (Exception ex) {
            LOGGER.severe("GestorHorariosManagerImplementation: error validar usuario");
            usuario=null;
        }
         return usuario;
    } 
    /**
     * Metodo que devuelve una lista de solicitudes segun el usuario y si son solicitudes enviadas, recibidas o pendientes de validar
     * @param usuario Usuario que consulta las solicitudes
     * @param estado Estado de la solicitud (enviado, recibido o null)
     * @return una lista de las solicitudes que cumplen con los criterios de busqueda
     */
    @Override
    public List<Solicitud> getSolicitudesByUsuario(Usuario usuario, String estado) {
        List<Solicitud> solicitudes;
        List<Jornada> jornadas = new ArrayList();
        List<Solicitud> solicitudesAux = new ArrayList();
        try {
            solicitudes = db.getSolicitudes();
            
            //Peticiones de cambio de turno que lanza el usuario
            if(estado.equals("enviado")){
                solicitudes = solicitudes.stream()
                            .filter(s -> s.getUsuarioSolicita().equals(usuario.getDNI()))
                            .filter(s -> !s.getEstado().equals("validado"))
                            .map(s -> s).collect(Collectors.toList());
                
            //Peticiones de cambio de turno que lanza cualquier otro usuario y son compatibles para cambiar por el usuario login
            } else if (estado.equals("recibido")) {
                  solicitudes = solicitudes.stream()
                                .filter(s -> s.getUsuarioAcepta().equals(usuario.getDNI()))
                          .map(s -> s).collect(Collectors.toList());
//                solicitudes.forEach((solicitud)-> {
//                    usuario.getJornadas().forEach((jornada) ->{
//                        //Comprobamos que sea la misma fecha
//                        if(jornada.getFecha()==getJornadaById(solicitud.getJornadaSolicita()).getFecha()){
//                            //Comprobamos que la letra del turno sea la misma. Ej: Turno A1 y A2 --> A==A
//                            if(jornada.getTurno().getID().substring(0, 1).equals(getJornadaById(solicitud.getJornadaSolicita()).getTurno().getID().substring(0, 1))){
//                                //Comprobamos que el numero del turno sea diferente(para poder cambiar el horario) Ej: Turno A1 y A2 --> 1!=2
//                                if(jornada.getTurno().getID().substring(1).compareTo(getJornadaById(solicitud.getJornadaSolicita()).getTurno().getID().substring(1))!=0){
//                                    solicitudesAux.add(solicitud);
//                                }
//                            }
//                        }
//                    });
//                });
          
                
            //Solicitudes que han sido aceptadas por dos empleados y que estan pendientes de validar por un encargado o gerente
            } else {
                solicitudes = solicitudes.stream()
                            .filter(s -> s.getEstado().equals("pendiente"))
                            .map(s -> s).collect(Collectors.toList());
            }
            
                    
             //LOGGER.info("GestorHorariosManagerImplementation: devolviendo solicitudes por validar");
        } catch (Exception ex) {
            LOGGER.severe("GestorHorariosManagerImplementation: error recibir solicitudes por validar");
            solicitudes=null;
        }    
        return solicitudes;
    }
    
    @Override
    public ArrayList <Jornada> getJornadasByFecha(Usuario usuario, LocalDate fecha) {
        ArrayList <Jornada> jornadas = new ArrayList<>();
        for(Jornada j : usuario.getJornadas()){
            //TODO
                
            }
        return null;
        }

    /**
     * Metodo que devuelve todos los turnos con sus funciones
     * @return Una coleccion con todos los turnos
     */
    @Override
    public ArrayList<Turno> getTurnos() {
        ArrayList<Turno> turno;
        
        try {
            turno=db.getTurnos();
             LOGGER.info("GestorHorariosManagerImplementation: devolviendo turnos");
        } catch (Exception ex) {
            LOGGER.severe("GestorHorariosManagerImplementation: error recibir turnos");
            turno=null;
        }    
        
        return turno;
    }
    /**
     * Metodo que llama al DBManager para crear jornadas de trabajo asignadas a un usuario
     * @param usuario Usuario que tiene la jornada
     * @param jornadas Una colección de jornadas para crear
     */
    @Override
    public void crearJornada(Usuario usuario,ArrayList<Jornada> jornadas) {
       try {
           db.crearJornada(usuario,jornadas);
            LOGGER.info("GestorHorariosManagerImplementation: creando jornadas");
       } catch (Exception ex) {
           LOGGER.severe("GestorHorariosManagerImplementation: error al crear jornadas");
       }
    }

    /**
     * Metodo que genera una contraseña aleatoria y la encripta
     * @param usuario el usuario
     * @return has contraseña
     */
    @Override
    public String getGenPassHash(Usuario usuario) {
         String hashPass,pass;
         try{
         pass=cp.generateAutomaticPassword();
         hashPass=cp.getPasswordHash(pass);
          mail.sendMailRegistro(usuario, pass);
          LOGGER.info("GestorHorariosManagerImplementation: guardando contrasña generada y encryptada");
         }catch(Exception ex){
            LOGGER.severe("GestorHorariosManagerImplementation: error al guardar contrasña generada y encryptada");
             hashPass=null;
         }
        return hashPass;
    }

    @Override
    public void encryptMailData() {
        cp.encryptMailData();
    }

    @Override
    public Jornada getJornadaById(int id) {
        Jornada jornada;
       try {
            jornada=db.getJornadaById(id);
            LOGGER.info("GestorHorariosManagerImplementation: recibiendo jornada");
       } catch (Exception ex) {
           LOGGER.severe("GestorHorariosManagerImplementation: error al recibir jornada");
           jornada=null;
       }
        return jornada;
    }
    
    /**
     * MEtodo que devuelve todas las jornadas
     * @return Colección de jornadas
     */
    @Override
    public ArrayList<Jornada> getAllJornadas() {
        ArrayList<Jornada> jor=new ArrayList<>();
         try{
             jor=db.getAllJornadas();
             LOGGER.info("GestorHorariosManagerImplementation: recibiendo todas las jornadas");
         }catch(Exception ex){
             LOGGER.severe("GestorHorariosManagerImplementation: error al recibir todas las jornadas");
             jor=null;
         }
        
        return jor;
    }
    /**
     * Metodo que llama a la base de datos y devuelve una colección de las solicitudes
     * filtradas por estado
     * @param estado Estado de la solicitud
     * @return Colección de solicitudes filtradas por estado
     */
    @Override
    public List<Solicitud> getSolicitudesByEstado(String estado) {
        List<Solicitud> solicitudes = new ArrayList<>();
       try {
           LOGGER.info("GestorHorariosManagerImplementation: recibiendo todas las solicitudes");
           solicitudes = db.getSolicitudes();
           if(estado != null){
               solicitudes = solicitudes.stream()
                       .filter(s -> s.getEstado().equals(estado))
                       .map(s -> s).collect(Collectors.toList());
               
           }
       } catch (Exception ex) {
           Logger.getLogger(GestorHorariosManagerImplementation.class.getName()).log(Level.SEVERE, null, ex);
           LOGGER.info("GestorHorariosManagerImplementation: error recibiendo todas las jornadas");
       }
       return solicitudes;
    }
    /**
     * 
     * @param pass
     * @return 
     */
    @Override
    public String getPassHash(String pass) {
        
        String hash;
        try{
            hash=cp.getPasswordHash(pass);
            LOGGER.info("GestorHorariosManagerImplementation: hash generado");
        }catch(Exception ex){
            LOGGER.info("GestorHorariosManagerImplementation: error al generar hash");
            hash=null;
        }
        
        return hash;
    }
         
        
}