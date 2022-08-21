package org.rivera.java.jdbc;

import java.sql.*;

public class JdbcEjemplo {
  public static void main(String[] args) {

    String url = "jdbc:mysql://localhost:3306/java_curso";
    String username = "root";
    String password = "sasa";
    //Poniendo las declaraciones enfrente del "try" evito hacer los "close" de cada uno
    try ( Connection conn = DriverManager.getConnection(url, username, password);
          Statement stmt = conn.createStatement();
          ResultSet result = stmt.executeQuery("SELECT * FROM productos") ) {

      System.out.println("---- Resultados de consulta ----");
      while( result.next() ) {
        System.out.print(result.getInt("id") + " | ");
        System.out.print(result.getString("nombre") + " | ");
        System.out.print(result.getInt("precio") + " | ");
        System.out.println("Resultado de fecha: " + result.getDate("fecha_registro"));
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

}
