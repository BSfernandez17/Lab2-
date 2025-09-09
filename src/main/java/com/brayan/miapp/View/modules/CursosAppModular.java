package com.brayan.miapp.View.modules;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import com.brayan.miapp.View.modules.componentes.cursos.FormularioCurso;
import com.brayan.miapp.View.modules.componentes.cursos.ListaCursos;
import com.brayan.miapp.View.modules.componentes.cursos.BarraEstadoCursos;

public class CursosAppModular extends Application {
    
    private FormularioCurso formularioCurso;
    private ListaCursos listaCursos;
    private BarraEstadoCursos barraEstado;
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Gestión de Cursos - Modular");
        
        // Crear componentes
        barraEstado = new BarraEstadoCursos();
        formularioCurso = new FormularioCurso(barraEstado);
        listaCursos = new ListaCursos(formularioCurso, barraEstado);
        
        // Configurar layout principal
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));
        root.setLeft(formularioCurso);
        root.setCenter(listaCursos);
        root.setBottom(barraEstado);
        
        Scene scene = new Scene(root, 1200, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
        
        // Cargar datos iniciales
        listaCursos.cargarCursos();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
