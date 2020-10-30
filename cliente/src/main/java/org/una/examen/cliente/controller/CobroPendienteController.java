/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.examen.cliente.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeView;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import org.una.examen.cliente.controller.util.Respuesta;
import org.una.examen.cliente.service.ClienteService;
import org.una.examen.cliente.dto.ClienteDTO;
import org.una.examen.cliente.dto.MembresiaDTO;
import org.una.examen.cliente.service.MembresiaService;
import org.una.examen.cliente.controller.util.Mensaje;
import org.una.examen.cliente.service.CobroPendienteService;
import org.una.examen.cliente.dto.CobroPendienteDTO;

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
    @FXML
    private JFXComboBox<MembresiaDTO> cbMembresia;
    @FXML
    private JFXTreeView<ClienteDTO> treeCobrosPendientes;
    @FXML
    private Label lbAviso;
    
    Respuesta respuesta;
    ClienteService clienteService;
    MembresiaService membresiaService;
    CobroPendienteService cobroService;
    ClienteDTO cliente;
    MembresiaDTO membresia;
    CobroPendienteDTO cobro;
    Map<String,Integer> periodicidad;
    String fecha = "yyyy-MM-dd";
    SimpleDateFormat formatoFecha = new SimpleDateFormat(fecha);
    
    @Override
    public void initialize() {
        clienteService = new ClienteService();
        membresiaService = new MembresiaService();
        cobroService = new CobroPendienteService();
        iniciarComboBoxPeriodicidad();
        iniciarComboBoxMembresia();
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
    
    public void iniciarComboBoxMembresia(){
        respuesta = membresiaService.getAll();
        if(respuesta.getEstado()){
            cbMembresia.getItems().clear();
            cbMembresia.getItems().addAll( (List<MembresiaDTO>) respuesta.getResultado("Membresias") );
        }else{
            System.out.println("Nada");
        }
    }
    
    public String obtenerPeriodicidad(int periodo){
        if(periodo == 1){
            return "Mensual";
        }else if(periodo == 2){
            return "Bimensual";
        }else if(periodo == 3){
            return "Trimestral";
        }else if(periodo == 4){
            return "Cuatrimestral";
        }else if(periodo == 5){
            return "Semestral";
        }else{
            return "Anual";
        }
    }
    
    public boolean validarInfoCliente(){
        return !txtNombre.getText().isBlank() && !txtCedula.getText().isBlank()
               && cbMembresia.getSelectionModel().getSelectedItem() != null;
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
    
    public void crearCobrosMensual(ClienteDTO c){
        Calendar fecha = Calendar.getInstance();
        float monto = c.getMembresia().getMonto()/12;
        for(int a=0; a<12;a++){
            fecha.add(Calendar.MONTH, 1);
            cobro = new CobroPendienteDTO();
            cobro.setMonto(monto);
            cobro.setFechaVencimiento(fecha.getTime());
            cobro.setPeriodo(c.getMembresia().getPeriodicidad());
            cobro.setCliente(c);
            respuesta = cobroService.guardarCobroPendiente(cobro);
            if(respuesta.getEstado()){
                c.getCobros().add( (CobroPendienteDTO) respuesta.getResultado("Cobros") );
            }
        }
    }
    
    public void crearCobrosBimensual(ClienteDTO c){
        Calendar fecha = Calendar.getInstance();
        float monto = c.getMembresia().getMonto()/24;
        for(int a=0; a<24;a++){
            fecha.add(Calendar.DAY_OF_MONTH, 15);
            cobro = new CobroPendienteDTO();
            cobro.setMonto(monto);
            cobro.setFechaVencimiento(fecha.getTime());
            cobro.setPeriodo(c.getMembresia().getPeriodicidad());
            cobro.setCliente(c);
            respuesta = cobroService.guardarCobroPendiente(cobro);
            if(respuesta.getEstado()){
                c.getCobros().add( (CobroPendienteDTO) respuesta.getResultado("Cobros") );
            }
        }
    }
    
    public void crearCobrosTrimentral(ClienteDTO c){
        Calendar fecha = Calendar.getInstance();
        float monto = c.getMembresia().getMonto()/4;
        for(int a=0; a<4;a++){
            fecha.add(Calendar.MONTH, 3);
            cobro = new CobroPendienteDTO();
            cobro.setMonto(monto);
            cobro.setFechaVencimiento(fecha.getTime());
            cobro.setPeriodo(c.getMembresia().getPeriodicidad());
            cobro.setCliente(c);
            respuesta = cobroService.guardarCobroPendiente(cobro);
            if(respuesta.getEstado()){
                c.getCobros().add( (CobroPendienteDTO) respuesta.getResultado("Cobros") );
            }
        }
    }
    
    public void crearCobrosCuatrimentral(ClienteDTO c){
        Calendar fecha = Calendar.getInstance();
        float monto = c.getMembresia().getMonto()/3;
        for(int a=0; a<3;a++){
            fecha.add(Calendar.MONTH, 4);
            cobro = new CobroPendienteDTO();
            cobro.setMonto(monto);
            cobro.setFechaVencimiento(fecha.getTime());
            cobro.setPeriodo(c.getMembresia().getPeriodicidad());
            cobro.setCliente(c);
            respuesta = cobroService.guardarCobroPendiente(cobro);
            if(respuesta.getEstado()){
                c.getCobros().add( (CobroPendienteDTO) respuesta.getResultado("Cobros") );
            }
        }
    }
    
    public void crearCobrosSemestral(ClienteDTO c){
        Calendar fecha = Calendar.getInstance();
        float monto = c.getMembresia().getMonto()/2;
        for(int a=0; a<2;a++){
            fecha.add(Calendar.MONTH, 6);
            cobro = new CobroPendienteDTO();
            cobro.setMonto(monto);
            cobro.setFechaVencimiento(fecha.getTime());
            cobro.setPeriodo(c.getMembresia().getPeriodicidad());
            cobro.setCliente(c);
            respuesta = cobroService.guardarCobroPendiente(cobro);
            if(respuesta.getEstado()){
                c.getCobros().add( (CobroPendienteDTO) respuesta.getResultado("Cobros") );
            }
        }
    }
    
    public void crearCobrosAnual(ClienteDTO c){
        Calendar fecha = Calendar.getInstance();
        float monto = c.getMembresia().getMonto();
        fecha.add(Calendar.YEAR, 1);
        cobro = new CobroPendienteDTO();
        cobro.setMonto(monto);
        cobro.setFechaVencimiento(fecha.getTime());
        cobro.setPeriodo(c.getMembresia().getPeriodicidad());
        cobro.setCliente(c);
        respuesta = cobroService.guardarCobroPendiente(cobro);
        if(respuesta.getEstado()){
            c.getCobros().add( (CobroPendienteDTO) respuesta.getResultado("Cobros") );
        }
    }
    
    public void mostrarArbolJerarquico(List<ClienteDTO> clientes){
        TreeItem root = new TreeItem<>("Clientes");
        treeCobrosPendientes.setRoot(root);
        clientes.stream().forEach(c->{
            TreeItem<String> tItem = new TreeItem<>(c.getNombre());
            root.getChildren().add(tItem);
            c.getCobros().stream().forEach(cob->{
                TreeItem<String> tItemcob = new TreeItem<>(String.valueOf("Monto: "+ cob.getMonto()+"\n" 
                +"Fecha de vencimiento: "+ formatoFecha.format(cob.getFechaVencimiento())+"\n"
                +obtenerPeriodicidad(cob.getPeriodo())));
                tItem.getChildren().add(tItemcob);
            });
        });
    }
    
    @FXML
    private void actAgregarCliente(ActionEvent event) {
        if(validarInfoCliente()){
            cliente = new ClienteDTO();
            cliente.setNombre(txtNombre.getText());
            cliente.setCedula(txtCedula.getText());
            cliente.setMembresia(cbMembresia.getSelectionModel().getSelectedItem());
            respuesta = clienteService.guardarCliente(cliente);
            if(respuesta.getEstado()){
                Mensaje.show(Alert.AlertType.CONFIRMATION, "Guardado Exitoso", "Se guardó el cliente con éxito");
                txtCedula.clear();
                txtNombre.clear();
                cliente = (ClienteDTO) respuesta.getResultado("Clientes");
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
                    cbMembresia.getItems().add( (MembresiaDTO) respuesta.getResultado("Membresias") );
                    txtDescripcion.clear();
                    txtMonto.clear();
                    membresia = (MembresiaDTO) respuesta.getResultado("Membresias");
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

    @FXML
    private void actGenerarCobros(ActionEvent event) {
        respuesta = cobroService.borrarTodos();
        respuesta = clienteService.getAll();
        
        List<ClienteDTO> clientes = ( List<ClienteDTO> )respuesta.getResultado("Clientes");
        for(ClienteDTO c: clientes){
            if(c.getMembresia().getPeriodicidad() == 1){
                crearCobrosMensual(c);
            }else if(c.getMembresia().getPeriodicidad() == 2){
                crearCobrosBimensual(c);
            }else if(c.getMembresia().getPeriodicidad() == 3){
                crearCobrosTrimentral(c);
            }else if(c.getMembresia().getPeriodicidad() == 4){
                crearCobrosCuatrimentral(c);
            }else if(c.getMembresia().getPeriodicidad() == 5){
                crearCobrosSemestral(c);
            }else{
                crearCobrosAnual(c);
            }
        }
        
        treeCobrosPendientes.setRoot(null);
        mostrarArbolJerarquico(clientes);
        lbAviso.setText("Cobros generador");
    }
}
