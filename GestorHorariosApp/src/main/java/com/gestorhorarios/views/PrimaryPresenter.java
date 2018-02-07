package com.gestorhorarios.views;

import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import com.gestorhorarios.GestorHorarios;
import datos.dbmanager;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class PrimaryPresenter {

    @FXML
    private View primary;

    @FXML
    private Label label;
    
    dbmanager m= new dbmanager();

    public void initialize() {
        primary.showingProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> obs, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    AppBar appBar = MobileApplication.getInstance().getAppBar();
                    appBar.setNavIcon(MaterialDesignIcon.MENU.button(e ->
                            MobileApplication.getInstance().showLayer(GestorHorarios.MENU_LAYER)));
                    appBar.setTitleText("Login");
                    appBar.getActionItems().add(MaterialDesignIcon.SEARCH.button(e ->System.out.println("Search")));
                    
                }
            }
        });
    }
    
    @FXML
    void buttonClick() {
        label.setText("Hello JavaFX Universe!");
        try {
            m.prueba();
        } catch (Exception ex) {
            Logger.getLogger(PrimaryPresenter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
