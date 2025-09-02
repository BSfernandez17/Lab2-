package com.brayan.miapp.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.brayan.miapp.Model.Facultad;
import com.brayan.miapp.Model.Persona;

public class FacultadDAO {

    public void insertar(Facultad f) {
        String sql = "INSERT INTO Facultad (id, nombre, decano_id) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, f.getId());
            ps.setString(2, f.getNombre());
            if (f.getDecano() != null) {
                ps.setDouble(3, f.getDecano().getId());
            } else {
                ps.setNull(3, Types.DOUBLE);
            }
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Facultad> listar() {
        List<Facultad> facultades = new ArrayList<>();
        String sql = "SELECT f.id, f.nombre, f.decano_id, p.nombres, p.apellidos, p.email " +
                    "FROM Facultad f LEFT JOIN Persona p ON f.decano_id = p.id";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Persona decano = null;
                if (rs.getObject("decano_id") != null) {
                    decano = new Persona(
                        rs.getDouble("decano_id"),
                        rs.getString("nombres"),
                        rs.getString("apellidos"),
                        rs.getString("email")
                    );
                }
                facultades.add(new Facultad(
                    rs.getDouble("id"),
                    rs.getString("nombre"),
                    decano
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return facultades;
    }

    public void eliminar(Double id) {
        String sql = "DELETE FROM Facultad WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Facultad buscarPorId(Double id) {
        String sql = "SELECT f.id, f.nombre, f.decano_id, p.nombres, p.apellidos, p.email " +
                    "FROM Facultad f LEFT JOIN Persona p ON f.decano_id = p.id WHERE f.id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Persona decano = null;
                if (rs.getObject("decano_id") != null) {
                    decano = new Persona(
                        rs.getDouble("decano_id"),
                        rs.getString("nombres"),
                        rs.getString("apellidos"),
                        rs.getString("email")
                    );
                }
                return new Facultad(
                    rs.getDouble("id"),
                    rs.getString("nombre"),
                    decano
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
