package com.brayan.miapp.View.controllers;

import com.brayan.miapp.Model.Programa;
import com.brayan.miapp.Model.Facultad;
import com.brayan.miapp.DAO.ProgramaDAO;
import com.brayan.miapp.DAO.FacultadDAO;
import java.util.List;

public class ProgramaController {
    
    private ProgramaDAO programaDAO;
    private FacultadDAO facultadDAO;
    private StatusCallback statusCallback;
    
    public ProgramaController() {
        this.programaDAO = new ProgramaDAO();
        this.facultadDAO = new FacultadDAO();
    }
    
    public void setStatusCallback(StatusCallback callback) {
        this.statusCallback = callback;
    }
    
    public void agregarPrograma(Programa programa) {
        try {
            programaDAO.insertar(programa);
            mostrarEstado("✅ Programa agregado correctamente", false);
        } catch (Exception e) {
            mostrarEstado("❌ Error al agregar programa: " + e.getMessage(), true);
        }
    }
    
    public void actualizarPrograma(Programa programa) {
        try {
            programaDAO.actualizar(programa);
            mostrarEstado("✅ Programa actualizado correctamente", false);
        } catch (Exception e) {
            mostrarEstado("❌ Error al actualizar programa: " + e.getMessage(), true);
        }
    }
    
    public void eliminarPrograma(Double id) {
        try {
            programaDAO.eliminar(id);
            mostrarEstado("✅ Programa eliminado correctamente", false);
        } catch (Exception e) {
            mostrarEstado("❌ Error al eliminar programa: " + e.getMessage(), true);
        }
    }
    
    public List<Programa> listarProgramas() {
        try {
            return programaDAO.listar();
        } catch (Exception e) {
            mostrarEstado("❌ Error al cargar programas: " + e.getMessage(), true);
            return List.of();
        }
    }
    
    public Programa buscarPrograma(Double id) {
        try {
            return programaDAO.buscarPorId(id);
        } catch (Exception e) {
            mostrarEstado("❌ Error al buscar programa: " + e.getMessage(), true);
            return null;
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
    
    public Programa obtenerPorId(Double id) {
        try {
            return programaDAO.buscarPorId(id);
        } catch (Exception e) {
            mostrarEstado("❌ Error al obtener programa: " + e.getMessage(), true);
            return null;
        }
    }
    
    private void mostrarEstado(String mensaje, boolean esError) {
        if (statusCallback != null) {
            statusCallback.mostrarEstado(mensaje, esError);
        }
    }
}
