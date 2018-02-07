package com.gestorhorarios;

import com.gestorhorarios.views.AgendaLaboralView;
import com.gestorhorarios.views.CambiosTurnoView;
import com.gestorhorarios.views.DatosEmpleadoView;
import com.gestorhorarios.views.PrimaryView;
import com.gestorhorarios.views.SecondaryView;
import com.gestorhorarios.views.EmpleadosView;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.layout.layer.SidePopupView;
import com.gluonhq.charm.glisten.visual.Swatch;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class GestorHorarios extends MobileApplication {

    public static final String PRIMARY_VIEW = HOME_VIEW;
    public static final String SECONDARY_VIEW = "Secondary View";//Secondary View
    public static final String MENU_LAYER = "Side Menu";
    public static final String AGENDA_LABORAL = "Agenda laboral";
    public static final String CAMBIOS_TURNO = "Cambios de turno";
    public static final String LISTA_EMPLEADOS ="Empleados";
    public static final String DATOS_EMPLEADO="Datos personales";
    
    @Override
    public void init() {
        addViewFactory(PRIMARY_VIEW, () -> new PrimaryView(PRIMARY_VIEW).getView());
        addViewFactory(SECONDARY_VIEW, () -> new SecondaryView(SECONDARY_VIEW).getView());
        addViewFactory(AGENDA_LABORAL, () -> new AgendaLaboralView(AGENDA_LABORAL).getView());
        addViewFactory(LISTA_EMPLEADOS, () -> new EmpleadosView(LISTA_EMPLEADOS).getView());
        addViewFactory(CAMBIOS_TURNO, () -> new CambiosTurnoView(CAMBIOS_TURNO).getView());
        addViewFactory(DATOS_EMPLEADO, () -> new DatosEmpleadoView(DATOS_EMPLEADO).getView());
        addLayerFactory(MENU_LAYER, () -> new SidePopupView(new DrawerManager().getDrawer()));
    }

    @Override
    public void postInit(Scene scene) {
        Swatch.BLUE.assignTo(scene);

        scene.getStylesheets().add(GestorHorarios.class.getResource("style.css").toExternalForm());
        ((Stage) scene.getWindow()).getIcons().add(new Image(GestorHorarios.class.getResourceAsStream("/icono_eroski.png")));
    }
}
