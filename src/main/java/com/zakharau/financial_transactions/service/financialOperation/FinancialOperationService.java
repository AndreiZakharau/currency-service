package com.zakharau.financial_transactions.service.financialOperation;

import com.zakharau.financial_transactions.entity.FinancialOperation;
import com.zakharau.financial_transactions.model.CreateFinancialOperationModel;
import com.zakharau.financial_transactions.model.FinancialOperationModel;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;

public interface FinancialOperationService extends CRUDService<FinancialOperationModel, CreateFinancialOperationModel> {

  @Transactional(readOnly = true)
  Optional<FinancialOperation> getFinancialOperationById(long id);
}
