package com.brayan.miapp.View.components;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class FacultadTableData {
    private StringProperty id;
    private StringProperty nombre;
    private StringProperty decano;
    
    public FacultadTableData(String id, String nombre, String decano) {
        this.id = new SimpleStringProperty(id);
        this.nombre = new SimpleStringProperty(nombre);
        this.decano = new SimpleStringProperty(decano);
    }
    
    // Getters para JavaFX PropertyValueFactory
    public String getId() { return id.get(); }
    public String getNombre() { return nombre.get(); }
    public String getDecano() { return decano.get(); }
    
    // Properties para JavaFX
    public StringProperty idProperty() { return id; }
    public StringProperty nombreProperty() { return nombre; }
    public StringProperty decanoProperty() { return decano; }
    
    // Setters
    public void setId(String id) { this.id.set(id); }
    public void setNombre(String nombre) { this.nombre.set(nombre); }
    public void setDecano(String decano) { this.decano.set(decano); }
}
