package com.zakharau.financial_transactions.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateFinancialOperationModel {

  private String description;
  private BigDecimal sum;
  private LocalDateTime activityDate;
}
