package com.zakharau.financial_transactions.service.financialOperation;

import java.util.List;

public interface CRUDService <T, K>{

  T save(K k);
  List<T> getAll();
  T update(T t);
  void delete(long id);

}
