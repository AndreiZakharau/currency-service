package com.zakharau.financial_transactions.repository;

import com.zakharau.financial_transactions.entity.FinancialOperation;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FinancialOperationRepo extends JpaRepository<FinancialOperation, Long> {

  List<FinancialOperation> findAll();

  Optional<FinancialOperation> getFinancialOperationById(long id);

}
