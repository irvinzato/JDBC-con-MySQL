package org.rivera.java.jdbc;

import org.rivera.java.jdbc.models.Producto;
import org.rivera.java.jdbc.repositorio.ProductoRepositorioImpl;
import org.rivera.java.jdbc.repositorio.Repositorio;
import org.rivera.java.jdbc.util.ConexionBaseDatos;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;


public class JdbcCrudEjemplo {
  public static void main(String[] args) {

    try (Connection conn = ConexionBaseDatos.getInstance()) {
      System.out.println("---- Búsqueda con todos los atributos de la tabla ----");
      Repositorio<Producto> repository = new ProductoRepositorioImpl();
      repository.findAll().forEach(System.out::println);

      System.out.println("---- Búsqueda por ID ----");
      System.out.println(repository.byId(2L));

      System.out.println("---- Creación de nuevo producto ----");
      Producto product = new Producto();
      product.setName("CPU Yeiyan 2");
      product.setPrice(12000);
      product.setRegisterDate(new Date());
      repository.save(product);
      System.out.println("Producto guardado con éxito");
      repository.findAll().forEach(System.out::println);

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

  }
}
