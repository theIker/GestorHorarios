/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestorhorarios.logic;

/**
 *
 * @author Miguel Axier Lafuente Peñas
 */
public class ManagerFactory {
    public static GestorHorariosManager gh;
    public ManagerFactory(){
        gh  = new GestorHorariosManagerImplementation();
    }
}
