package org.example.view;

import org.example.model.Libro;

import java.util.List;

public class LibroView {
    public void mensaje(String texto){
        System.out.println(texto);
    }

    public void mostrar(List<Libro> libros){
        if(libros.isEmpty()){
            System.out.println("No hay libros para mostrar");
            return;
        }
        System.out.println("--- Libros ---");
        for(Libro l : libros){
            System.out.printf("%d. %s - %s - $%.2f%n", l.id(), l.titulo(), l.autor(), l.precio());
        }

    }
}
