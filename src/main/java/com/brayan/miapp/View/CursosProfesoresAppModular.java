package com.brayan.miapp.View;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import com.brayan.miapp.View.components.FormularioCursoProfesor;
import com.brayan.miapp.View.components.ListaCursosProfesores;
import com.brayan.miapp.View.components.BarraEstadoCursosProfesores;
import com.brayan.miapp.View.controllers.CursoProfesorController;
import com.brayan.miapp.DAO.DatabaseSetup;
import com.brayan.miapp.DAO.DataSeeder;

public class CursosProfesoresAppModular extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Configurar base de datos
        DatabaseSetup.init();
        DataSeeder.poblarBaseDatos();
        
        // Crear controlador
        CursoProfesorController controller = new CursoProfesorController();
        
        // Crear componentes
        FormularioCursoProfesor formulario = new FormularioCursoProfesor(controller);
        ListaCursosProfesores lista = new ListaCursosProfesores(controller);
        BarraEstadoCursosProfesores barraEstado = new BarraEstadoCursosProfesores();
        
        // Conectar componentes con el controlador
        controller.setComponents(formulario, lista, barraEstado);
        
        // Layout principal
        BorderPane root = new BorderPane();
        root.setLeft(formulario);
        root.setCenter(lista);
        root.setBottom(barraEstado);
        root.setStyle("-fx-background-color: #ffffff;");
        
        Scene scene = new Scene(root, 1000, 700);
        
        primaryStage.setTitle("Sistema de Gestión de Cursos-Profesores - Modular");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
