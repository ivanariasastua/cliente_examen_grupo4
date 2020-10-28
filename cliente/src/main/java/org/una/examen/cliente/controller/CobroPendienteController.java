/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.examen.cliente.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.text.Format;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import org.una.examen.cliente.controller.util.Respuesta;
import org.una.examen.cliente.service.ClienteService;
import org.una.examen.cliente.dto.ClienteDTO;
import org.una.examen.cliente.dto.MembresiaDTO;
import org.una.examen.cliente.service.MembresiaService;
import org.una.examen.cliente.controller.util.Mensaje;
import org.una.examen.cliente.controller.util.Formato;

public class CobroPendienteController extends Controller implements Initializable{

    @FXML
    private JFXTextField txtNombre;
    @FXML
    private JFXTextField txtCedula;
    @FXML
    private JFXTextField txtMonto;
    @FXML
    private JFXTextField txtDescripcion;
    @FXML
    private JFXComboBox<String> cbPeriodicidad;
    
    Respuesta respuesta;
    ClienteService clienteService;
    MembresiaService membresiaService;
    ClienteDTO cliente;
    MembresiaDTO membresia;
    Map<String,Integer> periodicidad;
    
    @Override
    public void initialize() {
        clienteService = new ClienteService();
        membresiaService = new MembresiaService();
        iniciarComboBoxPeriodicidad();
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        
    }
    
    public void iniciarComboBoxPeriodicidad(){
        periodicidad = new HashMap();
        periodicidad.put("Mensual", 1);
        periodicidad.put("Bimensual", 2);
        periodicidad.put("Trimestral", 3);
        periodicidad.put("Cuatrimentral", 4);
        periodicidad.put("Sementral", 5);
        periodicidad.put("Anual", 6);
        
        List<String> periodos = new ArrayList<>();
        periodos.add("Mensual");
        periodos.add("Bimensual");
        periodos.add("Trimestral");
        periodos.add("Cuatrimentral");
        periodos.add("Sementral");
        periodos.add("Anual");
        cbPeriodicidad.getItems().addAll(periodos);
    }
    
    public boolean validarInfoCliente(){
        return !txtNombre.getText().isBlank() && !txtCedula.getText().isBlank();
    }
    
    public boolean validadInfoMembresia(){
        return !txtDescripcion.getText().isBlank() && !txtMonto.getText().isBlank() 
               && cbPeriodicidad.getSelectionModel().getSelectedItem() != null;
    }
    
    public boolean validarMontoMembresia(){
        try{
            Float.parseFloat(txtMonto.getText());
            return true;
        }catch(NumberFormatException ex){
            return false;
        }
    }

    @FXML
    private void actAgregarCliente(ActionEvent event) {
        if(validarInfoCliente()){
            cliente = new ClienteDTO();
            cliente.setNombre(txtNombre.getText());
            cliente.setCedula(txtCedula.getText());
            respuesta = clienteService.guardarCliente(cliente);
            if(respuesta.getEstado()){
                Mensaje.show(Alert.AlertType.CONFIRMATION, "Guardado Exitoso", "Se guardó el cliente con éxito");
                txtCedula.clear();
                txtNombre.clear();
            }else{
                Mensaje.show(Alert.AlertType.ERROR, "Error", "No se guardó el cliente\n" + respuesta.getMensaje());
            }
        }else{
            Mensaje.show(Alert.AlertType.WARNING, "Datos Faltantes", "Por favor, digite toda la información para guardar el cliente");
        }
    }

    @FXML
    private void actAgregarMembresía(ActionEvent event) {
        if(validarMontoMembresia()){
            if(validadInfoMembresia()){
                membresia = new MembresiaDTO();
                membresia.setDescripcion(txtDescripcion.getText());
                membresia.setMonto(Float.parseFloat(txtMonto.getText()));
                membresia.setPeriodicidad(periodicidad.get(cbPeriodicidad.getSelectionModel().getSelectedItem()));
                respuesta = membresiaService.guardarMembresia(membresia);
                if(respuesta.getEstado()){
                    Mensaje.show(Alert.AlertType.CONFIRMATION, "Guardado Exitoso", "Se guardó la membresía con éxito");
                }else{
                    Mensaje.show(Alert.AlertType.ERROR, "Error", "No se guardó el cliente\n" + respuesta.getMensaje());
                }
            }else{
                Mensaje.show(Alert.AlertType.WARNING, "Datos Faltantes", "Por favor, digite toda la información para guardar la membresía");
            }
        }else{
            Mensaje.show(Alert.AlertType.WARNING, "Monto Inválido", "Por favor, digite números en el campo del monto");
        }
    }
    
}
