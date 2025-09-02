package com.brayan.Services;

import com.brayan.miapp.Model.Inscripcion;
import com.brayan.miapp.DAO.InscripcionDAO;
import java.util.ArrayList;
import java.util.List;

public class CursosInscritos implements Servicios {
    private List<Inscripcion> listado = new ArrayList<>();
    private InscripcionDAO inscripcionDAO = new InscripcionDAO();

    public void inscribir(Inscripcion inscripcion) {
        listado.add(inscripcion);
        inscripcionDAO.insertar(inscripcion);
    }

    public void eliminar(Inscripcion inscripcion) {
        listado.remove(inscripcion);
        inscripcionDAO.eliminar(
            inscripcion.getCurso().getId(),
            inscripcion.getEstudiante().getId(),
            inscripcion.getAño(),
            inscripcion.getSemestre()
        );
    }

    public void actualizar(Inscripcion inscripcionAntigua, Inscripcion inscripcionNueva) {
        // Buscar y actualizar en la lista local
        for (int i = 0; i < listado.size(); i++) {
            Inscripcion ins = listado.get(i);
            if (ins.getCurso().getId().equals(inscripcionAntigua.getCurso().getId()) &&
                ins.getEstudiante().getId() == inscripcionAntigua.getEstudiante().getId() &&
                ins.getAño().equals(inscripcionAntigua.getAño()) &&
                ins.getSemestre().equals(inscripcionAntigua.getSemestre())) {
                listado.set(i, inscripcionNueva);
                break;
            }
        }
        // Actualizar en la base de datos
        inscripcionDAO.actualizar(inscripcionAntigua, inscripcionNueva);
    }

    public void guardarInformacion(Inscripcion inscripcion) {
        inscripcionDAO.insertar(inscripcion);
        // También agregar a la lista local si no está ya
        if (!listado.contains(inscripcion)) {
            listado.add(inscripcion);
        }
    }

    public void cargarDatos() {
        listado.clear();
        listado.addAll(inscripcionDAO.listar());
    }

    // Métodos adicionales útiles
    public List<Inscripcion> buscarPorEstudiante(Double estudianteId) {
        return inscripcionDAO.buscarPorEstudiante(estudianteId);
    }

    public List<Inscripcion> buscarPorCurso(Integer cursoId) {
        return inscripcionDAO.buscarPorCurso(cursoId);
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