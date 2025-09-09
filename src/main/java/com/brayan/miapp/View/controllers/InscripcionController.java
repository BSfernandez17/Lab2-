package com.brayan.miapp.View.controllers;

import javafx.scene.control.Alert;

import com.brayan.miapp.DAO.DatabaseConnection;
import com.brayan.miapp.Model.*;
import com.brayan.miapp.View.components.FormularioInscripcion;
import com.brayan.miapp.View.components.ListaInscripciones;
import com.brayan.miapp.View.components.BarraEstadoInscripciones;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class InscripcionController {
    
    private FormularioInscripcion formulario;
    private ListaInscripciones lista;
    private BarraEstadoInscripciones barraEstado;
    private List<String> inscripciones = new ArrayList<>();
    
    public void setComponents(FormularioInscripcion formulario, ListaInscripciones lista, BarraEstadoInscripciones barraEstado) {
        this.formulario = formulario;
        this.lista = lista;
        this.barraEstado = barraEstado;
        cargarInscripciones();
        cargarCursosYEstudiantes();
    }
    
    public void inscribirEstudiante() {
        if (!formulario.validarCampos()) {
            mostrarError("Todos los campos son obligatorios");
            return;
        }
        
        try {
            Curso curso = formulario.getCursoSeleccionado();
            Estudiante estudiante = formulario.getEstudianteSeleccionado();
            Integer año = formulario.getAño();
            Integer semestre = formulario.getSemestre();
            
            try (Connection conn = DatabaseConnection.getConnection()) {
                String sql = "INSERT INTO inscripciones (curso_id, año, semestre, estudiante_id) VALUES (?, ?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, curso.getId());
                stmt.setInt(2, año);
                stmt.setInt(3, semestre);
                stmt.setInt(4, ((Double) estudiante.getId()).intValue());
                
                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    barraEstado.actualizarEstado("Inscripción registrada exitosamente");
                    cargarInscripciones();
                    formulario.limpiarFormulario();
                } else {
                    mostrarError("Error al registrar la inscripción");
                }
            }
        } catch (Exception e) {
            mostrarError("Error: " + e.getMessage());
        }
    }
    
    public void eliminarInscripcion() {
        mostrarError("Función no implementada");
    }
    
    public void limpiarFormulario() {
        formulario.limpiarFormulario();
        barraEstado.actualizarEstado("Formulario limpiado");
    }
    
    public void refrescarDatos() {
        cargarInscripciones();
        cargarCursosYEstudiantes();
        barraEstado.actualizarEstado("Datos actualizados");
    }
    
    public void cargarInscripcionSeleccionada(int indice) {
        if (indice >= 0 && indice < inscripciones.size()) {
            barraEstado.actualizarEstado("Edición no disponible en esta versión");
        }
    }
    
    private void cargarInscripciones() {
        inscripciones.clear();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT i.id, c.nombre as curso_nombre, i.año, i.semestre, " +
                        "CONCAT(p.nombres, ' ', p.apellidos) as estudiante_nombre " +
                        "FROM inscripciones i " +
                        "JOIN cursos c ON i.curso_id = c.id " +
                        "JOIN estudiantes e ON i.estudiante_id = e.id " +
                        "JOIN personas p ON e.persona_id = p.id " +
                        "ORDER BY i.id";
            
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            while (rs.next()) {
                String inscripcion = String.format(
                    "Inscripcion{id=%d, curso=%s, año=%d, semestre=%d, estudiante=%s}",
                    rs.getInt("id"),
                    rs.getString("curso_nombre"),
                    rs.getInt("año"),
                    rs.getInt("semestre"),
                    rs.getString("estudiante_nombre")
                );
                inscripciones.add(inscripcion);
            }
            
            lista.actualizarLista(inscripciones);
            barraEstado.actualizarTotal(inscripciones.size());
            
        } catch (SQLException e) {
            mostrarError("Error al cargar inscripciones: " + e.getMessage());
        }
    }
    
    private void cargarCursosYEstudiantes() {
        List<Curso> cursos = new ArrayList<>();
        List<Estudiante> estudiantes = new ArrayList<>();
        
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
            
            // Cargar estudiantes
            String sqlEstudiantes = "SELECT e.id, p.nombres, p.apellidos, p.email, e.codigo, e.activo, e.promedio " +
                                   "FROM estudiantes e " +
                                   "JOIN personas p ON e.persona_id = p.id " +
                                   "WHERE e.activo = true " +
                                   "ORDER BY p.nombres, p.apellidos";
            Statement stmtEstudiantes = conn.createStatement();
            ResultSet rsEstudiantes = stmtEstudiantes.executeQuery(sqlEstudiantes);
            
            while (rsEstudiantes.next()) {
                Estudiante estudiante = new Estudiante(
                    rsEstudiantes.getDouble("id"),
                    rsEstudiantes.getString("nombres"),
                    rsEstudiantes.getString("apellidos"),
                    rsEstudiantes.getString("email"),
                    rsEstudiantes.getDouble("codigo"),
                    null, // programa
                    rsEstudiantes.getBoolean("activo"),
                    rsEstudiantes.getDouble("promedio")
                );
                estudiantes.add(estudiante);
            }
            
            formulario.actualizarComboBoxes(cursos, estudiantes);
            
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
