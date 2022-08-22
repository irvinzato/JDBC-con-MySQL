package org.rivera.java.jdbc.repositorio;

import java.util.List;
//Generic para cualquier objeto
public interface Repositorio<T> {
  List<T> findAll();
  T byId(Long id);
  void save(T t);
  void delete(Long id);

}
