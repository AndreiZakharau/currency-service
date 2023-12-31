package com.zakharau.financial_transactions.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FinancialOperationModel {

  private UUID id;
  private String description;
  private BigDecimal sum;
  private LocalDateTime activityDate;
}
