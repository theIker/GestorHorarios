/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestorhorarios.views;

import com.gestorhorarios.GestorHorarios;
import static com.gestorhorarios.GestorHorarios.LISTA_EMPLEADOS;
import static com.gestorhorarios.GestorHorarios.LOGIN;
import com.gestorhorarios.datos.DBManagerHibernate;
import com.gestorhorarios.logic.GestorHorariosManager;
import com.gestorhorarios.logic.GestorHorariosManagerImplementation;
import com.gestorhorarios.logic.ManagerFactory;
import com.gestorhorarios.logic.models.Jornada;
import com.gestorhorarios.logic.models.Turno;
import com.gestorhorarios.logic.models.Usuario;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.control.Dialog;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


/**
 *
 * @author Iker Iglesias
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
    
   
    private ArrayList<Jornada> jornadas= new ArrayList<>();
    
    //dni del usuario selecciona para modificar desde la listview

    private Usuario user=new Usuario();
        
        
    
    public void initialize() {
        datosEmpleado.showingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                AppBar appBar = MobileApplication.getInstance().getAppBar();
                appBar.setNavIcon(MaterialDesignIcon.MENU.button(e -> 
                        MobileApplication.getInstance().showLayer(GestorHorarios.MENU_LAYER)));
                appBar.setTitleText("Datos personales");
                
                /*appBar.getActionItems().add(MaterialDesignIcon.SEARCH.button(e -> 
                        System.out.println("Search")));*/
                tfDni.setDisable(true);
                cbPerfil.setItems(FXCollections.observableArrayList("Empleado","Encargado","Gerente"));
                cbPerfil.getSelectionModel().selectFirst();
                
                cbJornadas.setPromptText("Ninguna jornada");
                
              
                if(ManagerFactory.gh.getUsuarioLogin().getPerfil().equals("empleado")){
                      cbPerfil.setVisible(false);
                      cbJornadas.setVisible(false);
                      btnBorrar.setVisible(false);
                      btnBorrarUsuario.setVisible(false);
                      btnAnyadir.setVisible(false);
                      lbJornadas.setVisible(false);
                      lbPerfil.setVisible(false);
                      lbBorrarJornada.setVisible(false);
                      lbAnyadirJornada.setVisible(false);
                      
                tfDni.setText(ManagerFactory.gh.getUsuarioLogin().getDNI());
                tfNombre.setText(ManagerFactory.gh.getUsuarioLogin().getNombre());
                tfApellido1.setText(ManagerFactory.gh.getUsuarioLogin().getApellido1());
                tfApellido2.setText(ManagerFactory.gh.getUsuarioLogin().getApellido2());
                tfEmail.setText(ManagerFactory.gh.getUsuarioLogin().getEmail());
                }
                
                else{
                    if(EmpleadosPresenter.crear)
                        tfDni.setDisable(false);
                    
                else{
                   
                    user=ManagerFactory.gh.getUsuario(EmpleadosPresenter.dniMod);
                    tfDni.setText(user.getDNI());
                    tfNombre.setText(user.getNombre());
                    tfApellido1.setText(user.getApellido1());
                    tfApellido2.setText(user.getApellido2());
                    tfEmail.setText(user.getEmail());
                    for(int i=0;i<cbPerfil.getItems().size();i++){
                         if(cbPerfil.getItems().get(i).toString().toLowerCase().equals(user.getPerfil().toLowerCase())){
                             cbPerfil.getSelectionModel().select(i);
                             break;
                         }
                    }
                  }
                            
                }
            }
        });
    }
    
    @FXML
    public void handleOnActionAddJornada(){
        ObservableList<Turno>turnos;
        ArrayList<Turno>t= new ArrayList<>();
        Dialog dialog = new Dialog();
        if(jornadas.size()>0){
            for(int i=0;i<ManagerFactory.gh.getTurnos().size();i++){
                 String hola=ManagerFactory.gh.getTurnos().get(i).getID();
                  hola=String.valueOf(hola.charAt(0));
                  if(jornadas.get(0).getTurno().getID().contains(hola)){
                      t.add(ManagerFactory.gh.getTurnos().get(i));
                  }
             }
                 
             
            turnos =FXCollections.observableArrayList(t);
         }
         else{
             turnos=FXCollections.observableArrayList(ManagerFactory.gh.getTurnos());
         }
         
            
            Jornada aux;
         ComboBox combo= new ComboBox();
         DatePicker date = new DatePicker();
         
         combo.setItems(turnos);
         dialog.setTitle(combo);
         dialog.setContent(date);
         dialog.setGraphic(new Label("Añadir jornada"));
         
         combo.getSelectionModel().selectFirst();
         Button cancelarButton = new Button("Cancelar");
        cancelarButton.setOnAction(e -> {
          dialog.hide();
        });
        
        Button guardarButton = new Button("Guardar");
         aux=new Jornada();
        
        guardarButton.setOnAction(e -> {
          try{
          LocalDate localDate = date.getValue();
           Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
           Date dat = Date.from(instant);
           
           aux.setFecha(dat);
           aux.setTurno((Turno) combo.getSelectionModel().getSelectedItem());
            jornadas.add(aux);
            ObservableList<Jornada>jornada=FXCollections.observableArrayList(jornadas);
            cbJornadas.setItems(jornada);
            cbJornadas.getSelectionModel().selectFirst();
            dialog.hide();
          }catch(Exception x){
             
                 date.setPromptText("Seleciona fecha");
                 
          }
          
              
          
            });
           
        dialog.getButtons().add(guardarButton);
        dialog.getButtons().add(cancelarButton);
        
        
        dialog.showAndWait();

     
     }
     
        @FXML
     public void handleOnActionBorrarJornada(){
         jornadas.remove(cbJornadas.getSelectionModel().getSelectedItem());
            cbJornadas.getItems().remove(cbJornadas.getSelectionModel().getSelectedItem());
            if(jornadas.size()==0)
                cbJornadas.setPromptText("Ninguna jornada");
            else
                cbJornadas.setPromptText("");
            
            cbJornadas.getSelectionModel().selectFirst();
      
     }
     
    @FXML
     public void handleOnActionBorrar(){
         
          ManagerFactory.gh.borrarUsuario(ManagerFactory.gh.getUsuario(tfDni.getText()));
          MobileApplication.getInstance().showMessage("Usuario: "+tfDni.getText()+" borrado");
            tfDni.setText("");
            tfNombre.setText("");
            tfApellido1.setText("");
            tfApellido2.setText("");
            tfEmail.setText("");
            jornadas.clear();
            cbJornadas.getItems().clear();
           GestorHorarios.drawer.updateItem(LISTA_EMPLEADOS);
     }
     
     @FXML
     public void handleOnActionGuardar(){
         
         switch(ManagerFactory.gh.getUsuarioLogin().getPerfil()){
             
             case "empleado":      
                        empleado();
                       break;
                       
             case "encargado" :
                          jefesMod();
                        break;
             case "gerente":
                          jefesMod();
                        break;
         
     }
     }

    private void empleado() {
             
              
        if(tfDni.getText().equals("")||tfNombre.getText().equals("")||tfApellido1.getText().equals("")||tfApellido2.getText().equals("")||
                      tfEmail.getText().equals(""))
                        {    
                            MobileApplication.getInstance().showMessage("Rellene todos los campos");
                                
                            }else{
                                if(ManagerFactory.gh.validarEmail(tfEmail.getText())){
                                    
                                    ManagerFactory.gh.getUsuarioLogin().setNombre(tfNombre.getText());
                                    ManagerFactory.gh.getUsuarioLogin().setEmail(tfEmail.getText());
                                    ManagerFactory.gh.getUsuarioLogin().setApellido1(tfApellido1.getText());
                                    ManagerFactory.gh.getUsuarioLogin().setApellido2(tfApellido2.getText());
                                    
                                    ManagerFactory.gh.modificarDatos(ManagerFactory.gh.getUsuarioLogin());
                                   MobileApplication.getInstance().showMessage("Datos guardados");
                               }else{
                                   MobileApplication.getInstance().showMessage("Formato de email no válido");
                            }
                        }  
       }
    private void jefesMod() {
         if(tfDni.getText().equals("")||tfNombre.getText().equals("")||tfApellido1.getText().equals("")||tfApellido2.getText().equals("")||
                      tfEmail.getText().equals("")||cbPerfil.getValue()==null)
                        {    
                            MobileApplication.getInstance().showMessage("Rellene todos los campos");
                                
                            }else{
                                if(ManagerFactory.gh.validarEmail(tfEmail.getText())){
                                            
                                       
                                       
                                   
                                        
                                        if(EmpleadosPresenter.crear){
                                            
                                            user.setDNI(tfDni.getText());
                                            user.setNombre(tfNombre.getText());
                                            user.setApellido1(tfApellido1.getText());
                                            user.setApellido2(tfApellido2.getText());
                                            user.setEmail(tfEmail.getText());
                                            user.setPerfil(cbPerfil.getSelectionModel().getSelectedItem().toString().toLowerCase());
                                            user.setJornadas(jornadas);
                                            
                                             if(ManagerFactory.gh.getUsuario(user.getDNI())==null){
                                                 ManagerFactory.gh.crearUsuario(user,ManagerFactory.gh.getGenPassHash(user));
                                                 ManagerFactory.gh.crearJornada(user,jornadas);
                                             MobileApplication.getInstance().showMessage("Usuario creado");
                                             tfDni.setText("");
                                             tfNombre.setText("");
                                             tfApellido1.setText("");
                                             tfApellido2.setText("");
                                             tfEmail.setText("");
                                             jornadas.clear();
                                             cbJornadas.getItems().clear();
                                             
                                             GestorHorarios.drawer.updateItem(LISTA_EMPLEADOS);
                                             }
                                             else{
                                                 MobileApplication.getInstance().showMessage("Ese usuario ya existe");
                                             }

                                        }
                                        else{
                                        
                                         ManagerFactory.gh.modificarDatos(user);     
                                         ManagerFactory.gh.crearJornada(user,jornadas);
                                         
                                           
                                         MobileApplication.getInstance().showMessage("Datos guardados");
                                             tfDni.setText("");
                                             tfNombre.setText("");
                                             tfApellido1.setText("");
                                             tfApellido2.setText("");
                                             tfEmail.setText("");
                                             jornadas.clear();
                                             cbJornadas.getItems().clear();
                                         
                                         GestorHorarios.drawer.updateItem(LISTA_EMPLEADOS);
                                        }
    
                               }else{
                                   MobileApplication.getInstance().showMessage("Formato de email no válido");
                            }
                        }   
    }
    
}