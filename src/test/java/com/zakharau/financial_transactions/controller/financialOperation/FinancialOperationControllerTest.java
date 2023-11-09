package com.zakharau.financial_transactions.controller.financialOperation;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.zakharau.financial_transactions.entity.FinancialOperation;
import com.zakharau.financial_transactions.model.CreateFinancialOperationModel;
import com.zakharau.financial_transactions.model.FinancialOperationModel;
import com.zakharau.financial_transactions.service.financialOperation.FinancialOperationService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class FinancialOperationControllerTest {

  @Mock
  private FinancialOperationService service;

  @InjectMocks
  private FinancialOperationController controller;


  @Test
  public void testSave() {

    CreateFinancialOperationModel operationModel = mock(CreateFinancialOperationModel.class);
    FinancialOperationModel expectedModel = mock(FinancialOperationModel.class);

    when(service.save(operationModel)).thenReturn(expectedModel);

    FinancialOperationModel result = controller.save(operationModel);

    verify(service).save(operationModel);
    assertEquals(expectedModel, result);
  }

  @Test
  public void testGetAllOperation() {

    FinancialOperationModel model = mock(FinancialOperationModel.class);
    List<FinancialOperationModel> expectedList = new ArrayList<>();
    expectedList.add(model);

    when(service.getAll()).thenReturn(expectedList);

    List<FinancialOperationModel> resultList = controller.getAllOperation();

    verify(service).getAll();
    assertEquals(expectedList, resultList);
  }

  @Test
  public void testUpdateOperation() {

    FinancialOperationModel model = mock(FinancialOperationModel.class);

    when(service.update(model)).thenReturn(model);

    FinancialOperationModel result = controller.updateOperation(model);

    verify(service).update(model);
    assertEquals(model, result);
  }

  @Test
  public void testDeletedOperation() {

    UUID id = mock(UUID.class);

    controller.deletedOperation(id);

    verify(service).delete(id);
  }

  @Test
  public void testGetOperationById() {

    UUID id = mock(UUID.class);
    FinancialOperation operation = mock(FinancialOperation.class);
    Optional<FinancialOperation> expectedOptional = Optional.of(operation);

    when(service.getFinancialOperationById(id)).thenReturn(expectedOptional);

    Optional<FinancialOperation> resultOptional = controller.getOperationById(id);

    verify(service).getFinancialOperationById(id);
    assertEquals(expectedOptional, resultOptional);
  }

}