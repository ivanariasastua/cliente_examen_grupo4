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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.una.examen.cliente.App;
import org.una.examen.cliente.controller.util.AppContext;
import org.una.examen.cliente.controller.util.Mensaje;
import org.una.examen.cliente.controller.util.Respuesta;
import org.una.examen.cliente.dto.CantonDTO;
import org.una.examen.cliente.dto.ProvinceDTO;
import org.una.examen.cliente.dto.ProvinciaDTO;
import org.una.examen.cliente.service.CantonService;
import org.una.examen.cliente.service.ProvinciaService;

/**
 * FXML Controller class
 *
 * @author Ivan Josué Arias Astua
 */
public class AgregarSectorController extends Controller implements Initializable {

    @FXML private RowConstraints rowSector;
    @FXML private JFXTextField txtNombre;
    @FXML private JFXTextField txtCodigo;
    @FXML private JFXTextField txtSectorPertenece;
    @FXML private RowConstraints rowNombre;
    @FXML private RowConstraints rowCodigo;
    
    private final ProvinciaService provinciaService = new ProvinciaService();
    private final CantonService cantonService = new CantonService();
    
    private Integer uso;
    private Boolean agregar;
    
    private ProvinciaDTO proviciaSelect = null;
    private CantonDTO cantonSelect = null;
    
    private ProvinceDTO prov = null;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    @FXML
    private void actGuardar(ActionEvent event) {
        if(validarCampos()){
            Respuesta res;
            if(uso == 1){
                proviciaSelect.setNombre(txtNombre.getText());
                proviciaSelect.setCodigo(txtCodigo.getText());
                res = provinciaService.guardar(proviciaSelect);
            }else{
                cantonSelect.setNombre(txtNombre.getText());
                cantonSelect.setCodigo(txtCodigo.getText());
                cantonSelect.setProvincia(prov);
                res = cantonService.guardar(cantonSelect);
            }
            if(res.getEstado()){
                Mensaje.show(Alert.AlertType.INFORMATION, "Guardar", "Registro guardado con éxito");
                LimpiarCampos();
                AppContext.getInstance().set("Uso2", 0);
            }else{
                Mensaje.show(Alert.AlertType.ERROR, "Guardar", res.getMensaje());
            }
        }else{
            Mensaje.show(Alert.AlertType.WARNING, "Guardar", "Algunos campos esta vacíos");
        }
        
    }

    @Override
    public void initialize() {
        Limpiar();
        uso = (Integer) AppContext.getInstance().get("Uso");
        agregar = (Boolean) AppContext.getInstance().get("Agregar");
        initNodes();
    }
    
    private void initNodes(){
        if(uso == 1){
            txtSectorPertenece.setVisible(false);
            rowSector.setPrefHeight(0);
            txtSectorPertenece.setPrefHeight(0);
            rowCodigo.setPrefHeight(150);
            rowNombre.setPrefHeight(150);
            if(agregar){
                proviciaSelect = new ProvinciaDTO(0, "", "", null);
            }else{
                proviciaSelect = (ProvinciaDTO) AppContext.getInstance().get("select");
                cargarDatos();
            }
        }else{
            txtSectorPertenece.setPromptText("Provincia");
            if(agregar){
                cantonSelect = new CantonDTO(0L, "", "", null, null);
            }else{
                cantonSelect = (CantonDTO) AppContext.getInstance().get("select");
                cargarDatos();
            }
        }
    }
    
    private Boolean validarCampos(){
        Boolean retorno = txtNombre.getText() != null && !txtNombre.getText().isEmpty() && txtCodigo.getText() != null && !txtCodigo.getText().isEmpty();
        if(uso == 1){
            return retorno;
        }else{
            return retorno && prov != null;
        }
    }
    
    private void Limpiar(){
        proviciaSelect = null;
        cantonSelect = null;
        prov = null;
        rowNombre.setPrefHeight(100);
        rowCodigo.setPrefHeight(100);
        rowSector.setPrefHeight(100);
        txtSectorPertenece.setVisible(true);
        txtSectorPertenece.setPrefHeight(50);
        txtCodigo.clear();
        txtNombre.clear();
        txtSectorPertenece.clear();
    }
    
    private void LimpiarCampos(){
        proviciaSelect = new ProvinciaDTO(0, "", "", null);
        cantonSelect = new CantonDTO(0L, "", "", null, null);
        prov = null;
        txtCodigo.clear();
        txtNombre.clear();
        txtSectorPertenece.clear();
    }
    
    private void cargarDatos(){
        String nombre, codigo;
        if(uso == 1){
            nombre = proviciaSelect.getNombre();
            codigo = proviciaSelect.getCodigo();
        }else{
            nombre = cantonSelect.getNombre();
            codigo = cantonSelect.getCodigo();
            txtSectorPertenece.setText(cantonSelect.getProvincia().getNombre());
            prov = cantonSelect.getProvincia();
        }
        txtNombre.setText(nombre);
        txtCodigo.setText(codigo);
    }
    
    private ProvinceDTO convertFromPronvicia(ProvinciaDTO p){
        ProvinceDTO pro = new ProvinceDTO(p.getId(), p.getNombre(), p.getCodigo());
        return pro;
    }

    @FXML
    private void actBuscarSector(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("BuscarSector.fxml"));
        loader.load();
        Controller controller = loader.getController();
        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setTitle("Cliente Examen");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(Boolean.FALSE);
        stage.initStyle(StageStyle.DECORATED);
        stage.sizeToScene();
        controller.setStage(stage);
        if(uso == 2){
            AppContext.getInstance().set("Uso2", 5);
            controller.initialize();
            controller.initEvents();
            stage.showAndWait();
            if(AppContext.getInstance().get("select") != null){
                prov = convertFromPronvicia((ProvinciaDTO) AppContext.getInstance().get("select"));
                txtSectorPertenece.setText(prov.getNombre());
            }
        }
        System.out.println("Salio");
    }
}
