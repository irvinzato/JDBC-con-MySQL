package org.rivera.java.jdbc.repositorio;

import org.rivera.java.jdbc.models.Producto;
import org.rivera.java.jdbc.util.ConexionBaseDatos;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductoRepositorioImpl implements Repositorio<Producto>{
  //Método privado para establecer la conexión a mi BD
  private Connection getConnection() throws SQLException {
    return ConexionBaseDatos.getInstance();
  }

  @Override
  public List<Producto> findAll() {
    List<Producto> products = new ArrayList<>();
    try( Statement stmt = getConnection().createStatement();
         ResultSet rs = stmt.executeQuery("SELECT * FROM productos")) {
      while( rs.next() ) {
        Producto p = new Producto();
        p.setId( rs.getLong("id") );
        p.setName( rs.getString("nombre") );
        p.setPrice( rs.getInt("precio") );
        p.setRegisterDate( rs.getDate("fecha_registro") );
        products.add(p);
      }
    }catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return products;
  }

  @Override
  public Producto byId(Long id) {
    return null;
  }

  @Override
  public void save(Producto producto) {

  }

  @Override
  public void delete(Long id) {

  }
}
