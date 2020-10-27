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
import org.una.examen.cliente.dto.ProyectoDTO;

/**
 *
 * @author cordo
 */
public class ProyectoService {

    public Respuesta getAll() {
        try {
            Request request = new Request("http://localhost:8989/proyectos/proyect/");
            request.get();
            if (request.isError()) {
                return new Respuesta(false, request.getError(), "Error al obtener todos los proyectos");
            }
            List<ProyectoDTO> result = (List<ProyectoDTO>) request.readEntity(new GenericType<List<ProyectoDTO>>() {
            });
            return new Respuesta(true, "Proyectos", result);
        } catch (Exception ex) {
            return new Respuesta(false, ex.toString(), "No puedo establecerce conexion con el servidor");
        }
    }

    public Respuesta guardarProyecto(ProyectoDTO proyecto) {
        try {
            Request request = new Request("proyectos/save");
            request.post(proyecto);
            if (request.isError()) {
                return new Respuesta(false, request.getError(), "No se pudo guardar el proyecto");
            }
            ProyectoDTO result = (ProyectoDTO) request.readEntity(ProyectoDTO.class);
            return new Respuesta(true, "Proyectos", result);
        } catch (Exception ex) {
            return new Respuesta(false, ex.toString(), "No puedo establecerce conexion con el servidor");
        }
    }

    public Respuesta modificarProyecto(Long id, ProyectoDTO proyecto) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("id", id);
            Request request = new Request("proyectos/editar", "/{id}", parametros);
            request.put(proyecto);
            if (request.isError()) {
                return new Respuesta(false, request.getError(), "No se pudo modificar el proyecto");
            }
            ProyectoDTO result = (ProyectoDTO) request.readEntity(ProyectoDTO.class);
            return new Respuesta(true, "Proyectos", result);
        } catch (Exception ex) {
            return new Respuesta(false, ex.toString(), "No puedo establecerce conexion con el servidor");
        }
    }

    public Respuesta deleteProyecto(Long id) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("id", id);
            Request request = new Request("proyectos", "/{id}", parametros);
            request.delete();
            if (request.isError()) {
                return new Respuesta(false, request.getError(), "No se pudo eliminar el proyecto");
            }
            return new Respuesta(true, "Proyecto eliminado exitosamente");
        } catch (Exception ex) {
            return new Respuesta(false, ex.toString(), "No puedo establecerce conexion con el servidor");
        }
    }
}
