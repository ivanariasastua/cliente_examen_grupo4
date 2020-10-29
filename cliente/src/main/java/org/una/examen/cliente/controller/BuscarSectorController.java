/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.examen.cliente.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.StageStyle;
import org.una.examen.cliente.controller.util.AppContext;
import org.una.examen.cliente.controller.util.FlowController;
import org.una.examen.cliente.controller.util.Mensaje;
import org.una.examen.cliente.controller.util.Respuesta;
import org.una.examen.cliente.dto.ProvinciaDTO;
import org.una.examen.cliente.dto.CantonDTO;
import org.una.examen.cliente.dto.DistritoDTO;
import org.una.examen.cliente.dto.UnidadDTO;
import org.una.examen.cliente.service.CantonService;
import org.una.examen.cliente.service.DistritoService;
import org.una.examen.cliente.service.ProvinciaService;
import org.una.examen.cliente.service.UnidadService;
/**
 * FXML Controller class
 *
 * @author Ivan Josué Arias Astua
 */
public class BuscarSectorController extends Controller implements Initializable {

    @FXML private JFXComboBox<String> cbFiltrar;
    @FXML private JFXTextField txtBuscar;
    @FXML private TableView tabla;
    @FXML private JFXButton btnEditar;
    @FXML private JFXButton btnEliminar;
    @FXML private JFXButton btnAgregar;
    @FXML private JFXButton btnSeleccionar;
    @FXML private Label lblTitulo;
    
    private Integer uso, uso2;
    
    private final ProvinciaService provinciaService = new ProvinciaService();
    private final CantonService cantonService = new CantonService();
    private final DistritoService distritoService = new DistritoService();
    private final UnidadService unidadService = new UnidadService();
    
    private ProvinciaDTO proviciaSelect = null;
    private CantonDTO cantonSelect = null;
    private DistritoDTO distritoSelect = null;
    private UnidadDTO unidadSelect = null;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    

    @FXML
    private void actBuscar(ActionEvent event) {
        if(cbFiltrar.getSelectionModel().getSelectedItem() != null && (txtBuscar.getText() != null && !txtBuscar.getText().isEmpty())){
            tabla.getItems().clear();
            String filtro = cbFiltrar.getSelectionModel().getSelectedItem(), buscar = txtBuscar.getText(), resultado;
            Respuesta res = null;
            if(uso == 1 || uso2 == 5){
                resultado = "Provincias";
                if(filtro.equals("Nombre")){
                    res = provinciaService.getByNombre(buscar);
                }else{
                    res = provinciaService.getByCodigo(buscar);
                }
            }else if(uso == 2 || uso2 == 6){
                resultado = "Cantones";
                if(filtro.equals("Nombre")){
                    res = cantonService.getByNombre(buscar);
                }else if(filtro.equals("Código")){
                    res = cantonService.getByCodigo(buscar);
                }else{
                    res = cantonService.getByProvincia(buscar);
                }
            }else if(uso == 3 || uso2 == 7){
                resultado = "Distritos";
                if(filtro.equals("Nombre")){
                    res = distritoService.getByNombre(buscar);
                }else if(filtro.equals("Código")){
                    res = distritoService.getByCodigo(buscar);
                }else{
                    res = distritoService.getByCanton(buscar);
                }
            }else{
                resultado = "Unidades";
                if(filtro.equals("Nombre")){
                    res = unidadService.getByNombre(buscar);
                }else if(filtro.equals("Código")){
                    res = unidadService.getByCodigo(buscar);
                }else if(filtro.equals("Población")){
                    try{
                        Integer poblacion = Integer.parseInt(buscar);
                        res = unidadService.getByPoblacion(poblacion);
                    }catch(NumberFormatException ex){
                        resultado = null;
                        Mensaje.show(Alert.AlertType.WARNING, "Filtrar por población", "El valor ingresado no es valido debe ser numérico");
                    }
                }else if(filtro.equals("Área")){
                    try{
                        Double area = Double.parseDouble(buscar);
                        res = unidadService.getByArea(area);
                    }catch(NumberFormatException ex){
                        resultado = null;
                        Mensaje.show(Alert.AlertType.WARNING, "Filtrar por área", "El valor ingresado no es valido debe ser numérico");
                    }
                }else if(filtro.equals("Tipo")){
                    res = unidadService.getByTipo(buscar);
                }else{
                    res = unidadService.getByDistrito(buscar);
                }
            }
            if(res != null){
                if(res.getEstado()){
                    if(uso == 1){
                        List<ProvinciaDTO> lista = (List<ProvinciaDTO>) res.getResultado(resultado);
                        tabla.getItems().addAll(lista);
                    }else if(uso == 2){
                        List<CantonDTO> lista = (List<CantonDTO>) res.getResultado(resultado);
                        tabla.getItems().addAll(lista);
                    }else if(uso == 3){
                        List<DistritoDTO> lista = (List<DistritoDTO>) res.getResultado(resultado);
                        tabla.getItems().addAll(lista);
                    }else{
                        List<UnidadDTO> lista = (List<UnidadDTO>) res.getResultado(resultado);
                        tabla.getItems().addAll(lista);
                    }
                }else{
                    Mensaje.show(Alert.AlertType.ERROR, "Buscar", res.getMensaje());
                }
            }
            cbFiltrar.getSelectionModel().select(null);
        }
    }

    @FXML
    private void actTabla(MouseEvent event) {
        if(tabla.getSelectionModel().getSelectedItem() != null){
            if(uso == 1 || uso2 == 5){
                proviciaSelect = (ProvinciaDTO) tabla.getSelectionModel().getSelectedItem();
                AppContext.getInstance().set("select", proviciaSelect);
            }else if(uso == 2 || uso2 == 6){
                cantonSelect = (CantonDTO) tabla.getSelectionModel().getSelectedItem();
                AppContext.getInstance().set("select", cantonSelect);
            }else if(uso == 3 || uso2 == 7){
                distritoSelect = (DistritoDTO) tabla.getSelectionModel().getSelectedItem();
                AppContext.getInstance().set("select", distritoSelect);
            }else{
                unidadSelect = (UnidadDTO) tabla.getSelectionModel().getSelectedItem();
                AppContext.getInstance().set("select", unidadSelect);
            } 
        }else{
            if(uso == 1 || uso2 == 5){
                proviciaSelect = null;
            }else if(uso == 2 || uso2 == 6){
                cantonSelect = null;
            }else if(uso == 3 || uso2 == 7){
                distritoSelect = null;
            }else{
                unidadSelect = null;
            }
            AppContext.getInstance().set("select", null);
        }
    }

    @FXML
    private void actEditar(ActionEvent event) {
        AppContext.getInstance().set("Agregar", false);
        if(uso == 1){
            if(proviciaSelect != null){
                FlowController.getInstance().goViewInNoResizableWindow("AgregarSector", Boolean.FALSE, StageStyle.DECORATED);
            }else{
                Mensaje.show(Alert.AlertType.ERROR, "Editar provincia", "No ha seleccionado una provincia");
            }
        }else if(uso == 2){
            if(cantonSelect != null){
                FlowController.getInstance().goViewInNoResizableWindow("AgregarSector", Boolean.FALSE, StageStyle.DECORATED);
            }else{
                Mensaje.show(Alert.AlertType.ERROR, "Editar canton", "No ha seleccionado una canton");
            }
        }else if(uso == 3){
            if(distritoSelect != null){
                FlowController.getInstance().goViewInNoResizableWindow("AgregarSector", Boolean.FALSE, StageStyle.DECORATED);
            }else{
                Mensaje.show(Alert.AlertType.ERROR, "Editar distrito", "No ha seleccionado una distrito");
            }
        }else{
            if(unidadSelect != null){
                FlowController.getInstance().goViewInNoResizableWindow("AgregarUnidad", Boolean.FALSE, StageStyle.DECORATED);
            }else{
                Mensaje.show(Alert.AlertType.ERROR, "Editar unidad", "No ha seleccionado una unidad");
            }
        }
    }

    @FXML
    private void actEliminar(ActionEvent event) {
        Respuesta res;
        if(uso == 1){
            if(proviciaSelect != null){
                if(Mensaje.showConfirmation("Eliminar provincia", this.getStage(), "¿Desea eliminar esta provincia?")){
                    res = provinciaService.delete(proviciaSelect.getId());
                    if(res.getEstado()){
                        Mensaje.show(Alert.AlertType.INFORMATION, "Eliminar provincia", "Provincia eliminada correctamente");
                    }else{
                        Mensaje.show(Alert.AlertType.ERROR, "Eliminar provincia", res.getMensaje());
                    }
                    tabla.getItems().remove(proviciaSelect);
                }
            }else{
                Mensaje.show(Alert.AlertType.ERROR, "Eliminar provincia", "No ha seleccionado una provincia");
            }
        }else if(uso == 2){
            if(cantonSelect != null){
                if(Mensaje.showConfirmation("Eliminar cantón", this.getStage(), "¿Desea eliminar el cantón?")){
                    res = cantonService.delete(cantonSelect.getId());
                    if(res.getEstado()){
                        Mensaje.show(Alert.AlertType.INFORMATION, "Eliminar cantón", "Cantón eliminada correctamente");
                    }else{
                        Mensaje.show(Alert.AlertType.ERROR, "Eliminar cantón", res.getMensaje());
                    }
                    tabla.getItems().remove(cantonSelect);
                }
            }else{
                Mensaje.show(Alert.AlertType.ERROR, "Eliminar canton", "No ha seleccionado una canton");
            }
        }else if(uso == 3){
            if(distritoSelect != null){
                if(Mensaje.showConfirmation("Eliminar distrito", this.getStage(), "¿Desea eliminar el distrito?")){
                    res = distritoService.delete(distritoSelect.getId());
                    if(res.getEstado()){
                        Mensaje.show(Alert.AlertType.INFORMATION, "Eliminar distrito", "Distrito eliminada correctamente");
                    }else{
                        Mensaje.show(Alert.AlertType.ERROR, "Eliminar distrito", res.getMensaje());
                    }
                    tabla.getItems().remove(distritoSelect);
                }
            }else{
                Mensaje.show(Alert.AlertType.ERROR, "Eliminar distrito", "No ha seleccionado una distrito");
            }
        }else{
            if(unidadSelect != null){
                if(Mensaje.showConfirmation("Eliminar unidad de distrito", this.getStage(), "¿Desea eliminar esta unidad de distrito?")){
                    res = unidadService.delete(unidadSelect.getId());
                    if(res.getEstado()){
                        Mensaje.show(Alert.AlertType.INFORMATION, "Eliminar unidad de distrito", "Unidad de distrito eliminada correctamente");
                    }else{
                        Mensaje.show(Alert.AlertType.ERROR, "Eliminar unidad de distrito", res.getMensaje());
                    }
                    tabla.getItems().remove(unidadSelect);
                }
            }else{
                Mensaje.show(Alert.AlertType.ERROR, "Eliminar unidad", "No ha seleccionado una unidad");
            }
        }
    }

    @FXML
    private void actLimpiar(ActionEvent event) {
        tabla.getItems().clear();
        txtBuscar.clear();
    }

    @FXML
    private void actAgregar(ActionEvent event) {
        AppContext.getInstance().set("Agregar", true);
        if(uso != 4)
            FlowController.getInstance().goViewInNoResizableWindow("AgregarSector", Boolean.FALSE, StageStyle.DECORATED);
        else
            FlowController.getInstance().goViewInNoResizableWindow("AgregarUnidad", Boolean.FALSE, StageStyle.DECORATED);
    }

    @Override
    public void initialize() {
        Limpiar();
        uso = (Integer) AppContext.getInstance().get("Uso");
        uso2 = (Integer) AppContext.getInstance().get("Uso2");
        initNodes();
    }
    
    public void initNodes(){
        tabla.getColumns().clear();
        List<String> items = new ArrayList<>();
        items.add("Nombre");
        items.add("Codigo");
        if(uso == 1 || uso2 == 5){
            lblTitulo.setText("Buscar provincias");
            TableColumn<ProvinciaDTO, String> colNombre = new TableColumn<>("Nombre");
            colNombre.setCellValueFactory((p) -> new SimpleStringProperty(p.getValue().getNombre()));
            TableColumn<ProvinciaDTO, String> colCodigo = new TableColumn<>("Código");
            colCodigo.setCellValueFactory((p) -> new SimpleStringProperty(p.getValue().getCodigo()));
            tabla.getColumns().addAll(colNombre, colCodigo);
        }else if(uso == 2 || uso2 == 6){
            lblTitulo.setText("Buscar cantones");
            TableColumn<CantonDTO, String> colNombre = new TableColumn<>("Nombre");
            colNombre.setCellValueFactory((p) -> new SimpleStringProperty(p.getValue().getNombre()));
            TableColumn<CantonDTO, String> colCodigo = new TableColumn<>("Código");
            colCodigo.setCellValueFactory((p) -> new SimpleStringProperty(p.getValue().getCodigo()));
            TableColumn<CantonDTO, String> colProvincia = new TableColumn<>("Provincia");
            colProvincia.setCellValueFactory((p) -> new SimpleStringProperty(p.getValue().getProvincia().getNombre()));
            tabla.getColumns().addAll(colNombre, colCodigo, colProvincia);
            items.add("Provincia");
        }else if(uso == 3 || uso2 == 7){
            lblTitulo.setText("Buscar distritos");
            TableColumn<DistritoDTO, String> colNombre = new TableColumn<>("Nombre");
            colNombre.setCellValueFactory((p) -> new SimpleStringProperty(p.getValue().getNombre()));
            TableColumn<DistritoDTO, String> colCodigo = new TableColumn<>("Código");
            colCodigo.setCellValueFactory((p) -> new SimpleStringProperty(p.getValue().getCodigo()));
            TableColumn<DistritoDTO, String> colCanton = new TableColumn<>("Cantón");
            colCanton.setCellValueFactory((p) -> new SimpleStringProperty(p.getValue().getCanton().getNombre()));
            tabla.getColumns().addAll(colNombre, colCodigo, colCanton);
            items.add("Cantón");
        }else{
            lblTitulo.setText("Buscar Unidades de distrito");
            TableColumn<UnidadDTO, String> colNombre = new TableColumn<>("Nombre");
            colNombre.setCellValueFactory((p) -> new SimpleStringProperty(p.getValue().getNombre()));
            TableColumn<UnidadDTO, String> colCodigo = new TableColumn<>("Código");
            colCodigo.setCellValueFactory((p) -> new SimpleStringProperty(p.getValue().getCodigo()));
            TableColumn<UnidadDTO, String> colPoblacion = new TableColumn<>("Población");
            colPoblacion.setCellValueFactory((p) -> new SimpleStringProperty(p.getValue().getPoblacion().toString()));
            TableColumn<UnidadDTO, String> colArea = new TableColumn<>("Área");
            colArea.setCellValueFactory((p) -> new SimpleStringProperty(p.getValue().getArea().toString()));
            TableColumn<UnidadDTO, String> colDistrito = new TableColumn<>("Distrito");
            colDistrito.setCellValueFactory((p) -> new SimpleStringProperty(p.getValue().getDistrito().getNombre()));
            tabla.getColumns().addAll(colNombre, colCodigo, colPoblacion, colArea, colDistrito);
            items.add("Tipo");
            items.add("Población");
            items.add("Área");
            items.add("Distrito");
        }
        cbFiltrar.getItems().clear();
        cbFiltrar.getItems().addAll(items);
        if(uso2 == 5 || uso2 == 6 || uso2 == 7 || uso2 == 8){
            btnAgregar.setVisible(false);
            btnEditar.setVisible(false);
            btnEliminar.setVisible(false);
            btnSeleccionar.setVisible(true);
        }else{
            btnAgregar.setVisible(true);
            btnEditar.setVisible(true);
            btnEliminar.setVisible(true);
            btnSeleccionar.setVisible(false);
        }
    }
    
    private void Limpiar(){
        tabla.getItems().clear();
        tabla.getColumns().clear();
        cbFiltrar.getItems().clear();
        txtBuscar.clear();
    }

    @FXML
    private void actSeleccionar(ActionEvent event) {
        if(uso2 == 5){
            if(proviciaSelect != null){
                this.getStage().close();
            }else{
                Mensaje.show(Alert.AlertType.WARNING, "Seleccionar provincia", "No ha seleccionado una provincia");
            }
        }else if(uso2 == 6){
            if(cantonSelect != null){
                this.getStage().close();
            }else{
                Mensaje.show(Alert.AlertType.WARNING, "Seleccionar cantón", "No ha seleccionado una provincia");
            }
        }else if(uso2 == 7){
            if(distritoSelect != null){
                this.getStage().close();
            }else{
                Mensaje.show(Alert.AlertType.WARNING, "Seleccionar distrito", "No ha seleccionado una provincia");
            }
        }else{
            if(unidadSelect != null){
                this.getStage().close();
            }else{
                Mensaje.show(Alert.AlertType.WARNING, "Seleccionar unidad de distrito", "No ha seleccionado una unidad de distrito");
            }
        }
    }
    
}
