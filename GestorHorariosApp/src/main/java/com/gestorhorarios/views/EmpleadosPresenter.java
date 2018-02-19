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
import com.gestorhorarios.logic.ManagerFactory;
import com.gestorhorarios.logic.models.Usuario;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.control.CharmListView;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
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
    public static String dniMod;
    
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
                
                cargarLista();
                lvEmpleados.selectedItemProperty().addListener(new ChangeListener() {
                    @Override
                    public void changed(ObservableValue obs, Object ov, Object nv) {
                            crear=false;
                            Usuario usu=(Usuario) lvEmpleados.getSelectedItem();
                            dniMod=usu.getDNI();
                            GestorHorarios.drawer.updateItem(DATOS_EMPLEADO);
                    }
                });
                /*appBar.getActionItems().add(MaterialDesignIcon.SEARCH.button(e -> 
                        System.out.println("Search")));*/
                //lvAgenda.setItems();
            }
        });
        
    }
     
    public void cargarLista(){
       
        empleados=FXCollections.observableArrayList(ManagerFactory.gh.getUsuarios());
        lvEmpleados.setItems(empleados);
    }
    
    @FXML
    public void handleOnActionCrear(){
        crear = true;
        GestorHorarios.drawer.updateItem(DATOS_EMPLEADO);
    }
    @FXML
    public void handleOnTouchPressedModificar(){
        Usuario usu=(Usuario) lvEmpleados.getSelectedItem();
        dniMod=usu.getDNI();
        GestorHorarios.drawer.updateItem(DATOS_EMPLEADO);
    }
}