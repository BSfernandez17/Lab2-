package com.brayan.miapp;

import com.brayan.miapp.DAO.DatabaseSetup;
import com.brayan.miapp.DAO.DataSeeder;
import com.brayan.miapp.DAO.PersonaDAO;
import com.brayan.miapp.Model.Persona;
import java.util.List;

public class TestPersonas {
    public static void main(String[] args) {
        System.out.println("=== Test de Personas ===");
        
        // Inicializar base de datos
        DatabaseSetup.init();
        DataSeeder.poblarBaseDatos();
        
        // Crear DAO y probar
        PersonaDAO personaDAO = new PersonaDAO();
        
        System.out.println("\n--- Listando personas en la base de datos ---");
        List<Persona> personas = personaDAO.listar();
        
        if (personas.isEmpty()) {
            System.out.println("❌ No se encontraron personas en la base de datos");
        } else {
            System.out.println("✅ Se encontraron " + personas.size() + " personas:");
            for (Persona p : personas) {
                System.out.println("  • " + p.toString());
            }
        }
        
        System.out.println("\n=== Fin del test ===");
    }
}
