package com.zakharau.financial_transactions.controller;

import com.zakharau.financial_transactions.model.CurrencyRate;
import com.zakharau.financial_transactions.service.currencyRate.CurrencyRateService;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
class CurrencyController {

  public final CurrencyRateService currencyRateService;

  @GetMapping("/currencyRate/{currency}/{date}")
  public CurrencyRate getCurrencyRate(@PathVariable("currency") String currency,
      @DateTimeFormat(pattern = "dd-MM-yyyy") @PathVariable("date") LocalDate date)  {

    return currencyRateService.getCurrencyRate(currency, date);
  }

}

