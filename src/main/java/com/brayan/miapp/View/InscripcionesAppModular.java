package com.brayan.miapp.View;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import com.brayan.miapp.View.components.FormularioInscripcion;
import com.brayan.miapp.View.components.ListaInscripciones;
import com.brayan.miapp.View.components.BarraEstadoInscripciones;
import com.brayan.miapp.View.controllers.InscripcionController;
import com.brayan.miapp.DAO.DatabaseSetup;
import com.brayan.miapp.DAO.DataSeeder;

public class InscripcionesAppModular extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Configurar base de datos
        DatabaseSetup.init();
        DataSeeder.poblarBaseDatos();
        
        // Crear controlador
        InscripcionController controller = new InscripcionController();
        
        // Crear componentes
        FormularioInscripcion formulario = new FormularioInscripcion(controller);
        ListaInscripciones lista = new ListaInscripciones(controller);
        BarraEstadoInscripciones barraEstado = new BarraEstadoInscripciones();
        
        // Conectar componentes con el controlador
        controller.setComponents(formulario, lista, barraEstado);
        
        // Layout principal
        BorderPane root = new BorderPane();
        root.setLeft(formulario);
        root.setCenter(lista);
        root.setBottom(barraEstado);
        root.setStyle("-fx-background-color: #ffffff;");
        
        Scene scene = new Scene(root, 1000, 700);
        
        primaryStage.setTitle("Sistema de Gestión de Inscripciones - Modular");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
