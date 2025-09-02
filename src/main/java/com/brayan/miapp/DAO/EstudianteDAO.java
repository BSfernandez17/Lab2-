package com.brayan.miapp.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.brayan.miapp.Model.Estudiante;
import com.brayan.miapp.Model.Programa;
import com.brayan.miapp.Model.Facultad;
import com.brayan.miapp.Model.Persona;

public class EstudianteDAO {

    public void insertar(Estudiante e) {
        String sql = "INSERT INTO Estudiante (id, codigo, programa_id, activo, promedio) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, e.getId());
            ps.setDouble(2, e.getCodigo());
            if (e.getPrograma() != null) {
                ps.setDouble(3, e.getPrograma().getId());
            } else {
                ps.setNull(3, Types.DOUBLE);
            }
            ps.setBoolean(4, e.getActivo());
            ps.setDouble(5, e.getPromedio());
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public List<Estudiante> listar() {
        List<Estudiante> estudiantes = new ArrayList<>();
        String sql = "SELECT p.id, p.nombres, p.apellidos, p.email, " +
                    "e.codigo, e.activo, e.promedio, e.programa_id, " +
                    "pr.nombre as programa_nombre, pr.duracion, pr.registro, pr.facultad_id, " +
                    "f.nombre as facultad_nombre, f.decano_id, " +
                    "dec.nombres as decano_nombres, dec.apellidos as decano_apellidos, dec.email as decano_email " +
                    "FROM Estudiante e " +
                    "JOIN Persona p ON e.id = p.id " +
                    "LEFT JOIN Programa pr ON e.programa_id = pr.id " +
                    "LEFT JOIN Facultad f ON pr.facultad_id = f.id " +
                    "LEFT JOIN Persona dec ON f.decano_id = dec.id";
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
                estudiantes.add(new Estudiante(
                    rs.getDouble("id"),
                    rs.getString("nombres"),
                    rs.getString("apellidos"),
                    rs.getString("email"),
                    rs.getDouble("codigo"),
                    programa,
                    rs.getBoolean("activo"),
                    rs.getDouble("promedio")
                ));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return estudiantes;
    }

    public void eliminar(Double id) {
        String sql = "DELETE FROM Estudiante WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public Estudiante buscarPorId(Double id) {
        String sql = "SELECT p.id, p.nombres, p.apellidos, p.email, " +
                    "e.codigo, e.activo, e.promedio, e.programa_id, " +
                    "pr.nombre as programa_nombre, pr.duracion, pr.registro, pr.facultad_id, " +
                    "f.nombre as facultad_nombre, f.decano_id, " +
                    "dec.nombres as decano_nombres, dec.apellidos as decano_apellidos, dec.email as decano_email " +
                    "FROM Estudiante e " +
                    "JOIN Persona p ON e.id = p.id " +
                    "LEFT JOIN Programa pr ON e.programa_id = pr.id " +
                    "LEFT JOIN Facultad f ON pr.facultad_id = f.id " +
                    "LEFT JOIN Persona dec ON f.decano_id = dec.id " +
                    "WHERE e.id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, id);
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
                return new Estudiante(
                    rs.getDouble("id"),
                    rs.getString("nombres"),
                    rs.getString("apellidos"),
                    rs.getString("email"),
                    rs.getDouble("codigo"),
                    programa,
                    rs.getBoolean("activo"),
                    rs.getDouble("promedio")
                );
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
