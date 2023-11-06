package com.zakharau.financial_transactions.util;

import com.zakharau.financial_transactions.entity.FinancialOperation;
import com.zakharau.financial_transactions.model.CreateFinancialOperationModel;
import com.zakharau.financial_transactions.model.FinancialOperationModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FinancialOperationMapper {

  FinancialOperation fromFinancialOperationModel(FinancialOperationModel model);

  FinancialOperationModel fromFinancialOperation(FinancialOperation operation);

  FinancialOperation fromCreateFinancialOperationModel(CreateFinancialOperationModel createModel);

}
