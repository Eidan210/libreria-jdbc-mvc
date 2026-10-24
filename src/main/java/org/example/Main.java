package org.example;

import org.example.controller.LibroController;
import org.example.dao.LibroDao;
import org.example.view.LibroView;

public class Main {
    public static void main(String[] args) {
        LibroController controller = new LibroController(new LibroView(), new LibroDao());

        controller.registrar( "Noches Blancas", "Fiódor Dostoyevski", 20.99);
        controller.registrar("", "Martin luther king", 20.99);
        controller.registrar("Habitos Atomicos", "James Clear", 29.99);

        controller.listar();

        controller.buscarPorAutor("Fiódor Dostoyevski");
    }
}