package org.rivera.java.jdbc;

import org.rivera.java.jdbc.models.Producto;
import org.rivera.java.jdbc.repositorio.ProductoRepositorioImpl;
import org.rivera.java.jdbc.repositorio.Repositorio;
import org.rivera.java.jdbc.util.ConexionBaseDatos;

import java.sql.Connection;
import java.sql.SQLException;


public class JdbcCrudEjemplo {
  public static void main(String[] args) {

    try (Connection conn = ConexionBaseDatos.getInstance()) {
      Repositorio<Producto> repository = new ProductoRepositorioImpl();
      repository.findAll().forEach(System.out::println);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

  }
}
