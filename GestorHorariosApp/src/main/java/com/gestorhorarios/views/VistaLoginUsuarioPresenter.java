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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 *
 * @author 2dam
 */
public class VistaLoginUsuarioPresenter {
     @FXML
    private View login;
    @FXML
    private TextField tfNombre;
    @FXML
    private PasswordField pfPass;
    
    
    public void initialize(){
         login.showingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                AppBar appBar = MobileApplication.getInstance().getAppBar();
                appBar.setNavIcon(MaterialDesignIcon.MENU.button(e -> 
                        MobileApplication.getInstance().showLayer(GestorHorarios.MENU_LAYER)));
                appBar.setTitleText("Login");
            }
        });
       
    }
    

}
