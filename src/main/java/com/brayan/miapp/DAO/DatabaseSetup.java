package com.brayan.miapp.DAO;

import java.sql.Connection;
import java.sql.Statement;

public class DatabaseSetup {
    public static void init() {
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute(
                "CREATE TABLE IF NOT EXISTS Persona (" +
                "id DOUBLE PRIMARY KEY," +
                "nombres VARCHAR(255)," +
                "apellidos VARCHAR(255)," +
                "email VARCHAR(255)" +
                ")"
            );

            // Tabla Facultad
            stmt.execute(
                "CREATE TABLE IF NOT EXISTS Facultad (" +
                "id DOUBLE PRIMARY KEY," +
                "nombre VARCHAR(255)," +
                "decano_id DOUBLE," +
                "FOREIGN KEY (decano_id) REFERENCES Persona(id)" +
                ")"
            );

            // Tabla Programa
            stmt.execute(
                "CREATE TABLE IF NOT EXISTS Programa (" +
                "id DOUBLE PRIMARY KEY," +
                "nombre VARCHAR(255)," +
                "duracion DOUBLE," +
                "registro DATE," +
                "facultad_id DOUBLE," +
                "FOREIGN KEY (facultad_id) REFERENCES Facultad(id)" +
                ")"
            );

            // Tabla Profesor
            stmt.execute(
                "CREATE TABLE IF NOT EXISTS Profesor (" +
                "id DOUBLE PRIMARY KEY," +
                "tipo_contrato VARCHAR(255)," +
                "FOREIGN KEY (id) REFERENCES Persona(id)" +
                ")"
            );

            // Tabla Estudiante (actualizada con referencia a Programa)
            stmt.execute(
                "CREATE TABLE IF NOT EXISTS Estudiante (" +
                "id DOUBLE PRIMARY KEY," +
                "codigo DOUBLE," +
                "programa_id DOUBLE," +
                "activo BOOLEAN," +
                "promedio DOUBLE," +
                "FOREIGN KEY (id) REFERENCES Persona(id)," +
                "FOREIGN KEY (programa_id) REFERENCES Programa(id)" +
                ")"
            );

            // Tabla Curso
            stmt.execute(
                "CREATE TABLE IF NOT EXISTS Curso (" +
                "id INTEGER PRIMARY KEY," +
                "nombre VARCHAR(255)," +
                "programa_id DOUBLE," +
                "activo BOOLEAN," +
                "FOREIGN KEY (programa_id) REFERENCES Programa(id)" +
                ")"
            );

            // Tabla CursoProfesor (relación many-to-many)
            stmt.execute(
                "CREATE TABLE IF NOT EXISTS CursoProfesor (" +
                "profesor_id DOUBLE," +
                "curso_id INTEGER," +
                "año INTEGER," +
                "semestre INTEGER," +
                "PRIMARY KEY (profesor_id, curso_id, año, semestre)," +
                "FOREIGN KEY (profesor_id) REFERENCES Profesor(id)," +
                "FOREIGN KEY (curso_id) REFERENCES Curso(id)" +
                ")"
            );

            // Tabla Inscripcion (relación many-to-many)
            stmt.execute(
                "CREATE TABLE IF NOT EXISTS Inscripcion (" +
                "curso_id INTEGER," +
                "estudiante_id DOUBLE," +
                "año INTEGER," +
                "semestre INTEGER," +
                "PRIMARY KEY (curso_id, estudiante_id, año, semestre)," +
                "FOREIGN KEY (curso_id) REFERENCES Curso(id)," +
                "FOREIGN KEY (estudiante_id) REFERENCES Estudiante(id)" +
                ")"
            );

            System.out.println("Todas las tablas han sido creadas correctamente.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
