/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.examen.cliente.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
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
    private TreeView<String> treeV;

    private ProyectoService proyService = new ProyectoService();
    private List<ProyectoDTO> proyectos;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @Override
    public void initialize() {
        proyectos= new ArrayList<>();
        cargarArbol();
    }

    public void cargarArbol() {
     //   treeV.getRoot().getChildren().clear();
        Respuesta res = proyService.getAll();
        if (res.getEstado()) {
            proyectos = (List<ProyectoDTO>) res.getResultado("Proyectos");
            System.out.println(proyectos.size());
            if (proyectos != null) {
                TreeItem<String> root = new TreeItem<>("Proyectos");
                treeV.setRoot(root);
                proyectos.stream().forEach(x -> {
                    TreeItem<String> item = new TreeItem<>(x.getNombre());
                    root.getChildren().add(item);
                    List<TareaDTO> tareas = x.getTarea();
                    System.out.println(tareas.size());
                    if (tareas != null) {
                        tareas.stream().forEach(y -> {
                            TreeItem<String> itemTarea = new TreeItem<>(y.getNombre());
                            item.getChildren().add(itemTarea);
                        });
                    }
                });
            }
        } else {
            Mensaje.show(Alert.AlertType.ERROR, "Error", res.getMensaje());
        }

    }
    
}
