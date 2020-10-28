/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.examen.cliente.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ws.rs.core.GenericType;
import org.una.examen.cliente.controller.util.Request;
import org.una.examen.cliente.controller.util.Respuesta;
import org.una.examen.cliente.dto.CobroPendienteDTO;


public class CobroPendienteService {
    
    public Respuesta guardarCobroPendiente(CobroPendienteDTO cobro){
        try{
            Request request = new Request("http://localhost:8989/cobros_pendientes/save");
            request.post(cobro);
            if(request.isError()){
                return new Respuesta(false, request.getError(), "Error al guardar el cobro");
            }
            CobroPendienteDTO result = (CobroPendienteDTO) request.readEntity(CobroPendienteDTO.class);
            return new Respuesta(true, "Cobros", result);
        }catch(Exception ex){
            return new Respuesta(false, ex.toString(), "No se estableció conexión con el servidor");
        }
    }
    
    /*
    public Respuesta obtenerPorCliente(Long id){
        try{
            Map<String, Object> parametros = new HashMap();
            parametros.put("id", id);
            Request request = new Request("http://localhost:8989/cobros_pendientes/cliente", "/{id}", parametros);
            request.get();
            if(request.isError()){
                return new Respuesta(false, request.getError(), "Error al obtener los cobros del cliente");
            }
            List<CobroPendienteDTO> result = (List<CobroPendienteDTO>) request.readEntity(new GenericType<List<CobroPendienteDTO>>(){});
            return new Respuesta(true, "Cobros", result);
        }catch(Exception ex){
            return new Respuesta(false, ex.toString(), "No se estableció conexión con la base de datos");
        }
        
    }*/
}
