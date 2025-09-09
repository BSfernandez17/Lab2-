package com.brayan.miapp.View.components;

import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import com.brayan.miapp.Model.Estudiante;

public class ListaEstudiantes {
    private VBox contenedor;
    private TableView<EstudianteTableData> tabla;
    private ObservableList<EstudianteTableData> datos;
    
    public ListaEstudiantes() {
        inicializarComponentes();
        configurarTabla();
    }
    
    private void inicializarComponentes() {
        contenedor = new VBox(10);
        tabla = new TableView<>();
        datos = FXCollections.observableArrayList();
        
        // Título
        Label titulo = new Label("Lista de Estudiantes");
        titulo.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        
        contenedor.getChildren().addAll(titulo, tabla);
    }
    
    private void configurarTabla() {
        // Crear columnas
        TableColumn<EstudianteTableData, String> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colId.setPrefWidth(50);
        
        TableColumn<EstudianteTableData, String> colNombres = new TableColumn<>("Nombres");
        colNombres.setCellValueFactory(new PropertyValueFactory<>("nombres"));
        colNombres.setPrefWidth(120);
        
        TableColumn<EstudianteTableData, String> colApellidos = new TableColumn<>("Apellidos");
        colApellidos.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
        colApellidos.setPrefWidth(120);
        
        TableColumn<EstudianteTableData, String> colEmail = new TableColumn<>("Email");
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colEmail.setPrefWidth(150);
        
        TableColumn<EstudianteTableData, String> colCodigo = new TableColumn<>("Código");
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colCodigo.setPrefWidth(80);
        
        TableColumn<EstudianteTableData, String> colPrograma = new TableColumn<>("Programa");
        colPrograma.setCellValueFactory(new PropertyValueFactory<>("programa"));
        colPrograma.setPrefWidth(100);
        
        TableColumn<EstudianteTableData, String> colPromedio = new TableColumn<>("Promedio");
        colPromedio.setCellValueFactory(new PropertyValueFactory<>("promedio"));
        colPromedio.setPrefWidth(80);
        
        TableColumn<EstudianteTableData, String> colActivo = new TableColumn<>("Activo");
        colActivo.setCellValueFactory(new PropertyValueFactory<>("activo"));
        colActivo.setPrefWidth(60);
        
        // Agregar columnas a la tabla
        tabla.getColumns().addAll(colId, colNombres, colApellidos, colEmail, colCodigo, colPrograma, colPromedio, colActivo);
        
        // Configurar datos
        tabla.setItems(datos);
        tabla.setRowFactory(tv -> {
            TableRow<EstudianteTableData> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    // Doble clic para seleccionar
                }
            });
            return row;
        });
    }
    
    public VBox getContenedor() { return contenedor; }
    public TableView<EstudianteTableData> getTabla() { return tabla; }
    public ObservableList<EstudianteTableData> getDatos() { return datos; }
    
    public void agregarEstudiante(Estudiante estudiante) {
        EstudianteTableData data = new EstudianteTableData(
            String.valueOf(estudiante.getId()),
            String.valueOf(estudiante.getCodigo()),
            estudiante.getNombres(),
            estudiante.getApellidos(),
            estudiante.getEmail(),
            estudiante.getPrograma() != null ? estudiante.getPrograma().getNombre() : "N/A",
            estudiante.getActivo() ? "Sí" : "No",
            String.valueOf(estudiante.getPromedio())
        );
        datos.add(data);
    }
    
    public void actualizarEstudiante(Estudiante estudiante) {
        for (int i = 0; i < datos.size(); i++) {
            EstudianteTableData data = datos.get(i);
            if (data.getId().equals(String.valueOf(estudiante.getId()))) {
                data.setCodigo(String.valueOf(estudiante.getCodigo()));
                data.setNombres(estudiante.getNombres());
                data.setApellidos(estudiante.getApellidos());
                data.setEmail(estudiante.getEmail());
                data.setPrograma(estudiante.getPrograma() != null ? estudiante.getPrograma().getNombre() : "N/A");
                data.setActivo(estudiante.getActivo() ? "Sí" : "No");
                data.setPromedio(String.valueOf(estudiante.getPromedio()));
                tabla.refresh();
                break;
            }
        }
    }
    
    public void eliminarEstudiante(String id) {
        datos.removeIf(data -> data.getId().equals(id));
    }
    
    public void limpiarLista() {
        datos.clear();
    }
    
    public EstudianteTableData getEstudianteSeleccionado() {
        return tabla.getSelectionModel().getSelectedItem();
    }
}
