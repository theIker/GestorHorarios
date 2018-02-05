/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.util.ArrayList;
import models.Jornada;
import models.Usuario;

/**
 *
 * @author Iker Iglesias
 */
public interface LogicInterface {
     public ArrayList<Usuario> getUsuarioByTurnoAndFecha(Jornada jornada);
}
