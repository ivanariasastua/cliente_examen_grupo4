/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.examen.cliente.service;

import java.util.List;
import javax.ws.rs.core.GenericType;
import org.una.examen.cliente.controller.util.Request;
import org.una.examen.cliente.controller.util.Respuesta;
import org.una.examen.cliente.dto.ClienteDTO;

public class ClienteService {
    
    public Respuesta guardarCliente(ClienteDTO cliente){
        try{
            Request request = new Request("http://localhost:8989/clientes/save");
            request.post(cliente);
            if(request.isError()){
                return new Respuesta(false, request.getError(),"Error al guardar el cliente");
            }
            ClienteDTO result = (ClienteDTO) request.readEntity(ClienteDTO.class);
            return new Respuesta(true, "Clientes", result);
        }catch(Exception ex){
            return new Respuesta(false, ex.toString(), "No se estableció conexión con el servidor");
        }
    }
    
    
    public Respuesta getAll(){
        try{
            Request request = new Request("http://localhost:8989/clientes/");
            request.get();
            if(request.isError()){
                return new Respuesta(false, request.getError(), "Error al obtener los clientes");
            }
            List<ClienteDTO> result = (List<ClienteDTO>) request.readEntity(new GenericType<List<ClienteDTO>>(){});
            return new Respuesta(true, "Clientes", result);
        }catch(Exception ex){
            return new Respuesta(false, ex.toString(), "No se estableció conexión con el servidor");
        }
    }
}
