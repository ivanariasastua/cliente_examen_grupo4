/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.examen.cliente.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import org.una.examen.cliente.service.TareaService;
import org.una.examen.cliente.dto.TareaDTO;
import org.una.examen.cliente.dto.ProyectoDTO;
/**
 * FXML Controller class
 *
 * @author cordo
 */
public class InfoTareaController implements Initializable {

    @FXML
    private JFXTextField txtNombre;
    @FXML
    private JFXTextField txtImportancia;
    @FXML
    private JFXTextArea txtDescripcion;
    @FXML
    private JFXComboBox<ProyectoDTO> cbxProyecto;
    @FXML
    private JFXTextField txtUrgencia;
    @FXML
    private JFXTextField txtPrioridad;
    @FXML
    private JFXTextField txtPorcentaje;

    
    private TareaDTO tareaDTO = new TareaDTO();
    private TareaService tareaService = new TareaService();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void actGuardar(ActionEvent event) {
    }

    @FXML
    private void actLiimpiar(ActionEvent event) {
    }
    
}
