package org.rivera.java.jdbc;

import org.rivera.java.jdbc.models.Producto;
import org.rivera.java.jdbc.repositorio.ProductoRepositorioImpl;
import org.rivera.java.jdbc.repositorio.Repositorio;
import org.rivera.java.jdbc.util.ConexionBaseDatos;

import java.sql.Connection;
import java.sql.SQLException;


public class JdbcCrudDeleteEjemplo {
  public static void main(String[] args) {

    try (Connection conn = ConexionBaseDatos.getInstance()) {
      System.out.println("---- Búsqueda con todos los atributos de la tabla ----");
      Repositorio<Producto> repository = new ProductoRepositorioImpl();
      repository.findAll().forEach(System.out::println);

      System.out.println("---- Eliminar producto ----");
      Producto producto = new Producto();
      producto.setId(4L);
      repository.delete( producto.getId() );
      System.out.println("Producto eliminado con éxito");
      repository.findAll().forEach(System.out::println);

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

  }
}
