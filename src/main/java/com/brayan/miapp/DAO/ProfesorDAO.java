package com.brayan.miapp.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.brayan.miapp.Model.Profesor;

public class ProfesorDAO {

    public void insertar(Profesor p) {
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);
            
            // Insertar en tabla personas
            String sqlPersona = "MERGE INTO personas (id, nombres, apellidos, email) VALUES (?, ?, ?, ?)";
            try (PreparedStatement psPersona = conn.prepareStatement(sqlPersona)) {
                psPersona.setDouble(1, p.getId());
                psPersona.setString(2, p.getNombres());
                psPersona.setString(3, p.getApellidos());
                psPersona.setString(4, p.getEmail());
                psPersona.executeUpdate();
            }
            
            // Insertar en tabla profesores
            String sqlProfesor = "INSERT INTO profesores (id, persona_id, nivel, contrato, activo) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement psProfesor = conn.prepareStatement(sqlProfesor)) {
                psProfesor.setDouble(1, p.getId());
                psProfesor.setDouble(2, p.getId()); // persona_id es el mismo que id
                psProfesor.setString(3, "Docente"); // nivel por defecto
                psProfesor.setString(4, p.getTipoContrato()); // contrato
                psProfesor.setBoolean(5, true); // activo por defecto
                psProfesor.executeUpdate();
            }
            
            conn.commit();
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public List<Profesor> listar() {
        List<Profesor> profesores = new ArrayList<>();
        String sql = "SELECT p.id, p.nombres, p.apellidos, p.email, pr.contrato " +
                    "FROM profesores pr JOIN personas p ON pr.persona_id = p.id WHERE pr.activo = true";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                profesores.add(new Profesor(
                    rs.getDouble("id"),
                    rs.getString("nombres"),
                    rs.getString("apellidos"),
                    rs.getString("email"),
                    rs.getString("contrato")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return profesores;
    }

    public void eliminar(Double id) {
        String sql = "DELETE FROM profesores WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Profesor buscarPorId(Double id) {
        String sql = "SELECT p.id, p.nombres, p.apellidos, p.email, pr.contrato " +
                    "FROM profesores pr JOIN personas p ON pr.persona_id = p.id WHERE pr.id = ?";
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
                    rs.getString("contrato")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public void actualizar(Profesor p) {
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);
            
            // Actualizar en tabla personas
            String sqlPersona = "UPDATE personas SET nombres = ?, apellidos = ?, email = ? WHERE id = ?";
            try (PreparedStatement psPersona = conn.prepareStatement(sqlPersona)) {
                psPersona.setString(1, p.getNombres());
                psPersona.setString(2, p.getApellidos());
                psPersona.setString(3, p.getEmail());
                psPersona.setDouble(4, p.getId());
                psPersona.executeUpdate();
            }
            
            // Actualizar en tabla profesores
            String sqlProfesor = "UPDATE profesores SET contrato = ? WHERE id = ?";
            try (PreparedStatement psProfesor = conn.prepareStatement(sqlProfesor)) {
                psProfesor.setString(1, p.getTipoContrato());
                psProfesor.setDouble(2, p.getId());
                psProfesor.executeUpdate();
            }
            
            conn.commit();
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
