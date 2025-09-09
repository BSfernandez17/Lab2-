package com.brayan.miapp.View;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import com.brayan.miapp.View.EstudiantesAppModular;
import com.brayan.miapp.View.InscripcionesAppModular;
import com.brayan.miapp.View.CursosProfesoresAppModular;
import com.brayan.miapp.View.FacultadesAppModular;
import com.brayan.miapp.View.modules.ProfesoresAppModular;
import com.brayan.miapp.View.modules.CursosAppModular;
import com.brayan.miapp.View.modules.ProgramasAppModular;

public class MainApp extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Sistema de Gestión Universitaria - Menú Principal");
        
        // Crear el layout principal
        VBox root = createMainLayout();
        
        // Crear ScrollPane para permitir scroll
        ScrollPane scrollPane = new ScrollPane(root);
        scrollPane.setFitToWidth(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
        
        // Crear escena con mayor altura
        Scene scene = new Scene(scrollPane, 600, 700);
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        primaryStage.setMinWidth(500);
        primaryStage.setMinHeight(600);
        primaryStage.show();
    }
    
    private VBox createMainLayout() {
        VBox root = new VBox(15);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #f8f9fa, #e9ecef);");
        
        // Título principal
        Label titulo = new Label("🎓 Sistema de Gestión Universitaria");
        titulo.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        
        Label subtitulo = new Label("Arquitectura Modular - Seleccione el módulo que desea utilizar");
        subtitulo.setStyle("-fx-font-size: 14px; -fx-text-fill: #7f8c8d;");
        subtitulo.setWrapText(true);
        
        // Panel de botones centralizados
        VBox panelPrincipal = new VBox(12);
        panelPrincipal.setAlignment(Pos.CENTER);
        panelPrincipal.setPrefWidth(350);
        
        Label tituloModular = new Label("📱 Módulos del Sistema");
        tituloModular.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #34495e;");
        
        Button btnPersonasModular = crearBoton("👥 Gestión de Personas", "#3498db", this::abrirAppPersonasModular);
        Button btnInscripcionesModular = crearBoton("📚 Gestión de Inscripciones", "#2980b9", this::abrirAppInscripcionesModular);
        Button btnCursosModular = crearBoton("📖 Gestión de Cursos", "#8e44ad", this::abrirAppCursosModular);
        Button btnCursosProfesoresModular = crearBoton("🏫 Gestión de Cursos-Profesores", "#ff9800", this::abrirAppCursosProfesoresModular);
        Button btnEstudiantesModular = crearBoton("🎓 Gestión de Estudiantes", "#e74c3c", this::abrirAppEstudiantesModular);
        Button btnProfesoresModular = crearBoton("👨‍🏫 Gestión de Profesores", "#9b59b6", this::abrirAppProfesoresModular);
        Button btnFacultadesModular = crearBoton("🏛️ Gestión de Facultades", "#16a085", this::abrirAppFacultadesModular);
        Button btnProgramasModular = crearBoton("📋 Gestión de Programas", "#f39c12", this::abrirAppProgramasModular);
        
        panelPrincipal.getChildren().addAll(
            tituloModular, 
            btnPersonasModular, 
            btnInscripcionesModular, 
            btnCursosModular, 
            btnCursosProfesoresModular, 
            btnEstudiantesModular, 
            btnProfesoresModular, 
            btnFacultadesModular, 
            btnProgramasModular
        );
        
        // Información adicional
        VBox infoPanel = new VBox(5);
        infoPanel.setAlignment(Pos.CENTER);
        
        Label infoLabel = new Label("💡 Tip: Use la rueda del mouse para hacer scroll si no ve todos los módulos");
        infoLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #95a5a6; -fx-font-style: italic;");
        infoLabel.setWrapText(true);
        
        infoPanel.getChildren().add(infoLabel);
        
        root.getChildren().addAll(titulo, subtitulo, panelPrincipal, infoPanel);
        return root;
    }
    
    private Button crearBoton(String texto, String color, Runnable accion) {
        Button boton = new Button(texto);
        boton.setPrefWidth(320);
        boton.setPrefHeight(40);
        
        String estiloBase = String.format(
            "-fx-font-size: 12px; -fx-font-weight: bold; " +
            "-fx-background-color: %s; -fx-text-fill: white; " +
            "-fx-border-radius: 6; -fx-background-radius: 6; " +
            "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 3, 0, 0, 1);", color
        );
        
        String estiloHover = String.format(
            "-fx-font-size: 12px; -fx-font-weight: bold; " +
            "-fx-background-color: derive(%s, -15%%); -fx-text-fill: white; " +
            "-fx-border-radius: 6; -fx-background-radius: 6; " +
            "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 4, 0, 0, 2);", color
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
            new CursosAppModular().start(new Stage());
        } catch (Exception e) {
            mostrarError("Error al abrir Gestión de Cursos", e.getMessage());
        }
    }
    
    private void abrirAppCursosProfesoresModular() {
        try {
            new CursosProfesoresAppModular().start(new Stage());
        } catch (Exception e) {
            mostrarError("Error al abrir Gestión de Cursos-Profesores", e.getMessage());
        }
    }
    
    private void abrirAppEstudiantesModular() {
        try {
            new EstudiantesAppModular().start(new Stage());
        } catch (Exception e) {
            mostrarError("Error al abrir Gestión de Estudiantes", e.getMessage());
        }
    }
    
    private void abrirAppProfesoresModular() {
        try {
            new ProfesoresAppModular().start(new Stage());
        } catch (Exception e) {
            mostrarError("Error al abrir Gestión de Profesores", e.getMessage());
        }
    }
    
    private void abrirAppFacultadesModular() {
        try {
            FacultadesAppModular facultadesApp = new FacultadesAppModular();
            facultadesApp.abrirVentana();
        } catch (Exception e) {
            mostrarError("Error al abrir Gestión de Facultades", e.getMessage());
        }
    }
    
    private void abrirAppProgramasModular() {
        try {
            ProgramasAppModular programasApp = new ProgramasAppModular();
            programasApp.abrirVentana();
        } catch (Exception e) {
            mostrarError("Error al abrir Gestión de Programas", e.getMessage());
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
