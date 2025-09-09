package com.brayan.miapp.View.controllers;

import com.brayan.miapp.Model.Persona;
import com.brayan.Services.InscripcionesPersonas;
import com.brayan.miapp.View.components.FormularioPersona;
import com.brayan.miapp.View.components.ListaPersonas;

public class PersonaController {
    
    private InscripcionesPersonas servicio;
    private FormularioPersona formulario;
    private ListaPersonas listaPersonas;
    private StatusCallback statusCallback;
    
    public PersonaController(InscripcionesPersonas servicio) {
        this.servicio = servicio;
    }
    
    public void setFormulario(FormularioPersona formulario) {
        this.formulario = formulario;
    }
    
    public void setListaPersonas(ListaPersonas listaPersonas) {
        this.listaPersonas = listaPersonas;
    }
    
    public void setStatusCallback(StatusCallback callback) {
        this.statusCallback = callback;
    }
    
    public void agregarPersona() {
        try {
            if (formulario.validarCampos()) {
                Persona persona = formulario.getPersonaFromForm();
                servicio.inscribir(persona);
                actualizarLista();
                formulario.limpiarFormulario();
                mostrarEstado("✅ Persona agregada correctamente", false);
            }
        } catch (Exception e) {
            mostrarEstado("❌ Error al agregar persona: " + e.getMessage(), true);
        }
    }
    
    public void actualizarPersona() {
        try {
            if (formulario.validarCampos()) {
                Persona persona = formulario.getPersonaFromForm();
                servicio.actualizar(persona);
                actualizarLista();
                formulario.limpiarFormulario();
                mostrarEstado("✅ Persona actualizada correctamente", false);
            }
        } catch (Exception e) {
            mostrarEstado("❌ Error al actualizar persona: " + e.getMessage(), true);
        }
    }
    
    public void eliminarPersona() {
        try {
            if (!formulario.getIdFromForm().isEmpty()) {
                Double id = Double.parseDouble(formulario.getIdFromForm());
                Persona personaAEliminar = new Persona(id, "", "", "");
                servicio.eliminar(personaAEliminar);
                actualizarLista();
                formulario.limpiarFormulario();
                mostrarEstado("✅ Persona eliminada correctamente", false);
            } else {
                mostrarEstado("⚠️ Ingrese el ID de la persona a eliminar", true);
            }
        } catch (Exception e) {
            mostrarEstado("❌ Error al eliminar persona: " + e.getMessage(), true);
        }
    }
    
    public void cargarPersonaSeleccionada(int index) {
        if (index >= 0) {
            String personaStr = servicio.imprimirPosicion(index);
            try {
                String[] parts = personaStr.split(", ");
                String id = parts[0].substring(parts[0].indexOf("=") + 1);
                String nombres = parts[1].substring(parts[1].indexOf("'") + 1, parts[1].lastIndexOf("'"));
                String apellidos = parts[2].substring(parts[2].indexOf("'") + 1, parts[2].lastIndexOf("'"));
                String email = parts[3].substring(parts[3].indexOf("'") + 1, parts[3].lastIndexOf("'"));
                
                Persona persona = new Persona(Double.parseDouble(id), nombres, apellidos, email);
                formulario.cargarPersona(persona);
                mostrarEstado("📝 Datos cargados para edición", false);
            } catch (Exception e) {
                mostrarEstado("❌ Error al cargar datos de la persona seleccionada", true);
            }
        }
    }
    
    public void refrescarLista() {
        servicio.cargarDatos();
        actualizarLista();
        mostrarEstado("Lista actualizada desde la base de datos", false);
    }
    
    private void actualizarLista() {
        if (listaPersonas != null) {
            listaPersonas.actualizarLista(servicio.imprimirListado());
        }
    }
    
    public void mostrarEstado(String mensaje, boolean esError) {
        if (statusCallback != null) {
            statusCallback.mostrarEstado(mensaje, esError);
        }
    }
}
