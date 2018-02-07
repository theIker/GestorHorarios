package com.gestorhorarios;

import com.gestorhorarios.views.PrimaryView;
import com.gestorhorarios.views.SecondaryView;
import com.gestorhorarios.views.VistaLoginUsuarioView;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.layout.layer.SidePopupView;
import com.gluonhq.charm.glisten.visual.Swatch;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import models.Usuario;

public class GestorHorarios extends MobileApplication {
    public static Usuario usuario = new Usuario();
    public static final String LOGIN = HOME_VIEW;
    public static final String PRIMARY_VIEW = "Primary View";
    public static final String SECONDARY_VIEW = "Secondary View";
    public static final String MENU_LAYER = "Side Menu";
    
    
    
    
    @Override
    public void init() {
        addViewFactory(PRIMARY_VIEW, () -> new PrimaryView(PRIMARY_VIEW).getView());
        addViewFactory(SECONDARY_VIEW, () -> new SecondaryView(SECONDARY_VIEW).getView());
        addViewFactory(LOGIN, () -> new VistaLoginUsuarioView(LOGIN).getView());  
        addLayerFactory(MENU_LAYER, () -> new SidePopupView(new DrawerManager().getDrawer()));
    }

    @Override
    public void postInit(Scene scene) {
        Swatch.BLUE.assignTo(scene);

        scene.getStylesheets().add(GestorHorarios.class.getResource("style.css").toExternalForm());
        ((Stage) scene.getWindow()).getIcons().add(new Image(GestorHorarios.class.getResourceAsStream("/icon.png")));
    }
}
