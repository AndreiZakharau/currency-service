package com.zakharau.financial_transactions.controller.financialOperation;

import com.zakharau.financial_transactions.entity.FinancialOperation;
import com.zakharau.financial_transactions.model.CreateFinancialOperationModel;
import com.zakharau.financial_transactions.model.FinancialOperationInCurrency;
import com.zakharau.financial_transactions.model.FinancialOperationModel;
import com.zakharau.financial_transactions.service.financialOperation.FinancialOperationService;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FinancialOperationController implements FinancialOperationApi {

  private final FinancialOperationService service;

  @Override
  public FinancialOperationModel save(CreateFinancialOperationModel operationModel) {

    return service.save(operationModel);
  }

  @Override
  public List<FinancialOperationModel> getAllOperation() {

    return service.getAll();
  }

  @Override
  public FinancialOperationModel updateOperation(FinancialOperationModel model) {

    return service.update(model);
  }

  @Override
  public void deletedOperation(UUID id) {

    service.delete(id);
  }

  @Override
  public Optional<FinancialOperation> getOperationById(UUID id) {
    return service.getFinancialOperationById(id);
  }

  @Override
  public List<FinancialOperationInCurrency> getAllOperationByCurrency(String currency,
      LocalDate startDate, LocalDate finishDate) {
    return service.operationInCurrencyList(currency,startDate,finishDate);
  }
}
