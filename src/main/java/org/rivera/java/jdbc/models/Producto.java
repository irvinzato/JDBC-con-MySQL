package org.rivera.java.jdbc.models;

import java.util.Date;

public class Producto {

  private Long id;
  private String name;
  private Integer price;
  private Date registerDate;
  private Categoria categoria; //Relación entre objetos(Como lo diseñe en mis tablas SQL, llave foránea)

  public Producto() {
  }

  public Producto(Long id, String name, Integer price, Date registerDate) {
    this.id = id;
    this.name = name;
    this.price = price;
    this.registerDate = registerDate;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getPrice() {
    return price;
  }

  public void setPrice(Integer price) {
    this.price = price;
  }

  public Date getRegisterDate() {
    return registerDate;
  }

  public void setRegisterDate(Date registerDate) {
    this.registerDate = registerDate;
  }

  public Categoria getCategoria() {
    return categoria;
  }

  public void setCategoria(Categoria categoria) {
    this.categoria = categoria;
  }

  @Override
  public String toString() {
    String cat = (this.categoria == null) ?"No creada" : categoria.getName();
    return "id = " + id +
            ", name = " + name +
            ", price = " + price +
            ", registerDate = " + registerDate +
            ", category = " + cat;
  }
}
