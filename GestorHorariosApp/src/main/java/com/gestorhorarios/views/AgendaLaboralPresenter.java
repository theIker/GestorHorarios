/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestorhorarios.views;

import com.gestorhorarios.GestorHorarios;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.control.CharmListView;

import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import datos.dbmanager;
import java.awt.Color;
import java.awt.Rectangle;
import static java.awt.SystemColor.text;
import static java.lang.Math.E;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Separator;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import static javax.swing.Spring.height;
import static javax.swing.Spring.width;
import models.Funcion;
import models.Jornada;
import models.Turno;


/**
 *
 * @author 2dam
 */
public class AgendaLaboralPresenter{
    
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
    //private CharmListView<String, ? extends Comparable> lvAgenda;
    private ListView lvAgenda;

    

    private ObservableList<String> oblJornadas;
    
    
    dbmanager m= new dbmanager();
    
     public void initialize() {
        // lvAgenda = new CharmListView<>();
        agendaLaboral.showingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                AppBar appBar = MobileApplication.getInstance().getAppBar();
                appBar.setNavIcon(MaterialDesignIcon.MENU.button(e -> MobileApplication.getInstance().showLayer(GestorHorarios.MENU_LAYER)));
                appBar.setTitleText("Agenda laboral");                
                /*appBar.getActionItems().add(MaterialDesignIcon.SEARCH.button(e -> 
                        System.out.println("Search")));*/
                 ObservableList<String> items = lvAgenda.getItems();
              //   String t="hoy";                 
                 items.add("hoy");
                 items.add("es");
                 items.add("viernes");
                
               
                    
                //initClassic();
                
            }
        });
        //lvAgenda.setItems();
        
    }
     
     
    public void initClassic() {


        try {
            
            //oblProducts = FXCollections.observableArrayList(productManager.getProductByCategory(AppConstants.PRODUCT_PIZZA));
            ArrayList <String> a = new ArrayList();
            a.add("1");
            a.add("2");
            a.add("3");
            oblJornadas = (ObservableList)FXCollections.observableArrayList(a);
            
            
            lvAgenda.setItems(oblJornadas);
        
        
            lvAgenda.setCellFactory(p -> new AgendaListCell());
            
        } catch (Exception ex) {
            Logger.getLogger(AgendaLaboralPresenter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
