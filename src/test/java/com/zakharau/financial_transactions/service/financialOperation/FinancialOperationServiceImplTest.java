package com.zakharau.financial_transactions.service.financialOperation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class FinancialOperationServiceImplTest {

  @Mock
  private FinancialOperationRepo repo;

  @Mock
  private FinancialOperationMapper mapper;

  @Mock
  private CurrencyRateService currencyRateService;

  @InjectMocks
  private FinancialOperationServiceImpl service;

  private List<FinancialOperation> operationList;
  private List<FinancialOperationModel> operationModelList;
  private CurrencyRate currencyRate;
  private FinancialOperation financialOperation;

  @BeforeEach
  public void setUp() {
    currencyRate = new CurrencyRate("840", "USD", "1", "Доллар США", "76,4479");
    financialOperation = new FinancialOperation(UUID.randomUUID(),
        "transfer to card",
        (BigDecimal.valueOf(3300)), LocalDateTime.now());
    operationList = List.of(financialOperation);
    operationModelList = List.of(new FinancialOperationModel());
  }

  @Test
  void save_ShouldReturnFinancialOperationModel_WhenModelIsValid() {

    CreateFinancialOperationModel model = new CreateFinancialOperationModel();
    FinancialOperation financialOperation = new FinancialOperation();
    FinancialOperationModel expectedModel = new FinancialOperationModel();

    when(mapper.fromCreateFinancialOperationModel(model)).thenReturn(financialOperation);
    when(repo.save(financialOperation)).thenReturn(financialOperation);
    when(mapper.fromFinancialOperation(financialOperation)).thenReturn(expectedModel);

    FinancialOperationModel result = service.save(model);

    assertEquals(expectedModel, result);
    verify(mapper).fromCreateFinancialOperationModel(model);
    verify(repo).save(financialOperation);
    verify(mapper).fromFinancialOperation(financialOperation);
  }

  @Test
  void getAll_ShouldReturnListOfFinancialOperationModels_WhenOperationsExist() {

    UUID id = UUID.randomUUID();
    List<FinancialOperation> operationList = new ArrayList<>();
    operationList.add(new FinancialOperation(id, "transfer to card", (BigDecimal.valueOf(3300)),
        LocalDateTime.now()));
    operationList.add(new FinancialOperation(id, "transfer to check", (BigDecimal.valueOf(3300)),
        LocalDateTime.now()));

    when(repo.findAll()).thenReturn(operationList);
    when(mapper.fromFinancialOperation(operationList.get(0))).thenReturn(
        new FinancialOperationModel());
    when(mapper.fromFinancialOperation(operationList.get(1))).thenReturn(
        new FinancialOperationModel());

    List<FinancialOperationModel> result = service.getAll();

    assertEquals(2, result.size());
    verify(repo).findAll();
    verify(mapper).fromFinancialOperation(operationList.get(0));
    verify(mapper).fromFinancialOperation(operationList.get(1));
  }

  @Test
  void update_ShouldReturnFinancialOperationModel_WhenModelExists() {

    UUID id = UUID.randomUUID();
    LocalDateTime activityDate = LocalDateTime.now();
    FinancialOperation financialOperation = new FinancialOperation(id, "transfer to card",
        (BigDecimal.valueOf(3300)), activityDate);
    FinancialOperationModel model = new FinancialOperationModel(id, "transfer to check",
        (BigDecimal.valueOf(3300)), activityDate);
    FinancialOperationModel expectedModel = new FinancialOperationModel(id, "transfer to check",
        (BigDecimal.valueOf(3300)), activityDate);

    when(mapper.fromFinancialOperationModel(model)).thenReturn(financialOperation);
    when(service.getFinancialOperationById(model.getId())).thenReturn(
        Optional.of(financialOperation));
    when(repo.saveAndFlush(financialOperation)).thenReturn(financialOperation);
    when(mapper.fromFinancialOperation(financialOperation)).thenReturn(expectedModel);

    FinancialOperationModel result = service.update(model);

    assertEquals(expectedModel, result);
    verify(mapper).fromFinancialOperationModel(model);
    verify(repo).saveAndFlush(financialOperation);
    verify(mapper).fromFinancialOperation(financialOperation);
  }

  @Test
  void delete_ShouldThrowEntityNotFoundException_WhenFinancialOperationDoesNotExist() {

    UUID id = UUID.randomUUID();
    String message = String.format("Financial operation with id %s does not exist", id);

    when(repo.getFinancialOperationById(id)).thenReturn(Optional.empty());

    assertThrows(EntityNotFoundException.class, () -> service.delete(id), message);
    verify(repo, never()).delete(any());
  }

  @Test
  void delete_ShouldDeleteFinancialOperation_WhenFinancialOperationExists() {

    UUID id = UUID.randomUUID();

    when(repo.getFinancialOperationById(id)).thenReturn(Optional.of(financialOperation));

    service.delete(id);

    verify(repo).delete(financialOperation);
  }

  @Test
  void getFinancialOperationById_ShouldReturnOptionalFinancialOperation_WhenFinancialOperationExists() {

    UUID id = UUID.randomUUID();

    when(repo.getFinancialOperationById(id)).thenReturn(Optional.of(financialOperation));

    Optional<FinancialOperation> result = service.getFinancialOperationById(id);

    assertTrue(result.isPresent());
    assertEquals(financialOperation, result.get());
    verify(repo).getFinancialOperationById(id);
  }

  @Test
  void getFinancialOperationById_ShouldReturnEmptyOptional_WhenFinancialOperationDoesNotExist() {

    UUID id = UUID.randomUUID();

    when(repo.getFinancialOperationById(id)).thenReturn(Optional.empty());

    Optional<FinancialOperation> result = service.getFinancialOperationById(id);

    assertFalse(result.isPresent());
    verify(repo).getFinancialOperationById(id);
  }

  @Test
  public void testGetOperationListByTimeInterval() {

    when(repo.findAllByActivityDateBetween(any(), any())).thenReturn(operationList);
    when(mapper.fromFinancialOperation(any())).thenReturn(new FinancialOperationModel());

    List<FinancialOperationModel> result = service.getOperationListByTimeInterval(
        LocalDate.now(), LocalDate.now());

    assertEquals(operationModelList, result);

    verify(repo, times(1)).findAllByActivityDateBetween(any(), any());
    verify(mapper, times(operationList.size())).fromFinancialOperation(any());
  }

  @Test
  public void testOperationInCurrencyList() {

    UUID id = UUID.randomUUID();
    FinancialOperationModel operationModel = new FinancialOperationModel(id, "transfer to card",
        (BigDecimal.valueOf(3300)), LocalDateTime.now());

    when(repo.findAllByActivityDateBetween(any(), any())).thenReturn(operationList);
    when(currencyRateService.getCurrencyRate(anyList(), any())).thenReturn(List.of(currencyRate));
    when(mapper.fromFinancialOperation(any())).thenReturn(operationModel);

    List<FinancialOperationInCurrency> result = service.operationInCurrencyList(
        "USD", LocalDate.now(), LocalDate.now());

    assertNotNull(result);
    assertFalse(result.isEmpty());

    verify(currencyRateService, times(1)).getCurrencyRate(anyList(), any());
    verify(mapper, times(operationModelList.size())).fromFinancialOperation(any());
  }

  @Test
  public void testGetOperationListByTimeIntervalWithNullDates() {

    when(repo.findAllByActivityDateBetween(any(), any())).thenReturn(operationList);
    when(mapper.fromFinancialOperation(any())).thenReturn(new FinancialOperationModel());

    List<FinancialOperationModel> result = service.getOperationListByTimeInterval(null, null);

    assertEquals(operationModelList, result);

    verify(repo, times(1)).findAllByActivityDateBetween(any(), any());
    verify(mapper, times(operationList.size())).fromFinancialOperation(any());
  }

  @Test
  public void testOperationInCurrencyListWithNullDates() {

    UUID id = UUID.randomUUID();
    FinancialOperationModel operationModel = new FinancialOperationModel(id, "transfer to card",
        (BigDecimal.valueOf(3300)), LocalDateTime.now());

    when(repo.findAllByActivityDateBetween(any(), any())).thenReturn(operationList);
    when(currencyRateService.getCurrencyRate(anyList(), any())).thenReturn(List.of(currencyRate));
    when(mapper.fromFinancialOperation(any())).thenReturn(operationModel);

    List<FinancialOperationInCurrency> result = service.operationInCurrencyList(
        "USD", null, null);

    assertNotNull(result);
    assertFalse(result.isEmpty());

    verify(currencyRateService, times(1)).getCurrencyRate(anyList(), any());
    verify(mapper, times(operationModelList.size())).fromFinancialOperation(any());
  }

  @Test
  public void testOperationInCurrencyListWithFinishDateBeforeStartDate() {

    UUID id = UUID.randomUUID();
    FinancialOperationModel operationModel = new FinancialOperationModel(id, "transfer to card",
        (BigDecimal.valueOf(3300)), LocalDateTime.now());

    when(repo.findAllByActivityDateBetween(any(), any())).thenReturn(operationList);
    when(currencyRateService.getCurrencyRate(anyList(), any())).thenReturn(List.of(currencyRate));
    when(mapper.fromFinancialOperation(any())).thenReturn(operationModel);

    List<FinancialOperationInCurrency> result = service.operationInCurrencyList(
        "USD", LocalDate.now(), LocalDate.now().minusDays(1));

    assertNotNull(result);
    assertFalse(result.isEmpty());
    assertEquals(result.size(), 1);

    verify(currencyRateService, times(1)).getCurrencyRate(anyList(), any());
    verify(mapper, times(operationModelList.size())).fromFinancialOperation(any());
  }

}