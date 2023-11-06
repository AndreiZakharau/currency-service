package com.zakharau.financial_transactions.controller.currency;

import com.zakharau.financial_transactions.model.CurrencyRate;
import com.zakharau.financial_transactions.service.currencyRate.CurrencyRateService;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
class CurrencyController implements CurrencyApi {

  public final CurrencyRateService currencyRateService;

  @Override
  public List<CurrencyRate> getCurrencyRate(List<String> currencies, LocalDate date)  {

    return currencyRateService.getCurrencyRate(currencies, date);
  }

}

