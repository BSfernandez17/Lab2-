package com.brayan.miapp.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.brayan.miapp.Model.Profesor;

public class ProfesorDAO {

    public void insertar(Profesor p) {
        String sql = "INSERT INTO Profesor (id, tipo_contrato) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, p.getId());
            ps.setString(2, p.getTipoContrato());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Profesor> listar() {
        List<Profesor> profesores = new ArrayList<>();
        String sql = "SELECT p.id, p.nombres, p.apellidos, p.email, pr.tipo_contrato " +
                    "FROM Profesor pr JOIN Persona p ON pr.id = p.id";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                profesores.add(new Profesor(
                    rs.getDouble("id"),
                    rs.getString("nombres"),
                    rs.getString("apellidos"),
                    rs.getString("email"),
                    rs.getString("tipo_contrato")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return profesores;
    }

    public void eliminar(Double id) {
        String sql = "DELETE FROM Profesor WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Profesor buscarPorId(Double id) {
        String sql = "SELECT p.id, p.nombres, p.apellidos, p.email, pr.tipo_contrato " +
                    "FROM Profesor pr JOIN Persona p ON pr.id = p.id WHERE pr.id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Profesor(
                    rs.getDouble("id"),
                    rs.getString("nombres"),
                    rs.getString("apellidos"),
                    rs.getString("email"),
                    rs.getString("tipo_contrato")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
