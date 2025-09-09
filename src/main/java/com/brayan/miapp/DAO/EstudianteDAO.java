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
        try (Connection conn = DatabaseConnection.getConnection()) {
            // Primero insertar en la tabla personas
            String sqlPersona = "MERGE INTO personas (id, nombres, apellidos, email) VALUES (?, ?, ?, ?)";
            try (PreparedStatement psPersona = conn.prepareStatement(sqlPersona)) {
                psPersona.setDouble(1, e.getId());
                psPersona.setString(2, e.getNombres());
                psPersona.setString(3, e.getApellidos());
                psPersona.setString(4, e.getEmail());
                psPersona.executeUpdate();
            }
            
            // Luego insertar en la tabla estudiantes
            String sqlEstudiante = "MERGE INTO estudiantes (id, codigo, programa_id, activo, promedio) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement psEstudiante = conn.prepareStatement(sqlEstudiante)) {
                psEstudiante.setDouble(1, e.getId());
                psEstudiante.setDouble(2, e.getCodigo());
                if (e.getPrograma() != null) {
                    psEstudiante.setDouble(3, e.getPrograma().getId());
                } else {
                    psEstudiante.setNull(3, Types.DOUBLE);
                }
                psEstudiante.setBoolean(4, e.getActivo());
                psEstudiante.setDouble(5, e.getPromedio());
                psEstudiante.executeUpdate();
            }
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
                    "FROM estudiantes e " +
                    "JOIN personas p ON e.id = p.id " +
                    "LEFT JOIN programas pr ON e.programa_id = pr.id " +
                    "LEFT JOIN facultades f ON pr.facultad_id = f.id " +
                    "LEFT JOIN personas dec ON f.decano_id = dec.id";
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
        try (Connection conn = DatabaseConnection.getConnection()) {
            // Primero eliminar de estudiantes (por la clave foránea)
            String sqlEstudiante = "DELETE FROM estudiantes WHERE id=?";
            try (PreparedStatement psEstudiante = conn.prepareStatement(sqlEstudiante)) {
                psEstudiante.setDouble(1, id);
                psEstudiante.executeUpdate();
            }
            
            // Luego eliminar de personas
            String sqlPersona = "DELETE FROM personas WHERE id=?";
            try (PreparedStatement psPersona = conn.prepareStatement(sqlPersona)) {
                psPersona.setDouble(1, id);
                psPersona.executeUpdate();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public void actualizar(Estudiante e) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            // Actualizar la tabla personas
            String sqlPersona = "UPDATE personas SET nombres=?, apellidos=?, email=? WHERE id=?";
            try (PreparedStatement psPersona = conn.prepareStatement(sqlPersona)) {
                psPersona.setString(1, e.getNombres());
                psPersona.setString(2, e.getApellidos());
                psPersona.setString(3, e.getEmail());
                psPersona.setDouble(4, e.getId());
                psPersona.executeUpdate();
            }
            
            // Actualizar la tabla estudiantes
            String sqlEstudiante = "UPDATE estudiantes SET codigo=?, programa_id=?, activo=?, promedio=? WHERE id=?";
            try (PreparedStatement psEstudiante = conn.prepareStatement(sqlEstudiante)) {
                psEstudiante.setDouble(1, e.getCodigo());
                if (e.getPrograma() != null) {
                    psEstudiante.setDouble(2, e.getPrograma().getId());
                } else {
                    psEstudiante.setNull(2, Types.DOUBLE);
                }
                psEstudiante.setBoolean(3, e.getActivo());
                psEstudiante.setDouble(4, e.getPromedio());
                psEstudiante.setDouble(5, e.getId());
                psEstudiante.executeUpdate();
            }
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
                    "FROM estudiantes e " +
                    "JOIN personas p ON e.id = p.id " +
                    "LEFT JOIN programas pr ON e.programa_id = pr.id " +
                    "LEFT JOIN facultades f ON pr.facultad_id = f.id " +
                    "LEFT JOIN personas dec ON f.decano_id = dec.id " +
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
