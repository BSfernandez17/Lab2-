package com.brayan.miapp;

import com.brayan.miapp.DAO.DatabaseSetup;
import com.brayan.miapp.Model.Persona;
import com.brayan.Services.InscripcionesPersonas;

/**
 * Aplicación de gestión universitaria
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println("=== Sistema de Gestión Universitaria ===");
        
        // Inicializar la base de datos
        System.out.println("Inicializando base de datos...");
        DatabaseSetup.init();
        
        // Crear el servicio de inscripciones de personas
        InscripcionesPersonas servicio = new InscripcionesPersonas();
        
        // Crear algunas personas de ejemplo
        Persona persona1 = new Persona(1.0, "Juan Carlos", "Pérez García", "juan.perez@universidad.edu");
        Persona persona2 = new Persona(2.0, "María Elena", "González López", "maria.gonzalez@universidad.edu");
        Persona persona3 = new Persona(3.0, "Carlos Alberto", "Rodríguez Martín", "carlos.rodriguez@universidad.edu");
        
        System.out.println("\n=== Inscribiendo personas ===");
        servicio.inscribir(persona1);
        servicio.inscribir(persona2);
        servicio.inscribir(persona3);
        
        System.out.println("Personas inscritas: " + servicio.cantidadActual());
        
        System.out.println("\n=== Listado de personas ===");
        for (String persona : servicio.imprimirListado()) {
            System.out.println(persona);
        }
        
        // Actualizar una persona
        System.out.println("\n=== Actualizando persona ===");
        persona1.setEmail("juan.perez.nuevo@universidad.edu");
        servicio.actualizar(persona1);
        
        System.out.println("Persona actualizada:");
        System.out.println(servicio.imprimirPosicion(0));
        
        // Cargar datos desde la base de datos
        System.out.println("\n=== Cargando datos desde BD ===");
        servicio.cargarDatos();
        System.out.println("Datos cargados. Total: " + servicio.cantidadActual());
        
        for (String persona : servicio.imprimirListado()) {
            System.out.println(persona);
        }
        
        System.out.println("\n=== Programa finalizado ===");
    }
}
