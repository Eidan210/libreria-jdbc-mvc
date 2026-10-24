package org.example;

import org.example.controller.LibroController;
import org.example.dao.LibroDao;
import org.example.view.LibroView;

public class Main {
    public static void main(String[] args) {
        LibroController controller = new LibroController(new LibroView(), new LibroDao());

        controller.registrar("Noches Blancas", "Fiódor Dostoyevski", 20.99);
        controller.registrar("Habitos Atomicos", "James Clear", 29.99);

        controller.actualizar(1, "Noches Blancas", "Fiódor Dostoyevski", 25.00); // existe
        controller.actualizar(99, "Fantasma", "Nadie", 10.00); // no existe
        controller.actualizar(1, "Noches Blancas", "", 25.00); // autor vacío
        controller.eliminar(2); // existe
        controller.eliminar(99); // no existe
        controller.eliminar(0); // id inválido
        controller.listar();

        controller.buscarPorAutor("Fiódor Dostoyevski");
    }
}