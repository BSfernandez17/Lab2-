package com.brayan.miapp;

import com.brayan.miapp.DAO.DatabaseSetup;
import com.brayan.miapp.Model.*;
import com.brayan.miapp.DAO.*;

public class DataSeeder {
    
    public static void main(String[] args) {
        System.out.println("=== Inicializando base de datos con datos de ejemplo ===");
        
        // Inicializar base de datos
        DatabaseSetup.init();
        
        // Crear DAOs
        PersonaDAO personaDAO = new PersonaDAO();
        FacultadDAO facultadDAO = new FacultadDAO();
        ProgramaDAO programaDAO = new ProgramaDAO();
        EstudianteDAO estudianteDAO = new EstudianteDAO();
        ProfesorDAO profesorDAO = new ProfesorDAO();
        CursoDAO cursoDAO = new CursoDAO();
        InscripcionDAO inscripcionDAO = new InscripcionDAO();
        CursoProfesorDAO cursoProfesorDAO = new CursoProfesorDAO();
        
        try {
            // 1. Crear personas (si no existen)
            System.out.println("Creando personas...");
            Persona p1 = new Persona(1.0, "Juan Carlos", "Pérez García", "juan.perez@universidad.edu");
            Persona p2 = new Persona(2.0, "María Elena", "González López", "maria.gonzalez@universidad.edu");
            Persona p3 = new Persona(3.0, "Carlos Alberto", "Rodríguez Martín", "carlos.rodriguez@universidad.edu");
            Persona p4 = new Persona(4.0, "Ana Sofía", "Martínez Cruz", "ana.martinez@universidad.edu");
            Persona p5 = new Persona(5.0, "Luis Fernando", "Sánchez Ruiz", "luis.sanchez@universidad.edu");
            
            try { personaDAO.insertar(p1); } catch (Exception e) { /* Ya existe */ }
            try { personaDAO.insertar(p2); } catch (Exception e) { /* Ya existe */ }
            try { personaDAO.insertar(p3); } catch (Exception e) { /* Ya existe */ }
            try { personaDAO.insertar(p4); } catch (Exception e) { /* Ya existe */ }
            try { personaDAO.insertar(p5); } catch (Exception e) { /* Ya existe */ }
            
            // 2. Crear facultades
            System.out.println("Creando facultades...");
            Facultad f1 = new Facultad(1.0, "Facultad de Ingeniería", p1);
            Facultad f2 = new Facultad(2.0, "Facultad de Ciencias", p2);
            
            try { facultadDAO.insertar(f1); } catch (Exception e) { /* Ya existe */ }
            try { facultadDAO.insertar(f2); } catch (Exception e) { /* Ya existe */ }
            
            // 3. Crear programas
            System.out.println("Creando programas...");
            Programa prog1 = new Programa(1.0, "Ingeniería de Sistemas", 10.0, new java.util.Date(), f1);
            Programa prog2 = new Programa(2.0, "Ingeniería Industrial", 10.0, new java.util.Date(), f1);
            Programa prog3 = new Programa(3.0, "Matemáticas", 8.0, new java.util.Date(), f2);
            
            try { programaDAO.insertar(prog1); } catch (Exception e) { /* Ya existe */ }
            try { programaDAO.insertar(prog2); } catch (Exception e) { /* Ya existe */ }
            try { programaDAO.insertar(prog3); } catch (Exception e) { /* Ya existe */ }
            
            // 4. Crear estudiantes
            System.out.println("Creando estudiantes...");
            Estudiante est1 = new Estudiante(3.0, "Carlos Alberto", "Rodríguez Martín", "carlos.rodriguez@universidad.edu", 
                                           20201001.0, prog1, true, 4.2);
            Estudiante est2 = new Estudiante(4.0, "Ana Sofía", "Martínez Cruz", "ana.martinez@universidad.edu", 
                                           20201002.0, prog2, true, 4.5);
            
            try { estudianteDAO.insertar(est1); } catch (Exception e) { /* Ya existe */ }
            try { estudianteDAO.insertar(est2); } catch (Exception e) { /* Ya existe */ }
            
            // 5. Crear profesores
            System.out.println("Creando profesores...");
            Profesor prof1 = new Profesor(1.0, "Juan Carlos", "Pérez García", "juan.perez@universidad.edu", "Catedrático");
            Profesor prof2 = new Profesor(5.0, "Luis Fernando", "Sánchez Ruiz", "luis.sanchez@universidad.edu", "Asociado");
            
            try { profesorDAO.insertar(prof1); } catch (Exception e) { /* Ya existe */ }
            try { profesorDAO.insertar(prof2); } catch (Exception e) { /* Ya existe */ }
            
            // 6. Crear cursos
            System.out.println("Creando cursos...");
            Curso curso1 = new Curso(1, "Programación I", prog1, true);
            Curso curso2 = new Curso(2, "Base de Datos", prog1, true);
            Curso curso3 = new Curso(3, "Cálculo I", prog3, true);
            Curso curso4 = new Curso(4, "Estadística", prog2, true);
            
            try { cursoDAO.insertar(curso1); } catch (Exception e) { /* Ya existe */ }
            try { cursoDAO.insertar(curso2); } catch (Exception e) { /* Ya existe */ }
            try { cursoDAO.insertar(curso3); } catch (Exception e) { /* Ya existe */ }
            try { cursoDAO.insertar(curso4); } catch (Exception e) { /* Ya existe */ }
            
            // 7. Crear inscripciones
            System.out.println("Creando inscripciones...");
            Inscripcion ins1 = new Inscripcion(curso1, 2024, 1, est1);
            Inscripcion ins2 = new Inscripcion(curso2, 2024, 1, est1);
            Inscripcion ins3 = new Inscripcion(curso1, 2024, 1, est2);
            Inscripcion ins4 = new Inscripcion(curso3, 2024, 2, est2);
            
            try { inscripcionDAO.insertar(ins1); } catch (Exception e) { /* Ya existe */ }
            try { inscripcionDAO.insertar(ins2); } catch (Exception e) { /* Ya existe */ }
            try { inscripcionDAO.insertar(ins3); } catch (Exception e) { /* Ya existe */ }
            try { inscripcionDAO.insertar(ins4); } catch (Exception e) { /* Ya existe */ }
            
            // 8. Crear asignaciones curso-profesor
            System.out.println("Creando asignaciones curso-profesor...");
            CursoProfesor cp1 = new CursoProfesor(prof1, 2024, 1, curso1);
            CursoProfesor cp2 = new CursoProfesor(prof1, 2024, 1, curso2);
            CursoProfesor cp3 = new CursoProfesor(prof2, 2024, 2, curso3);
            CursoProfesor cp4 = new CursoProfesor(prof2, 2024, 1, curso4);
            
            try { cursoProfesorDAO.insertar(cp1); } catch (Exception e) { /* Ya existe */ }
            try { cursoProfesorDAO.insertar(cp2); } catch (Exception e) { /* Ya existe */ }
            try { cursoProfesorDAO.insertar(cp3); } catch (Exception e) { /* Ya existe */ }
            try { cursoProfesorDAO.insertar(cp4); } catch (Exception e) { /* Ya existe */ }
            
            System.out.println("=== Datos de ejemplo creados exitosamente ===");
            
            // Verificar datos creados
            System.out.println("\n=== Verificación de datos ===");
            System.out.println("Personas: " + personaDAO.listar().size());
            System.out.println("Facultades: " + facultadDAO.listar().size());
            System.out.println("Programas: " + programaDAO.listar().size());
            System.out.println("Estudiantes: " + estudianteDAO.listar().size());
            System.out.println("Profesores: " + profesorDAO.listar().size());
            System.out.println("Cursos: " + cursoDAO.listar().size());
            System.out.println("Inscripciones: " + inscripcionDAO.listar().size());
            System.out.println("Asignaciones Curso-Profesor: " + cursoProfesorDAO.listar().size());
            
        } catch (Exception e) {
            System.err.println("Error al crear datos de ejemplo: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
