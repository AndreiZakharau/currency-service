package com.zakharau.financial_transactions.service.parser;

import com.zakharau.financial_transactions.model.CurrencyRate;
import java.util.List;

public interface CurrencyRateParser {

  List<CurrencyRate> parse(String ratesAsString);
}
