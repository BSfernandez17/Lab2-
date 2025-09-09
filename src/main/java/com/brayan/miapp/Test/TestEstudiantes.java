package com.brayan.miapp.Test;

import com.brayan.miapp.DAO.DatabaseSetup;
import com.brayan.miapp.DAO.EstudianteDAO;
import com.brayan.miapp.DAO.PersonaDAO;
import com.brayan.miapp.Model.Estudiante;
import com.brayan.miapp.Model.Persona;
import com.brayan.miapp.Model.Programa;

public class TestEstudiantes {
    public static void main(String[] args) {
        // Inicializar base de datos
        DatabaseSetup.init();
        
        PersonaDAO personaDAO = new PersonaDAO();
        EstudianteDAO estudianteDAO = new EstudianteDAO();
        
        try {
            // Crear algunas personas primero
            Persona persona1 = new Persona(1.0, "Juan Carlos", "Pérez García", "juan@email.com");
            Persona persona2 = new Persona(2.0, "María Elena", "López Rodríguez", "maria@email.com");
            Persona persona3 = new Persona(3.0, "Carlos Alberto", "Martínez Sánchez", "carlos@email.com");
            
            personaDAO.insertar(persona1);
            personaDAO.insertar(persona2);
            personaDAO.insertar(persona3);
            
            // Crear estudiantes
            Estudiante est1 = new Estudiante(1.0, "Juan Carlos", "Pérez García", "juan@email.com", 
                                           20231001.0, null, true, 4.2);
            Estudiante est2 = new Estudiante(2.0, "María Elena", "López Rodríguez", "maria@email.com", 
                                           20231002.0, null, true, 4.5);
            Estudiante est3 = new Estudiante(3.0, "Carlos Alberto", "Martínez Sánchez", "carlos@email.com", 
                                           20231003.0, null, true, 3.8);
            
            estudianteDAO.insertar(est1);
            estudianteDAO.insertar(est2);
            estudianteDAO.insertar(est3);
            
            System.out.println("✅ Datos de prueba insertados correctamente");
            
            // Listar estudiantes para verificar
            var estudiantes = estudianteDAO.listar();
            System.out.println("📋 Estudiantes en base de datos: " + estudiantes.size());
            for (Estudiante e : estudiantes) {
                System.out.println("- " + e.toString());
            }
            
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
