/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.examen.cliente.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import org.una.examen.cliente.controller.util.FlowController;

/**
 * FXML Controller class
 *
 * @author cordo
 */
public class InicioController extends Controller implements Initializable {

    @FXML
    private BorderPane pane;
    @FXML
    private VBox vbox;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void actNuevoProyecto(ActionEvent event) {
        FlowController.getInstance().goViewPanel(vbox, "InfoProyecto");
    }

    @FXML
    private void actNuevaTarea(ActionEvent event) {
        FlowController.getInstance().goViewPanel(vbox, "InfoTarea");
    }

    @FXML
    private void actInicio(ActionEvent event) {
        FlowController.getInstance().goViewPanel(vbox, "Arbol");
    }

    @Override
    public void initialize() {
    }
    
}
