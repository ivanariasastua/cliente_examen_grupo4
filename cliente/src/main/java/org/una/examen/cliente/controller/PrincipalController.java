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
import javafx.stage.StageStyle;
import org.una.examen.cliente.controller.util.FlowController;

/**
 * FXML Controller class
 *
 * @author Ivan Josué Arias Astua
 */
public class PrincipalController extends Controller implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    

    @Override
    public void initialize() {
    }

    @FXML
    private void actTareas(ActionEvent event) {
        this.closeWindow();
        FlowController.getInstance().goViewInNoResizableWindow("Inicio", false, StageStyle.UTILITY);
    }

    @FXML
    private void actProvincias(ActionEvent event) {
        this.closeWindow();
        FlowController.getInstance().goViewInNoResizableWindow("Demografia", false, StageStyle.UTILITY);
    }

    @FXML
    private void actCobros(ActionEvent event) {
    }
    
}
