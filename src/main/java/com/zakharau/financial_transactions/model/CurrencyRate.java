package com.zakharau.financial_transactions.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@AllArgsConstructor
@Builder
public class CurrencyRate {

  String numCode;
  String charCode;
  String nominal;
  String name;
  String value;
}
