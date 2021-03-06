/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.examen.cliente.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MembresiaDTO {
    
    private Long id;
    private Float monto;
    private String descripcion;
    private Integer periodicidad;
    private List<ClienteDTO> clientes = new ArrayList();
    
    @Override
    public String toString(){
        return this.descripcion +". Monto: "+String.valueOf(this.monto);
    }
}
