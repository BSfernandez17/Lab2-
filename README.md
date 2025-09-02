# Sistema de Gestión Universitaria

## Descripción
Sistema de gestión universitaria desarrollado en Java con JavaFX para la interfaz gráfica y H2 como base de datos embebida.

## Arquitectura del Proyecto

```
src/main/java/com/brayan/miapp/
├── Model/           # Entidades del dominio
├── DAO/            # Acceso a datos
├── Services/       # Lógica de negocio
├── View/           # Interfaz gráfica (JavaFX)
└── App.java        # Aplicación de consola
```

## Entidades
- **Persona**: Clase base para personas
- **Estudiante**: Hereda de Persona
- **Profesor**: Hereda de Persona
- **Facultad**: Facultades de la universidad
- **Programa**: Programas académicos
- **Curso**: Cursos disponibles
- **Inscripcion**: Relación estudiante-curso
- **CursoProfesor**: Relación profesor-curso

## Funcionalidades

### Interfaz Gráfica (JavaFX)
- ✅ Gestión completa de personas (CRUD)
- ✅ Interfaz intuitiva y moderna
- ✅ Validación de datos
- ✅ Persistencia en base de datos
- ✅ Actualización en tiempo real

### Servicios Disponibles
- **InscripcionesPersonas**: Gestión de personas
- **CursosInscritos**: Gestión de inscripciones
- **CursosProfesores**: Gestión de asignaciones profesor-curso

## Ejecución

### Interfaz Gráfica
```bash
mvn javafx:run
```

### Aplicación de Consola
```bash
mvn exec:java -Dexec.mainClass="com.brayan.miapp.App"
```

### Compilación
```bash
mvn clean compile
```

## Requisitos
- Java 11 o superior
- Maven 3.6+
- JavaFX 17

## Base de Datos
El sistema utiliza H2 Database (embebida) que se crea automáticamente al ejecutar la aplicación.

## Características Técnicas
- **Patrón DAO** para acceso a datos
- **Arquitectura en capas** (Model-Service-View)
- **Interfaz gráfica moderna** con JavaFX
- **Base de datos relacional** con integridad referencial
- **Validación de datos** en tiempo real
- **Manejo de excepciones** robusto
