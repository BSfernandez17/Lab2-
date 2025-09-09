package com.brayan.miapp.View.controllers;

import com.brayan.miapp.Model.Curso;
import com.brayan.miapp.Model.Programa;
import com.brayan.miapp.DAO.CursoDAO;
import com.brayan.miapp.DAO.ProgramaDAO;
import java.util.List;

public class CursoController {
    
    private CursoDAO cursoDAO;
    private ProgramaDAO programaDAO;
    private StatusCallback statusCallback;
    
    public CursoController() {
        this.cursoDAO = new CursoDAO();
        this.programaDAO = new ProgramaDAO();
    }
    
    public void setStatusCallback(StatusCallback callback) {
        this.statusCallback = callback;
    }
    
    public void agregarCurso(Curso curso) {
        try {
            cursoDAO.insertar(curso);
            mostrarEstado("✅ Curso agregado correctamente", false);
        } catch (Exception e) {
            mostrarEstado("❌ Error al agregar curso: " + e.getMessage(), true);
        }
    }
    
    public void actualizarCurso(Curso curso) {
        try {
            cursoDAO.actualizar(curso);
            mostrarEstado("✅ Curso actualizado correctamente", false);
        } catch (Exception e) {
            mostrarEstado("❌ Error al actualizar curso: " + e.getMessage(), true);
        }
    }
    
    public void eliminarCurso(Integer id) {
        try {
            cursoDAO.eliminar(id);
            mostrarEstado("✅ Curso eliminado correctamente", false);
        } catch (Exception e) {
            mostrarEstado("❌ Error al eliminar curso: " + e.getMessage(), true);
        }
    }
    
    public List<Curso> listarCursos() {
        try {
            return cursoDAO.listar();
        } catch (Exception e) {
            mostrarEstado("❌ Error al cargar cursos: " + e.getMessage(), true);
            return List.of();
        }
    }
    
    public Curso buscarCurso(Integer id) {
        try {
            return cursoDAO.buscarPorId(id);
        } catch (Exception e) {
            mostrarEstado("❌ Error al buscar curso: " + e.getMessage(), true);
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
