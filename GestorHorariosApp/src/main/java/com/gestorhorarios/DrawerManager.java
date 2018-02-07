package com.gestorhorarios;

import static com.gestorhorarios.GestorHorarios.AGENDA_LABORAL;
import static com.gestorhorarios.GestorHorarios.CAMBIOS_TURNO;
import static com.gestorhorarios.GestorHorarios.DATOS_EMPLEADO;
import static com.gestorhorarios.GestorHorarios.LOGIN;
import com.gluonhq.charm.down.Platform;
import com.gluonhq.charm.down.Services;
import com.gluonhq.charm.down.plugins.LifecycleService;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.application.ViewStackPolicy;
import com.gluonhq.charm.glisten.control.Avatar;
import com.gluonhq.charm.glisten.control.NavigationDrawer;
import com.gluonhq.charm.glisten.control.NavigationDrawer.Item;
import com.gluonhq.charm.glisten.control.NavigationDrawer.ViewItem;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import static com.gestorhorarios.GestorHorarios.MENU_LAYER;
import javafx.scene.Node;
import javafx.scene.image.Image;
import com.gestorhorarios.logic.models.Usuario;

public class DrawerManager {
   
    private NavigationDrawer drawer;
    private ViewItem item1;
    private ViewItem item2;
    private ViewItem item3;
    public DrawerManager() {
        
        this.drawer = new NavigationDrawer();
        
        NavigationDrawer.Header header = new NavigationDrawer.Header("Eroski",
                "Gestor de horarios",                                           //Añadir nuestro icono
                new Avatar(21, new Image(DrawerManager.class.getResourceAsStream("/icon.png"))));
        drawer.setHeader(header);
            item1 = new ViewItem("Agenda laboral", MaterialDesignIcon.HOME.graphic(), AGENDA_LABORAL, ViewStackPolicy.SKIP);
            item2 = new ViewItem("Datos personales", MaterialDesignIcon.DASHBOARD.graphic(), DATOS_EMPLEADO);
            item3 = new ViewItem("Cambios de turno", MaterialDesignIcon.DASHBOARD.graphic(), CAMBIOS_TURNO);
                  
        
        //final Item loginItem = new ViewItem("Login",MaterialDesignIcon.HOME.graphic(), LOGIN );
        
        //Si la aplicacion se ejecuta en un escritorio se le añade el boton QUIT
        
        if (Platform.isDesktop()) {
            final Item quitItem = new Item("Quit", MaterialDesignIcon.EXIT_TO_APP.graphic());
            quitItem.selectedProperty().addListener((obs, ov, nv) -> {
                if (nv) {
                    Services.get(LifecycleService.class).ifPresent(LifecycleService::shutdown);
                }
            });
            drawer.getItems().add(quitItem);
        }
        
        drawer.addEventHandler(NavigationDrawer.ITEM_SELECTED, 
                e -> MobileApplication.getInstance().hideLayer(MENU_LAYER));
        
//        MobileApplication.getInstance().viewProperty().addListener((obs, oldView, newView) -> updateItem(newView.getName()));
//        updateItem(PRIMARY_VIEW);
    }
    
    public void updateItem(String nameView) {
        for (Node item : drawer.getItems()) {
            if (item instanceof ViewItem && ((ViewItem) item).getViewName().equals(nameView)) {
                drawer.setSelectedItem(item);
                break;
            }
        }
    }
    
    public NavigationDrawer getDrawer() {
        return drawer;
    }
    
    public void agnadirItem(Usuario usuario){
       // if(usuario.getPerfil().compareTo("empleado")==0){
            drawer.getItems().addAll(item1, item2,item3);
       // }
    }

}
