package datos;

import entity.Funciones;
import entity.Jornadas;
import entity.Solicitudes;
import entity.Turnos;
import entity.Usuarios;

import enumerations.EstadoSolicitud;
import java.util.ArrayList;
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


//HOLA


public class dbmanager{

	private Session session;

	public void openConnection() throws Exception {
		SessionFactory sesion=HibernateUtil.getSessionFactory();
		session=sesion.openSession();
	}
	private void closeConnection() throws Exception {
		session.close();
	}
	//////////////////////////////////////////////////
	
        /**
         * e
         * @param dni
         * @param pass
         * @return
         * @throws Exception 
         */
        public Usuario validarUsuario(String dni, String pass) throws Exception{
           Usuario usu = null;
            
           this.openConnection();
              Usuarios u = (Usuarios) session.createQuery("FROM Usuarios where dni='"+dni+"' and hashPass='"+pass+"'");	
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
        
        
        public void prueba() throws Exception{
            this.openConnection();
              
		List<Usuarios> results = session.createQuery("FROM Usuarios").list();	
                Iterator<Usuarios> l= results.iterator();
             
                while(l.hasNext()){
                    Usuarios u=l.next();
                    System.out.println(u.getNombre());
                    
                }
                this.closeConnection();

        }
        
        
        
        public void getUsu() throws Exception{
            this.openConnection();
           
           
             List<Usuarios> employeeList = session.createQuery("FROM Usuarios").list();
               for(int i=0;i<employeeList.size();i++){
                   System.out.println(employeeList.get(i).getNombre()+" "+employeeList.get(i).getApellido1()+" "+employeeList.get(i).getApellido2());
                    getSolicitudes(employeeList.get(i).getSolicitudeses());
                 
               }
               System.out.println("Hola");
            this.closeConnection();
        }
        
        /**
         * 
         * @param solicitudes
         * @return 
         */
       private ArrayList<Solicitud> getSolicitudes(Set<Solicitudes> solicitudes) {
		
		ArrayList<Solicitud> sol=new ArrayList<>();
		Solicitud aux;
		for(Solicitudes s : solicitudes) {
			aux=new Solicitud();
                        s.getEstado();
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
        * 
        * @param jornadas
        * @return 
        */
         private ArrayList<Jornada> getJornadas(Set<Jornadas> jornadas) {
		
		ArrayList<Jornada> jor=new ArrayList<>();
		Jornada aux;
		for(Jornadas j : jornadas) {
			aux= new Jornada();
                        aux.setID(j.getId());
                        aux.setFecha(j.getFecha());
                        aux.setTurno(getTurno(j.getTurnos()));
			
		}
                
		return jor;
        }
         
        private Turno getTurno(Turnos turnos){
            Turno turno = null;
            
            turno.setHoraEntrada(turnos.getHoraEntrada());
            turno.setHoraSalida(turnos.getHoraSalida());
            turno.setID(turnos.getId());
            turno.setFunciones(getFunciones(turnos.getFuncioneses()));
            
            
            return turno;
        }
        
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
