package com.zakharau.financial_transactions.controller.currency;

import com.zakharau.financial_transactions.model.CurrencyRate;
import java.time.LocalDate;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

@RequestMapping("/currencyRate")
public interface CurrencyApi {

  @GetMapping()
  @ResponseStatus(HttpStatus.OK)
  List<CurrencyRate> getCurrencyRate(
      @RequestParam(value = "currencies", defaultValue = "USD, EUR",
          required = false) List<String> currencies,
      @DateTimeFormat(pattern = "dd-MM-yyyy") @RequestParam(value = "date",
           required = false) LocalDate date);
}
