package com.zakharau.financial_transactions.controller.financialOperation;

import com.zakharau.financial_transactions.entity.FinancialOperation;
import com.zakharau.financial_transactions.model.CreateFinancialOperationModel;
import com.zakharau.financial_transactions.model.FinancialOperationInCurrency;
import com.zakharau.financial_transactions.model.FinancialOperationModel;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

@RequestMapping("operation")
@Validated
public interface FinancialOperationApi {

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  FinancialOperationModel save(@Valid @RequestBody CreateFinancialOperationModel operationModel);

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  List<FinancialOperationModel> getAllOperation();

  @PutMapping
  @ResponseStatus(HttpStatus.CREATED)
  FinancialOperationModel updateOperation(@RequestBody FinancialOperationModel model);

  @DeleteMapping("{id}")
  @ResponseStatus(HttpStatus.OK)
  void deletedOperation(@PathVariable UUID id);

  @GetMapping("{id}")
  @ResponseStatus(HttpStatus.OK)
  Optional<FinancialOperation> getOperationById(@Valid @PathVariable UUID id);

  @GetMapping("currency")
  @ResponseStatus(HttpStatus.OK)
  List<FinancialOperationInCurrency> getAllOperationByCurrency(
      @RequestParam(value = "currency", defaultValue = "USD",
          required = false) String currency,
      @DateTimeFormat(pattern = "dd-MM-yyyy") @RequestParam(value = "startDate", required = false) LocalDate startDate,
      @DateTimeFormat(pattern = "dd-MM-yyyy") @RequestParam(value = "finishDate", required = false) LocalDate finishDate);

}
