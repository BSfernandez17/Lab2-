package com.brayan.miapp.View.controllers;

import com.brayan.miapp.Model.Facultad;
import com.brayan.miapp.Model.Persona;
import com.brayan.miapp.DAO.FacultadDAO;
import com.brayan.miapp.DAO.PersonaDAO;
import java.util.List;

public class FacultadController {
    
    private FacultadDAO facultadDAO;
    private PersonaDAO personaDAO;
    private StatusCallback statusCallback;
    
    public FacultadController() {
        this.facultadDAO = new FacultadDAO();
        this.personaDAO = new PersonaDAO();
    }
    
    public void setStatusCallback(StatusCallback callback) {
        this.statusCallback = callback;
    }
    
    public void agregarFacultad(Facultad facultad) {
        try {
            facultadDAO.insertar(facultad);
            mostrarEstado("✅ Facultad agregada correctamente", false);
        } catch (Exception e) {
            mostrarEstado("❌ Error al agregar facultad: " + e.getMessage(), true);
        }
    }
    
    public void actualizarFacultad(Facultad facultad) {
        try {
            facultadDAO.actualizar(facultad);
            mostrarEstado("✅ Facultad actualizada correctamente", false);
        } catch (Exception e) {
            mostrarEstado("❌ Error al actualizar facultad: " + e.getMessage(), true);
        }
    }
    
    public void eliminarFacultad(Double id) {
        try {
            facultadDAO.eliminar(id);
            mostrarEstado("✅ Facultad eliminada correctamente", false);
        } catch (Exception e) {
            mostrarEstado("❌ Error al eliminar facultad: " + e.getMessage(), true);
        }
    }
    
    public List<Facultad> listarFacultades() {
        try {
            return facultadDAO.listar();
        } catch (Exception e) {
            mostrarEstado("❌ Error al cargar facultades: " + e.getMessage(), true);
            return List.of();
        }
    }
    
    public Facultad buscarFacultad(Double id) {
        try {
            return facultadDAO.buscarPorId(id);
        } catch (Exception e) {
            mostrarEstado("❌ Error al buscar facultad: " + e.getMessage(), true);
            return null;
        }
    }
    
    public List<Persona> listarPersonas() {
        try {
            return personaDAO.listar();
        } catch (Exception e) {
            mostrarEstado("❌ Error al cargar personas para decanos: " + e.getMessage(), true);
            return List.of();
        }
    }
    
    private void mostrarEstado(String mensaje, boolean esError) {
        if (statusCallback != null) {
            statusCallback.mostrarEstado(mensaje, esError);
        }
    }
}
