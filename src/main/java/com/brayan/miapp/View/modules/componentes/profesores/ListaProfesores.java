package com.brayan.miapp.View.modules.componentes.profesores;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import com.brayan.miapp.Model.Profesor;
import com.brayan.miapp.View.controllers.ProfesorController;
import com.brayan.miapp.View.components.ProfesorTableData;

public class ListaProfesores extends VBox {
    
    private TableView<ProfesorTableData> tablaProfesores;
    private ObservableList<ProfesorTableData> listaProfesores;
    private ProfesorController controller;
    private FormularioProfesor formulario;
    private BarraEstadoProfesores barraEstado;
    
    public ListaProfesores(FormularioProfesor formulario, BarraEstadoProfesores barraEstado) {
        this.formulario = formulario;
        this.barraEstado = barraEstado;
        this.controller = new ProfesorController();
        this.controller.setStatusCallback(barraEstado);
        this.listaProfesores = FXCollections.observableArrayList();
        
        configurarTabla();
        configurarEventos();
        configurarIntegracion();
    }
    
    private void configurarTabla() {
        setSpacing(10);
        setPadding(new Insets(10));
        setStyle("-fx-border-color: #cccccc; -fx-border-width: 1px; -fx-padding: 10px;");
        
        Label titulo = new Label("Lista de Profesores");
        titulo.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        
        tablaProfesores = new TableView<>();
        tablaProfesores.setItems(listaProfesores);
        
        // Configurar columnas
        TableColumn<ProfesorTableData, String> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colId.setPrefWidth(80);
        
        TableColumn<ProfesorTableData, String> colNombres = new TableColumn<>("Nombres");
        colNombres.setCellValueFactory(new PropertyValueFactory<>("nombres"));
        colNombres.setPrefWidth(150);
        
        TableColumn<ProfesorTableData, String> colApellidos = new TableColumn<>("Apellidos");
        colApellidos.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
        colApellidos.setPrefWidth(150);
        
        TableColumn<ProfesorTableData, String> colEmail = new TableColumn<>("Email");
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colEmail.setPrefWidth(200);
        
        TableColumn<ProfesorTableData, String> colTipoContrato = new TableColumn<>("Tipo Contrato");
        colTipoContrato.setCellValueFactory(new PropertyValueFactory<>("tipoContrato"));
        colTipoContrato.setPrefWidth(120);
        
        tablaProfesores.getColumns().addAll(colId, colNombres, colApellidos, colEmail, colTipoContrato);
        
        // Botones de acción
        Button btnEditar = new Button("Editar");
        Button btnEliminar = new Button("Eliminar");
        Button btnActualizar = new Button("Actualizar Lista");
        
        btnEditar.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");
        btnEliminar.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");
        btnActualizar.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        
        btnEditar.setOnAction(e -> editarProfesor());
        btnEliminar.setOnAction(e -> eliminarProfesor());
        btnActualizar.setOnAction(e -> cargarProfesores());
        
        getChildren().addAll(titulo, tablaProfesores, btnEditar, btnEliminar, btnActualizar);
    }
    
    private void configurarEventos() {
        tablaProfesores.setRowFactory(tv -> {
            TableRow<ProfesorTableData> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    editarProfesor();
                }
            });
            return row;
        });
    }
    
    private void configurarIntegracion() {
        formulario.setOnCambio(() -> cargarProfesores());
    }
    
    public void cargarProfesores() {
        listaProfesores.clear();
        try {
            for (Profesor profesor : controller.listarProfesores()) {
                listaProfesores.add(new ProfesorTableData(
                    String.valueOf(profesor.getId()),
                    profesor.getNombres(),
                    profesor.getApellidos(),
                    profesor.getEmail(),
                    profesor.getTipoContrato()
                ));
            }
            barraEstado.mostrarEstado("✅ Profesores cargados: " + listaProfesores.size(), false);
        } catch (Exception e) {
            barraEstado.mostrarEstado("❌ Error al cargar profesores", true);
        }
    }
    
    private void editarProfesor() {
        ProfesorTableData seleccionado = tablaProfesores.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            formulario.cargarProfesor(seleccionado);
            barraEstado.mostrarEstado("📝 Editando profesor: " + seleccionado.getNombres(), false);
        } else {
            barraEstado.mostrarEstado("⚠️ Seleccione un profesor para editar", true);
        }
    }
    
    private void eliminarProfesor() {
        ProfesorTableData seleccionado = tablaProfesores.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmar eliminación");
            alert.setHeaderText("¿Está seguro de eliminar este profesor?");
            alert.setContentText("Profesor: " + seleccionado.getNombres() + " " + seleccionado.getApellidos());
            
            if (alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
                controller.eliminarProfesor(Double.valueOf(seleccionado.getId()));
                cargarProfesores();
            }
        } else {
            barraEstado.mostrarEstado("⚠️ Seleccione un profesor para eliminar", true);
        }
    }
}
