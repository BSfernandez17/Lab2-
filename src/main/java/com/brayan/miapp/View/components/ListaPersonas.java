package com.brayan.miapp.View.components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.brayan.miapp.View.controllers.PersonaController;
import java.util.List;

public class ListaPersonas extends VBox {
    
    private TableView<PersonaTableData> tablaPersonas;
    private PersonaController controller;
    
    public ListaPersonas(PersonaController controller) {
        this.controller = controller;
        initializeComponents();
        setupLayout();
    }
    
    private void initializeComponents() {
        tablaPersonas = new TableView<>();
        tablaPersonas.setPrefHeight(400);
        tablaPersonas.setStyle("-fx-border-color: #bdc3c7; -fx-border-radius: 5; -fx-background-radius: 5;");
        
        // Crear las columnas
        TableColumn<PersonaTableData, String> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colId.setPrefWidth(80);
        
        TableColumn<PersonaTableData, String> colNombres = new TableColumn<>("Nombres");
        colNombres.setCellValueFactory(new PropertyValueFactory<>("nombres"));
        colNombres.setPrefWidth(150);
        
        TableColumn<PersonaTableData, String> colApellidos = new TableColumn<>("Apellidos");
        colApellidos.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
        colApellidos.setPrefWidth(150);
        
        TableColumn<PersonaTableData, String> colEmail = new TableColumn<>("Email");
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colEmail.setPrefWidth(200);
        
        // Suprimir warning de tipo genérico para varargs
        @SuppressWarnings("unchecked")
        TableColumn<PersonaTableData, String>[] columnas = new TableColumn[]{colId, colNombres, colApellidos, colEmail};
        tablaPersonas.getColumns().addAll(columnas);
        
        // Al seleccionar una persona, cargar sus datos en el formulario
        tablaPersonas.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) { // Doble clic
                int index = tablaPersonas.getSelectionModel().getSelectedIndex();
                controller.cargarPersonaSeleccionada(index);
            }
        });
    }
    
    private void setupLayout() {
        this.setSpacing(15);
        this.setPadding(new Insets(20));
        
        Label lblTitulo = new Label("Lista de Personas Registradas");
        lblTitulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        
        Button btnRefrescar = new Button("🔄 Refrescar Lista");
        btnRefrescar.setStyle("-fx-pref-width: 200; -fx-padding: 10; -fx-font-weight: bold; -fx-background-color: #f39c12; -fx-text-fill: white; -fx-border-radius: 5; -fx-background-radius: 5;");
        btnRefrescar.setOnAction(e -> controller.refrescarLista());
        
        HBox panelBotonRefrescar = new HBox(btnRefrescar);
        panelBotonRefrescar.setAlignment(Pos.CENTER);
        
        Label lblInstrucciones = new Label("💡 Tip: Haz doble clic en una persona para editarla");
        lblInstrucciones.setStyle("-fx-text-fill: #7f8c8d; -fx-font-style: italic;");
        
        this.getChildren().addAll(lblTitulo, tablaPersonas, panelBotonRefrescar, lblInstrucciones);
    }

    public void actualizarLista(List<String> personas) {
        ObservableList<PersonaTableData> datos = FXCollections.observableArrayList();
        
        for (String personaStr : personas) {
            try {
                // Parsear la cadena para extraer los datos
                // Formato: "Persona{id=1.0, nombres='Juan', apellidos='Perez', email='juan@email.com'}"
                String[] parts = personaStr.split(", ");
                String id = parts[0].substring(parts[0].indexOf("=") + 1);
                String nombres = parts[1].substring(parts[1].indexOf("'") + 1, parts[1].lastIndexOf("'"));
                String apellidos = parts[2].substring(parts[2].indexOf("'") + 1, parts[2].lastIndexOf("'"));
                String email = parts[3].substring(parts[3].indexOf("'") + 1, parts[3].lastIndexOf("'"));
                
                datos.add(new PersonaTableData(id, nombres, apellidos, email));
            } catch (Exception e) {
                // Si hay error al parsear, omitir esta entrada
                continue;
            }
        }
        
        tablaPersonas.setItems(datos);
    }
}
