package org.rivera.java.jdbc.clacesprincipalesconforaneas;

import org.rivera.java.jdbc.models.Categoria;
import org.rivera.java.jdbc.models.Producto;
import org.rivera.java.jdbc.repositorio.ProductoRepositorioImplForanea;
import org.rivera.java.jdbc.repositorio.Repositorio;
import org.rivera.java.jdbc.util.ConexionBaseDatos;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;


public class JdbcCrudForaneaEjemplo {
  public static void main(String[] args) {

    try (Connection conn = ConexionBaseDatos.getInstance()) {
      System.out.println("---- Búsqueda con todos los atributos de la tabla ----");
      Repositorio<Producto> repository = new ProductoRepositorioImplForanea();
      repository.findAll().forEach(System.out::println);

      System.out.println("---- Búsqueda por ID ----");
      System.out.println(repository.byId(2L));

      System.out.println("---- Creación de nuevo producto ----");
      Producto product = new Producto();
      product.setName("CPU Yeiyan Red Dragon");
      product.setPrice(22000);
      product.setRegisterDate(new Date());
      Categoria category = new Categoria(); //Ahora esta asociado con objeto "Categoria", asi que lo ocupa
      category.setId(3L);
      product.setCategoria(category);

      repository.save(product);
      System.out.println("Producto guardado con éxito");
      repository.findAll().forEach(System.out::println);

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

  }
}
