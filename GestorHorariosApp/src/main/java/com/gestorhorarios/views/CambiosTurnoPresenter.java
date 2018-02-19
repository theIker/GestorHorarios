/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestorhorarios.views;

import com.gestorhorarios.GestorHorarios;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.control.Dialog;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

/**
 *
 * @author 2dam
 */
public class CambiosTurnoPresenter {
    
    @FXML
    private View cambiosTurno;
    @FXML
    private ComboBox cbSolicitudes;
    @FXML
    private Button btnBuscar;
    @FXML
    private ListView lvSolicitudes;
    
            
    
    public void initialize() {
        cambiosTurno.showingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                AppBar appBar = MobileApplication.getInstance().getAppBar();
                appBar.setNavIcon(MaterialDesignIcon.MENU.button(e -> 
                        MobileApplication.getInstance().showLayer(GestorHorarios.MENU_LAYER)));
                appBar.setTitleText("Cambios de turno");
                
                /*appBar.getActionItems().add(MaterialDesignIcon.SEARCH.button(e -> 
                        System.out.println("Search")));*/
            }
        });
    
    }
    
    public void dialogPendienteAceptar(){
        Dialog dialog = new Dialog();
            dialog.setTitle(new Label("Estado de la solicitud"));
            dialog.setContent(new Label("El compañero aún no ha revisado su solicitud"));
            Button okButton = new Button("Acpetar");
            okButton.setOnAction(e -> {
                dialog.hide();
            });
            dialog.getButtons().add(okButton);
            dialog.showAndWait();
    }
    public void dialogPendienteValidar(){
        Dialog dialog = new Dialog();
            dialog.setTitle(new Label("Estado de la solicitud"));
            dialog.setContent(new Label("Pendiente de validar por el encargado."));
            Button okButton = new Button("Acpetar");
            okButton.setOnAction(e -> {
                dialog.hide();
            });
            dialog.getButtons().add(okButton);
            dialog.showAndWait();
    }
    public void dialogAceptarSolicitud(){
        Dialog dialog = new Dialog();
            dialog.setTitle(new Label("Aceptar solicitud"));
            dialog.setContent(new Label("No se podrá deshacer la operación. ¿Estas seguro?"));
            Button okButton = new Button("Acpetar");
            Button cancelButton = new Button("Cancelar");
            okButton.setOnAction(e -> {
                dialog.hide();
            });
            dialog.getButtons().add(okButton);
            dialog.showAndWait();
    }
    public void dialogValidarSolicitud(){
        
    }
}