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
import org.una.examen.cliente.dto.DistritoDTO;

/**
 *
 * @author Ivan Josué Arias Astua
 */
public class DistritoService {
    
    public Respuesta getByNombre(String nombre){
        try{
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("nombre", nombre);
            Request request = new Request("http://localhost:8989/distrito/nombre", "/{nombre}", parametros);
            request.get();
            if(request.isError())
                return new Respuesta(Boolean.FALSE, request.getError());
            List<DistritoDTO> result = (List<DistritoDTO>) request.readEntity(new GenericType<List<DistritoDTO>>(){});
            return new Respuesta(Boolean.TRUE, "Distritos", result);
        }catch(Exception ex){
            return new Respuesta(false, ex.toString(), "No puedo establecerce conexion con el servidor");
        }
    }
    
    public Respuesta getByCodigo(String codigo){
        try{
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("codigo", codigo);
            Request request = new Request("http://localhost:8989/distrito/codigo", "/{codigo}", parametros);
            request.get();
            if(request.isError())
                return new Respuesta(Boolean.FALSE, request.getError());
            List<DistritoDTO> result = (List<DistritoDTO>) request.readEntity(new GenericType<List<DistritoDTO>>(){});
            return new Respuesta(Boolean.TRUE, "Distritos", result);
        }catch(Exception ex){
            return new Respuesta(false, ex.toString(), "No puedo establecerce conexion con el servidor");
        }
    }
    
    public Respuesta getByCanton(String canton){
        try{
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("canton", canton);
            Request request = new Request("http://localhost:8989/distrito/canton", "/{canton}", parametros);
            request.get();
            if(request.isError())
                return new Respuesta(Boolean.FALSE, request.getError());
            List<DistritoDTO> result = (List<DistritoDTO>) request.readEntity(new GenericType<List<DistritoDTO>>(){});
            return new Respuesta(Boolean.TRUE, "Distritos", result);
        }catch(Exception ex){
            return new Respuesta(false, ex.toString(), "No puedo establecerce conexion con el servidor");
        }
    }
    
    public Respuesta guardar(DistritoDTO dto){
        try{
            Request request;
            if(dto.getId() == 0){
                request = new Request("http://localhost:8989/distrito/save");
                request.post(dto);
            }else{
                Map<String, Object> parametros = new HashMap<>();
                parametros.put("id", dto.getId());
                request = new Request("http://localhost:8989/distrito/edit", "/{id}", parametros);
                request.put(dto);
            }
            if(request.isError())
                return new Respuesta(Boolean.FALSE, request.getError());
            DistritoDTO result = (DistritoDTO) request.readEntity(DistritoDTO.class);
            return new Respuesta(Boolean.TRUE, "Distrito", result);
        }catch(Exception ex){
            return new Respuesta(false, ex.toString(), "No puedo establecerce conexion con el servidor");
        }
    }
    
    public Respuesta delete(Integer id){
        try{
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("id", id);
            Request request = new Request("http://localhost:8989/distrito/", "/{id}", parametros);
            request.delete();
            if(request.isError())
                return new Respuesta(Boolean.FALSE, request.getError());
            return new Respuesta(Boolean.TRUE, "Eliminado correctamente");
        }catch(Exception ex){
            return new Respuesta(false, ex.toString(), "No puedo establecerce conexion con el servidor");
        }
    }
}
