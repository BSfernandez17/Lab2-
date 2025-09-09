package com.brayan.miapp.View.components;

import java.util.List;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import com.brayan.miapp.Model.Facultad;
import com.brayan.miapp.DAO.FacultadDAO;

public class ListaFacultades {
    private VBox contenedor;
    private TableView<FacultadTableData> tabla;
    private ObservableList<FacultadTableData> datos;
    private FacultadDAO facultadDAO;
    
    public ListaFacultades() {
        this.facultadDAO = new FacultadDAO();
        inicializarComponentes();
        configurarTabla();
    }
    
    private void inicializarComponentes() {
        contenedor = new VBox(10);
        tabla = new TableView<>();
        datos = FXCollections.observableArrayList();
        
        // Título
        Label titulo = new Label("Lista de Facultades");
        titulo.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        
        contenedor.getChildren().addAll(titulo, tabla);
    }
    
    private void configurarTabla() {
        // Crear columnas
        TableColumn<FacultadTableData, String> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colId.setPrefWidth(50);
        
        TableColumn<FacultadTableData, String> colNombre = new TableColumn<>("Nombre de la Facultad");
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colNombre.setPrefWidth(300);
        
        TableColumn<FacultadTableData, String> colDecano = new TableColumn<>("Decano");
        colDecano.setCellValueFactory(new PropertyValueFactory<>("decano"));
        colDecano.setPrefWidth(250);
        
        // Agregar columnas a la tabla
        tabla.getColumns().addAll(colId, colNombre, colDecano);
        
        // Configurar datos
        tabla.setItems(datos);
        tabla.setRowFactory(tv -> {
            TableRow<FacultadTableData> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    // Doble clic para seleccionar
                }
            });
            return row;
        });
        
        // Configurar selección
        tabla.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }
    
    public VBox getContenedor() { return contenedor; }
    public TableView<FacultadTableData> getTabla() { return tabla; }
    public ObservableList<FacultadTableData> getDatos() { return datos; }
    
    public void agregarFacultad(Facultad facultad) {
        FacultadTableData data = new FacultadTableData(
            facultad.getId() != null ? facultad.getId().toString() : "0",
            facultad.getNombre() != null ? facultad.getNombre() : "Sin nombre",
            facultad.getDecano() != null ? (facultad.getDecano().getNombres() + " " + facultad.getDecano().getApellidos()) : "Sin decano"
        );
        datos.add(data);
    }
    
    public void actualizarFacultad(Facultad facultad) {
        for (int i = 0; i < datos.size(); i++) {
            FacultadTableData data = datos.get(i);
            if (data.getId().equals(facultad.getId().toString())) {
                data.setNombre(facultad.getNombre() != null ? facultad.getNombre() : "Sin nombre");
                data.setDecano(facultad.getDecano() != null ? 
                    (facultad.getDecano().getNombres() + " " + facultad.getDecano().getApellidos()) : "Sin decano");
                tabla.refresh();
                break;
            }
        }
    }
    
    public void eliminarFacultad(String id) {
        datos.removeIf(data -> data.getId().equals(id));
    }
    
    public void limpiarLista() {
        datos.clear();
    }
    
    public FacultadTableData getFacultadSeleccionada() {
        return tabla.getSelectionModel().getSelectedItem();
    }
    
    public void seleccionarFacultad(String id) {
        for (FacultadTableData data : datos) {
            if (data.getId().equals(id)) {
                tabla.getSelectionModel().select(data);
                tabla.scrollTo(data);
                break;
            }
        }
    }
    
    public void actualizarLista() {
        datos.clear();
        try {
            List<Facultad> facultades = facultadDAO.listar();
            for (Facultad facultad : facultades) {
                FacultadTableData data = new FacultadTableData(
                    facultad.getId() != null ? facultad.getId().toString() : "0",
                    facultad.getNombre() != null ? facultad.getNombre() : "Sin nombre",
                    facultad.getDecano() != null ? 
                        (facultad.getDecano().getNombres() + " " + facultad.getDecano().getApellidos()) : "Sin decano"
                );
                datos.add(data);
            }
        } catch (Exception e) {
            System.err.println("Error al actualizar lista de facultades: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
