package com.brayan.miapp.View.controllers;

import javafx.scene.control.Alert;

import com.brayan.miapp.DAO.DatabaseConnection;
import com.brayan.miapp.Model.*;
import com.brayan.miapp.View.components.FormularioCursoProfesor;
import com.brayan.miapp.View.components.ListaCursosProfesores;
import com.brayan.miapp.View.components.BarraEstadoCursosProfesores;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class CursoProfesorController {
    
    private FormularioCursoProfesor formulario;
    private ListaCursosProfesores lista;
    private BarraEstadoCursosProfesores barraEstado;
    private List<String> asignaciones = new ArrayList<>();
    
    public void setComponents(FormularioCursoProfesor formulario, ListaCursosProfesores lista, BarraEstadoCursosProfesores barraEstado) {
        this.formulario = formulario;
        this.lista = lista;
        this.barraEstado = barraEstado;
        cargarAsignaciones();
        cargarCursosYProfesores();
    }
    
    public void asignarCursoProfesor() {
        if (!formulario.validarCampos()) {
            mostrarError("Debe seleccionar un curso y un profesor");
            return;
        }
        
        try {
            Curso curso = formulario.getCursoSeleccionado();
            Profesor profesor = formulario.getProfesorSeleccionado();
            
            try (Connection conn = DatabaseConnection.getConnection()) {
                String sql = "INSERT INTO curso_profesor (curso_id, profesor_id) VALUES (?, ?)";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, curso.getId());
                stmt.setInt(2, ((Double) profesor.getId()).intValue());
                
                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    barraEstado.actualizarEstado("Asignación realizada exitosamente");
                    cargarAsignaciones();
                    formulario.limpiarFormulario();
                } else {
                    mostrarError("Error al realizar la asignación");
                }
            }
        } catch (Exception e) {
            mostrarError("Error: " + e.getMessage());
        }
    }
    
    public void desasignarCursoProfesor() {
        mostrarError("Función no implementada");
    }
    
    public void limpiarFormulario() {
        formulario.limpiarFormulario();
        barraEstado.actualizarEstado("Formulario limpiado");
    }
    
    public void refrescarDatos() {
        cargarAsignaciones();
        cargarCursosYProfesores();
        barraEstado.actualizarEstado("Datos actualizados");
    }
    
    public void cargarAsignacionSeleccionada(int indice) {
        if (indice >= 0 && indice < asignaciones.size()) {
            barraEstado.actualizarEstado("Edición no disponible en esta versión");
        }
    }
    
    private void cargarAsignaciones() {
        asignaciones.clear();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT c.nombre as curso_nombre, " +
                        "CONCAT(p.nombres, ' ', p.apellidos) as profesor_nombre, " +
                        "pr.nivel, pr.contrato " +
                        "FROM curso_profesor cp " +
                        "JOIN cursos c ON cp.curso_id = c.id " +
                        "JOIN profesores pr ON cp.profesor_id = pr.id " +
                        "JOIN personas p ON pr.persona_id = p.id " +
                        "ORDER BY c.nombre";
            
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            while (rs.next()) {
                String asignacion = String.format(
                    "CursoProfesor{curso=%s, profesor=%s, nivel=%s, contrato=%s}",
                    rs.getString("curso_nombre"),
                    rs.getString("profesor_nombre"),
                    rs.getString("nivel"),
                    rs.getString("contrato")
                );
                asignaciones.add(asignacion);
            }
            
            lista.actualizarLista(asignaciones);
            barraEstado.actualizarTotal(asignaciones.size());
            
        } catch (SQLException e) {
            mostrarError("Error al cargar asignaciones: " + e.getMessage());
        }
    }
    
    private void cargarCursosYProfesores() {
        List<Curso> cursos = new ArrayList<>();
        List<Profesor> profesores = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection()) {
            // Cargar cursos
            String sqlCursos = "SELECT id, nombre, activo FROM cursos WHERE activo = true ORDER BY nombre";
            Statement stmtCursos = conn.createStatement();
            ResultSet rsCursos = stmtCursos.executeQuery(sqlCursos);
            
            while (rsCursos.next()) {
                Curso curso = new Curso(
                    rsCursos.getInt("id"),
                    rsCursos.getString("nombre"),
                    null, // programa
                    rsCursos.getBoolean("activo")
                );
                cursos.add(curso);
            }
            
            // Cargar profesores
            String sqlProfesores = "SELECT pr.id, p.nombres, p.apellidos, p.email, pr.nivel, pr.contrato, pr.activo " +
                                  "FROM profesores pr " +
                                  "JOIN personas p ON pr.persona_id = p.id " +
                                  "WHERE pr.activo = true " +
                                  "ORDER BY p.nombres, p.apellidos";
            Statement stmtProfesores = conn.createStatement();
            ResultSet rsProfesores = stmtProfesores.executeQuery(sqlProfesores);
            
            while (rsProfesores.next()) {
                Profesor profesor = new Profesor(
                    rsProfesores.getDouble("id"),
                    rsProfesores.getString("nombres"),
                    rsProfesores.getString("apellidos"),
                    rsProfesores.getString("email"),
                    rsProfesores.getString("contrato")
                );
                profesores.add(profesor);
            }
            
            formulario.actualizarComboBoxes(cursos, profesores);
            
        } catch (SQLException e) {
            mostrarError("Error al cargar datos: " + e.getMessage());
        }
    }
    
    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
        
        if (barraEstado != null) {
            barraEstado.actualizarEstado("Error: " + mensaje);
        }
    }
}
