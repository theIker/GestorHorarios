/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestorhorarios.views;

import com.gestorhorarios.GestorHorarios;
import com.gestorhorarios.logic.ManagerFactory;
import com.gestorhorarios.logic.models.Jornada;
import com.gestorhorarios.logic.models.Solicitud;
import com.gestorhorarios.logic.models.Usuario;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.control.CharmListView;
import com.gluonhq.charm.glisten.control.Dialog;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 *
 * @author Miguel Axier Lafuente Peñas
 */
public class CambiosTurnoPresenter {
    
    @FXML
    private View cambiosTurno;
    @FXML
    private ComboBox cbSolicitudes;
    @FXML
    private Button btnBuscar;
    @FXML
    private CharmListView lvSolicitudes;
    
            
    
    public void initialize() {
        cambiosTurno.showingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                AppBar appBar = MobileApplication.getInstance().getAppBar();
                appBar.setNavIcon(MaterialDesignIcon.MENU.button(e -> 
                        MobileApplication.getInstance().showLayer(GestorHorarios.MENU_LAYER)));
                appBar.setTitleText("Cambios de turno");
                if(ManagerFactory.gh.getUsuarioLogin().getPerfil().compareTo("empleado")!=0){
                    cbSolicitudes.setItems(FXCollections.observableArrayList("Solicitudes enviadas"
                                                                            ,"Solicitudes recibidas"
                                                                            ,"Validar solicitudes"));
                } else {
                    cbSolicitudes.setItems(FXCollections.observableArrayList("Solicitudes enviadas"
                                                                            ,"Solicitudes recibidas"));
                }
                cbSolicitudes.getSelectionModel().selectFirst();
                lvSolicitudes.selectedItemProperty().addListener(new ChangeListener(){
                    @Override
                    public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                        Solicitud solicitud = (Solicitud) lvSolicitudes.getSelectedItem();
                        clickList(solicitud);
                        try{
                            lvSolicitudes.setSelectedItem(null);
                        } catch (Exception e){}
                    }
                
                });
                cargarListaEnviado();
            }
        });
    
    }
    /**
     * Metodo que controla la acción sobre el boton buscar
     */
    @FXML
    public void handleOnActionBuscar(){
        if(cbSolicitudes.getSelectionModel().isSelected(0)){
            cargarListaEnviado();
        } else if(cbSolicitudes.getSelectionModel().isSelected(1)){
            cargarListaRecibido();
        } else if(cbSolicitudes.getSelectionModel().isSelected(2)){
            cargarListaPendiente();
        }
    }
    /**
     * Metodo que crea y muestra un dialog que informa al usuario del estado de una solicitud
     */
    public void dialogPendienteAceptar(){
        Dialog dialog = new Dialog();
            dialog.setTitle(new Label("Estado de la solicitud"));
            dialog.setContent(new Label("El compañero aún no ha revisado su solicitud"));
            Button okButton = new Button("Aceptar");
            okButton.setOnAction(e -> {
                dialog.hide();
            });
            dialog.getButtons().add(okButton);
            dialog.showAndWait();
    }
    /**
     * Metodo que crea y muestra un dialog que informa al usuario del estado de una solicitud
     */
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
    /**
     * Metodo que crea y muestra un dialog para que un usuario acepte una solicitud de cambio
     */
    public void dialogAceptarSolicitud(Solicitud solicitud){
        Dialog dialog = new Dialog();
        Usuario usuario = ManagerFactory.gh.getUsuarioLogin();
        Jornada jornadaCambio = ManagerFactory.gh.getJornadaCambio((List<Jornada>) usuario.getJornadas(), ManagerFactory.gh.getJornadaById(solicitud.getJornadaSolicita()));
            dialog.setTitle(new Label("Aceptar solicitud"));
            dialog.setContent(new Label("No se podrá deshacer la operación. ¿Estas seguro?"));
            Button okButton = new Button("Aceptar");
            Button cancelButton = new Button("Cancelar");
            okButton.setOnAction(e -> {  
                ManagerFactory.gh.aceptarSolicitud(usuario, solicitud, jornadaCambio);
                dialog.hide();   
            });
            cancelButton.setOnAction(e ->{
                dialog.hide();
            });
            dialog.getButtons().addAll(okButton,cancelButton);
            dialog.showAndWait();
            
            ManagerFactory.gh.setUsuarioLogin(ManagerFactory.gh.getUsuario(ManagerFactory.gh.getUsuarioLogin().getDNI()));
            cargarListaRecibido();
    }
    //Metodo que crea y muestra un dialog para validar un cambio de turno
    public void dialogValidarSolicitud(Solicitud solicitud){
        Dialog dialog = new Dialog();
            dialog.setTitle(new Label("Validar solicitud"));
            dialog.setContent(new Label("No se podrá deshacer la operación. ¿Estas seguro?"));
            Button okButton = new Button("Acpetar");
            Button cancelButton = new Button("Denegar");
            okButton.setOnAction(e -> {
                ManagerFactory.gh.validarSolicitud(ManagerFactory.gh.getUsuarioLogin(), solicitud, "validada");
                cargarListaPendiente();
                dialog.hide();
            });
            cancelButton.setOnAction(e ->{
                ManagerFactory.gh.validarSolicitud(ManagerFactory.gh.getUsuarioLogin(), solicitud, "denegada");
                cargarListaPendiente();
                dialog.hide();
            });
            dialog.getButtons().addAll(okButton,cancelButton);
            dialog.showAndWait();
    }
    /**
     * Metodo que carga la lista con las solicitudes realizadas por el usuario
     */
    public void cargarListaEnviado(){
        ArrayList<Solicitud> solEnviadas;
        solEnviadas = (ArrayList<Solicitud>) ManagerFactory.gh
                .getSolicitudesByUsuario(ManagerFactory.gh.getUsuarioLogin(),"enviada");
        ObservableList ol = FXCollections.observableArrayList(solEnviadas);
        lvSolicitudes.setItems(ol);
                
    }
    /**
     * Metodo que carga la lista con las solicitudes que pueden ser aceptadas por el usuario
     */
    public void cargarListaRecibido(){
        ArrayList<Solicitud> solRecibidas;
        solRecibidas = (ArrayList<Solicitud>) ManagerFactory.gh
                .getSolicitudesByUsuario(ManagerFactory.gh.getUsuarioLogin(), "recibida");
        ObservableList ol = FXCollections.observableArrayList(solRecibidas);
        lvSolicitudes.setItems(ol);
    }
    /**
     * Metodo que carga la lista con las solicitudes penientes de validar por un encargado o gerente
     */
    public void cargarListaPendiente(){
        ArrayList<Solicitud> solPendientes;
        solPendientes = (ArrayList<Solicitud>) ManagerFactory.gh
                .getSolicitudesByUsuario(ManagerFactory.gh.getUsuarioLogin(), null);
        ObservableList ol = FXCollections.observableArrayList(solPendientes);
        lvSolicitudes.setItems(ol);
    }
    /**
     * Metodo que interactua con las solicitudes cuando son seleccionadas en la lista
     * @param solicitud 
     */
    public void clickList(Solicitud solicitud){
        //Solicitudes que estan pendiente de validar por un encargado
        if(solicitud.getEstado().equals("pendiente")){
            if(ManagerFactory.gh.getUsuarioLogin().getPerfil().equals("empleado")){
                dialogPendienteValidar();
            } else {
                dialogValidarSolicitud(solicitud);
            }
            
        }
        //Solicitudes que lanza el usuario login y que aún no han sido aceptadas por otro empleado
        if(solicitud.getEstado().equals("enviada") && solicitud.getUsuarioSolicita().equals(ManagerFactory.gh.getUsuarioLogin().getDNI())){
            dialogPendienteAceptar();
        }
        //solicitudes que lanza otro empleado y estan pendiente de aceptar por el usuario login
        if(solicitud.getEstado().equals("enviada") && !solicitud.getUsuarioSolicita().equals(ManagerFactory.gh.getUsuarioLogin().getDNI())){
            dialogAceptarSolicitud(solicitud);
        }
    }
}