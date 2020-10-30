/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.examen.cliente.controller.util;
import java.util.ArrayList;
import java.util.List;
import org.una.examen.cliente.dto.ProvinciaDTO;
import org.una.examen.cliente.dto.CantonDTO;
import org.una.examen.cliente.dto.DistritoDTO;
import org.una.examen.cliente.dto.UnidadDTO;
/**
 *
 * @author Ivan Josué Arias Astua
 */
public class Demografia {
    Object dato;
    String nombre;
    Integer poblacion;
    Double area;
    List<Demografia> hijos;

    public Demografia(Object dato, String nombre) {
        this.dato = dato;
        this.nombre = nombre;
        this.poblacion = 0;
        this.area = 0.0;
        hijos = new ArrayList<>();
    }

    public Demografia(Object dato, String nombre, Integer poblacion, Double area) {
        this.dato = dato;
        this.nombre = nombre;
        this.poblacion = poblacion;
        this.area = area;
        hijos = new ArrayList<>();
    }

    public void setDato(Object dato) {
        this.dato = dato;
    }

    public void setPoblacion(Integer poblacion) {
        this.poblacion = poblacion;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public void setHijos(List<Demografia> hijos) {
        this.hijos = hijos;
        calcularPoblacionArea();
    }

    public Object getDato() {
        return dato;
    }

    public Integer getPoblacion() {
        return poblacion;
    }

    public Double getArea() {
        return area;
    }

    public List<Demografia> getHijos() {
        return hijos;
    }
    
    public String infDato(){
        String info = "";
        if(dato.getClass().equals(ProvinciaDTO.class)){
            ProvinciaDTO provincia = (ProvinciaDTO) dato;
            info = "Nombre: "+provincia.getNombre() +
                   "\nCódigo: "+provincia.getCodigo() +
                   "\nCantidad de Cantones: "+provincia.getCantones().size();
        }else if(dato.getClass().equals(CantonDTO.class)){
            CantonDTO canton = (CantonDTO) dato;
            info = "Nombre: "+canton.getNombre() +
                   "\nCódigo: "+canton.getCodigo() +
                   "\nCantidad de Distritos: "+canton.getDistritos().size();
        }else if(dato.getClass().equals(DistritoDTO.class)){
            DistritoDTO distrito = (DistritoDTO) dato;
            info = "Nombre: "+distrito.getNombre() +
                   "\nCódigo: "+distrito.getCodigo() +
                   "\nCantidad de Barrios: "+distrito.getUnidades().stream().filter(unidad->unidad.getTipo().equals("Barrio")).count() +
                   "\nCantidad de Calles: "+distrito.getUnidades().stream().filter(unidad->unidad.getTipo().equals("Calle")).count() +
                   "\nCantidad de Poblados: "+distrito.getUnidades().stream().filter(unidad->unidad.getTipo().equals("Poblado")).count() +
                   "\nCantidad de Urbanizaciones: "+distrito.getUnidades().stream().filter(unidad->unidad.getTipo().equals("Urbanización")).count();
        }else{
            UnidadDTO unidad = (UnidadDTO) dato; 
            info = "Nombre: "+unidad.getNombre()+"\nCódigo: "+unidad.getCodigo()+"\nTipo: "+unidad.getTipo();
        }
        if(poblacion > 0 && area > 0.0)
            info += "\nPoblacion: "+poblacion + "\nÁrea: "+area;
        return info;
    }
    
    private void calcularPoblacionArea(){
        Integer pobaux = 0; 
        Double areaux = 0.0;
        for(Demografia hijo : hijos){
            pobaux += hijo.getPoblacion();
            areaux += hijo.getArea();
        }
        if(poblacion == 0 && area == 0){
            this.poblacion = pobaux;
            this.area = areaux;
        }
    }

    @Override
    public String toString() {
        String retorno = nombre;
        if(poblacion > 0 && area > 0.0) {
            retorno += " Población: "+poblacion+" Área: "+area;
        }
        return retorno;
    }
}
