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
import datos.dbmanager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 *
 * @author 2dam
 */
public class DatosEmpleadoPresenter {
    
    @FXML
    private View datosEmpleado;
    @FXML
    private Label lbDni;
    @FXML
    private TextField tfDni;
    @FXML
    private Label lbNombre;
    @FXML
    private TextField tfNombre;
    @FXML
    private Label lbApellido1;
    @FXML
    private TextField tfApellido1;
    @FXML 
    private Label lbApellido2;
    @FXML
    private TextField tfApellido2;
    @FXML
    private Label lbEmail;
    @FXML
    private TextField tfEmail;
    @FXML
    private Label lbPerfil;
    @FXML
    private ComboBox cbPerfil;
    @FXML
    private Label lbJornadas;
    @FXML
    private ComboBox cbJornadas;
    @FXML
    private Button btnAnyadir;
    @FXML
    private Label lbAnyadirJornada;
    @FXML
    private Button btnBorrar;
    @FXML
    private Label lbBorrarJornada;
    @FXML
    private Button btnBorrarUsuario;
    @FXML
    private Button btnGuardarUsuario;
    
        
    dbmanager m= new dbmanager();
    
     public void initialize() {
        datosEmpleado.showingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                AppBar appBar = MobileApplication.getInstance().getAppBar();
                appBar.setNavIcon(MaterialDesignIcon.MENU.button(e -> 
                        MobileApplication.getInstance().showLayer(GestorHorarios.MENU_LAYER)));
                appBar.setTitleText("Datos personales");
                
                /*appBar.getActionItems().add(MaterialDesignIcon.SEARCH.button(e -> 
                        System.out.println("Search")));*/
            }
        });
    
}
}
