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
        cursoProfesorDAO.insertar(cursoProfesor);
    }
    
    public void eliminar(CursoProfesor cursoProfesor) {
        listado.remove(cursoProfesor);
        cursoProfesorDAO.eliminar(
            cursoProfesor.getProfesor().getId(),
            cursoProfesor.getCurso().getId(),
            cursoProfesor.getAño(),
            cursoProfesor.getSemestre()
        );
    }
    
    public void actualizar(CursoProfesor cursoProfesorAntiguo, CursoProfesor cursoProfesorNuevo) {
        // Buscar y actualizar en la lista local
        for (int i = 0; i < listado.size(); i++) {
            CursoProfesor cp = listado.get(i);
            if (cp.getCurso().getId().equals(cursoProfesorAntiguo.getCurso().getId()) &&
                cp.getProfesor().getId() == cursoProfesorAntiguo.getProfesor().getId() &&
                cp.getAño().equals(cursoProfesorAntiguo.getAño()) &&
                cp.getSemestre().equals(cursoProfesorAntiguo.getSemestre())) {
                listado.set(i, cursoProfesorNuevo);
                break;
            }
        }
        // Actualizar en la base de datos
        // cursoProfesorDAO.actualizar(cursoProfesorAntiguo, cursoProfesorNuevo);
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