/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 * 
 * Class that contains the different types of tasks an employee ({@link model.models.Usuario})
 * can be asked to perform during his working time ({@link model.models.Turno})
 * <br> <br>
 * Contains an id ({@link id}) that identifies it and a name ({@link nombre})
 * that specifies the function that the worker is going to perform
 * 
 * @author Pedro
 * 
 */
public class Funcion {

    private int id;
    private String nombre;
    
    /**
     *
     * Empty constructor. Makes a new empty {@link model.models.Funcion} object.
     * 
     */
    public Funcion () {
        
        //Empty
        
    }
    
    /**
     * 
     * Detailed constructor. Makes a new {@link model.models.Funcion} object and
     * fills it with the data that's received on the method's call.
     * 
     * @param id        Identifies the function ({@link model.models.Funcion})
     * @param nombre    Name of the function ({@link model.models.Funcion})
     */
    public Funcion (int id,
                      String nombre) {
        
        this.id = id;
        this.nombre = nombre;
        
    }
    
    public int getID () {
        return this.id;
    }
    
    public void setID (int id) {
        this.id = id;
    }
    
    public String getNombre (){
        return this.nombre;
    }
    
    public void setNombre (String nombre) {
        this.nombre = nombre;
    }
    
}