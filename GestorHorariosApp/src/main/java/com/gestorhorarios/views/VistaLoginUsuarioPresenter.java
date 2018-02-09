/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestorhorarios.views;


import com.gestorhorarios.DrawerManager;
import com.gestorhorarios.GestorHorarios;
import static com.gestorhorarios.GestorHorarios.PRIMARY_VIEW;
import com.gestorhorarios.logic.GestorHorariosManager;
import com.gestorhorarios.logic.GestorHorariosManagerImplementation;
import com.gestorhorarios.logic.models.Funcion;
import com.gestorhorarios.logic.models.Jornada;
import com.gestorhorarios.logic.models.Turno;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.control.Dialog;




import com.gluonhq.charm.glisten.mvc.View;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import com.gestorhorarios.logic.models.Usuario;
import com.gluonhq.charm.glisten.control.CharmListView;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Miguel Axier Lafuente Peñas
 */
public class VistaLoginUsuarioPresenter {
    
    @FXML
    private View wup;
    @FXML
    private CharmListView mycharm;
    
    private GestorHorariosManager gh;
    private ArrayList <Jornada> js;
    
    public void initialize(){
        
        wup.showingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                MobileApplication.getInstance().getAppBar().setVisible(false);
                gh = new GestorHorariosManagerImplementation();
            }
        });
        
        ArrayList <Funcion> fs = new ArrayList();
        Funcion f = new Funcion(1,"Carniceria");
        Funcion f2 = new Funcion(2,"Heladeria");
        fs.add(f);
        fs.add(f2);
        js = new ArrayList();
        Turno t = new Turno("1","09:00","14:00",fs);
        Date d = new Date();
        Jornada j = new Jornada(1,d,t);
        js.add(j);
        js.add(j);
        ObservableList <Jornada> ol = FXCollections.observableArrayList(js);
        mycharm.setItems(ol);
        
        
    }
    
    /*
    @FXML
    private View login;
    @FXML
    private TextField tfNombre;
    @FXML
    private PasswordField pfPass;
    private GestorHorariosManager gh;
    
    public void initialize(){
         login.showingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                MobileApplication.getInstance().getAppBar().setVisible(false);
                gh = new GestorHorariosManagerImplementation();
            }
        });
       
    }
    @FXML
    public void handleOnActionEntrar(){
        Usuario usuario = new Usuario();      
        usuario = gh.validarUsuario(tfNombre.getText(), pfPass.getText().trim());
        if(usuario==null){
            MobileApplication.getInstance().showMessage("Usuario o contraseña incorrecta");
            tfNombre.setText(null);
            pfPass.setText(null);
            
        } else {
            GestorHorarios.usuario = usuario;
            DrawerManager hola = new DrawerManager();
            hola.updateItem(PRIMARY_VIEW);
            //Entrar a la siguiente ventana pasandole el usuario
        }
    }
    @FXML
    public void handleOnActionRecPass(){
        Dialog dialog = new Dialog();
        dialog.setTitle(new Label("Introduce el email"));
        dialog.setContent(new TextField());
        
        Button cancelar = new Button("Cancelar");
        cancelar.setOnAction(e -> {
            dialog.hide();
        });
        Button enviar = new Button("Enviar");
        enviar.setOnAction(e -> {
            //comprobar email
            //llamar al metodo de generar contraseña
            //enviar email
        });
        dialog.getButtons().add(enviar);
        dialog.getButtons().add(cancelar);
        dialog.showAndWait();
    }
    */
}
