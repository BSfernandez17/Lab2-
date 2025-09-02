package com.brayan.miapp.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.brayan.miapp.Model.CursoProfesor;
import com.brayan.miapp.Model.Profesor;
import com.brayan.miapp.Model.Curso;
import com.brayan.miapp.Model.Programa;
import com.brayan.miapp.Model.Facultad;
import com.brayan.miapp.Model.Persona;

public class CursoProfesorDAO {

    public void insertar(CursoProfesor cp) {
        String sql = "INSERT INTO CursoProfesor (profesor_id, curso_id, año, semestre) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, cp.getProfesor().getId());
            ps.setInt(2, cp.getCurso().getId());
            ps.setInt(3, cp.getAño());
            ps.setInt(4, cp.getSemestre());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<CursoProfesor> listar() {
        List<CursoProfesor> cursoProfesores = new ArrayList<>();
        String sql = "SELECT cp.profesor_id, cp.curso_id, cp.año, cp.semestre, " +
                    "p.nombres, p.apellidos, p.email, pr.tipo_contrato, " +
                    "c.nombre as curso_nombre, c.activo, c.programa_id, " +
                    "prog.nombre as programa_nombre, prog.duracion, prog.registro, prog.facultad_id, " +
                    "f.nombre as facultad_nombre, f.decano_id, " +
                    "dec.nombres as decano_nombres, dec.apellidos as decano_apellidos, dec.email as decano_email " +
                    "FROM CursoProfesor cp " +
                    "JOIN Profesor pr ON cp.profesor_id = pr.id " +
                    "JOIN Persona p ON pr.id = p.id " +
                    "JOIN Curso c ON cp.curso_id = c.id " +
                    "LEFT JOIN Programa prog ON c.programa_id = prog.id " +
                    "LEFT JOIN Facultad f ON prog.facultad_id = f.id " +
                    "LEFT JOIN Persona dec ON f.decano_id = dec.id";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Profesor profesor = new Profesor(
                    rs.getDouble("profesor_id"),
                    rs.getString("nombres"),
                    rs.getString("apellidos"),
                    rs.getString("email"),
                    rs.getString("tipo_contrato")
                );

                Programa programa = null;
                if (rs.getObject("programa_id") != null) {
                    Facultad facultad = null;
                    if (rs.getObject("facultad_id") != null) {
                        Persona decano = null;
                        if (rs.getObject("decano_id") != null) {
                            decano = new Persona(
                                rs.getDouble("decano_id"),
                                rs.getString("decano_nombres"),
                                rs.getString("decano_apellidos"),
                                rs.getString("decano_email")
                            );
                        }
                        facultad = new Facultad(
                            rs.getDouble("facultad_id"),
                            rs.getString("facultad_nombre"),
                            decano
                        );
                    }
                    programa = new Programa(
                        rs.getDouble("programa_id"),
                        rs.getString("programa_nombre"),
                        rs.getDouble("duracion"),
                        rs.getDate("registro"),
                        facultad
                    );
                }

                Curso curso = new Curso(
                    rs.getInt("curso_id"),
                    rs.getString("curso_nombre"),
                    programa,
                    rs.getBoolean("activo")
                );

                cursoProfesores.add(new CursoProfesor(
                    profesor,
                    rs.getInt("año"),
                    rs.getInt("semestre"),
                    curso
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cursoProfesores;
    }

    public void eliminar(Double profesorId, Integer cursoId, Integer año, Integer semestre) {
        String sql = "DELETE FROM CursoProfesor WHERE profesor_id=? AND curso_id=? AND año=? AND semestre=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, profesorId);
            ps.setInt(2, cursoId);
            ps.setInt(3, año);
            ps.setInt(4, semestre);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<CursoProfesor> buscarPorProfesor(Double profesorId) {
        List<CursoProfesor> cursoProfesores = new ArrayList<>();
        String sql = "SELECT cp.profesor_id, cp.curso_id, cp.año, cp.semestre, " +
                    "p.nombres, p.apellidos, p.email, pr.tipo_contrato, " +
                    "c.nombre as curso_nombre, c.activo, c.programa_id, " +
                    "prog.nombre as programa_nombre, prog.duracion, prog.registro, prog.facultad_id, " +
                    "f.nombre as facultad_nombre, f.decano_id, " +
                    "dec.nombres as decano_nombres, dec.apellidos as decano_apellidos, dec.email as decano_email " +
                    "FROM CursoProfesor cp " +
                    "JOIN Profesor pr ON cp.profesor_id = pr.id " +
                    "JOIN Persona p ON pr.id = p.id " +
                    "JOIN Curso c ON cp.curso_id = c.id " +
                    "LEFT JOIN Programa prog ON c.programa_id = prog.id " +
                    "LEFT JOIN Facultad f ON prog.facultad_id = f.id " +
                    "LEFT JOIN Persona dec ON f.decano_id = dec.id " +
                    "WHERE cp.profesor_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, profesorId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Profesor profesor = new Profesor(
                    rs.getDouble("profesor_id"),
                    rs.getString("nombres"),
                    rs.getString("apellidos"),
                    rs.getString("email"),
                    rs.getString("tipo_contrato")
                );

                Programa programa = null;
                if (rs.getObject("programa_id") != null) {
                    Facultad facultad = null;
                    if (rs.getObject("facultad_id") != null) {
                        Persona decano = null;
                        if (rs.getObject("decano_id") != null) {
                            decano = new Persona(
                                rs.getDouble("decano_id"),
                                rs.getString("decano_nombres"),
                                rs.getString("decano_apellidos"),
                                rs.getString("decano_email")
                            );
                        }
                        facultad = new Facultad(
                            rs.getDouble("facultad_id"),
                            rs.getString("facultad_nombre"),
                            decano
                        );
                    }
                    programa = new Programa(
                        rs.getDouble("programa_id"),
                        rs.getString("programa_nombre"),
                        rs.getDouble("duracion"),
                        rs.getDate("registro"),
                        facultad
                    );
                }

                Curso curso = new Curso(
                    rs.getInt("curso_id"),
                    rs.getString("curso_nombre"),
                    programa,
                    rs.getBoolean("activo")
                );

                cursoProfesores.add(new CursoProfesor(
                    profesor,
                    rs.getInt("año"),
                    rs.getInt("semestre"),
                    curso
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cursoProfesores;
    }
}
