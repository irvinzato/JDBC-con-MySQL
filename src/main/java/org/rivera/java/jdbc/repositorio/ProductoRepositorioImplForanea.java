package org.rivera.java.jdbc.repositorio;

import org.rivera.java.jdbc.models.Categoria;
import org.rivera.java.jdbc.models.Producto;
import org.rivera.java.jdbc.util.ConexionBaseDatos;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoRepositorioImplForanea implements Repositorio<Producto>{
  private Connection getConnection() throws SQLException {
    return ConexionBaseDatos.getInstance();
  }

  //Al crear el producto debo tener la relación de las nuevas columnas del otro objeto
  private Producto createProduct(ResultSet rs) throws SQLException {
    Producto p = new Producto();
    p.setId( rs.getLong("id") );
    p.setName( rs.getString("nombre") );
    p.setPrice( rs.getInt("precio") );
    p.setRegisterDate( rs.getDate("fecha_registro") );
    Categoria category = new Categoria();
    category.setId( rs.getLong("categoria_id") );
    category.setName( rs.getString("categoria") ); //Es la columna "nombre de categoría" pero le di alias "categoria" al hacer la consulta
    p.setCategoria(category);
    return p;
  }
  //CRUD COMPLETO CON LLAVE FORÁNEA
  @Override
  public List<Producto> findAll() {
    List<Producto> products = new ArrayList<>();
    try( Statement stmt = getConnection().createStatement();
         ResultSet rs = stmt.executeQuery("SELECT p.*, c.nombre AS categoria FROM productos AS p " +
                 "INNER JOIN categorias AS c ON(p.categoria_id = c.id)")) {
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
    try(PreparedStatement stmt = getConnection().prepareStatement("SELECT p.*, c.nombre AS categoria FROM productos AS p " +
                                  "INNER JOIN categorias AS c ON(p.categoria_id = c.id) WHERE p.id = ?" )) { //Debo ser explícito con la tabla y columna que me refiero
      stmt.setLong(1, id);
      try (ResultSet rs = stmt.executeQuery()) {
        if (rs.next()) {
          product = createProduct(rs);
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return product;
  }

  @Override
  public void save(Producto producto) {
    String query;
    if ( producto.getId() != null && producto.getId() > 0 ) {
      query = "UPDATE productos SET nombre=?, precio=?, categoria_id=? WHERE id=?";
    } else {
      query = "INSERT INTO productos(nombre, precio, categoria_id, fecha_registro) VALUES(?, ?, ?, ?)";
    }
    try( PreparedStatement stmt = getConnection().prepareStatement(query) ) {
      stmt.setString(1, producto.getName());
      stmt.setLong(2, producto.getPrice());
      stmt.setLong(3, producto.getCategoria().getId());
      if ( producto.getId() != null && producto.getId() > 0 ) {
        stmt.setLong(4, producto.getId());
      } else {
        stmt.setDate(4, new Date(producto.getRegisterDate().getTime()));
      }
      stmt.executeUpdate();
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
