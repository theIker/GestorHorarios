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
import com.gestorhorarios.logic.models.Usuario;
import com.gestorhorarios.logic.models.Funcion;
import com.gestorhorarios.logic.models.Turno;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.control.CharmListCell;
import com.gluonhq.charm.glisten.control.CharmListView;
import com.gluonhq.charm.glisten.control.Dialog;
import com.gluonhq.charm.glisten.control.ListTile;

import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Callback;


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
        agendaLaboral.showingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                AppBar appBar = MobileApplication.getInstance().getAppBar();
                appBar.setNavIcon(MaterialDesignIcon.MENU.button(e -> 
                        MobileApplication.getInstance().showLayer(GestorHorarios.MENU_LAYER)));
                appBar.setTitleText("Agenda laboral");
                
                /*appBar.getActionItems().add(MaterialDesignIcon.SEARCH.button(e -> 
                        System.out.println("Search")));*/
                if(ManagerFactory.gh.getUsuarioLogin().getPerfil().compareTo("empleado")==0){
                    lbVerAgenda.setVisible(false);
                    //lbVerAgenda.setStyle("");
                    
                    cbNombre.setVisible(false);
                } else {
                    cbNombre.setItems(FXCollections.observableArrayList(ManagerFactory.gh.getUsuarios()));
                    cbNombre.getSelectionModel().select(ManagerFactory.gh.getUsuarioLogin());
                }
                cargarLista();
            }
        });
        
        //NEW LOGIC IMPLEMENTATION
                    lvAgenda.selectedItemProperty().addListener(new ChangeListener() {
                        @Override
                        public void changed(ObservableValue obs, Object ov, Object nv) {
                            Jornada j = (Jornada) lvAgenda.getSelectedItem();
                            clickList(j);
                            lvAgenda.setSelectedItem(null);
                        }
                    });
        
        lvAgenda.setCellFactory(p -> new CharmListCell<Jornada>() {

        @Override 
        public void updateItem(Jornada item, boolean empty) {
          super.updateItem(item, empty);
          if (item != null && !empty) {
            VBox buttons = new VBox(MaterialDesignIcon.DATE_RANGE.graphic());
            buttons.setMaxHeight(Double.MAX_VALUE);
            buttons.setAlignment(Pos.TOP_LEFT);
            ListTile tile = new ListTile();       
            String txtfunciones="";
            Collection<Funcion> funciones=item.getTurno().getFunciones();
            for(Funcion o : funciones){
                if(txtfunciones.equals("")){
                    txtfunciones=txtfunciones+o.getNombre().toString();
                }
                else{
                    txtfunciones=txtfunciones+", "+o.getNombre().toString();
                }             
            }                 
            tile.textProperty().setAll(item.toString()); 
            tile.setTextLine(0,item.getFecha().toString());
            tile.setTextLine(1,txtfunciones);
            tile.setTextLine(2,item.getTurno().toString());
//            tile.textProperty().add(txtfunciones);
            tile.setPrimaryGraphic(buttons);
           // tile.setPrefHeight(); asigna una altura por defecto
           
            tile.setOnMouseClicked(e -> buttons.getChildren().setAll(MaterialDesignIcon.DATE_RANGE.graphic()));
            setText(null);
            setGraphic(tile);
          } else {
            setText(null);
            setGraphic(null);
          }
        }

      });
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
//        lvAgenda.setCellFactory(new Callback<CharmListView<Jornada, ? extends Comparable>, CharmListCell<Jornada>> () {
//           @Override
//           public CharmListCell<Jornada> call(CharmListView<Jornada, ? extends Comparable> param) {
//               throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//           }
//        lvAgenda.setCellFactory(Callback<CharmListView <Jornada, ? extends Comparable>, CharmListCell<Jornada>> value){
//          
//
//           @Override
//            CharmListCell<Jornada> call(CharmListView<Jornada, ? extends Comparable> param) {
//               throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//           }
//    });
    }
       //NEW LOGIC IMPLEMENTATION

    /**
     *
     * @param j
     */
    public void clickList(Jornada j){
        
        Dialog dialog = new Dialog();
        dialog.setGraphic(new Label("Realizar una solicitud"));
        
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
        
        if (turnos.isEmpty()) {
            
            Label l = new Label("No hay jornadas disponibles para realizar cambios");
            dialog.setContent(l);
            
            Button okayButton = new Button("Okay");
            
            okayButton.setOnAction(e -> {
                dialog.hide();
            });
            
        } else {
            
            ObservableList <Turno> turnosDisponibles = FXCollections.observableArrayList();
            
            for (Turno t : turnos) {
                
                if (turnosDisponibles.isEmpty()) {
                    turnosDisponibles.add(t);
                } else {
                    for (Turno t2 : turnosDisponibles) {
                        if (!(t.equals(t2))) {
                            turnosDisponibles.add(t);
                        }
                    }
                }
                
            }
            
            ComboBox combo= new ComboBox();
            combo.setItems(turnosDisponibles);
            dialog.setContent(combo);
        
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
            
        }
              
        dialog.showAndWait();
     
     } 
        
    
}