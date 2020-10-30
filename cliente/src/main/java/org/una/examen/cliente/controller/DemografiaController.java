/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.examen.cliente.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import org.una.examen.cliente.controller.util.AppContext;
import org.una.examen.cliente.controller.util.FlowController;
import org.una.examen.cliente.service.ProvinciaService;

/**
 * FXML Controller class
 *
 * @author Ivan Josué Arias Astua
 */
public class DemografiaController extends Controller implements Initializable {

    @FXML
    private Pane paneContenedor;

    private final ProvinciaService service = new ProvinciaService();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    

    @FXML
    private void actProvincias(ActionEvent event) {
        AppContext.getInstance().set("Uso", 1);
        AppContext.getInstance().set("Uso2", 0);
        FlowController.getInstance().goViewPanel(paneContenedor, "BuscarSector");
    }

    @FXML
    private void actCantones(ActionEvent event) {
        AppContext.getInstance().set("Uso", 2);
        AppContext.getInstance().set("Uso2", 0);
        FlowController.getInstance().goViewPanel(paneContenedor, "BuscarSector");
    }

    @FXML
    private void actDistrito(ActionEvent event) {
        AppContext.getInstance().set("Uso", 3);
        AppContext.getInstance().set("Uso2", 0);
        FlowController.getInstance().goViewPanel(paneContenedor, "BuscarSector");
    }

    @FXML
    private void actUnidades(ActionEvent event) {
        AppContext.getInstance().set("Uso", 4);
        AppContext.getInstance().set("Uso2", 0);
        FlowController.getInstance().goViewPanel(paneContenedor, "BuscarSector");
    }

    @FXML
    private void actVerInformacion(ActionEvent event) {
        FlowController.getInstance().goViewPanel(paneContenedor, "VerInformacion");
    }

    @Override
    public void initialize() {
    }
    
}
