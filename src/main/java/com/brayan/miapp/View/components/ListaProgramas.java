package com.brayan.miapp.View.components;

import java.util.List;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import com.brayan.miapp.Model.Programa;
import com.brayan.miapp.DAO.ProgramaDAO;

public class ListaProgramas {
    private VBox contenedor;
    private TableView<ProgramaTableData> tabla;
    private ObservableList<ProgramaTableData> datos;
    private ProgramaDAO programaDAO;
    
    public ListaProgramas() {
        this.programaDAO = new ProgramaDAO();
        inicializarComponentes();
        configurarTabla();
    }
    
    private void inicializarComponentes() {
        contenedor = new VBox(10);
        tabla = new TableView<>();
        datos = FXCollections.observableArrayList();
        
        // Título
        Label titulo = new Label("Lista de Programas Académicos");
        titulo.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        
        contenedor.getChildren().addAll(titulo, tabla);
    }
    
    private void configurarTabla() {
        // Crear columnas
        TableColumn<ProgramaTableData, String> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colId.setPrefWidth(50);
        
        TableColumn<ProgramaTableData, String> colNombre = new TableColumn<>("Nombre del Programa");
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colNombre.setPrefWidth(200);
        
        TableColumn<ProgramaTableData, String> colDuracion = new TableColumn<>("Duración");
        colDuracion.setCellValueFactory(new PropertyValueFactory<>("duracion"));
        colDuracion.setPrefWidth(80);
        
        TableColumn<ProgramaTableData, String> colRegistro = new TableColumn<>("Fecha Registro");
        colRegistro.setCellValueFactory(new PropertyValueFactory<>("registro"));
        colRegistro.setPrefWidth(120);
        
        TableColumn<ProgramaTableData, String> colFacultad = new TableColumn<>("Facultad");
        colFacultad.setCellValueFactory(new PropertyValueFactory<>("facultad"));
        colFacultad.setPrefWidth(150);
        
        // Agregar columnas a la tabla
        tabla.getColumns().addAll(colId, colNombre, colDuracion, colRegistro, colFacultad);
        
        // Configurar datos
        tabla.setItems(datos);
        tabla.setRowFactory(tv -> {
            TableRow<ProgramaTableData> row = new TableRow<>();
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
    public TableView<ProgramaTableData> getTabla() { return tabla; }
    public ObservableList<ProgramaTableData> getDatos() { return datos; }
    
    public void agregarPrograma(Programa programa) {
        ProgramaTableData data = new ProgramaTableData(
            programa.getId() != null ? programa.getId().toString() : "0",
            programa.getNombre(),
            programa.getDuracion() != null ? programa.getDuracion().toString() + " sem." : "0 sem.",
            programa.getRegistro() != null ? java.text.DateFormat.getDateInstance().format(programa.getRegistro()) : "Sin fecha",
            programa.getFacultad() != null ? programa.getFacultad().getNombre() : "N/A"
        );
        datos.add(data);
    }
    
    public void actualizarPrograma(Programa programa) {
        for (int i = 0; i < datos.size(); i++) {
            ProgramaTableData data = datos.get(i);
            if (data.getId().equals(programa.getId().toString())) {
                data.setNombre(programa.getNombre());
                data.setDuracion(programa.getDuracion() != null ? programa.getDuracion().toString() + " sem." : "");
                data.setRegistro(programa.getRegistro() != null ? programa.getRegistro().toString() : "");
                data.setFacultad(programa.getFacultad() != null ? programa.getFacultad().getNombre() : "N/A");
                tabla.refresh();
                break;
            }
        }
    }
    
    public void eliminarPrograma(String id) {
        datos.removeIf(data -> data.getId().equals(id));
    }
    
    public void limpiarLista() {
        datos.clear();
    }
    
    public ProgramaTableData getProgramaSeleccionado() {
        return tabla.getSelectionModel().getSelectedItem();
    }
    
    public void seleccionarPrograma(String id) {
        for (ProgramaTableData data : datos) {
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
            List<Programa> programas = programaDAO.listar();
            for (Programa programa : programas) {
                String fechaTexto = "Sin fecha";
                if (programa.getRegistro() != null) {
                    try {
                        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
                        fechaTexto = sdf.format(programa.getRegistro());
                    } catch (Exception e) {
                        fechaTexto = programa.getRegistro().toString();
                    }
                }
                
                ProgramaTableData data = new ProgramaTableData(
                    programa.getId() != null ? programa.getId().toString() : "0",
                    programa.getNombre() != null ? programa.getNombre() : "Sin nombre",
                    programa.getDuracion() != null ? programa.getDuracion().toString() + " sem." : "0 sem.",
                    fechaTexto,
                    programa.getFacultad() != null ? programa.getFacultad().getNombre() : "Sin facultad"
                );
                datos.add(data);
            }
        } catch (Exception e) {
            System.err.println("Error al actualizar lista de programas: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
