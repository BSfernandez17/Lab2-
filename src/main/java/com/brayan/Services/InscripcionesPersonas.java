package com.brayan.Services;

import com.brayan.miapp.Model.Persona;
import com.brayan.miapp.DAO.PersonaDAO;
import java.util.ArrayList;
import java.util.List;

public class InscripcionesPersonas implements Servicios {
    private List<Persona> listado = new ArrayList<>();
    private PersonaDAO personaDAO = new PersonaDAO();

    public void inscribir(Persona persona) {
        listado.add(persona);
        personaDAO.insertar(persona);
    }

    public void eliminar(Persona persona) {
        listado.remove(persona);
        personaDAO.eliminar(persona.getId());
    }

    public void actualizar(Persona persona) {
        // Buscar la persona en la lista local y actualizarla
        for (int i = 0; i < listado.size(); i++) {
            if (listado.get(i).getId() == persona.getId()) {
                listado.set(i, persona);
                break;
            }
        }
        // Actualizar en la base de datos
        personaDAO.actualizar(persona);
    }

    public void guardarInformacion(Persona persona) {
        personaDAO.insertar(persona);
        // También agregar a la lista local si no está ya
        if (!listado.contains(persona)) {
            listado.add(persona);
        }
    }

    public void cargarDatos() {
        listado.clear();
        listado.addAll(personaDAO.listar());
    }

    @Override
    public String imprimirPosicion(int posicion) {
        return listado.get(posicion).toString();
    }

    @Override
    public Integer cantidadActual() {
        return listado.size();
    }

    @Override
    public List<String> imprimirListado() {
        return listado.stream().map(Object::toString).toList();
    }
}