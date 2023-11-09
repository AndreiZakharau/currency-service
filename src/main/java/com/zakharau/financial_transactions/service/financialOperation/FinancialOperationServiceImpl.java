package com.zakharau.financial_transactions.service.financialOperation;

import com.zakharau.financial_transactions.entity.FinancialOperation;
import com.zakharau.financial_transactions.model.CreateFinancialOperationModel;
import com.zakharau.financial_transactions.model.CurrencyRate;
import com.zakharau.financial_transactions.model.FinancialOperationInCurrency;
import com.zakharau.financial_transactions.model.FinancialOperationModel;
import com.zakharau.financial_transactions.repository.FinancialOperationRepo;
import com.zakharau.financial_transactions.service.currencyRate.CurrencyRateService;
import com.zakharau.financial_transactions.util.FinancialOperationMapper;
import jakarta.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FinancialOperationServiceImpl implements FinancialOperationService {

  private final FinancialOperationRepo repo;
  private final FinancialOperationMapper mapper;
  private final CurrencyRateService currencyRateService;

  @Transactional(propagation = Propagation.SUPPORTS)
  @Override
  public FinancialOperationModel save(CreateFinancialOperationModel model) {

    FinancialOperation financialOperation = mapper.fromCreateFinancialOperationModel(model);
    financialOperation.setActivityDate(LocalDateTime.now());
    financialOperation.setId(UUID.randomUUID());
    return mapper.fromFinancialOperation(repo.save(financialOperation));
  }

  @Transactional(readOnly = true)
  @Override
  public List<FinancialOperationModel> getAll() {

    List<FinancialOperation> operationList = repo.findAll();
    return operationList.stream().map(mapper::fromFinancialOperation).toList();
  }

  @Transactional
  @Override
  public FinancialOperationModel update(FinancialOperationModel model) {

    if (model.getActivityDate() == null) {
      model.setActivityDate(LocalDateTime.now());
    }
    FinancialOperation financialOperation = mapper.fromFinancialOperationModel(model);
    if (getFinancialOperationById(model.getId()).isPresent()) {

      return mapper.fromFinancialOperation(repo.saveAndFlush(financialOperation));
    } else {
      throw new EntityNotFoundException("This financial operation does not exist");
    }

  }

  @Transactional
  @Override
  public void delete(UUID id) {

    Optional<FinancialOperation> financialOperation = getFinancialOperationById(id);
    if (financialOperation.isPresent()) {
      repo.delete(financialOperation.get());
    } else {
      throw new EntityNotFoundException(
          String.format("Financial operation with id %s does not exist", id));
    }
  }

  @Transactional(readOnly = true)
  @Override
  public Optional<FinancialOperation> getFinancialOperationById(UUID id) {

    return repo.getFinancialOperationById(id);
  }

  @Transactional(readOnly = true)
  @Override
  public List<FinancialOperationModel> getOperationListByTimeInterval(LocalDate startDate,
      LocalDate finishDate) {

    startDate = startDate == null ? LocalDate.now() : startDate;
    finishDate = finishDate == null ? LocalDate.now() : finishDate;

    List<FinancialOperation> operationList = repo.findAllByActivityDateBetween(
        startDate.atStartOfDay(),
        finishDate.atStartOfDay());
    return operationList.stream().map(mapper::fromFinancialOperation).toList();
  }

  @Override
  public List<FinancialOperationInCurrency> operationInCurrencyList(String currency,
      LocalDate startDate,
      LocalDate finishDate) {

    startDate = startDate == null ? LocalDate.now() : startDate;
    finishDate = finishDate == null ? LocalDate.now().plusDays(1) : finishDate.plusDays(1);

    if (finishDate.isBefore(startDate)) {
      LocalDate temp = finishDate;
      finishDate = startDate;
      startDate = temp;
    }

    List<FinancialOperationModel> modelList = getOperationListByTimeInterval(startDate, finishDate);
    CurrencyRate currencyRate = currencyRateService.getCurrencyRate(List.of(currency),
        LocalDate.from(LocalDateTime.now())).get(0);
    BigDecimal decimalValue = new BigDecimal(currencyRate.getValue().replace(",", "."));

    return modelList.stream()
        .map(operation -> {
          FinancialOperationInCurrency convertedOperation = new FinancialOperationInCurrency();
          convertedOperation.setCharCode(currencyRate.getCharCode());
          convertedOperation.setRate(currencyRate.getValue());
          convertedOperation.setName(currencyRate.getName());
          convertedOperation.setFinancialOperationModel(operation);
          convertedOperation.setSumInRate(
              operation.getSum().divide(decimalValue, RoundingMode.FLOOR));
          return convertedOperation;
        })
        .collect(Collectors.toList());

  }
}
