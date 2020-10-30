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
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.una.examen.cliente.App;
import org.una.examen.cliente.dto.DistritoDTO;
import org.una.examen.cliente.dto.CantonDTO;
import org.una.examen.cliente.dto.CantomDTO;
import org.una.examen.cliente.controller.util.AppContext;
import org.una.examen.cliente.controller.util.Formato;
import org.una.examen.cliente.controller.util.Mensaje;
import org.una.examen.cliente.controller.util.Respuesta;
import org.una.examen.cliente.service.DistritoService;

/**
 * FXML Controller class
 *
 * @author Ivan Josué Arias Astua
 */
public class AgregarDistritoController extends Controller implements Initializable {

    @FXML
    private JFXTextField txtNombre;
    @FXML
    private JFXTextField txtCodigo;
    @FXML
    private JFXTextField txtPoblacion;
    @FXML
    private JFXTextField txtArea;
    @FXML
    private JFXTextField txtSectorPertenece;
    
    private DistritoDTO select = null;
    private final DistritoService service = new DistritoService();
    private CantomDTO canton = null;
    @FXML
    private GridPane gpDistrito;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txtArea.setTextFormatter(Formato.getInstance().twoDecimalFormat());
        txtPoblacion.setTextFormatter(Formato.getInstance().integerFormat());
    }    

    @FXML
    private void actGuardar(ActionEvent event) {
        if(validarCampos()){
            Integer poblacion = 0; 
            Double area = 0D;
            poblacion = Integer.parseInt(txtPoblacion.getText());
            area = Double.parseDouble(txtArea.getText());
            if(poblacion > 0 && area > 0D){
                select.setNombre(txtNombre.getText());
                select.setCodigo(txtCodigo.getText());
                select.setArea(area);
                select.setPoblacion(poblacion);
                select.setCanton(canton);
                Respuesta res = service.guardar(select);
                if(res.getEstado()){
                    Limpiar();
                    Mensaje.show(Alert.AlertType.INFORMATION, "Guardar", "Se registro existosamente");
                }else{
                    Mensaje.show(Alert.AlertType.ERROR, "Guardar", res.getMensaje());
                }
            }else{
                Mensaje.show(Alert.AlertType.WARNING, "Guardar", "Los datos de poblacion y/o area no son correctos");
            }
        }
    }
    
    private Boolean validarCampos(){
        return txtNombre.getText() != null && !txtNombre.getText().isEmpty() &&
               txtCodigo.getText() != null && !txtCodigo.getText().isEmpty() &&
               txtArea.getText() != null && !txtArea.getText().isEmpty() &&
               txtPoblacion.getText() != null && !txtPoblacion.getText().isEmpty() &&
               canton != null;
    }
    
    private void Limpiar(){
        txtCodigo.clear();
        txtNombre.clear();
        txtArea.clear();
        txtPoblacion.clear();
        txtSectorPertenece.clear();
        canton = null;
        select = new DistritoDTO(0L, "", "", 0, 0D, null, null);
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
        AppContext.getInstance().set("Uso2", 6);
        controller.initialize();
        controller.initEvents();
        stage.showAndWait();
        if(AppContext.getInstance().get("select") != null){
            canton = convertFromCanton((CantonDTO) AppContext.getInstance().get("select"));
            txtSectorPertenece.setText(canton.getNombre());
        }
    }
    
    private CantomDTO convertFromCanton(CantonDTO c){
        CantomDTO can = new CantomDTO(c.getId(), c.getNombre(), c.getCodigo(), c.getProvincia());
        return can;
    }
    
    private void cargarDatos(){
        if(AppContext.getInstance().get("select") == null){
            select = new DistritoDTO(0L, "", "", 0, 0D, null, null);
        }else{
            select = (DistritoDTO) AppContext.getInstance().get("select");
            txtNombre.setText(select.getNombre());
            txtCodigo.setText(select.getCodigo());
            txtArea.setText(select.getArea().toString());
            txtPoblacion.setText(select.getPoblacion().toString());
            txtSectorPertenece.setText(select.getCanton().getNombre());
            canton = select.getCanton();
        } 
    }

    @Override
    public void initialize() {
        Limpiar();
        cargarDatos();
    }
    
}
