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
import org.una.examen.cliente.dto.UnidadDTO;

/**
 *
 * @author Ivan Josué Arias Astua
 */
public class UnidadService {
    
    public Respuesta getByNombre(String nombre){
        try{
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("nombre", nombre);
            Request request = new Request("http://localhost:8989/unidad/nombre", "/{nombre}", parametros);
            request.get();
            if(request.isError())
                return new Respuesta(Boolean.FALSE, request.getError());
            List<UnidadDTO> result = (List<UnidadDTO>) request.readEntity(new GenericType<List<UnidadDTO>>(){});
            return new Respuesta(Boolean.TRUE, "Unidades", result);
        }catch(Exception ex){
            return new Respuesta(false, ex.toString(), "No puedo establecerce conexion con el servidor");
        }
    }
    
    public Respuesta getByCodigo(String codigo){
        try{
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("codigo", codigo);
            Request request = new Request("http://localhost:8989/unidad/codigo", "/{codigo}", parametros);
            request.get();
            if(request.isError())
                return new Respuesta(Boolean.FALSE, request.getError());
            List<UnidadDTO> result = (List<UnidadDTO>) request.readEntity(new GenericType<List<UnidadDTO>>(){});
            return new Respuesta(Boolean.TRUE, "Unidades", result);
        }catch(Exception ex){
            return new Respuesta(false, ex.toString(), "No puedo establecerce conexion con el servidor");
        }
    }
    
    public Respuesta getByDistrito(String distrito){
        try{
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("distrito", distrito);
            Request request = new Request("http://localhost:8989/unidad/distrito", "/{distrito}", parametros);
            request.get();
            if(request.isError())
                return new Respuesta(Boolean.FALSE, request.getError());
            List<UnidadDTO> result = (List<UnidadDTO>) request.readEntity(new GenericType<List<UnidadDTO>>(){});
            return new Respuesta(Boolean.TRUE, "Unidades", result);
        }catch(Exception ex){
            return new Respuesta(false, ex.toString(), "No puedo establecerce conexion con el servidor");
        }
    }
    
    public Respuesta getByPoblacion(Integer poblacion){
        try{
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("poblacion", poblacion);
            Request request = new Request("http://localhost:8989/unidad/poblacion", "/{poblacion}", parametros);
            request.get();
            if(request.isError())
                return new Respuesta(Boolean.FALSE, request.getError());
            List<UnidadDTO> result = (List<UnidadDTO>) request.readEntity(new GenericType<List<UnidadDTO>>(){});
            return new Respuesta(Boolean.TRUE, "Unidades", result);
        }catch(Exception ex){
            return new Respuesta(false, ex.toString(), "No puedo establecerce conexion con el servidor");
        }
    }
    
    public Respuesta getByArea(Double area){
        try{
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("area", area);
            Request request = new Request("http://localhost:8989/unidad/area", "/{area}", parametros);
            request.get();
            if(request.isError())
                return new Respuesta(Boolean.FALSE, request.getError());
            List<UnidadDTO> result = (List<UnidadDTO>) request.readEntity(new GenericType<List<UnidadDTO>>(){});
            return new Respuesta(Boolean.TRUE, "Unidades", result);
        }catch(Exception ex){
            return new Respuesta(false, ex.toString(), "No puedo establecerce conexion con el servidor");
        }
    }
    
    public Respuesta guardar(UnidadDTO dto){
        try{
            Request request;
            if(dto.getId() == 0){
                request = new Request("http://localhost:8989/unidad/save");
                request.post(dto);
            }else{
                Map<String, Object> parametros = new HashMap<>();
                parametros.put("id", dto.getId());
                request = new Request("http://localhost:8989/unidad/edit", "/{id}", parametros);
                request.put(dto);
            }
            if(request.isError())
                return new Respuesta(Boolean.FALSE, request.getError());
            UnidadDTO result = (UnidadDTO) request.readEntity(UnidadDTO.class);
            return new Respuesta(Boolean.TRUE, "Unidad", result);
        }catch(Exception ex){
            return new Respuesta(false, ex.toString(), "No puedo establecerce conexion con el servidor");
        }
    }
    
    public Respuesta delete(Integer id){
        try{
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("id", id);
            Request request = new Request("http://localhost:8989/unidad/", "/{id}", parametros);
            request.delete();
            if(request.isError())
                return new Respuesta(Boolean.FALSE, request.getError());
            return new Respuesta(Boolean.TRUE, "Eliminado correctamente");
        }catch(Exception ex){
            return new Respuesta(false, ex.toString(), "No puedo establecerce conexion con el servidor");
        }
    }
}
