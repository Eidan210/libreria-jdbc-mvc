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
        if(titulo == null || titulo.isBlank()){
            view.mensaje("No contiene titulo valido");
            return;
        }

        if(precio <= 0){
            view.mensaje("El precio debe ser mayor a 0");
            return;
        }

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

}
