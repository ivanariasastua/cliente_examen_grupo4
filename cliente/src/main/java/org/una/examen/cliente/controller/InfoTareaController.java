/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.examen.cliente.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyEvent;
import org.una.examen.cliente.controller.util.Mensaje;
import org.una.examen.cliente.controller.util.Respuesta;
import org.una.examen.cliente.service.TareaService;
import org.una.examen.cliente.dto.TareaDTO;
import org.una.examen.cliente.dto.ProyectoDTO;
import org.una.examen.cliente.service.ProyectoService;

/**
 * FXML Controller class
 *
 * @author cordo
 */
public class InfoTareaController extends Controller implements Initializable {

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
    private ProyectoService proyService = new ProyectoService();
    List<ProyectoDTO> proyectos = new ArrayList<>();
    @FXML
    private JFXButton btnEliminar;
    @FXML
    private JFXTextField txtfechaIni;
    @FXML
    private JFXTextField txtFechaFin;

    TareaDTO tarSelec = new TareaDTO();
    boolean selec = false;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        llenarComboBox();

    }

    @Override
    public void initialize() {
        selec = false;
        btnEliminar.setVisible(false);
        llenarComboBox();
    }

    public void llenarComboBox() {
        Respuesta res = proyService.getAll();
        if (res.getEstado()) {
            proyectos = (List<ProyectoDTO>) res.getResultado("Proyectos");
            System.out.println(proyectos);
            ObservableList<ProyectoDTO> item = FXCollections.observableArrayList(proyectos);
            cbxProyecto.setItems(item);
        }
    }

    @FXML
    private void actGuardar(ActionEvent event) {
        if (selec) {
            if (validarCampos()) {
                tarSelec.setId(tarSelec.getId());
                tarSelec.setDescripcion(txtDescripcion.getText());
                tarSelec.setImportancia(Integer.valueOf(txtImportancia.getText()));
                tarSelec.setPorcentajeAvance(Integer.valueOf(txtPorcentaje.getText()));
                tarSelec.setProyecto(cbxProyecto.getValue());
                tarSelec.setUrgencia(Integer.valueOf(txtUrgencia.getText()));
                tarSelec.setNombre(txtNombre.getText());
                Respuesta res = tareaService.modificarTarea(tarSelec.getId(), tarSelec);
                if (res.getEstado()) {
                    Mensaje.show(Alert.AlertType.INFORMATION, "Editado", "Tarea editada correctamente");
                } else {
                    Mensaje.show(Alert.AlertType.ERROR, "Error", res.getMensaje());
                }
            }
        } else {
            if (validarCampos()) {
                tareaDTO.setDescripcion(txtDescripcion.getText());
                tareaDTO.setImportancia(Integer.valueOf(txtImportancia.getText()));
                tareaDTO.setPorcentajeAvance(Integer.valueOf(txtPorcentaje.getText()));
                tareaDTO.setProyecto(cbxProyecto.getValue());
                tareaDTO.setUrgencia(Integer.valueOf(txtUrgencia.getText()));
                tareaDTO.setNombre(txtNombre.getText());
                Respuesta res = tareaService.guardarTarea(tareaDTO);
                if (res.getEstado()) {
                    Mensaje.show(Alert.AlertType.INFORMATION, "Guardado", "Tarea guardada correctamente");
                } else {
                    Mensaje.show(Alert.AlertType.ERROR, "Error", res.getMensaje());
                }
            }
        }

    }

    public boolean validarCampos() {
        if (txtImportancia.getText() == null || txtNombre.getText() == null || txtPorcentaje.getText() == null || txtUrgencia.getText() == null || cbxProyecto.getValue() == null) {
            Mensaje.show(Alert.AlertType.WARNING, "Campos requeridos", "Los siguientes campos son obligatorios \n*Nombre\n*Importancia\n*Urgencia\n*Porcentaje\n*Proyecto");
            return false;
        }
        return true;
    }

    public void limpiar() {
        txtDescripcion.setText(null);
        txtImportancia.setText(null);
        txtNombre.setText(null);
        txtPorcentaje.setText(null);
        txtPrioridad.setText(null);
        txtUrgencia.setText(null);
        cbxProyecto.setValue(null);
        txtfechaIni.setText(null);
        txtFechaFin.setText(null);
        tarSelec = new TareaDTO();
        tareaDTO = new TareaDTO();
        btnEliminar.setVisible(false);
        selec = false;
    }

    @FXML
    private void actLiimpiar(ActionEvent event) {
        limpiar();
    }

    public void cargarDatos(TareaDTO tarea) {
        tarSelec = tarea;
        selec = true;
        btnEliminar.setVisible(true);
        txtDescripcion.setText(tarea.getDescripcion());
        txtImportancia.setText(String.valueOf(tarea.getImportancia()));
        txtNombre.setText(tarea.getNombre());
        txtPorcentaje.setText(String.valueOf(tarea.getPorcentajeAvance()));
        cbxProyecto.setValue(tarea.getProyecto());
        //  txtPrioridad.setText(String.valueOf(tarea.));
        txtUrgencia.setText(String.valueOf(tarea.getUrgencia()));
        txtfechaIni.setText(tarea.getFechaInicio().toString());
        txtFechaFin.setText(tarea.getFechaFinalizacion().toString());
    }

    @FXML
    private void actEliminar(ActionEvent event) {
        if (Mensaje.showConfirmation("Eliminar la tarea", null, "Desea eliminar la tarea?")) {
            Respuesta res = tareaService.deleteTarea(tarSelec.getId());
            if (res.getEstado()) {
                Mensaje.show(Alert.AlertType.INFORMATION, "Eliminado", "Tarea eliminada correctamente");
                limpiar();
                selec = false;
            } else {
                Mensaje.show(Alert.AlertType.ERROR, "Error", res.getMensaje());
            }
        }
    }

    public boolean calcularPriorid() {
        if (txtImportancia.getText() == null || txtImportancia.getText().isEmpty() || txtImportancia.getText().equals("")) {
            return false;
        }
        if (txtUrgencia.getText() == null || txtUrgencia.getText().isEmpty() || txtUrgencia.getText().equals("")) {
            return false;
        }
        return true;
    }

    @FXML
    private void actCalcularPrioridad(ActionEvent event) {
        if (calcularPriorid()) {
            Integer num1 = Integer.valueOf(txtImportancia.getText());
            Integer num2 = Integer.valueOf(txtUrgencia.getText());
            Integer resultado = num1 * num2;
            txtPrioridad.setText(String.valueOf(resultado));
        } else {
            Mensaje.show(Alert.AlertType.WARNING, "Campos requeridos", "Es necesario ingresar importancia y urgencia");
        }
    }

}
