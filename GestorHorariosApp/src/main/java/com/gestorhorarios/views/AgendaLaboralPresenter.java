/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestorhorarios.views;

import com.gestorhorarios.GestorHorarios;
import com.gestorhorarios.logic.GestorHorariosManager;
import com.gestorhorarios.logic.GestorHorariosManagerImplementation;
import com.gestorhorarios.logic.ManagerFactory;
import com.gestorhorarios.logic.models.Jornada;
import com.gestorhorarios.logic.models.Turno;
import com.gestorhorarios.logic.models.Usuario;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.control.CharmListView;
import com.gluonhq.charm.glisten.control.Dialog;

import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
    
    
     public void initialize() {
        agendaLaboral.showingProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> obs, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    AppBar appBar = MobileApplication.getInstance().getAppBar();
                    appBar.setNavIcon(MaterialDesignIcon.MENU.button(e ->
                            MobileApplication.getInstance().showLayer(GestorHorarios.MENU_LAYER)));
                    appBar.setTitleText("Agenda laboral");
                    /*appBar.getActionItems().add(MaterialDesignIcon.SEARCH.button(e ->
                    System.out.println("Search")));*/
                    if(ManagerFactory.gh.getUsuarioLogin().getPerfil().compareTo("empleado")==0){
                        lbVerAgenda.setVisible(false);
                        cbNombre.setVisible(false);
                    } else {
                        cbNombre.setItems(FXCollections.observableArrayList(ManagerFactory.gh.getUsuarios()));
                        cbNombre.getSelectionModel().select(ManagerFactory.gh.getUsuarioLogin());
                    }
                    
                    //NEW LOGIC IMPLEMENTATION
                    lvAgenda.selectedItemProperty().addListener(new ChangeListener() {
                        @Override
                        public void changed(ObservableValue obs, Object ov, Object nv) {
                            Jornada j = (Jornada) lvAgenda.getSelectedItem();
                            clickList(j);
                            lvAgenda.setSelectedItem(null);
                        }
                    });
                    
                    cargarLista();
                }
            }
        });
        //lvAgenda.setItems();
    }
    
     //NEW LOGIC IMPLEMENTATION
    public void clickList(Jornada j){
        
        Dialog dialog = new Dialog();
        
        //Obtener los turnos que hay ese mismo día
        ArrayList <Jornada> jornadaList = ManagerFactory.gh.getAllJornadas();
        ObservableList <Jornada> jornadas = FXCollections.observableArrayList(jornadaList) ;
        ObservableList <Turno> turnos = FXCollections.observableArrayList();
        
        for (Jornada jor : jornadas) {
            
            if (jor.getFecha().equals(j.getFecha())) {
                if (jor.getTurno().getID().substring(0, 1).compareTo(j.getTurno().getID().substring(0,1)) == 0
                        && jor.getTurno().getID().substring(1, 2).compareTo(j.getTurno().getID().substring(1,2)) != 0) {
                    
                    turnos.add(jor.getTurno());
                    
                }
            }
            
        }
        
        ComboBox combo= new ComboBox();
        
        combo.setItems(turnos);
        dialog.setContent(combo);
        dialog.setGraphic(new Label("Realizar una solicitud"));
        
        combo.getSelectionModel().selectFirst();
        
        Button cancelarButton = new Button("Cancelar");
        
         cancelarButton.setOnAction(e -> {
          dialog.hide();
        });
        
        Button guardarButton = new Button("Realizar");
        
        guardarButton.setOnAction(e -> {
            
            dialog.hide();
            
        });
           
        dialog.getButtons().add(guardarButton);
        dialog.getButtons().add(cancelarButton);
              
        dialog.showAndWait();
     
     } 
    
    @FXML
    public void handleOnActionBuscar(){
        Usuario usuarioAux = new Usuario();
        if(cbNombre.getValue()== null){
            usuarioAux = ManagerFactory.gh.getUsuarioLogin();
        } else {
            usuarioAux = (Usuario) cbNombre.getSelectionModel().getSelectedItem();
        }
        
    }
    
    public void cargarLista(){
       ArrayList<Jornada> jornadas = new ArrayList<>(ManagerFactory.gh.getUsuarioLogin().getJornadas());
       ObservableList ol = FXCollections.observableArrayList(jornadas);
       lvAgenda.setItems(ol);
        
    }
}