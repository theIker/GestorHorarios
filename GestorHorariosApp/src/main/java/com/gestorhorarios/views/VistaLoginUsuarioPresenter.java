/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestorhorarios.views;

import com.gestorhorarios.GestorHorarios;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.control.AppBar;

import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 *
 * @author Miguel Axier Lafuente Peñas
 */
public class VistaLoginUsuarioPresenter {
    
    
    @FXML
    private View login;
    @FXML
    private TextField tfNombre;
    @FXML
    private PasswordField pfPass;
    @FXML
    private Button btnEntra;
    @FXML
    private Hyperlink recPass;
    
    public void initialize(){
        
       
    }
    @FXML
    public void handleOnActionEntrar(){
        
    }

}
