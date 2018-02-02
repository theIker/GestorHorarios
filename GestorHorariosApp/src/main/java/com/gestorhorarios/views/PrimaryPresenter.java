package com.gestorhorarios.views;

import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import com.gestorhorarios.GestorHorarios;
import datos.dbmanager;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import models.Funcion;
import models.Jornada;
import models.Solicitud;
import models.Usuario;

public class PrimaryPresenter {

    @FXML
    private View primary;

    @FXML
    private Label label;
    
    dbmanager m= new dbmanager();

    public void initialize() {
        primary.showingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                AppBar appBar = MobileApplication.getInstance().getAppBar();
                appBar.setNavIcon(MaterialDesignIcon.MENU.button(e -> 
                        MobileApplication.getInstance().showLayer(GestorHorarios.MENU_LAYER)));
                appBar.setTitleText("Primary");
                appBar.getActionItems().add(MaterialDesignIcon.SEARCH.button(e -> 
                        System.out.println("Search")));
            }
        });
    }
    
    @FXML
    void buttonClick() throws Exception {
        label.setText("Hello JavaFX Universe!");
        String pass="paco";
        Usuario u= new Usuario();
        ArrayList<Solicitud>sol= new ArrayList<>();
        Solicitud aux= new Solicitud();
        aux.setEstado("enviada");
        aux.setID(2);
        aux=new Solicitud();
        aux.setEstado("enviada2");
        aux.setID(3);
        sol.add(aux);
        u.setDNI("1");
        u.setNombre("paco");
        u.setSolicitudes(sol);
        m.crearUsuario(u, pass);

      
    }
    
}
