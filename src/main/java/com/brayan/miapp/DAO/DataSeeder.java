package com.brayan.miapp.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * DataSeeder - Utilidad para poblar la base de datos con datos de prueba
 * 
 * Esta clase maneja toda la creación de datos de prueba para el sistema universitario.
 * Incluye datos para todas las entidades: personas, facultades, programas, estudiantes,
 * profesores, cursos, inscripciones y asignaciones curso-profesor.
 * 
 * Uso:
 * - mvn exec:java -Dexec.mainClass="com.brayan.miapp.DAO.DataSeeder" (poblar datos)
 * - mvn exec:java -Dexec.mainClass="com.brayan.miapp.DAO.DataSeeder" -Dexec.args="clean" (limpiar datos)
 * 
 * @author Brayan
 * @version 1.0
 */

public class DataSeeder {
    
    public static void main(String[] args) {
        System.out.println("🚀 Ejecutando DataSeeder...");
        if (args.length > 0 && "clean".equals(args[0])) {
            System.out.println("🧹 Modo limpieza activado");
            limpiarBaseDatos();
        } else {
            poblarBaseDatos();
        }
    }
    
    public static void limpiarBaseDatos() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            System.out.println("🧹 Limpiando base de datos...");
            limpiarDatos(conn);
            System.out.println("✅ Base de datos limpiada exitosamente!");
        } catch (Exception e) {
            System.err.println("❌ Error limpiando la base de datos: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public static void poblarBaseDatos() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            System.out.println("Poblando base de datos con datos de ejemplo...");
            
            // Limpiar datos existentes
            limpiarDatos(conn);
            
            // Insertar personas (más datos de prueba)
            insertarPersona(conn, 1, "Juan Carlos", "Pérez García", "juan.perez@universidad.edu");
            insertarPersona(conn, 2, "María Elena", "González López", "maria.gonzalez@universidad.edu");
            insertarPersona(conn, 3, "Carlos Alberto", "Rodríguez Martín", "carlos.rodriguez@universidad.edu");
            insertarPersona(conn, 4, "Ana Sofía", "Martínez Cruz", "ana.martinez@estudiante.edu");
            insertarPersona(conn, 5, "Luis Fernando", "Hernández Silva", "luis.hernandez@estudiante.edu");
            insertarPersona(conn, 6, "Carmen Rosa", "Vásquez Torres", "carmen.vasquez@estudiante.edu");
            insertarPersona(conn, 7, "Roberto Carlos", "Jiménez Mora", "roberto.jimenez@estudiante.edu");
            insertarPersona(conn, 8, "Patricia Isabel", "Ruiz Gómez", "patricia.ruiz@estudiante.edu");
            
            // Profesores adicionales
            insertarPersona(conn, 201, "Carlos", "Rodriguez", "carlos.rodriguez@university.edu");
            insertarPersona(conn, 202, "Ana", "Martinez", "ana.martinez@university.edu");
            insertarPersona(conn, 203, "Luis", "Garcia", "luis.garcia@university.edu");
            insertarPersona(conn, 204, "Maria", "Lopez", "maria.lopez@university.edu");
            
            // Insertar facultades
            insertarFacultad(conn, 1, "Facultad de Ingeniería", 1);
            insertarFacultad(conn, 2, "Facultad de Ciencias", 2);
            insertarFacultad(conn, 3, "Facultad de Humanidades", 3);
            
            // Insertar programas (más programas de prueba)
            insertarPrograma(conn, 1, "Ingeniería de Sistemas", 8, java.sql.Date.valueOf("2010-01-15"), 1);
            insertarPrograma(conn, 2, "Ingeniería Industrial", 10, java.sql.Date.valueOf("2008-03-20"), 1);
            insertarPrograma(conn, 3, "Matemáticas", 8, java.sql.Date.valueOf("2005-09-10"), 2);
            insertarPrograma(conn, 4, "Matemáticas Aplicadas", 6, java.sql.Date.valueOf("2012-02-28"), 2);
            insertarPrograma(conn, 5, "Física", 10, java.sql.Date.valueOf("2007-08-15"), 2);
            insertarPrograma(conn, 6, "Química", 8, java.sql.Date.valueOf("2009-01-30"), 2);
            
            // Insertar estudiantes (más estudiantes de prueba)
            insertarEstudiante(conn, 4, 4, 2019001, 1, true, 4.2);
            insertarEstudiante(conn, 5, 5, 2019002, 1, true, 3.8);
            insertarEstudiante(conn, 6, 6, 2020001, 2, true, 4.0);
            insertarEstudiante(conn, 7, 7, 2020002, 3, true, 3.9);
            insertarEstudiante(conn, 8, 8, 2021001, 1, true, 4.1);
            
            // Insertar profesores (más profesores de prueba)
            insertarProfesor(conn, 1, 1, "Catedrático", "Tiempo Completo", true);
            insertarProfesor(conn, 2, 2, "Asociado", "Tiempo Completo", true);
            insertarProfesor(conn, 3, 3, "Asistente", "Cátedra", true);
            insertarProfesor(conn, 201, 201, "Docente", "Tiempo Completo", true);
            insertarProfesor(conn, 202, 202, "Docente", "Medio Tiempo", true);
            insertarProfesor(conn, 203, 203, "Docente", "Catedra", true);
            insertarProfesor(conn, 204, 204, "Docente", "Tiempo Completo", true);
            
            // Insertar cursos (más cursos de prueba)
            insertarCurso(conn, 1, "Programación I", 1, true);
            insertarCurso(conn, 2, "Bases de Datos", 1, true);
            insertarCurso(conn, 3, "Algoritmos", 1, true);
            insertarCurso(conn, 4, "Matemáticas Discretas", 3, true);
            insertarCurso(conn, 101, "Programación II", 1, true);
            insertarCurso(conn, 102, "Estructura de Datos", 1, true);
            insertarCurso(conn, 103, "Ingeniería de Software", 1, true);
            insertarCurso(conn, 201, "Cálculo I", 3, true);
            insertarCurso(conn, 202, "Álgebra Lineal", 3, true);
            insertarCurso(conn, 301, "Algoritmos Avanzados", 1, false); // Inactivo
            
            // Insertar inscripciones
            insertarInscripcion(conn, 1, 2024, 1, 4);
            insertarInscripcion(conn, 2, 2024, 1, 4);
            insertarInscripcion(conn, 1, 2024, 1, 5);
            insertarInscripcion(conn, 3, 2024, 1, 5);
            insertarInscripcion(conn, 101, 2024, 1, 6);
            insertarInscripcion(conn, 102, 2024, 1, 7);
            insertarInscripcion(conn, 201, 2024, 1, 8);
            
            // Insertar asignaciones curso-profesor (simplificado)
            insertarCursoProfesor(conn, 1, 1);
            insertarCursoProfesor(conn, 2, 2);
            insertarCursoProfesor(conn, 3, 1);
            insertarCursoProfesor(conn, 4, 3);
            insertarCursoProfesor(conn, 101, 201);
            insertarCursoProfesor(conn, 102, 202);
            insertarCursoProfesor(conn, 201, 203);
            
            System.out.println("✅ Base de datos poblada exitosamente!");
            System.out.println("📊 Datos creados:");
            System.out.println("   - 12 personas");
            System.out.println("   - 3 facultades");
            System.out.println("   - 6 programas académicos");
            System.out.println("   - 5 estudiantes");
            System.out.println("   - 7 profesores");
            System.out.println("   - 10 cursos (9 activos, 1 inactivo)");
            System.out.println("   - 7 inscripciones");
            System.out.println("   - 7 asignaciones curso-profesor");
            
        } catch (Exception e) {
            System.err.println("❌ Error poblando la base de datos: " + e.getMessage());
            e.printStackTrace();
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
    
    private static void insertarPrograma(Connection conn, int id, String nombre, int duracion, java.sql.Date registro, int facultadId) throws Exception {
        String sql = "MERGE INTO programas (id, nombre, duracion, registro, facultad_id) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.setString(2, nombre);
            stmt.setInt(3, duracion);
            stmt.setDate(4, registro);
            stmt.setInt(5, facultadId);
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
    
    private static void limpiarDatos(Connection conn) throws Exception {
        System.out.println("🧹 Limpiando datos existentes...");
        
        // Eliminar en orden inverso debido a las claves foráneas
        try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM curso_profesor")) {
            stmt.executeUpdate();
        }
        try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM inscripciones")) {
            stmt.executeUpdate();
        }
        try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM cursos")) {
            stmt.executeUpdate();
        }
        try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM profesores")) {
            stmt.executeUpdate();
        }
        try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM estudiantes")) {
            stmt.executeUpdate();
        }
        try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM programas")) {
            stmt.executeUpdate();
        }
        try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM facultades")) {
            stmt.executeUpdate();
        }
        try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM personas")) {
            stmt.executeUpdate();
        }
        
        System.out.println("✅ Datos existentes eliminados");
    }
}
