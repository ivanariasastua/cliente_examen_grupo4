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
import org.una.examen.cliente.dto.ProvinciaDTO;

/**
 *
 * @author Ivan Josué Arias Astua
 */
public class ProvinciaService {
    
    public Respuesta getAll(){
        try{
            Request request = new Request("http://localhost:8989/provincia/");
            request.get();
            if(request.isError())
                return new Respuesta(Boolean.FALSE, request.getError());
            List<ProvinciaDTO> result = (List<ProvinciaDTO>) request.readEntity(new GenericType<List<ProvinciaDTO>>(){});
            return new Respuesta(Boolean.TRUE, "Provincias", result);
        }catch(Exception ex){
            return new Respuesta(false, ex.toString(), "No puedo establecerce conexion con el servidor");
        }
    }
    
    public Respuesta getByNombre(String nombre){
        try{
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("nombre", nombre);
            Request request = new Request("http://localhost:8989/provincia/nombre", "/{nombre}", parametros);
            request.get();
            if(request.isError())
                return new Respuesta(Boolean.FALSE, request.getError());
            List<ProvinciaDTO> result = (List<ProvinciaDTO>) request.readEntity(new GenericType<List<ProvinciaDTO>>(){});
            return new Respuesta(Boolean.TRUE, "Provincias", result);
        }catch(Exception ex){
            return new Respuesta(false, ex.toString(), "No puedo establecerce conexion con el servidor");
        }
    }
    
    public Respuesta getByCodigo(String codigo){
        try{
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("codigo", codigo);
            Request request = new Request("http://localhost:8989/provincia/codigo", "/{codigo}", parametros);
            request.get();
            if(request.isError())
                return new Respuesta(Boolean.FALSE, request.getError());
            List<ProvinciaDTO> result = (List<ProvinciaDTO>) request.readEntity(new GenericType<List<ProvinciaDTO>>(){});
            return new Respuesta(Boolean.TRUE, "Provincias", result);
        }catch(Exception ex){
            return new Respuesta(false, ex.toString(), "No puedo establecerce conexion con el servidor");
        }
    }
    
    public Respuesta guardar(ProvinciaDTO dto){
        try{
            Request request;
            if(dto.getId() == 0){
                request = new Request("http://localhost:8989/provincia/save");
                request.post(dto);
            }else{
                Map<String, Object> parametros = new HashMap<>();
                parametros.put("id", dto.getId());
                request = new Request("http://localhost:8989/provincia/edit", "/{id}", parametros);
                request.put(dto);
            }
            if(request.isError())
                return new Respuesta(Boolean.FALSE, request.getError());
            ProvinciaDTO result = (ProvinciaDTO) request.readEntity(ProvinciaDTO.class);
            return new Respuesta(Boolean.TRUE, "Provincia", result);
        }catch(Exception ex){
            return new Respuesta(false, ex.toString(), "No puedo establecerce conexion con el servidor");
        }
    }
    
    public Respuesta delete(Integer id){
        try{
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("id", id);
            Request request = new Request("http://localhost:8989/provincia/", "/{id}", parametros);
            request.delete();
            if(request.isError())
                return new Respuesta(Boolean.FALSE, request.getError());
            return new Respuesta(Boolean.TRUE, "Eliminado correctamente");
        }catch(Exception ex){
            return new Respuesta(false, ex.toString(), "No puedo establecerce conexion con el servidor");
        }
    }
}
