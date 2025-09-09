package com.brayan.miapp.View;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import com.brayan.miapp.DAO.DatabaseSetup;
import com.brayan.miapp.View.modules.ProgramasAppModular;

public class MenuPrincipal extends Application {
    
    private Stage primaryStage;
    
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        
        // Inicializar base de datos
        try {
            DatabaseSetup.init();
        } catch (Exception e) {
            mostrarError("Error al inicializar la base de datos: " + e.getMessage());
            return;
        }
        
        // Crear la interfaz del menú principal
        VBox root = new VBox(30);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(50));
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #f8f9fa, #e9ecef);");
        
        // Título principal
        Label titulo = new Label("Sistema de Gestión Universitaria");
        titulo.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #495057;");
        
        Label subtitulo = new Label("Seleccione el módulo que desea gestionar");
        subtitulo.setStyle("-fx-font-size: 16px; -fx-text-fill: #6c757d;");
        
        // Contenedor de botones
        VBox botonesContainer = new VBox(20);
        botonesContainer.setAlignment(Pos.CENTER);
        botonesContainer.setMaxWidth(400);
        
        // Botón para Gestión de Facultades
        Button btnFacultades = new Button("📚 Gestión de Facultades");
        btnFacultades.setStyle(
            "-fx-background-color: #007bff; " +
            "-fx-text-fill: white; " +
            "-fx-font-size: 18px; " +
            "-fx-font-weight: bold; " +
            "-fx-padding: 15 30; " +
            "-fx-background-radius: 8; " +
            "-fx-cursor: hand;"
        );
        btnFacultades.setPrefWidth(350);
        btnFacultades.setOnMouseEntered(e -> 
            btnFacultades.setStyle(btnFacultades.getStyle() + "-fx-background-color: #0056b3;"));
        btnFacultades.setOnMouseExited(e -> 
            btnFacultades.setStyle(btnFacultades.getStyle().replace("-fx-background-color: #0056b3;", "-fx-background-color: #007bff;")));
        
        // Botón para Gestión de Programas
        Button btnProgramas = new Button("🎓 Gestión de Programas Académicos");
        btnProgramas.setStyle(
            "-fx-background-color: #28a745; " +
            "-fx-text-fill: white; " +
            "-fx-font-size: 18px; " +
            "-fx-font-weight: bold; " +
            "-fx-padding: 15 30; " +
            "-fx-background-radius: 8; " +
            "-fx-cursor: hand;"
        );
        btnProgramas.setPrefWidth(350);
        btnProgramas.setOnMouseEntered(e -> 
            btnProgramas.setStyle(btnProgramas.getStyle() + "-fx-background-color: #1e7e34;"));
        btnProgramas.setOnMouseExited(e -> 
            btnProgramas.setStyle(btnProgramas.getStyle().replace("-fx-background-color: #1e7e34;", "-fx-background-color: #28a745;")));
        
        // Botón para Gestión de Estudiantes (futuro)
        Button btnEstudiantes = new Button("👥 Gestión de Estudiantes");
        btnEstudiantes.setStyle(
            "-fx-background-color: #ffc107; " +
            "-fx-text-fill: black; " +
            "-fx-font-size: 18px; " +
            "-fx-font-weight: bold; " +
            "-fx-padding: 15 30; " +
            "-fx-background-radius: 8; " +
            "-fx-cursor: hand;"
        );
        btnEstudiantes.setPrefWidth(350);
        btnEstudiantes.setDisable(true); // Deshabilitado por ahora
        
        // Botón para Gestión de Profesores (futuro)
        Button btnProfesores = new Button("👨‍🏫 Gestión de Profesores");
        btnProfesores.setStyle(
            "-fx-background-color: #dc3545; " +
            "-fx-text-fill: white; " +
            "-fx-font-size: 18px; " +
            "-fx-font-weight: bold; " +
            "-fx-padding: 15 30; " +
            "-fx-background-radius: 8; " +
            "-fx-cursor: hand;"
        );
        btnProfesores.setPrefWidth(350);
        btnProfesores.setDisable(true); // Deshabilitado por ahora
        
        // Botón de salida
        Button btnSalir = new Button("❌ Salir del Sistema");
        btnSalir.setStyle(
            "-fx-background-color: #6c757d; " +
            "-fx-text-fill: white; " +
            "-fx-font-size: 16px; " +
            "-fx-padding: 10 25; " +
            "-fx-background-radius: 6;"
        );
        btnSalir.setPrefWidth(200);
        
        // Configurar eventos
        btnFacultades.setOnAction(e -> abrirGestionFacultades());
        btnProgramas.setOnAction(e -> abrirGestionProgramas());
        btnSalir.setOnAction(e -> {
            primaryStage.close();
            System.exit(0);
        });
        
        // Agregar elementos al contenedor
        botonesContainer.getChildren().addAll(
            btnFacultades, 
            btnProgramas, 
            new Separator(),
            btnEstudiantes, 
            btnProfesores,
            new Separator(),
            btnSalir
        );
        
        // Información del sistema
        Label infoSistema = new Label("Sistema desarrollado para la gestión integral universitaria");
        infoSistema.setStyle("-fx-font-size: 12px; -fx-text-fill: #868e96; -fx-font-style: italic;");
        
        root.getChildren().addAll(titulo, subtitulo, botonesContainer, infoSistema);
        
        // Configurar escena
        Scene scene = new Scene(root, 600, 700);
        primaryStage.setTitle("Sistema de Gestión Universitaria - Menú Principal");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.centerOnScreen();
        primaryStage.show();
    }
    
    private void abrirGestionFacultades() {
        try {
            FacultadesAppModular facultadesApp = new FacultadesAppModular();
            facultadesApp.abrirVentana();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error al abrir Gestión de Facultades");
            alert.setHeaderText("No se pudo abrir la aplicación");
            alert.setContentText("Para ejecutar la gestión de facultades:\n\n" +
                                "1. Ejecuta: mvn javafx:run@facultades\n" +
                                "2. O usa: ./ejecutar.sh facultades\n\n" +
                                "Error técnico: " + e.getMessage());
            alert.showAndWait();
        }
    }
    
    private void abrirGestionProgramas() {
        try {
            ProgramasAppModular programasApp = new ProgramasAppModular();
            programasApp.abrirVentana();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error al abrir Gestión de Programas");
            alert.setHeaderText("No se pudo abrir la aplicación");
            alert.setContentText("Para ejecutar la gestión de programas:\n\n" +
                                "1. Ejecuta: mvn javafx:run@programas\n" +
                                "2. O usa: ./ejecutar.sh programas\n\n" +
                                "Error técnico: " + e.getMessage());
            alert.showAndWait();
        }
    }
    
    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
