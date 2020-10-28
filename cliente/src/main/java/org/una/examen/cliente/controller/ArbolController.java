/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.examen.cliente.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.una.examen.cliente.App;
import org.una.examen.cliente.controller.util.Mensaje;
import org.una.examen.cliente.controller.util.Respuesta;
import org.una.examen.cliente.dto.ProyectoDTO;
import org.una.examen.cliente.dto.TareaDTO;
import org.una.examen.cliente.service.ProyectoService;

/**
 * FXML Controller class
 *
 * @author cordo
 */
public class ArbolController extends Controller implements Initializable {

    @FXML
    private TreeView<ProyectoDTO> treeV;

    private ProyectoService proyService = new ProyectoService();
    private List<ProyectoDTO> proyectos;

    final String color = "-fx-background-color: #d3d3d3;";
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @Override
    public void initialize() {
        proyectos = new ArrayList<>();
        cargarArbol();
    }
    
    
    public String llenarNotas(TareaDTO tarea) {
        if (tarea != null) {
            return tarea.getNombre() + "\n\nFecha de inicio: " + tarea.getFechaInicio()
                    + "\nFecha de finalizacion: " + tarea.getFechaFinalizacion() + "\nImportancia: " + tarea.getImportancia().toString() + "\nPorcentaje de avance: "
                    + tarea.getPorcentajeAvance().toString() + "\nDescripcion: " + tarea.getDescripcion();
        }
        return "";
    }

    public void seleccionarTarea(JFXButton b, TareaDTO tarea) {
        b.setStyle(color);
        b.setOnMouseClicked(x -> {
            if (tarea.getNombre() != null) {
                try {
                    FXMLLoader loader = new FXMLLoader(App.class.getResource("InfoTarea.fxml"));
                    Parent root = loader.load();
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.show();
                    InfoTareaController editar = loader.getController();
                    editar.cargarDatos(tarea);
                } catch (IOException ex) {
                    Logger.getLogger(ArbolController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    
    public void asignarColores(TareaDTO tarea,JFXTextArea t){
        final String fondo;
        if(tarea.getPorcentajeAvance()>=0 && tarea.getPorcentajeAvance()<=20){
            fondo = "-fx-background-color: #d38989;";
            t.setStyle(fondo);
        }else if(tarea.getPorcentajeAvance()>20 && tarea.getPorcentajeAvance()<=50){
            fondo = "-fx-background-color: #e7b085;";
            t.setStyle(fondo);
        }else if(tarea.getPorcentajeAvance()>50 && tarea.getPorcentajeAvance()<=70){
            fondo = "-fx-background-color: #ffff9a;";
            t.setStyle(fondo);
        }else if(tarea.getPorcentajeAvance()>70 && tarea.getPorcentajeAvance()<=100){
            fondo = "-fx-background-color: #8fe79f;";
            t.setStyle(fondo);
        }
    }


    public void cargarArbol() {
        Respuesta res = proyService.getAll();
        if (res.getEstado()) {
            proyectos = (List<ProyectoDTO>) res.getResultado("Proyectos");
            if (proyectos != null) {
                TreeItem root = new TreeItem<>("Proyectos");
                treeV.setRoot(root);
                proyectos.stream().forEach(x -> {
                    HBox hb = new HBox();
                    JFXButton bot = new JFXButton("Seleccionar Proyecto");
                    seleccionarProyecto(bot, x);
                    Label l = new Label(x.getNombre());
                    hb.getChildren().addAll(l, bot);
                    TreeItem item = new TreeItem<>(hb);
                    root.getChildren().addAll(item);
                    List<TareaDTO> tareas = x.getTarea();
                    if (tareas != null) {
                        tareas.stream().forEach(y -> {
                            VBox box = new VBox();
                            JFXButton boton = new JFXButton("Seleccionar Tarea");
                            seleccionarTarea(boton, y);
                            JFXTextArea v = new JFXTextArea();
                            asignarColores(y, v);
                            v.setEditable(false);
                            v.setText(llenarNotas(y));
                            box.getChildren().addAll(v, boton);
                            TreeItem<VBox> item2 = new TreeItem<>(box);
                            item.getChildren().add(item2);
                        });
                    }
                });
            }
        } else {
            Mensaje.show(Alert.AlertType.ERROR, "Error", res.getMensaje());
        }

    }

    public void seleccionarProyecto(JFXButton b, ProyectoDTO proy) {
        b.setStyle(color);
        b.setOnMouseClicked(x -> {
            if (proy.getNombre() != null) {
                try {
                    FXMLLoader loader = new FXMLLoader(App.class.getResource("InfoProyecto.fxml"));
                    Parent root = loader.load();
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.show();
                    InfoProyectoController editar = loader.getController();
                    editar.cargarDatos(proy);
                } catch (IOException ex) {
                    Logger.getLogger(ArbolController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

}
