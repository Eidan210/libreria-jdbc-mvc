package org.example.controller;

import org.example.dao.LibroDao;
import org.example.model.Libro;
import org.example.view.LibroView;

import java.sql.SQLException;

public class LibroController {
    private final LibroDao dao;
    private final LibroView view;

    public LibroController(LibroView view, LibroDao dao) {
        this.view = view;
        this.dao = dao;
    }

    public void registrar(String titulo, String autor, double precio) {

        if(!datosValidos(titulo, autor, precio)) return;

        try {
            Libro libro = new Libro(0, titulo,autor,precio);
            dao.insertar(libro);
            view.mensaje("Libro registrado exitosamente: "+titulo);

        } catch (SQLException e) {
            view.mensaje("Error al registrar el libro "+e.getMessage());
        }
    }

    public void listar() {
        try {
            view.mostrar(dao.listar());
        }catch (SQLException e){
            view.mensaje("Error al listar los libros "+e.getMessage());
        }
    }

    public void buscarPorAutor(String autor){
        if (autor == null || autor.isBlank()){
            view.mensaje("Debes ingresar un Autor");
            return;
        }
        try {
            view.mostrar(dao.buscarPorAutor(autor));
        }catch (SQLException e){
            view.mensaje("Error al buscar el libro "+e.getMessage());
        }
    }

    // --- Ayudantes privados ---

    private boolean datosValidos(String titulo, String autor, double precio){
        if(titulo == null || titulo.isBlank()){
            view.mensaje("No contiene titulo valido");
            return false;
        }

        if(precio <= 0){
            view.mensaje("El precio debe ser mayor a 0");
            return false;
        }

        if(autor == null || autor.isBlank()){
            view.mensaje("Debes ingresar un Autor");
            return false;
        }
        return true;
    }

    private boolean idValido(int id){
        if(id <= 0) {
            view.mensaje("Debes ingresar un ID");
            return false;
        }
        return true;
    }

    // --- Final de Ayudantes privados ---

    public void actualizar(int id, String titulo, String autor, double precio) {

        if(!idValido(id)) return;

        if(!datosValidos(titulo,autor,precio)) return;

        try {
            Libro libro = new Libro(id, titulo,autor,precio);
            int filas = dao.actualizar(libro);

            if(filas == 0){
                view.mensaje("No existe un libro con id "+ id);
            } else {
                view.mensaje("Libro actualizado exitosamente: "+titulo);
            }

        }catch (SQLException e){
            view.mensaje("Error al actualizar el libro "+e.getMessage());
        }
    }

    public void eliminar(int id) {
        if(!idValido(id)) return;

        try{
            int filas = dao.eliminar(id);

            if(filas == 0){
                view.mensaje("No existe el libro con id "+ id);
            } else {
                view.mensaje("Libro eliminado exitosamente: "+id);
            }

        } catch (SQLException e){
            view.mensaje("Error al eliminar el libro "+e.getMessage());
        }
    }
}
