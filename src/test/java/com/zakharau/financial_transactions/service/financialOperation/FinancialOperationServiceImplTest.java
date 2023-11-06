package com.zakharau.financial_transactions.service.financialOperation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.zakharau.financial_transactions.entity.FinancialOperation;
import com.zakharau.financial_transactions.model.CreateFinancialOperationModel;
import com.zakharau.financial_transactions.model.FinancialOperationModel;
import com.zakharau.financial_transactions.repository.FinancialOperationRepo;
import com.zakharau.financial_transactions.util.FinancialOperationMapper;
import jakarta.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

  @InjectMocks
  private FinancialOperationServiceImpl service;

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

    List<FinancialOperation> operationList = new ArrayList<>();
    operationList.add(new FinancialOperation(1L,"transfer to card", (BigDecimal.valueOf(3300)),LocalDateTime.now()));
    operationList.add(new FinancialOperation(2L,"transfer to check", (BigDecimal.valueOf(3300)),LocalDateTime.now()));

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

    long id = 1;
    LocalDateTime activityDate = LocalDateTime.now();
    FinancialOperation financialOperation = new FinancialOperation(id, "transfer to card", (BigDecimal.valueOf(3300)),activityDate );
    FinancialOperationModel model = new FinancialOperationModel(id, "transfer to check", (BigDecimal.valueOf(3300)),activityDate);
    FinancialOperationModel expectedModel = new FinancialOperationModel(id, "transfer to check", (BigDecimal.valueOf(3300)),activityDate);

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

    long id = 1;
    String message = String.format("Financial operation with id %s does not exist", id);

    when(repo.getFinancialOperationById(id)).thenReturn(Optional.empty());

    assertThrows(EntityNotFoundException.class, () -> service.delete(id), message);
    verify(repo, never()).delete(any());
  }

  @Test
  void delete_ShouldDeleteFinancialOperation_WhenFinancialOperationExists() {

    long id = 1;
    FinancialOperation financialOperation = new FinancialOperation();

    when(repo.getFinancialOperationById(id)).thenReturn(Optional.of(financialOperation));

    service.delete(id);

    verify(repo).delete(financialOperation);
  }

  @Test
  void getFinancialOperationById_ShouldReturnOptionalFinancialOperation_WhenFinancialOperationExists() {

    long id = 1;
    FinancialOperation financialOperation = new FinancialOperation();

    when(repo.getFinancialOperationById(id)).thenReturn(Optional.of(financialOperation));

    Optional<FinancialOperation> result = service.getFinancialOperationById(id);

    assertTrue(result.isPresent());
    assertEquals(financialOperation, result.get());
    verify(repo).getFinancialOperationById(id);
  }

  @Test
  void getFinancialOperationById_ShouldReturnEmptyOptional_WhenFinancialOperationDoesNotExist() {

    long id = 1;

    when(repo.getFinancialOperationById(id)).thenReturn(Optional.empty());

    Optional<FinancialOperation> result = service.getFinancialOperationById(id);

    assertFalse(result.isPresent());
    verify(repo).getFinancialOperationById(id);
  }

}