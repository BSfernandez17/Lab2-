package com.brayan.miapp.View.controllers;

import com.brayan.miapp.Model.Estudiante;
import com.brayan.miapp.Model.Programa;
import com.brayan.miapp.DAO.EstudianteDAO;
import com.brayan.miapp.DAO.ProgramaDAO;
import java.util.List;

public class EstudianteController {
    
    private EstudianteDAO estudianteDAO;
    private ProgramaDAO programaDAO;
    private StatusCallback statusCallback;
    
    public EstudianteController() {
        this.estudianteDAO = new EstudianteDAO();
        this.programaDAO = new ProgramaDAO();
    }
    
    public void setStatusCallback(StatusCallback callback) {
        this.statusCallback = callback;
    }
    
    public void agregarEstudiante(Estudiante estudiante) {
        try {
            estudianteDAO.insertar(estudiante);
            mostrarEstado("✅ Estudiante agregado correctamente", false);
        } catch (Exception e) {
            mostrarEstado("❌ Error al agregar estudiante: " + e.getMessage(), true);
        }
    }
    
    public void actualizarEstudiante(Estudiante estudiante) {
        try {
            // Usar el método actualizar en lugar de eliminar e insertar
            estudianteDAO.actualizar(estudiante);
            mostrarEstado("✅ Estudiante actualizado correctamente", false);
        } catch (Exception e) {
            mostrarEstado("❌ Error al actualizar estudiante: " + e.getMessage(), true);
        }
    }
    
    public void eliminarEstudiante(Double id) {
        try {
            estudianteDAO.eliminar(id);
            mostrarEstado("✅ Estudiante eliminado correctamente", false);
        } catch (Exception e) {
            mostrarEstado("❌ Error al eliminar estudiante: " + e.getMessage(), true);
        }
    }
    
    public List<Estudiante> listarEstudiantes() {
        try {
            return estudianteDAO.listar();
        } catch (Exception e) {
            mostrarEstado("❌ Error al cargar estudiantes: " + e.getMessage(), true);
            return List.of();
        }
    }
    
    public Estudiante buscarEstudiante(Double id) {
        try {
            return estudianteDAO.buscarPorId(id);
        } catch (Exception e) {
            mostrarEstado("❌ Error al buscar estudiante: " + e.getMessage(), true);
            return null;
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
    
    private void mostrarEstado(String mensaje, boolean esError) {
        if (statusCallback != null) {
            statusCallback.mostrarEstado(mensaje, esError);
        }
    }
}
