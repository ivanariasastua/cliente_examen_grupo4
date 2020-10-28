/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.examen.cliente.controller;

import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.una.examen.cliente.App;
import org.una.examen.cliente.controller.util.FlowController;
import org.una.examen.cliente.controller.util.Mensaje;
import org.una.examen.cliente.controller.util.Respuesta;
import org.una.examen.cliente.dto.ProyectoDTO;
import org.una.examen.cliente.service.ProyectoService;

/**
 * FXML Controller class
 *
 * @author cordo
 */
public class InfoProyectoController extends Controller implements Initializable {

    @FXML
    private JFXTextField txtNombre;
    @FXML
    private JFXTextField txtfechaIni;
    @FXML
    private JFXTextField txtfechaFin;

    private ProyectoDTO proyDto = new ProyectoDTO();
    private ProyectoService proyService = new ProyectoService();
    @FXML
    private BorderPane pane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @Override
    public void initialize() {
    }

    @FXML
    private void actGuardar(ActionEvent event) {
        if (txtNombre.getText() != null) {
            proyDto.setNombre(txtNombre.getText());
            Respuesta res = proyService.guardarProyecto(proyDto);
            if (res.getEstado()) {
                Mensaje.show(Alert.AlertType.INFORMATION, "Guardado", "Proyecto guardado correctamente");
            } else {
                Mensaje.show(Alert.AlertType.ERROR, "Error", res.getMensaje());
            }
        } else {
            Mensaje.show(Alert.AlertType.WARNING, "Campos requeridos", "El campo nombre es obligatorio");
        }

    }

    @FXML
    private void actAtras(ActionEvent event) throws IOException {
    }

}
