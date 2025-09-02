package com.brayan.miapp.DAO;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DataSeeder {
    
    public static void poblarBaseDatos() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            System.out.println("Poblando base de datos con datos de ejemplo...");
            
            // Insertar personas
            insertarPersona(conn, 1, "Juan Carlos", "Pérez García", "juan.perez@universidad.edu");
            insertarPersona(conn, 2, "María Elena", "González López", "maria.gonzalez@universidad.edu");
            insertarPersona(conn, 3, "Carlos Alberto", "Rodríguez Martín", "carlos.rodriguez@universidad.edu");
            insertarPersona(conn, 4, "Ana Sofía", "Martínez Cruz", "ana.martinez@estudiante.edu");
            insertarPersona(conn, 5, "Luis Fernando", "Hernández Silva", "luis.hernandez@estudiante.edu");
            
            // Insertar facultades
            insertarFacultad(conn, 1, "Facultad de Ingeniería", 1);
            insertarFacultad(conn, 2, "Facultad de Ciencias", 2);
            
            // Insertar programas
            insertarPrograma(conn, 1, "Ingeniería de Sistemas", 1);
            insertarPrograma(conn, 2, "Ingeniería Industrial", 1);
            insertarPrograma(conn, 3, "Matemáticas", 2);
            
            // Insertar estudiantes
            insertarEstudiante(conn, 4, 4, 2019001, 1, true, 4.2);
            insertarEstudiante(conn, 5, 5, 2019002, 1, true, 3.8);
            
            // Insertar profesores
            insertarProfesor(conn, 1, 1, "Catedrático", "Tiempo Completo", true);
            insertarProfesor(conn, 2, 2, "Asociado", "Tiempo Completo", true);
            insertarProfesor(conn, 3, 3, "Asistente", "Cátedra", true);
            
            // Insertar cursos
            insertarCurso(conn, 1, "Programación I", 1, true);
            insertarCurso(conn, 2, "Bases de Datos", 1, true);
            insertarCurso(conn, 3, "Algoritmos", 1, true);
            insertarCurso(conn, 4, "Matemáticas Discretas", 3, true);
            
            // Insertar inscripciones
            insertarInscripcion(conn, 1, 2024, 1, 4);
            insertarInscripcion(conn, 2, 2024, 1, 4);
            insertarInscripcion(conn, 1, 2024, 1, 5);
            insertarInscripcion(conn, 3, 2024, 1, 5);
            
            // Insertar asignaciones curso-profesor
            insertarCursoProfesor(conn, 1, 1);
            insertarCursoProfesor(conn, 2, 2);
            insertarCursoProfesor(conn, 3, 1);
            insertarCursoProfesor(conn, 4, 3);
            
            System.out.println("Base de datos poblada exitosamente!");
            
        } catch (Exception e) {
            System.err.println("Error poblando la base de datos: " + e.getMessage());
        }
    }
    
    private static void insertarPersona(Connection conn, int id, String nombres, String apellidos, String email) throws Exception {
        String sql = "MERGE INTO personas (id, nombres, apellidos, email) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.setString(2, nombres);
            stmt.setString(3, apellidos);
            stmt.setString(4, email);
            stmt.executeUpdate();
        }
    }
    
    private static void insertarFacultad(Connection conn, int id, String nombre, int decanoId) throws Exception {
        String sql = "MERGE INTO facultades (id, nombre, decano_id) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.setString(2, nombre);
            stmt.setInt(3, decanoId);
            stmt.executeUpdate();
        }
    }
    
    private static void insertarPrograma(Connection conn, int id, String nombre, int facultadId) throws Exception {
        String sql = "MERGE INTO programas (id, nombre, facultad_id) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.setString(2, nombre);
            stmt.setInt(3, facultadId);
            stmt.executeUpdate();
        }
    }
    
    private static void insertarEstudiante(Connection conn, int id, int personaId, int codigo, int programaId, boolean activo, double promedio) throws Exception {
        String sql = "MERGE INTO estudiantes (id, persona_id, codigo, programa_id, activo, promedio) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.setInt(2, personaId);
            stmt.setInt(3, codigo);
            stmt.setInt(4, programaId);
            stmt.setBoolean(5, activo);
            stmt.setDouble(6, promedio);
            stmt.executeUpdate();
        }
    }
    
    private static void insertarProfesor(Connection conn, int id, int personaId, String nivel, String contrato, boolean activo) throws Exception {
        String sql = "MERGE INTO profesores (id, persona_id, nivel, contrato, activo) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.setInt(2, personaId);
            stmt.setString(3, nivel);
            stmt.setString(4, contrato);
            stmt.setBoolean(5, activo);
            stmt.executeUpdate();
        }
    }
    
    private static void insertarCurso(Connection conn, int id, String nombre, int programaId, boolean activo) throws Exception {
        String sql = "MERGE INTO cursos (id, nombre, programa_id, activo) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.setString(2, nombre);
            stmt.setInt(3, programaId);
            stmt.setBoolean(4, activo);
            stmt.executeUpdate();
        }
    }
    
    private static void insertarInscripcion(Connection conn, int cursoId, int año, int semestre, int estudianteId) throws Exception {
        String sql = "INSERT INTO inscripciones (curso_id, año, semestre, estudiante_id) " +
                    "SELECT ?, ?, ?, ? " +
                    "WHERE NOT EXISTS (SELECT 1 FROM inscripciones WHERE curso_id = ? AND año = ? AND semestre = ? AND estudiante_id = ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, cursoId);
            stmt.setInt(2, año);
            stmt.setInt(3, semestre);
            stmt.setInt(4, estudianteId);
            stmt.setInt(5, cursoId);
            stmt.setInt(6, año);
            stmt.setInt(7, semestre);
            stmt.setInt(8, estudianteId);
            stmt.executeUpdate();
        }
    }
    
    private static void insertarCursoProfesor(Connection conn, int cursoId, int profesorId) throws Exception {
        String sql = "INSERT INTO curso_profesor (curso_id, profesor_id) " +
                    "SELECT ?, ? " +
                    "WHERE NOT EXISTS (SELECT 1 FROM curso_profesor WHERE curso_id = ? AND profesor_id = ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, cursoId);
            stmt.setInt(2, profesorId);
            stmt.setInt(3, cursoId);
            stmt.setInt(4, profesorId);
            stmt.executeUpdate();
        }
    }
}
