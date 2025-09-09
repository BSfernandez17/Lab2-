package com.brayan.miapp.View.modules;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import com.brayan.miapp.View.modules.componentes.profesores.FormularioProfesor;
import com.brayan.miapp.View.modules.componentes.profesores.ListaProfesores;
import com.brayan.miapp.View.modules.componentes.profesores.BarraEstadoProfesores;

public class ProfesoresAppModular extends Application {
    
    private FormularioProfesor formularioProfesor;
    private ListaProfesores listaProfesores;
    private BarraEstadoProfesores barraEstado;
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Gestión de Profesores - Modular");
        
        // Crear componentes
        barraEstado = new BarraEstadoProfesores();
        formularioProfesor = new FormularioProfesor(barraEstado);
        listaProfesores = new ListaProfesores(formularioProfesor, barraEstado);
        
        // Configurar layout principal
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));
        root.setLeft(formularioProfesor);
        root.setCenter(listaProfesores);
        root.setBottom(barraEstado);
        
        Scene scene = new Scene(root, 1000, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
        
        // Cargar datos iniciales
        listaProfesores.cargarProfesores();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
