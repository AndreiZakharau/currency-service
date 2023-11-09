package com.zakharau.financial_transactions.model;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Value;

@Builder
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FinancialOperationInCurrency {

  private String charCode;
  private String rate;
  private String name;
  private FinancialOperationModel financialOperationModel;
  private BigDecimal sumInRate;
}
