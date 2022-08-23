package org.rivera.java.jdbc.clacesprincipalesconforaneas;

import org.rivera.java.jdbc.models.Categoria;
import org.rivera.java.jdbc.models.Producto;
import org.rivera.java.jdbc.repositorio.ProductoRepositorioImplForanea;
import org.rivera.java.jdbc.repositorio.Repositorio;
import org.rivera.java.jdbc.util.ConexionBaseDatos;

import java.sql.Connection;
import java.sql.SQLException;


public class JdbcCrudUpdateForaneaEjemplo {
  public static void main(String[] args) {

    try (Connection conn = ConexionBaseDatos.getInstance()) {
      System.out.println("---- Búsqueda con todos los atributos de la tabla ----");
      Repositorio<Producto> repository = new ProductoRepositorioImplForanea();
      repository.findAll().forEach(System.out::println);

      System.out.println("---- Actualizar producto ----");
      Producto producto = new Producto();
      producto.setName("CPU Yeiyan Mod Bas");
      producto.setPrice(10000);
      producto.setId(5L);
      Categoria category = new Categoria();
      category.setId(2L);
      producto.setCategoria(category);

      repository.save(producto);
      System.out.println("Actualización de producto exitosa");
      System.out.println( repository.byId(producto.getId()) );

      System.out.println("El borrar queda igual porque solo utiliza el ID");

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

  }
}
