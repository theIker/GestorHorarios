package com.gestorhorarios;

import com.gestorhorarios.logic.ManagerFactory;
import com.gestorhorarios.views.LoginUsuarioView;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.layout.layer.SidePopupView;
import com.gluonhq.charm.glisten.visual.Swatch;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import com.gestorhorarios.logic.models.Usuario;
import com.gestorhorarios.views.AgendaLaboralView;
import com.gestorhorarios.views.CambiosTurnoView;
import com.gestorhorarios.views.DatosEmpleadoView;
import com.gestorhorarios.views.EmpleadosView;

public class GestorHorarios extends MobileApplication {
    
    public static ManagerFactory managerFactory = new ManagerFactory();
    
    public static DrawerManager drawer;
    
    
    public static final String LOGIN = HOME_VIEW;
    public static final String AGENDA_LABORAL = "Agenda laboral";
    public static final String CAMBIOS_TURNO = "Cambios de turno";
    public static final String DATOS_EMPLEADO = "Datos empleado";
    public static final String LISTA_EMPLEADOS = "Lista empleados";
    public static final String MENU_LAYER = "Side Menu";
    
    
    
    
    @Override
    public void init() {
        addViewFactory(LISTA_EMPLEADOS, () -> new EmpleadosView(LISTA_EMPLEADOS).getView());
        addViewFactory(AGENDA_LABORAL, () -> new AgendaLaboralView(AGENDA_LABORAL).getView());
        addViewFactory(CAMBIOS_TURNO, () -> new CambiosTurnoView(CAMBIOS_TURNO).getView());
        addViewFactory(DATOS_EMPLEADO, () -> new DatosEmpleadoView(DATOS_EMPLEADO).getView());
        addViewFactory(LOGIN, () -> new LoginUsuarioView(LOGIN).getView());
        drawer = new DrawerManager();
        addLayerFactory(MENU_LAYER, () -> new SidePopupView(drawer.getDrawer()));
        
        
    }

    @Override
    public void postInit(Scene scene) {
        Swatch.BLUE.assignTo(scene);
        scene.getStylesheets().add(GestorHorarios.class.getResource("style.css").toExternalForm());
        ((Stage) scene.getWindow()).getIcons().add(new Image(GestorHorarios.class.getResourceAsStream("/icono_eroski.png")));
    }
}
