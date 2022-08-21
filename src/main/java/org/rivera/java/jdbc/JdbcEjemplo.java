package org.rivera.java.jdbc;

import java.sql.*;

public class JdbcEjemplo {
  public static void main(String[] args) {

    String url = "jdbc:mysql://localhost:3306/java_curso";
    String username = "root";
    String password = "sasa";

    try {
      Connection conn = DriverManager.getConnection(url, username, password);
      Statement stmt = conn.createStatement();
      ResultSet result = stmt.executeQuery("SELECT * FROM productos");

      System.out.println("---- Resultados de consulta ----");
      while( result.next() ) {
        System.out.println("Resultado de id: " + result.getInt("id"));
        System.out.println("Resultado de nombre: " + result.getString("nombre"));
      }
      result.close();
      stmt.close();
      conn.close();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

}
