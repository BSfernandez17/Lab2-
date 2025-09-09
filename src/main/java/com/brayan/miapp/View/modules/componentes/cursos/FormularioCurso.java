package com.brayan.miapp.View.modules.componentes.cursos;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import com.brayan.miapp.Model.Curso;
import com.brayan.miapp.Model.Programa;
import com.brayan.miapp.View.controllers.CursoController;
import com.brayan.miapp.View.controllers.ProgramaController;

public class FormularioCurso extends VBox {
    
    private TextField txtId;
    private TextField txtNombre;
    private ComboBox<Programa> cmbPrograma;
    private CheckBox chkActivo;
    private Button btnAgregar;
    private Button btnActualizar;
    private Button btnLimpiar;
    
    private CursoController cursoController;
    private ProgramaController programaController;
    private BarraEstadoCursos barraEstado;
    private boolean esActualizacion = false;
    
    public FormularioCurso(BarraEstadoCursos barraEstado) {
        this.barraEstado = barraEstado;
        this.cursoController = new CursoController();
        this.programaController = new ProgramaController();
        this.cursoController.setStatusCallback(barraEstado);
        this.programaController.setStatusCallback(barraEstado);
        
        configurarFormulario();
        configurarEventos();
        cargarProgramas();
    }
    
    private void configurarFormulario() {
        setSpacing(10);
        setPadding(new Insets(10));
        setAlignment(Pos.TOP_LEFT);
        setStyle("-fx-border-color: #cccccc; -fx-border-width: 1px; -fx-padding: 10px;");
        
        Label titulo = new Label("Datos del Curso");
        titulo.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setAlignment(Pos.TOP_LEFT);
        
        // Campos del formulario
        txtId = new TextField();
        txtId.setPromptText("ID del curso");
        
        txtNombre = new TextField();
        txtNombre.setPromptText("Nombre del curso");
        
        cmbPrograma = new ComboBox<>();
        cmbPrograma.setPromptText("Seleccione un programa");
        cmbPrograma.setPrefWidth(200);
        
        chkActivo = new CheckBox("Curso activo");
        chkActivo.setSelected(true);
        
        // Añadir al grid
        grid.add(new Label("ID:"), 0, 0);
        grid.add(txtId, 1, 0);
        grid.add(new Label("Nombre:"), 0, 1);
        grid.add(txtNombre, 1, 1);
        grid.add(new Label("Programa:"), 0, 2);
        grid.add(cmbPrograma, 1, 2);
        grid.add(chkActivo, 1, 3);
        
        // Botones
        btnAgregar = new Button("Agregar Curso");
        btnActualizar = new Button("Actualizar Curso");
        btnLimpiar = new Button("Limpiar");
        
        btnAgregar.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        btnActualizar.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");
        btnLimpiar.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");
        
        btnActualizar.setVisible(false);
        
        getChildren().addAll(titulo, grid, btnAgregar, btnActualizar, btnLimpiar);
    }
    
    private void configurarEventos() {
        btnAgregar.setOnAction(e -> agregarCurso());
        btnActualizar.setOnAction(e -> actualizarCurso());
        btnLimpiar.setOnAction(e -> limpiarFormulario());
    }
    
    private void cargarProgramas() {
        cmbPrograma.getItems().clear();
        for (Programa programa : programaController.listarProgramas()) {
            cmbPrograma.getItems().add(programa);
        }
        
        // Configurar el display del ComboBox
        cmbPrograma.setConverter(new javafx.util.StringConverter<Programa>() {
            @Override
            public String toString(Programa programa) {
                return programa != null ? programa.getNombre() : "";
            }
            
            @Override
            public Programa fromString(String string) {
                return null;
            }
        });
    }
    
    private void agregarCurso() {
        if (!validarCampos()) return;
        
        try {
            Integer id = Integer.parseInt(txtId.getText());
            Curso curso = new Curso(
                id,
                txtNombre.getText(),
                cmbPrograma.getValue(),
                chkActivo.isSelected()
            );
            
            cursoController.agregarCurso(curso);
            limpiarFormulario();
            notificarCambio();
        } catch (NumberFormatException ex) {
            barraEstado.mostrarEstado("❌ ID debe ser un número válido", true);
        }
    }
    
    private void actualizarCurso() {
        if (!validarCampos()) return;
        
        try {
            Integer id = Integer.parseInt(txtId.getText());
            Curso curso = new Curso(
                id,
                txtNombre.getText(),
                cmbPrograma.getValue(),
                chkActivo.isSelected()
            );
            
            cursoController.actualizarCurso(curso);
            limpiarFormulario();
            notificarCambio();
        } catch (NumberFormatException ex) {
            barraEstado.mostrarEstado("❌ ID debe ser un número válido", true);
        }
    }
    
    private boolean validarCampos() {
        if (txtId.getText().trim().isEmpty() ||
            txtNombre.getText().trim().isEmpty()) {
            
            barraEstado.mostrarEstado("❌ ID y nombre son obligatorios", true);
            return false;
        }
        return true;
    }
    
    public void cargarCurso(CursoTableData cursoData) {
        txtId.setText(cursoData.getId().toString());
        txtNombre.setText(cursoData.getNombre());
        
        // Buscar el programa correspondiente
        String nombrePrograma = cursoData.getPrograma();
        if (!"Sin programa".equals(nombrePrograma)) {
            for (Programa programa : cmbPrograma.getItems()) {
                if (programa.getNombre().equals(nombrePrograma)) {
                    cmbPrograma.setValue(programa);
                    break;
                }
            }
        }
        
        chkActivo.setSelected("Sí".equals(cursoData.getActivo()));
        
        btnAgregar.setVisible(false);
        btnActualizar.setVisible(true);
        esActualizacion = true;
        txtId.setEditable(false);
    }
    
    public void limpiarFormulario() {
        txtId.clear();
        txtNombre.clear();
        cmbPrograma.setValue(null);
        chkActivo.setSelected(true);
        
        btnAgregar.setVisible(true);
        btnActualizar.setVisible(false);
        esActualizacion = false;
        txtId.setEditable(true);
        barraEstado.limpiarEstado();
    }
    
    private Runnable onCambioListener;
    
    public void setOnCambio(Runnable listener) {
        this.onCambioListener = listener;
    }
    
    private void notificarCambio() {
        if (onCambioListener != null) {
            onCambioListener.run();
        }
    }
}
