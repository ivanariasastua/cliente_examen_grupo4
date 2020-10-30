/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.examen.cliente.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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
import org.una.examen.cliente.dto.DistrictDTO;
import org.una.examen.cliente.dto.DistritoDTO;
import org.una.examen.cliente.dto.UnidadDTO;
import org.una.examen.cliente.service.UnidadService;

/**
 * FXML Controller class
 *
 * @author Ivan Josué Arias Astua
 */
public class AgregarUnidadController extends Controller implements Initializable {

    @FXML private RowConstraints rowSector;
    @FXML private JFXTextField txtNombre;
    @FXML private JFXTextField txtCodigo;
    @FXML private JFXTextField txtSectorPertenece;
    @FXML private JFXComboBox<String> cbTipo;

    private UnidadDTO select = null;
    private final UnidadService service = new UnidadService();
    private DistrictDTO distrito = null;
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initNodes();
    }    

    @FXML
    private void actGuardar(ActionEvent event) {
        if(validarCampos()){
            select.setNombre(txtNombre.getText());
            select.setCodigo(txtCodigo.getText());
            select.setTipo(cbTipo.getSelectionModel().getSelectedItem());
            select.setDistrito(distrito);
            Respuesta res = service.guardar(select);
            if(res.getEstado()){
                Limpiar();
                Mensaje.show(Alert.AlertType.INFORMATION, "Guardar", "Se registro existosamente");
            }else{
                Mensaje.show(Alert.AlertType.ERROR, "Guardar", res.getMensaje());
            }
        }
    }

    @Override
    public void initialize() {
        Limpiar();
        cargarDatos();
    }
    
    private Boolean validarCampos(){
        return txtNombre.getText() != null && !txtNombre.getText().isEmpty() &&
               txtCodigo.getText() != null && !txtCodigo.getText().isEmpty() &&
               cbTipo.getSelectionModel().getSelectedItem() != null &&
               distrito != null;
    }
    
    private void initNodes(){
        List<String> lista = new ArrayList<>();
        lista.add("Barrio");
        lista.add("Calle");
        lista.add("Poblado");
        lista.add("Urbanización");
        cbTipo.getItems().addAll(lista);
    }
    
    private void Limpiar(){
        txtCodigo.clear();
        txtNombre.clear();
        txtSectorPertenece.clear();
        select = new UnidadDTO(0L, "", "", "", null);
        distrito = null;
        cbTipo.getSelectionModel().select(null);
    }
    
    private void cargarDatos(){
        if(AppContext.getInstance().get("select") == null){
            select = new UnidadDTO(0L, "", "", "", null);
        }else{
            select = (UnidadDTO) AppContext.getInstance().get("select");
            txtNombre.setText(select.getNombre());
            txtCodigo.setText(select.getCodigo());
            cbTipo.getSelectionModel().select(select.getTipo());
            txtSectorPertenece.setText(select.getDistrito().getNombre());
            distrito = select.getDistrito();
        } 
    }
    
    private DistrictDTO convertFromDistrito(DistritoDTO distrito){
        DistrictDTO d = new DistrictDTO(distrito.getId(), distrito.getNombre(), distrito.getCodigo(), distrito.getCanton());
        return d;
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
        AppContext.getInstance().set("Uso2", 7);
        controller.initialize();
        controller.initEvents();
        stage.showAndWait();
        if(AppContext.getInstance().get("select") != null){
            distrito = convertFromDistrito((DistritoDTO) AppContext.getInstance().get("select"));
            txtSectorPertenece.setText(distrito.getNombre());
        }
    }
}
