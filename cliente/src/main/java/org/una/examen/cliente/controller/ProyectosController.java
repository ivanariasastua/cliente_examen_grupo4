/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.examen.cliente.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import org.una.examen.cliente.controller.util.Respuesta;
import org.una.examen.cliente.dto.ProyectoDTO;
import org.una.examen.cliente.dto.TareaDTO;
import org.una.examen.cliente.service.ProyectoService;
/**
 * FXML Controller class
 *
 * @author cordo
 */
public class ProyectosController extends Controller implements Initializable {

    @FXML
    private BorderPane pane;
    @FXML
    private TreeView<String> idTreeV;
    @FXML
    private VBox vbox;
    
    private ProyectoDTO proyDto = new ProyectoDTO();
    private ProyectoService proyService = new ProyectoService();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Respuesta res = proyService.getAll();
        if(res.getEstado()){
            List<ProyectoDTO> list = (List<ProyectoDTO>) res.getResultado("Proyectos");
            System.out.println(list.size());
        }else{
            System.out.println(res.getMensaje());
            System.out.println(res.getMensajeInterno());
        }
    }

    @Override
    public void initialize() {
    }
    
    public void cargarArbol() {
//        TreeItem<String> root = new TreeItem<>("Autores");
//        idTreeV.setRoot(root);
//
//        //le agregamos los autores a la raiz del treeV
//        autores.stream().forEach(x->{
//            TreeItem<String> item = new TreeItem<>(x.getNombre());
//            root.getChildren().add(item);
//            List<Libro> lib = libros.stream().filter(y->y.getAutor().equals(x.getNombre())).collect(Collectors.toList());
//            lib.stream().forEach(l->{
//                TreeItem<String> itemLib = new TreeItem<>(l.getNombre());
//                item.getChildren().add(itemLib);
//            });
//        });
    }

    
}
