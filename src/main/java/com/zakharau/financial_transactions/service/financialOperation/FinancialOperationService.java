package com.zakharau.financial_transactions.service.financialOperation;

import com.zakharau.financial_transactions.entity.FinancialOperation;
import com.zakharau.financial_transactions.model.CreateFinancialOperationModel;
import com.zakharau.financial_transactions.model.FinancialOperationInCurrency;
import com.zakharau.financial_transactions.model.FinancialOperationModel;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FinancialOperationService extends
    EntityService<FinancialOperationModel, CreateFinancialOperationModel> {

  Optional<FinancialOperation> getFinancialOperationById(UUID id);

  List<FinancialOperationModel> getOperationListByTimeInterval(LocalDate startDate,
      LocalDate finishDate);

  List<FinancialOperationInCurrency> operationInCurrencyList(String currency,
      LocalDate startDate,
      LocalDate finishDate);
}
