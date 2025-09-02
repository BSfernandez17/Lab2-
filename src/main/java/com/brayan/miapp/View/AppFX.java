package com.brayan.miapp.View;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import com.brayan.miapp.DAO.DatabaseSetup;
import com.brayan.Services.InscripcionesPersonas;
import com.brayan.miapp.View.components.*;
import com.brayan.miapp.View.controllers.PersonaController;

public class AppFX extends Application {
    
    private PersonaController controller;
    private FormularioPersona formularioPersona;
    private ListaPersonas listaPersonas;
    private BarraEstado barraEstado;
    
    @Override
    public void start(Stage primaryStage) {
        // Inicializar base de datos y servicio
        DatabaseSetup.init();
        InscripcionesPersonas servicio = new InscripcionesPersonas();
        servicio.cargarDatos();
        
        // Inicializar controlador
        controller = new PersonaController(servicio);
        
        // Configurar la ventana principal
        primaryStage.setTitle("Sistema de Gestión Universitaria");
        
        // Crear componentes
        initializeComponents();
        
        // Configurar relaciones entre componentes
        setupComponentRelations();
        
        // Crear el layout principal
        BorderPane root = createMainLayout();
        
        // Crear escena
        Scene scene = new Scene(root, 900, 650);
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        primaryStage.show();
        
        // Cargar datos iniciales
        controller.refrescarLista();
    }
    
    private void initializeComponents() {
        formularioPersona = new FormularioPersona(controller);
        listaPersonas = new ListaPersonas(controller);
        barraEstado = new BarraEstado();
    }
    
    private void setupComponentRelations() {
        controller.setFormulario(formularioPersona);
        controller.setListaPersonas(listaPersonas);
        controller.setStatusCallback(barraEstado::mostrarEstado);
    }
    
    private BorderPane createMainLayout() {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));
        root.setStyle("-fx-background-color: #ffffff;");
        
        // Ensamblar componentes
        root.setTop(new TituloApp());
        root.setLeft(formularioPersona);
        root.setCenter(listaPersonas);
        root.setBottom(barraEstado);
        
        return root;
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
