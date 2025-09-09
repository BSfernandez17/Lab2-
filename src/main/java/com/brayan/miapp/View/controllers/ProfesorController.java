package com.brayan.miapp.View.controllers;

import com.brayan.miapp.Model.Profesor;
import com.brayan.miapp.DAO.ProfesorDAO;
import java.util.List;

public class ProfesorController {
    
    private ProfesorDAO profesorDAO;
    private StatusCallback statusCallback;
    
    public ProfesorController() {
        this.profesorDAO = new ProfesorDAO();
    }
    
    public void setStatusCallback(StatusCallback callback) {
        this.statusCallback = callback;
    }
    
    public void agregarProfesor(Profesor profesor) {
        try {
            profesorDAO.insertar(profesor);
            mostrarEstado("✅ Profesor agregado correctamente", false);
        } catch (Exception e) {
            mostrarEstado("❌ Error al agregar profesor: " + e.getMessage(), true);
        }
    }
    
    public void actualizarProfesor(Profesor profesor) {
        try {
            profesorDAO.actualizar(profesor);
            mostrarEstado("✅ Profesor actualizado correctamente", false);
        } catch (Exception e) {
            mostrarEstado("❌ Error al actualizar profesor: " + e.getMessage(), true);
        }
    }
    
    public void eliminarProfesor(Double id) {
        try {
            profesorDAO.eliminar(id);
            mostrarEstado("✅ Profesor eliminado correctamente", false);
        } catch (Exception e) {
            mostrarEstado("❌ Error al eliminar profesor: " + e.getMessage(), true);
        }
    }
    
    public List<Profesor> listarProfesores() {
        try {
            return profesorDAO.listar();
        } catch (Exception e) {
            mostrarEstado("❌ Error al cargar profesores: " + e.getMessage(), true);
            return List.of();
        }
    }
    
    public Profesor buscarProfesor(Double id) {
        try {
            return profesorDAO.buscarPorId(id);
        } catch (Exception e) {
            mostrarEstado("❌ Error al buscar profesor: " + e.getMessage(), true);
            return null;
        }
    }
    
    private void mostrarEstado(String mensaje, boolean esError) {
        if (statusCallback != null) {
            statusCallback.mostrarEstado(mensaje, esError);
        }
    }
}
