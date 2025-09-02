package com.brayan.Services;

import com.brayan.miapp.Model.CursoProfesor;
import com.brayan.miapp.DAO.CursoProfesorDAO;
import java.util.ArrayList;
import java.util.List;

public class CursosProfesores implements Servicios {
    private List<CursoProfesor> listado = new ArrayList<>();
    private CursoProfesorDAO cursoProfesorDAO = new CursoProfesorDAO();

    public void inscribir(CursoProfesor cursoProfesor) {
        listado.add(cursoProfesor);
    }

    public void guardarInformacion(CursoProfesor cursoProfesor) {
        cursoProfesorDAO.insertar(cursoProfesor);
        // También agregar a la lista local si no está ya
        if (!listado.contains(cursoProfesor)) {
            listado.add(cursoProfesor);
        }
    }

    public void cargarDatos() {
        listado.clear();
        listado.addAll(cursoProfesorDAO.listar());
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