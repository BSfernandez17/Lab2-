package com.brayan.miapp.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.brayan.miapp.Model.Curso;
import com.brayan.miapp.Model.Programa;
import com.brayan.miapp.Model.Facultad;
import com.brayan.miapp.Model.Persona;

public class CursoDAO {

    public void insertar(Curso c) {
        String sql = "INSERT INTO cursos (id, nombre, programa_id, activo) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, c.getId());
            ps.setString(2, c.getNombre());
            if (c.getPrograma() != null) {
                ps.setDouble(3, c.getPrograma().getId());
            } else {
                ps.setNull(3, Types.DOUBLE);
            }
            ps.setBoolean(4, c.getActivo());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Curso> listar() {
        List<Curso> cursos = new ArrayList<>();
        String sql = "SELECT c.id, c.nombre, c.activo, c.programa_id, " +
                    "p.nombre as programa_nombre, p.duracion, p.registro, p.facultad_id, " +
                    "f.nombre as facultad_nombre, f.decano_id, " +
                    "per.nombres, per.apellidos, per.email " +
                    "FROM cursos c " +
                    "LEFT JOIN programas p ON c.programa_id = p.id " +
                    "LEFT JOIN facultades f ON p.facultad_id = f.id " +
                    "LEFT JOIN personas per ON f.decano_id = per.id";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Programa programa = null;
                if (rs.getObject("programa_id") != null) {
                    Facultad facultad = null;
                    if (rs.getObject("facultad_id") != null) {
                        Persona decano = null;
                        if (rs.getObject("decano_id") != null) {
                            decano = new Persona(
                                rs.getDouble("decano_id"),
                                rs.getString("nombres"),
                                rs.getString("apellidos"),
                                rs.getString("email")
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
                cursos.add(new Curso(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    programa,
                    rs.getBoolean("activo")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cursos;
    }

    public void eliminar(Integer id) {
        String sql = "DELETE FROM cursos WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Curso buscarPorId(Integer id) {
        String sql = "SELECT c.id, c.nombre, c.activo, c.programa_id, " +
                    "p.nombre as programa_nombre, p.duracion, p.registro, p.facultad_id, " +
                    "f.nombre as facultad_nombre, f.decano_id, " +
                    "per.nombres, per.apellidos, per.email " +
                    "FROM Curso c " +
                    "LEFT JOIN Programa p ON c.programa_id = p.id " +
                    "LEFT JOIN Facultad f ON p.facultad_id = f.id " +
                    "LEFT JOIN Persona per ON f.decano_id = per.id " +
                    "WHERE c.id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Programa programa = null;
                if (rs.getObject("programa_id") != null) {
                    Facultad facultad = null;
                    if (rs.getObject("facultad_id") != null) {
                        Persona decano = null;
                        if (rs.getObject("decano_id") != null) {
                            decano = new Persona(
                                rs.getDouble("decano_id"),
                                rs.getString("nombres"),
                                rs.getString("apellidos"),
                                rs.getString("email")
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
                return new Curso(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    programa,
                    rs.getBoolean("activo")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public void actualizar(Curso c) {
        String sql = "UPDATE cursos SET nombre = ?, programa_id = ?, activo = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, c.getNombre());
            if (c.getPrograma() != null) {
                ps.setDouble(2, c.getPrograma().getId());
            } else {
                ps.setNull(2, Types.DOUBLE);
            }
            ps.setBoolean(3, c.getActivo());
            ps.setInt(4, c.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
