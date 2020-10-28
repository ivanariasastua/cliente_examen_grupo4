/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.examen.cliente.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import org.una.examen.cliente.controller.util.Respuesta;
import org.una.examen.cliente.service.ClienteService;

public class CobroPendienteController extends Controller implements Initializable{

    Respuesta respuesta;
    ClienteService clienteService;
    
    @Override
    public void initialize() {
        clienteService = new ClienteService();
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        
    }
    
}
