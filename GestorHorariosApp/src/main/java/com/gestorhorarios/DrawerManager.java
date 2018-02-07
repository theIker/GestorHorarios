package com.gestorhorarios;

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
import static com.gestorhorarios.GestorHorarios.PRIMARY_VIEW;
import static com.gestorhorarios.GestorHorarios.SECONDARY_VIEW;
import static com.gestorhorarios.GestorHorarios.AGENDA_LABORAL;
import static com.gestorhorarios.GestorHorarios.CAMBIOS_TURNO;
import static com.gestorhorarios.GestorHorarios.LISTA_EMPLEADOS;
import static com.gestorhorarios.GestorHorarios.DATOS_EMPLEADO;
import javafx.scene.Node;
import javafx.scene.image.Image;

public class DrawerManager {

    private final NavigationDrawer drawer;

    public DrawerManager() {
        this.drawer = new NavigationDrawer();
        
        NavigationDrawer.Header header = new NavigationDrawer.Header("Eroski",
                "Gestor de turnos",
                new Avatar(21, new Image(DrawerManager.class.getResourceAsStream("/icono_eroski.png"))));
        drawer.setHeader(header);
        
        final Item primaryItem = new ViewItem("Login", MaterialDesignIcon.HOME.graphic(), PRIMARY_VIEW, ViewStackPolicy.SKIP);
        final Item datosItem = new ViewItem("Datos personales", MaterialDesignIcon.ACCOUNT_CIRCLE.graphic(), DATOS_EMPLEADO, ViewStackPolicy.SKIP); //ICONO DATOS PERSONALES
        final Item secondaryItem = new ViewItem("Empleados", MaterialDesignIcon.SUPERVISOR_ACCOUNT.graphic(), LISTA_EMPLEADOS);//Secondary ICONO EMPLEADOS
        final Item agendaItem = new ViewItem("Agenda laboral", MaterialDesignIcon.DATE_RANGE.graphic(), AGENDA_LABORAL);//Secondary ICONO AGENDA
        final Item turnosItem = new ViewItem("Cambios de turno", MaterialDesignIcon.VIEW_LIST.graphic(), CAMBIOS_TURNO);
        drawer.getItems().addAll(primaryItem,datosItem, secondaryItem,agendaItem,turnosItem);
        
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
        
        MobileApplication.getInstance().viewProperty().addListener((obs, oldView, newView) -> updateItem(newView.getName()));
        updateItem(PRIMARY_VIEW);
    }
    
    private void updateItem(String nameView) {
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
}
