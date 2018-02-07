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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;


/**
 *
 * @author 2dam
 */
public class AgendaLaboralPresenter {
    
    @FXML
    private View agendaLaboral;
    
    @FXML
    private Label lbVerAgenda;    
    @FXML
    private ComboBox cbNombre;    
    @FXML
    private DatePicker dpFecha;    
    @FXML 
    private Button btnBuscar;    
    @FXML
    private ListView lvAgenda;
    
    
     public void initialize() {
        agendaLaboral.showingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                AppBar appBar = MobileApplication.getInstance().getAppBar();
                appBar.setNavIcon(MaterialDesignIcon.MENU.button(e -> 
                        MobileApplication.getInstance().showLayer(GestorHorarios.MENU_LAYER)));
                appBar.setTitleText("Agenda laboral");
                
                /*appBar.getActionItems().add(MaterialDesignIcon.SEARCH.button(e -> 
                        System.out.println("Search")));*/
            }
        });
        //lvAgenda.setItems();
    }
}