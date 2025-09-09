package com.brayan.miapp.View.modules.componentes.cursos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import com.brayan.miapp.Model.Curso;
import com.brayan.miapp.View.controllers.CursoController;

public class ListaCursos extends VBox {
    
    private TableView<CursoTableData> tablaCursos;
    private ObservableList<CursoTableData> listaCursos;
    private CursoController controller;
    private FormularioCurso formulario;
    private BarraEstadoCursos barraEstado;
    
    public ListaCursos(FormularioCurso formulario, BarraEstadoCursos barraEstado) {
        this.formulario = formulario;
        this.barraEstado = barraEstado;
        this.controller = new CursoController();
        this.controller.setStatusCallback(barraEstado);
        this.listaCursos = FXCollections.observableArrayList();
        
        configurarTabla();
        configurarEventos();
        configurarIntegracion();
    }
    
    private void configurarTabla() {
        setSpacing(10);
        setPadding(new Insets(10));
        setStyle("-fx-border-color: #cccccc; -fx-border-width: 1px; -fx-padding: 10px;");
        
        Label titulo = new Label("Lista de Cursos");
        titulo.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        
        tablaCursos = new TableView<>();
        tablaCursos.setItems(listaCursos);
        
        // Configurar columnas
        TableColumn<CursoTableData, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colId.setPrefWidth(80);
        
        TableColumn<CursoTableData, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colNombre.setPrefWidth(200);
        
        TableColumn<CursoTableData, String> colPrograma = new TableColumn<>("Programa");
        colPrograma.setCellValueFactory(new PropertyValueFactory<>("programa"));
        colPrograma.setPrefWidth(200);
        
        TableColumn<CursoTableData, String> colActivo = new TableColumn<>("Activo");
        colActivo.setCellValueFactory(new PropertyValueFactory<>("activo"));
        colActivo.setPrefWidth(80);
        
        tablaCursos.getColumns().addAll(colId, colNombre, colPrograma, colActivo);
        
        // Botones de acción
        Button btnEditar = new Button("Editar");
        Button btnEliminar = new Button("Eliminar");
        Button btnActualizar = new Button("Actualizar Lista");
        
        btnEditar.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");
        btnEliminar.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");
        btnActualizar.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        
        btnEditar.setOnAction(e -> editarCurso());
        btnEliminar.setOnAction(e -> eliminarCurso());
        btnActualizar.setOnAction(e -> cargarCursos());
        
        getChildren().addAll(titulo, tablaCursos, btnEditar, btnEliminar, btnActualizar);
    }
    
    private void configurarEventos() {
        tablaCursos.setRowFactory(tv -> {
            TableRow<CursoTableData> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    editarCurso();
                }
            });
            return row;
        });
    }
    
    private void configurarIntegracion() {
        formulario.setOnCambio(() -> cargarCursos());
    }
    
    public void cargarCursos() {
        listaCursos.clear();
        try {
            for (Curso curso : controller.listarCursos()) {
                String nombrePrograma = curso.getPrograma() != null ? 
                    curso.getPrograma().getNombre() : "Sin programa";
                
                listaCursos.add(new CursoTableData(
                    curso.getId(),
                    curso.getNombre(),
                    nombrePrograma,
                    curso.getActivo()
                ));
            }
            barraEstado.mostrarEstado("✅ Cursos cargados: " + listaCursos.size(), false);
        } catch (Exception e) {
            barraEstado.mostrarEstado("❌ Error al cargar cursos: " + e.getMessage(), true);
        }
    }
    
    private void editarCurso() {
        CursoTableData seleccionado = tablaCursos.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            formulario.cargarCurso(seleccionado);
            barraEstado.mostrarEstado("📝 Editando curso: " + seleccionado.getNombre(), false);
        } else {
            barraEstado.mostrarEstado("⚠️ Seleccione un curso para editar", true);
        }
    }
    
    private void eliminarCurso() {
        CursoTableData seleccionado = tablaCursos.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmar eliminación");
            alert.setHeaderText("¿Está seguro de eliminar este curso?");
            alert.setContentText("Curso: " + seleccionado.getNombre());
            
            if (alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
                controller.eliminarCurso(seleccionado.getId());
                cargarCursos();
            }
        } else {
            barraEstado.mostrarEstado("⚠️ Seleccione un curso para eliminar", true);
        }
    }
}
