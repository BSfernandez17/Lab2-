package com.brayan.miapp.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.brayan.miapp.Model.Inscripcion;
import com.brayan.miapp.Model.Curso;
import com.brayan.miapp.Model.Estudiante;
import com.brayan.miapp.Model.Programa;

public class InscripcionDAO {

    public void insertar(Inscripcion i) {
        String sql = "INSERT INTO Inscripcion (curso_id, estudiante_id, año, semestre) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, i.getCurso().getId());
            ps.setDouble(2, i.getEstudiante().getId());
            ps.setInt(3, i.getAño());
            ps.setInt(4, i.getSemestre());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Inscripcion> listar() {
        List<Inscripcion> inscripciones = new ArrayList<>();
        String sql = "SELECT i.curso_id, i.estudiante_id, i.año, i.semestre, " +
                    "c.nombre as curso_nombre, c.activo as curso_activo, c.programa_id as curso_programa_id, " +
                    "p.nombres, p.apellidos, p.email, " +
                    "e.codigo, e.activo as estudiante_activo, e.promedio, e.programa_id as estudiante_programa_id, " +
                    "prog_c.nombre as curso_programa_nombre, prog_c.duracion as curso_programa_duracion, prog_c.registro as curso_programa_registro, prog_c.facultad_id as curso_programa_facultad_id, " +
                    "prog_e.nombre as estudiante_programa_nombre, prog_e.duracion as estudiante_programa_duracion, prog_e.registro as estudiante_programa_registro, prog_e.facultad_id as estudiante_programa_facultad_id " +
                    "FROM Inscripcion i " +
                    "JOIN Curso c ON i.curso_id = c.id " +
                    "JOIN Estudiante e ON i.estudiante_id = e.id " +
                    "JOIN Persona p ON e.id = p.id " +
                    "LEFT JOIN Programa prog_c ON c.programa_id = prog_c.id " +
                    "LEFT JOIN Programa prog_e ON e.programa_id = prog_e.id";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                // Crear programa del curso
                Programa cursoPrograma = null;
                if (rs.getObject("curso_programa_id") != null) {
                    cursoPrograma = new Programa(
                        rs.getDouble("curso_programa_id"),
                        rs.getString("curso_programa_nombre"),
                        rs.getDouble("curso_programa_duracion"),
                        rs.getDate("curso_programa_registro"),
                        null // Simplificamos sin facultad para evitar demasiada complejidad
                    );
                }

                // Crear curso
                Curso curso = new Curso(
                    rs.getInt("curso_id"),
                    rs.getString("curso_nombre"),
                    cursoPrograma,
                    rs.getBoolean("curso_activo")
                );

                // Crear programa del estudiante
                Programa estudiantePrograma = null;
                if (rs.getObject("estudiante_programa_id") != null) {
                    estudiantePrograma = new Programa(
                        rs.getDouble("estudiante_programa_id"),
                        rs.getString("estudiante_programa_nombre"),
                        rs.getDouble("estudiante_programa_duracion"),
                        rs.getDate("estudiante_programa_registro"),
                        null // Simplificamos sin facultad
                    );
                }

                // Crear estudiante
                Estudiante estudiante = new Estudiante(
                    rs.getDouble("estudiante_id"),
                    rs.getString("nombres"),
                    rs.getString("apellidos"),
                    rs.getString("email"),
                    rs.getDouble("codigo"),
                    estudiantePrograma,
                    rs.getBoolean("estudiante_activo"),
                    rs.getDouble("promedio")
                );

                inscripciones.add(new Inscripcion(
                    curso,
                    rs.getInt("año"),
                    rs.getInt("semestre"),
                    estudiante
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return inscripciones;
    }

    public void eliminar(Integer cursoId, Double estudianteId, Integer año, Integer semestre) {
        String sql = "DELETE FROM Inscripcion WHERE curso_id=? AND estudiante_id=? AND año=? AND semestre=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, cursoId);
            ps.setDouble(2, estudianteId);
            ps.setInt(3, año);
            ps.setInt(4, semestre);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actualizar(Inscripcion oldInscripcion, Inscripcion newInscripcion) {
        // Para actualizar una inscripción, primero eliminamos la antigua y luego insertamos la nueva
        // ya que los campos año y semestre pueden cambiar y son parte de la clave primaria
        eliminar(oldInscripcion.getCurso().getId(), oldInscripcion.getEstudiante().getId(), 
                oldInscripcion.getAño(), oldInscripcion.getSemestre());
        insertar(newInscripcion);
    }

    public List<Inscripcion> buscarPorEstudiante(Double estudianteId) {
        List<Inscripcion> inscripciones = new ArrayList<>();
        String sql = "SELECT i.curso_id, i.estudiante_id, i.año, i.semestre, " +
                    "c.nombre as curso_nombre, c.activo as curso_activo, c.programa_id as curso_programa_id, " +
                    "p.nombres, p.apellidos, p.email, " +
                    "e.codigo, e.activo as estudiante_activo, e.promedio, e.programa_id as estudiante_programa_id " +
                    "FROM Inscripcion i " +
                    "JOIN Curso c ON i.curso_id = c.id " +
                    "JOIN Estudiante e ON i.estudiante_id = e.id " +
                    "JOIN Persona p ON e.id = p.id " +
                    "WHERE i.estudiante_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, estudianteId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Curso curso = new Curso(
                    rs.getInt("curso_id"),
                    rs.getString("curso_nombre"),
                    null, // Simplificamos sin programa
                    rs.getBoolean("curso_activo")
                );

                Estudiante estudiante = new Estudiante(
                    rs.getDouble("estudiante_id"),
                    rs.getString("nombres"),
                    rs.getString("apellidos"),
                    rs.getString("email"),
                    rs.getDouble("codigo"),
                    null, // Simplificamos sin programa
                    rs.getBoolean("estudiante_activo"),
                    rs.getDouble("promedio")
                );

                inscripciones.add(new Inscripcion(
                    curso,
                    rs.getInt("año"),
                    rs.getInt("semestre"),
                    estudiante
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return inscripciones;
    }

    public List<Inscripcion> buscarPorCurso(Integer cursoId) {
        List<Inscripcion> inscripciones = new ArrayList<>();
        String sql = "SELECT i.curso_id, i.estudiante_id, i.año, i.semestre, " +
                    "c.nombre as curso_nombre, c.activo as curso_activo, " +
                    "p.nombres, p.apellidos, p.email, " +
                    "e.codigo, e.activo as estudiante_activo, e.promedio " +
                    "FROM Inscripcion i " +
                    "JOIN Curso c ON i.curso_id = c.id " +
                    "JOIN Estudiante e ON i.estudiante_id = e.id " +
                    "JOIN Persona p ON e.id = p.id " +
                    "WHERE i.curso_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, cursoId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Curso curso = new Curso(
                    rs.getInt("curso_id"),
                    rs.getString("curso_nombre"),
                    null, // Simplificamos sin programa
                    rs.getBoolean("curso_activo")
                );

                Estudiante estudiante = new Estudiante(
                    rs.getDouble("estudiante_id"),
                    rs.getString("nombres"),
                    rs.getString("apellidos"),
                    rs.getString("email"),
                    rs.getDouble("codigo"),
                    null, // Simplificamos sin programa
                    rs.getBoolean("estudiante_activo"),
                    rs.getDouble("promedio")
                );

                inscripciones.add(new Inscripcion(
                    curso,
                    rs.getInt("año"),
                    rs.getInt("semestre"),
                    estudiante
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return inscripciones;
    }
}
