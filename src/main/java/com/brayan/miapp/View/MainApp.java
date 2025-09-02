package com.brayan.miapp.View;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class MainApp extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Sistema de Gestión Universitaria - Menú Principal");
        
        // Crear el layout principal
        VBox root = createMainLayout();
        
        // Crear escena
        Scene scene = new Scene(root, 500, 450);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
    
    private VBox createMainLayout() {
        VBox root = new VBox(25);
        root.setPadding(new Insets(30));
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #f8f9fa, #e9ecef);");
        
        // Título principal
        Label titulo = new Label("🎓 Sistema de Gestión Universitaria");
        titulo.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        
        Label subtitulo = new Label("Arquitectura Modular - Seleccione el módulo que desea utilizar");
        subtitulo.setStyle("-fx-font-size: 16px; -fx-text-fill: #7f8c8d;");
        
        // Panel de botones centralizados
        VBox panelPrincipal = new VBox(20);
        panelPrincipal.setAlignment(Pos.CENTER);
        panelPrincipal.setPrefWidth(400);
        
        Label tituloModular = new Label("� Módulos del Sistema");
        tituloModular.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #34495e;");
        
        Button btnPersonasModular = crearBoton("👥 Gestión de Personas", "#eeee21ff", this::abrirAppPersonasModular);
        Button btnInscripcionesModular = crearBoton("📚 Gestión de Inscripciones", "#2990b6ff", this::abrirAppInscripcionesModular);
        Button btnCursosModular = crearBoton("🏫 Gestión de Cursos-Profesores", "#f50fbbff", this::abrirAppCursosModular);
        
        panelPrincipal.getChildren().addAll(tituloModular, btnPersonasModular, btnInscripcionesModular, btnCursosModular);
        
        // Información adicional
        VBox infoPanel = new VBox(5);
        infoPanel.setAlignment(Pos.CENTER);
        
        
        
        
        
        root.getChildren().addAll(titulo, subtitulo, panelPrincipal, infoPanel);
        return root;
    }
    
    private Button crearBoton(String texto, String color, Runnable accion) {
        Button boton = new Button(texto);
        boton.setPrefWidth(300);
        boton.setPrefHeight(45);
        
        String estiloBase = String.format(
            "-fx-font-size: 13px; -fx-font-weight: bold; " +
            "-fx-background-color: %s; -fx-text-fill: white; " +
            "-fx-border-radius: 8; -fx-background-radius: 8; " +
            "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 4, 0, 0, 2);", color
        );
        
        String estiloHover = String.format(
            "-fx-font-size: 13px; -fx-font-weight: bold; " +
            "-fx-background-color: derive(%s, -20%%); -fx-text-fill: white; " +
            "-fx-border-radius: 8; -fx-background-radius: 8; " +
            "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 6, 0, 0, 3);", color
        );
        
        boton.setStyle(estiloBase);
        boton.setOnMouseEntered(e -> boton.setStyle(estiloHover));
        boton.setOnMouseExited(e -> boton.setStyle(estiloBase));
        boton.setOnAction(e -> accion.run());
        
        return boton;
    }
    
    private void abrirAppPersonasModular() {
        try {
            new AppFX().start(new Stage()); // AppFX ya es modular
        } catch (Exception e) {
            mostrarError("Error al abrir Gestión de Personas", e.getMessage());
        }
    }
    
    private void abrirAppInscripcionesModular() {
        try {
            new InscripcionesAppModular().start(new Stage());
        } catch (Exception e) {
            mostrarError("Error al abrir Gestión de Inscripciones", e.getMessage());
        }
    }
    
    private void abrirAppCursosModular() {
        try {
            new CursosProfesoresAppModular().start(new Stage());
        } catch (Exception e) {
            mostrarError("Error al abrir Gestión de Cursos-Profesores", e.getMessage());
        }
    }
    
    private void mostrarError(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
