/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.examen.cliente.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.BorderPane;
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
    private DatePicker txtfechaIni;
    @FXML
    private DatePicker txtfechaFin;

    private ProyectoDTO proyDto = new ProyectoDTO();
    private ProyectoService proyService = new ProyectoService();
    @FXML
    private BorderPane pane;
    @FXML
    private JFXButton btnEliminar;

    ProyectoDTO proySelec = new ProyectoDTO();

    boolean selec = false;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

    }

    @Override
    public void initialize() {
        btnEliminar.setVisible(false);
        selec = false;
    }
    String hora = "HH:mm:ss";
    String fecha = "yyyy-MM-dd";
    SimpleDateFormat formatoHora = new SimpleDateFormat(hora);
    SimpleDateFormat formatoFecha = new SimpleDateFormat(fecha);

    @FXML
    private void actGuardar(ActionEvent event) {
        if (selec == true) {
            if (txtNombre.getText() != null) {
                proySelec.setId(proySelec.getId());
                proySelec.setNombre(txtNombre.getText());
                Date fin = DateUtils.asDate(txtfechaFin.getValue());
                Date ini = DateUtils.asDate(txtfechaIni.getValue());
                proySelec.setFechaInicio(ini);
                proySelec.setFechaFinalizacion(fin);
                Respuesta res = proyService.modificarProyecto(proySelec.getId(), proySelec);
                if (res.getEstado()) {
                    Mensaje.show(Alert.AlertType.INFORMATION, "Editado", "Proyecto editado correctamente");
                } else {
                    Mensaje.show(Alert.AlertType.ERROR, "Error", res.getMensaje());
                }
            } else {
                Mensaje.show(Alert.AlertType.WARNING, "Campos requeridos", "El campo nombre es obligatorio");
            }
        } else {
            if (txtNombre.getText() != null || txtNombre.getText().isEmpty() || txtfechaFin.getValue() != null || txtfechaIni.getValue() != null) {
                proyDto.setNombre(txtNombre.getText());
                Date fin = DateUtils.asDate(txtfechaFin.getValue());
                Date ini = DateUtils.asDate(txtfechaIni.getValue());
                proyDto.setFechaInicio(ini);
                proyDto.setFechaFinalizacion(fin);
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
    }

    public void cargarDatos(ProyectoDTO proy) {
        selec = true;
        proySelec = proy;
        btnEliminar.setVisible(true);
        txtNombre.setText(proy.getNombre());
        LocalDate ini = DateUtils.asLocalDate(proy.getFechaInicio());
        LocalDate fin = DateUtils.asLocalDate(proy.getFechaFinalizacion());
        txtfechaIni.setValue(ini);
        txtfechaFin.setValue(fin);
    }

    @FXML
    private void actEliminar(ActionEvent event) {
        if (Mensaje.showConfirmation("Eliminar proyecto", null, "Desea eliminar el proyecto?")) {
            Respuesta res = proyService.deleteProyecto(proySelec.getId());
            if (res.getEstado()) {
                Mensaje.show(Alert.AlertType.INFORMATION, "Eliminado", "Proyecto eliminado correctamente");
                limpiar();
            } else {
                Mensaje.show(Alert.AlertType.ERROR, "Error", res.getMensaje());
            }
        }
    }

    public void limpiar() {
        proyDto = new ProyectoDTO();
        proySelec = new ProyectoDTO();
        txtNombre.setText(null);
        txtfechaFin.setValue(null);
        txtfechaIni.setValue(null);
        btnEliminar.setVisible(false);
        selec = false;
    }

    @FXML
    private void actLimpiar(ActionEvent event) {
        limpiar();
    }

}
