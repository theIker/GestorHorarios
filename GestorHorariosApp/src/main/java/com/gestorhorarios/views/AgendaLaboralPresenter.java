/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestorhorarios.views;

import com.gestorhorarios.GestorHorarios;
import com.gestorhorarios.logic.GestorHorariosManager;
import com.gestorhorarios.logic.GestorHorariosManagerImplementation;
import com.gestorhorarios.logic.models.Jornada;
import com.gestorhorarios.logic.models.Usuario;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.control.CharmListView;

import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
    private CharmListView<Jornada, ? extends Comparable> lvAgenda;
    
    private GestorHorariosManager gh = new GestorHorariosManagerImplementation();
     public void initialize() {
        agendaLaboral.showingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                AppBar appBar = MobileApplication.getInstance().getAppBar();
                appBar.setNavIcon(MaterialDesignIcon.MENU.button(e -> 
                        MobileApplication.getInstance().showLayer(GestorHorarios.MENU_LAYER)));
                appBar.setTitleText("Agenda laboral");
                
                /*appBar.getActionItems().add(MaterialDesignIcon.SEARCH.button(e -> 
                        System.out.println("Search")));*/
                if(GestorHorarios.usuario.getPerfil().compareTo("empleado")==0){
                    lbVerAgenda.setVisible(false);
                    cbNombre.setVisible(false);
                } else {
                    cbNombre.setItems(FXCollections.observableArrayList(gh.getUsuarios()));
                    cbNombre.getSelectionModel().select(GestorHorarios.usuario);
                }
            }
        });
        //lvAgenda.setItems();
    }
     
    @FXML
    public void handleOnActionBuscar(){
        Usuario usuarioAux = new Usuario();
        if(cbNombre.getValue()== null){
            usuarioAux = GestorHorarios.usuario;
        } else {
            usuarioAux = (Usuario) cbNombre.getSelectionModel().getSelectedItem();
        }
        
    }
}