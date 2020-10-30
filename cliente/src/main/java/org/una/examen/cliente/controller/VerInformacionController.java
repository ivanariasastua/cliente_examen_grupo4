/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.examen.cliente.controller;

import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextArea;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import org.una.examen.cliente.controller.util.Demografia;
import org.una.examen.cliente.controller.util.Mensaje;
import org.una.examen.cliente.controller.util.Respuesta;
import org.una.examen.cliente.service.ProvinciaService;
import org.una.examen.cliente.dto.ProvinciaDTO;
import org.una.examen.cliente.dto.CantonDTO;
import org.una.examen.cliente.dto.DistritoDTO;
import org.una.examen.cliente.dto.UnidadDTO;
/**
 * FXML Controller class
 *
 * @author Ivan Josué Arias Astua
 */
public class VerInformacionController extends Controller implements Initializable {

    @FXML
    private ToggleGroup tgDato;
    @FXML
    private TreeView<Demografia> tvArbol;
    @FXML
    private JFXTextArea taInformacion;
    
    private final ProvinciaService service = new ProvinciaService();
    @FXML
    private JFXRadioButton rbPoblacion;
    
    //private List<Demografia> items;
 
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Mensaje.showProgressDialog(taskAllData(), "Ver Informacion", "Formando arbol");
    }    

    @FXML
    private void actOrdenar(ActionEvent event) {
    }

    @Override
    public void initialize() {
        
    }
    
    @FXML
    private void actActualizarArbol(ActionEvent event) {
    }
    
    private Task taskAllData(){
        return new Task() {
            @Override
            protected Object call() throws Exception {
                updateProgress(1D, 10D);
                updateMessage("Buscando la información");
                Respuesta res = service.getAll();
                if(res.getEstado()){
                    updateProgress(2D, 6D);
                    updateMessage("Información obtenida");
                    List<ProvinciaDTO> lista = (List<ProvinciaDTO>) res.getResultado("Provincias");
                    updateProgress(3D, 6D);
                    updateMessage("Procesando datos");
                    List<Demografia> items = procesarDatos(lista);
                    updateProgress(4D, 6D);
                    updateMessage("Datos procesados");
                    items = ordenarDatos(items);
                    updateProgress(5D, 6D);
                    updateMessage("Datos ordenados");
                    mostrarTreeView(items);
                    updateProgress(6D, 6D);
                    updateMessage("Finalizado");
                }else{
                    Platform.runLater( () -> {
                        Mensaje.show(Alert.AlertType.ERROR, "Buscar Información", res.getMensaje());
                    });
                }
                return true;
            }
        };
    }    
    
    private List<Demografia> procesarDatos(List<ProvinciaDTO> lista){
        List<Demografia> items = new ArrayList<>();
        List<Demografia> auxCantones = new ArrayList<>();
        List<Demografia> auxDistritos = new ArrayList<>();
        List<Demografia> auxUnidades = new ArrayList<>();
        Demografia auxP = null;
        Demografia auxC = null;
        Demografia auxD = null;
        for(ProvinciaDTO prov : lista){
            auxP = new Demografia(prov, prov.getNombre()); 
            auxCantones = new ArrayList<>();
            if(prov.getCantones() != null){
                for(CantonDTO can : prov.getCantones()){
                    auxC = new Demografia(can, can.getNombre());
                    auxDistritos = new ArrayList<>();
                    if(can.getDistritos() != null){
                        auxUnidades = new ArrayList<>();
                        for(DistritoDTO dis : can.getDistritos()){
                            auxD = new Demografia(dis, dis.getNombre(), dis.getPoblacion(), dis.getArea());
                            if(dis.getUnidades() != null){
                                for(UnidadDTO uni : dis.getUnidades()){
                                    auxUnidades.add(new Demografia(uni, uni.getNombre(), 0, 0.0));
                                }
                                auxD.setHijos(auxUnidades);
                            }
                            auxDistritos.add(auxD);
                        }
                        auxC.setHijos(auxDistritos);
                    }
                    auxCantones.add(auxC);
                }
                auxP.setHijos(auxCantones);
            }
            items.add(auxP);
        }
        return items;
    }

    private List<Demografia> ordenarDatos(List<Demografia> lista){
        List<Demografia> items = new ArrayList<>();
        if(rbPoblacion.isSelected()){
            for(Demografia d : lista){
                if(!d.getHijos().isEmpty()){
                    for(Demografia d1 : d.getHijos()){
                        if(!d1.getHijos().isEmpty()){
                            d1.getHijos().sort((Demografia obj1, Demografia obj2) -> obj2.getPoblacion().compareTo(obj1.getPoblacion()));
                        }
                    }
                    d.getHijos().sort((Demografia obj1, Demografia obj2) -> obj2.getPoblacion().compareTo(obj1.getPoblacion()));
                }
            }
            lista.sort((Demografia obj1, Demografia obj2) -> obj2.getPoblacion().compareTo(obj1.getPoblacion()));
        }else{
            for(Demografia d : lista){
                if(!d.getHijos().isEmpty()){
                    for(Demografia d1 : d.getHijos()){
                        if(!d1.getHijos().isEmpty()){
                            d1.getHijos().sort((Demografia obj1, Demografia obj2) -> obj2.getArea().compareTo(obj1.getArea()));
                        }
                    }
                    d.getHijos().sort((Demografia obj1, Demografia obj2) -> obj2.getArea().compareTo(obj1.getArea()));
                }
            }
            lista.sort((Demografia obj1, Demografia obj2) -> obj2.getArea().compareTo(obj1.getArea()));
        }
        items = lista;
        return items;
    }
    
    private void mostrarTreeView(List<Demografia> lista){
        //items = lista;
        Platform.runLater( () -> {
            TreeItem root = new TreeItem<>("Provincias");
            tvArbol.setRoot(root);
            for(Demografia d : lista){
                Label label = new Label(d.toString());
                label.setUserData(d);
                label.setOnMouseClicked( t -> {
                    Demografia dem = (Demografia) label.getUserData();
                    taInformacion.setText(dem.infDato());
                });
                TreeItem prov = new TreeItem(label);
                root.getChildren().add(prov);
                if(!d.getHijos().isEmpty()){
                    for(Demografia d1 : d.getHijos()){
                        Label label1 = new Label(d1.toString());
                        label1.setUserData(d1);
                        label1.setOnMouseClicked( t -> {
                            Demografia dem = (Demografia) label1.getUserData();
                            taInformacion.setText(dem.infDato());
                        });
                        TreeItem can = new TreeItem(label1);
                        prov.getChildren().add(can);
                        if(!d1.getHijos().isEmpty()){
                            for(Demografia d2 : d1.getHijos()){
                                Label label2 = new Label(d2.toString());
                                label2.setUserData(d2);
                                label2.setOnMouseClicked( t -> {
                                    Demografia dem = (Demografia) label2.getUserData();
                                    taInformacion.setText(dem.infDato());
                                });
                                TreeItem dis = new TreeItem(label2);
                                can.getChildren().add(dis);
                                if(!d2.getHijos().isEmpty()){
                                    for(Demografia d3 : d2.getHijos()){
                                        Label label3 = new Label(d3.toString());
                                        label3.setUserData(d3);
                                        label3.setOnMouseClicked( t -> {
                                            Demografia dem = (Demografia) label3.getUserData();
                                            taInformacion.setText(dem.infDato());
                                        });
                                        TreeItem uni = new TreeItem(label3);
                                        dis.getChildren().add(uni);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        });
    }
}
