package com.zakharau.financial_transactions.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
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
public class CreateFinancialOperationModel {

  @NotBlank(message = "Description can not be empty or null")
  private String description;
  @NotNull(message = "Sum can not be null")
  private BigDecimal sum;
  private LocalDateTime activityDate;
}
