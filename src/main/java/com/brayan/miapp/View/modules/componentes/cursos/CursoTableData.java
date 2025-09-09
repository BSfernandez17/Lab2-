package com.brayan.miapp.View.modules.componentes.cursos;

import javafx.beans.property.*;

public class CursoTableData {
    private final IntegerProperty id;
    private final StringProperty nombre;
    private final StringProperty programa;
    private final StringProperty activo;
    
    public CursoTableData(Integer id, String nombre, String programa, Boolean activo) {
        this.id = new SimpleIntegerProperty(id);
        this.nombre = new SimpleStringProperty(nombre);
        this.programa = new SimpleStringProperty(programa != null ? programa : "Sin programa");
        this.activo = new SimpleStringProperty(activo ? "Sí" : "No");
    }
    
    // Getters para propiedades (usados por TableView)
    public IntegerProperty idProperty() { return id; }
    public StringProperty nombreProperty() { return nombre; }
    public StringProperty programaProperty() { return programa; }
    public StringProperty activoProperty() { return activo; }
    
    // Getters para valores
    public Integer getId() { return id.get(); }
    public String getNombre() { return nombre.get(); }
    public String getPrograma() { return programa.get(); }
    public String getActivo() { return activo.get(); }
    
    // Setters
    public void setId(Integer value) { id.set(value); }
    public void setNombre(String value) { nombre.set(value); }
    public void setPrograma(String value) { programa.set(value); }
    public void setActivo(String value) { activo.set(value); }
}
