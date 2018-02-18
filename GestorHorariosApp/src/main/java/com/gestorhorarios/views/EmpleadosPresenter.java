/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestorhorarios.views;

import com.gestorhorarios.GestorHorarios;
import static com.gestorhorarios.GestorHorarios.AGENDA_LABORAL;
import static com.gestorhorarios.GestorHorarios.DATOS_EMPLEADO;
import com.gestorhorarios.logic.GestorHorariosManager;
import com.gestorhorarios.logic.GestorHorariosManagerImplementation;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.control.CharmListView;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

/**
 *
 * @author 2dam
 */
public class EmpleadosPresenter {
    public static Boolean crear = true;
    
    @FXML
    private View listaEmpleados;
    @FXML
    private CharmListView lvEmpleados;
    @FXML
    private Button btnAnyadirEmpleados;
    
    private ObservableList empleados;
    private GestorHorariosManager gh = new GestorHorariosManagerImplementation();
    
     public void initialize() {
        listaEmpleados.showingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                AppBar appBar = MobileApplication.getInstance().getAppBar();
                appBar.setNavIcon(MaterialDesignIcon.MENU.button(e -> 
                        MobileApplication.getInstance().showLayer(GestorHorarios.MENU_LAYER)));
                appBar.setTitleText("Empleados");
                
                empleados = (ObservableList) gh.getUsuarios();
                /*appBar.getActionItems().add(MaterialDesignIcon.SEARCH.button(e -> 
                        System.out.println("Search")));*/
                //lvAgenda.setItems();
            }
        });
        
    }
     
    public void cargarLista(){
        
    }
    
    @FXML
    public void handleOnActionCrear(){
        crear = true;
        GestorHorarios.drawer.updateItem(DATOS_EMPLEADO);
    }
    @FXML
    public void handleOnTouchPressedModificar(){
        //
    }
}