/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestorhorarios.views;


import com.gestorhorarios.DrawerManager;
import com.gestorhorarios.GestorHorarios;
import static com.gestorhorarios.GestorHorarios.AGENDA_LABORAL;
import com.gestorhorarios.logic.GestorHorariosManager;
import com.gestorhorarios.logic.GestorHorariosManagerImplementation;
import com.gestorhorarios.logic.ManagerFactory;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 *
 * @author Miguel Axier Lafuente Peñas
 */
public class LoginUsuarioPresenter {
    
    
    @FXML
    private View login;
    @FXML
    private TextField tfNombre;
    @FXML
    private PasswordField pfPass;
    
    public void initialize(){
         login.showingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                MobileApplication.getInstance().getAppBar().setVisible(false);
            }
        });
    }
    @FXML
    public void handleOnActionEntrar(){
     
        if(tfNombre.getText().compareTo("")==0||pfPass.getText().compareTo("")==0){
            Dialog dialog = new Dialog();
            dialog.setTitle(new Label("ERROR"));
            dialog.setContent(new Label("Los campos nombre y contraseña son obligatorios"));
            Button okButton = new Button("Acpetar");
            okButton.setOnAction(e -> {
                dialog.hide();
            });
            dialog.getButtons().add(okButton);
            dialog.showAndWait();
        } else {
            Usuario usuario = new Usuario();      
            usuario = ManagerFactory.gh.validarUsuario(tfNombre.getText(), ManagerFactory.gh.getPassHash(pfPass.getText()));
            if(usuario==null){
                MobileApplication.getInstance().showMessage("Usuario o contraseña incorrecta");
                tfNombre.setText(null);
                pfPass.setText(null);

            } else {
                ManagerFactory.gh.setUsuarioLogin(usuario);
                
                if(usuario.getPerfil().compareTo("empleado")!=0){
                    GestorHorarios.drawer.agnadirItemEncargado();
                }
                GestorHorarios.drawer.updateItem(AGENDA_LABORAL);
            }
        }
    }
    @FXML
    public void handleOnActionRecPass(){
        TextField txtEmail = new TextField();
        Dialog dialog = new Dialog();
        dialog.setTitle(new Label("Introduce el email"));
        dialog.setContent(txtEmail);
        Button cancelar = new Button("Cancelar");
        cancelar.setOnAction(e -> {
            dialog.hide();
        });
        Button enviar = new Button("Enviar");
        enviar.setOnAction(e -> {
            if(txtEmail.getText().compareTo("")==0){
                txtEmail.setPromptText("Introduce un email");
            }
            //comprobar email
            //llamar al metodo de generar contraseña
            //enviar email
        });
        dialog.getButtons().add(enviar);
        dialog.getButtons().add(cancelar);
        dialog.showAndWait();
    }
}
