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
      try (ResultSet rs = stmt.executeQuery()) {  //Para tener el auto close
        if (rs.next()) {
          product = createProduct(rs);
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return product;
  }

  @Override //Ocupo el método guardar para crear y actualizar
  public void save(Producto producto) {
    String query;
    if ( producto.getId() != null && producto.getId() > 0 ) {
      query = "UPDATE productos SET nombre=?, precio=? WHERE id=?";
    } else {
      query = "INSERT INTO productos(nombre, precio, fecha_registro) VALUES(?, ?, ?)";
    }
    try( PreparedStatement stmt = getConnection().prepareStatement(query) ) {
      stmt.setString(1, producto.getName());
      stmt.setLong(2, producto.getPrice());
      if ( producto.getId() != null && producto.getId() > 0 ) {
        stmt.setLong(3, producto.getId());
      } else {
        stmt.setDate(3, new Date(producto.getRegisterDate().getTime())); //Una fecha "util" no es una "sql" pero si al reves, por eso se debe hacer un cast
      }
      stmt.executeUpdate(); //Porque es una actualización
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void delete(Long id) {
    try( PreparedStatement stmt = getConnection().prepareStatement("DELETE FROM productos WHERE id=?") ) {
      stmt.setLong(1, id);
      stmt.executeUpdate();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}
