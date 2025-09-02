package com.brayan.miapp.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.brayan.miapp.Model.Programa;
import com.brayan.miapp.Model.Facultad;
import com.brayan.miapp.Model.Persona;

public class ProgramaDAO {

    public void insertar(Programa p) {
        String sql = "INSERT INTO Programa (id, nombre, duracion, registro, facultad_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, p.getId());
            ps.setString(2, p.getNombre());
            ps.setDouble(3, p.getDuracion());
            ps.setDate(4, new java.sql.Date(p.getRegistro().getTime()));
            if (p.getFacultad() != null) {
                ps.setDouble(5, p.getFacultad().getId());
            } else {
                ps.setNull(5, Types.DOUBLE);
            }
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Programa> listar() {
        List<Programa> programas = new ArrayList<>();
        String sql = "SELECT p.id, p.nombre, p.duracion, p.registro, p.facultad_id, " +
                    "f.nombre as facultad_nombre, f.decano_id, " +
                    "per.nombres, per.apellidos, per.email " +
                    "FROM Programa p " +
                    "LEFT JOIN Facultad f ON p.facultad_id = f.id " +
                    "LEFT JOIN Persona per ON f.decano_id = per.id";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
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
                programas.add(new Programa(
                    rs.getDouble("id"),
                    rs.getString("nombre"),
                    rs.getDouble("duracion"),
                    rs.getDate("registro"),
                    facultad
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return programas;
    }

    public void eliminar(Double id) {
        String sql = "DELETE FROM Programa WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Programa buscarPorId(Double id) {
        String sql = "SELECT p.id, p.nombre, p.duracion, p.registro, p.facultad_id, " +
                    "f.nombre as facultad_nombre, f.decano_id, " +
                    "per.nombres, per.apellidos, per.email " +
                    "FROM Programa p " +
                    "LEFT JOIN Facultad f ON p.facultad_id = f.id " +
                    "LEFT JOIN Persona per ON f.decano_id = per.id " +
                    "WHERE p.id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
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
                return new Programa(
                    rs.getDouble("id"),
                    rs.getString("nombre"),
                    rs.getDouble("duracion"),
                    rs.getDate("registro"),
                    facultad
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
