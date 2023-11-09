package com.zakharau.financial_transactions.service.currencyRate;

import com.zakharau.financial_transactions.model.CurrencyRate;
import java.time.LocalDate;
import java.util.List;

public interface CurrencyRateService {

  List<CurrencyRate> getCurrencyRate(List<String> currencies, LocalDate date);
}
