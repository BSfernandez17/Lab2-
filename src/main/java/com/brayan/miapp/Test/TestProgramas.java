package com.brayan.miapp.Test;

import com.brayan.miapp.DAO.DatabaseSetup;
import com.brayan.miapp.DAO.ProgramaDAO;
import com.brayan.miapp.Model.Programa;
import java.util.List;

public class TestProgramas {
    public static void main(String[] args) {
        // Inicializar base de datos
        DatabaseSetup.init();
        
        ProgramaDAO programaDAO = new ProgramaDAO();
        
        try {
            // Listar todos los programas
            List<Programa> programas = programaDAO.listar();
            
            System.out.println("📋 PROGRAMAS DISPONIBLES EN LA BASE DE DATOS:");
            System.out.println("=================================================");
            
            if (programas.isEmpty()) {
                System.out.println("❌ No hay programas registrados en la base de datos");
            } else {
                System.out.println("Total de programas: " + programas.size());
                System.out.println();
                
                for (Programa p : programas) {
                    System.out.println("🎓 ID: " + p.getId());
                    System.out.println("   Nombre: " + p.getNombre());
                    System.out.println("   Duración: " + p.getDuracion() + " semestres");
                    System.out.println("   Registro: " + p.getRegistro());
                    if (p.getFacultad() != null) {
                        System.out.println("   Facultad: " + p.getFacultad().getNombre());
                    } else {
                        System.out.println("   Facultad: No asignada");
                    }
                    System.out.println("   ---");
                }
            }
            
        } catch (Exception e) {
            System.out.println("❌ Error al consultar programas: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
