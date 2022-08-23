package org.rivera.java.jdbc.repositorio;

import org.rivera.java.jdbc.models.Producto;
import org.rivera.java.jdbc.util.ConexionBaseDatos;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoRepositorioImpl implements Repositorio<Producto>{
  //Método privado para establecer la conexión a mi BD
  private Connection getConnection() throws SQLException {
    return ConexionBaseDatos.getInstance();
  }

  private Producto createProduct(ResultSet rs) throws SQLException {
    Producto p = new Producto();
    p.setId( rs.getLong("id") );
    p.setName( rs.getString("nombre") );
    p.setPrice( rs.getInt("precio") );
    p.setRegisterDate( rs.getDate("fecha_registro") );
    return p;
  }

  @Override
  public List<Producto> findAll() {
    List<Producto> products = new ArrayList<>();
    try( Statement stmt = getConnection().createStatement();
         ResultSet rs = stmt.executeQuery("SELECT * FROM productos")) {
      while( rs.next() ) {
        Producto p = createProduct( rs );
        products.add(p);
      }
    }catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return products;
  }

  @Override
  public Producto byId(Long id) {
    Producto product = null;
    try(PreparedStatement stmt = getConnection().prepareStatement("SELECT * FROM productos WHERE id = ?")) { //PreparedStatement porque pueden variar los parámetros de la consulta
      stmt.setLong(1, id); //Asigno parámetro del ID
      ResultSet rs = stmt.executeQuery();
      if( rs.next() ) {
        product = createProduct( rs );
      }
      rs.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return product;
  }

  @Override
  public void save(Producto producto) {

  }

  @Override
  public void delete(Long id) {

  }
}
