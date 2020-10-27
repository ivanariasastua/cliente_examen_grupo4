/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.examen.cliente.service;

import java.util.HashMap;
import java.util.Map;
import org.una.examen.cliente.controller.util.Request;
import org.una.examen.cliente.controller.util.Respuesta;
import org.una.examen.cliente.dto.TareaDTO;
/**
 *
 * @author cordo
 */
public class TareaService {

    public Respuesta guardarTarea(TareaDTO tarea) {
        try {
            Request request = new Request("tareas/save");
            request.post(tarea);
            if (request.isError()) {
                return new Respuesta(false, request.getError(), "No se pudo guardar la tarea");
            }
            TareaDTO result = (TareaDTO) request.readEntity(TareaDTO.class);
            return new Respuesta(true, "Tareas", result);
        } catch (Exception ex) {
            return new Respuesta(false, ex.toString(), "No puedo establecerce conexion con el servidor");
        }
    }

    public Respuesta modificarTarea(Long id, TareaDTO tarea) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("id", id);
            Request request = new Request("tareas/editar", "/{id}", parametros);
            request.put(tarea);
            if (request.isError()) {
                return new Respuesta(false, request.getError(), "No se pudo modificar la tarea");
            }
            TareaDTO result = (TareaDTO) request.readEntity(TareaDTO.class);
            return new Respuesta(true, "Tareas", result);
        } catch (Exception ex) {
            return new Respuesta(false, ex.toString(), "No puedo establecerce conexion con el servidor");
        }
    }

    public Respuesta deleteTarea(Long id) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("id", id);
            Request request = new Request("tareas", "/{id}", parametros);
            request.delete();
            if (request.isError()) {
                return new Respuesta(false, request.getError(), "No se pudo eliminar la tarea");
            }
            return new Respuesta(true, "Tarea eliminado exitosamente");
        } catch (Exception ex) {
            return new Respuesta(false, ex.toString(), "No puedo establecerce conexion con el servidor");
        }
    }
}
