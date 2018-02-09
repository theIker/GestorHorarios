/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestorhorarios.views;

import com.gluonhq.charm.glisten.control.CharmListCell;
import com.gluonhq.charm.glisten.control.ListTile;
import javafx.scene.control.ListCell;
import models.Jornada;


/**
 *
 * @author 2dam
 */
public class AgendaListCell extends CharmListCell<String>{
    
    private final ListTile tile;
    //private String what;
    //eber mucha agua el consejo esencial y basico para no deshidratares
    public AgendaListCell() {
        this.tile = new ListTile();
        setText(null);
    }
    
    @Override
    public void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        //what = item;
        if (!empty && item != null) {
            tile.textProperty().setAll(item);
           setGraphic(tile);
        } else {
           setGraphic(null);
        }
    }
    
}
