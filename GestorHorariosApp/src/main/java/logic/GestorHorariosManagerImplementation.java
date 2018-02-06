/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import datos.DBManagerHibernate;
import datos.DataBaseInterface;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Jornada;
import models.Usuario;

/**
 *
 * @author Iker Iglesias
 */
public class GestorHorariosManagerImplementation implements GestorHorariosManager{
    
    private DataBaseInterface db= new DBManagerHibernate() ;
    
    /**
     * Se envia la jornada y aparecen los usuarios con los que es posible
     * intercambiar la jornada
     * @param jornada la jornada que se solicita
     * @return 
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
        }
        } catch (Exception ex) {
            Logger.getLogger(GestorHorariosManagerImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
     
        return u;
    }
    
    
}
