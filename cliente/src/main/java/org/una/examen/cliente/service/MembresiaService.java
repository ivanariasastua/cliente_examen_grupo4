/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.examen.cliente.service;

import org.una.examen.cliente.controller.util.Request;
import org.una.examen.cliente.controller.util.Respuesta;
import org.una.examen.cliente.dto.MembresiaDTO;

public class MembresiaService {
    
    public Respuesta guardarMembresia(MembresiaDTO membresia){
        try{
            Request request = new Request("http://localhost:8989/membresias/save");
            request.post(membresia);
            if(request.isError()){
                return new Respuesta(false, request.getError(), "Error al guardar la membresía");
            }
            MembresiaDTO result = (MembresiaDTO) request.readEntity(MembresiaDTO.class);
            return new Respuesta(true, "Membresias", result);
        }catch(Exception ex){
            return new Respuesta(false, ex.toString(), "No se estableció conexión con el servidor");
        }
    }
}
