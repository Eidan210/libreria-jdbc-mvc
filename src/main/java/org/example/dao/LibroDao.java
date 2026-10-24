package org.example.dao;

import org.example.model.Libro;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// DAO: única clase que accede a la base de datos. No imprime nada.
// Las SQLException se declaran con throws para que las maneje el controlador.

public class LibroDao {
    private static final String URL = "jdbc:mysql://localhost:3306/libreria";
    private static final String USER = "root";
    private static final String PASS = System.getenv().getOrDefault("DB_PASS", "cambiar");

    private Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL,USER,PASS);
    }

    public int insertar(Libro libro) throws SQLException {
        String sql = "INSERT INTO libro (titulo, autor, precio) VALUES (?, ?, ?)";

        try(Connection con = conectar();
            PreparedStatement ps = con.prepareStatement(sql)){

            ps.setString(1, libro.titulo());
            ps.setString(2, libro.autor());
            ps.setDouble(3, libro.precio());

            return ps.executeUpdate();
        }

    }

    public List<Libro> listar() throws SQLException{
        String sql = "SELECT  id, titulo, autor, precio FROM libro ORDER BY id";

        List<Libro> libros = new ArrayList<>();

        try(Connection con = conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()){

            while(rs.next()){
                Libro libro = new Libro(
                    rs.getInt("id"),
                    rs.getString("titulo"),
                    rs.getString("autor"),
                    rs.getDouble("precio"));

                libros.add(libro);
            }
        }
        return libros;
    }

    public List<Libro> buscarPorAutor(String autor) throws SQLException{
        String sql = "SELECT id, titulo, autor, precio FROM libro WHERE autor = ? ORDER BY id";
        List<Libro> libros = new ArrayList<>();

        try(Connection con = conectar();
            PreparedStatement ps = con.prepareStatement(sql)){

            ps.setString(1, autor);

            try(ResultSet rs = ps.executeQuery()){
                while(rs.next()){
                    Libro libro = new Libro(
                        rs.getInt("id"),
                        rs.getString("titulo"),
                        rs.getString("autor"),
                        rs.getDouble("precio"));
                    libros.add(libro);
                }
            }
        }
        return libros;
    }
}
