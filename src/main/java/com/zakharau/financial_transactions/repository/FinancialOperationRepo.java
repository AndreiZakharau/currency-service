package com.zakharau.financial_transactions.repository;

import com.zakharau.financial_transactions.entity.FinancialOperation;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FinancialOperationRepo extends JpaRepository<FinancialOperation, UUID> {

  List<FinancialOperation> findAll();

  Optional<FinancialOperation> getFinancialOperationById(UUID id);

  List<FinancialOperation> findAllByActivityDateBetween(LocalDateTime startDate, LocalDateTime finishDate);

}
