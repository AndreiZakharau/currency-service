package com.zakharau.financial_transactions.service.financialOperation;

import java.util.List;
import java.util.UUID;

public interface EntityService<T, K>{

  T save(K k);
  List<T> getAll();
  T update(T t);
  void delete(UUID id);

}
