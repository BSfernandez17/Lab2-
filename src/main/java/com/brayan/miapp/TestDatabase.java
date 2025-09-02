package com.brayan.miapp;

import com.brayan.miapp.DAO.DatabaseConnection;
import com.brayan.miapp.DAO.DatabaseSetup;
import com.brayan.miapp.DAO.DataSeeder;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class TestDatabase {
    public static void main(String[] args) {
        try {
            System.out.println("Configurando base de datos...");
            DatabaseSetup.init();
            
            System.out.println("Poblando datos...");
            DataSeeder.poblarBaseDatos();
            
            System.out.println("Verificando tabla inscripciones...");
            try (Connection conn = DatabaseConnection.getConnection();
                 Statement stmt = conn.createStatement()) {
                
                ResultSet rs = stmt.executeQuery("SELECT COUNT(*) as total FROM inscripciones");
                if (rs.next()) {
                    System.out.println("Total inscripciones: " + rs.getInt("total"));
                }
                
                rs = stmt.executeQuery("SELECT * FROM inscripciones LIMIT 5");
                while (rs.next()) {
                    System.out.println("Inscripción: " + 
                        "ID=" + rs.getInt("id") + 
                        ", Curso=" + rs.getInt("curso_id") + 
                        ", Estudiante=" + rs.getDouble("estudiante_id") + 
                        ", Año=" + rs.getInt("año") + 
                        ", Semestre=" + rs.getInt("semestre"));
                }
            }
            
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
