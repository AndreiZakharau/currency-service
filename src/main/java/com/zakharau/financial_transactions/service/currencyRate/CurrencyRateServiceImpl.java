package com.zakharau.financial_transactions.service.currencyRate;

import com.zakharau.financial_transactions.config.CbrConfig;
import com.zakharau.financial_transactions.model.CurrencyRate;
import com.zakharau.financial_transactions.service.parser.CurrencyRateParser;
import com.zakharau.financial_transactions.service.request.CbrRequest;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrencyRateServiceImpl implements CurrencyRateService {

  public static final String DATE_FORMAT = "dd/MM/yyyy";
  private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);
  private final CbrRequest cbrRequest;
  private final CurrencyRateParser currencyRateParser;
  private final CbrConfig cbrConfig;

  @Override
  public List<CurrencyRate> getCurrencyRate(List<String> currencies, LocalDate date) {

    if (date == null) {
      date = LocalDate.now();
    }

    String urlWithParams = String.format("%s?date_req=%s", cbrConfig.getUrl(),
        DATE_FORMATTER.format(date));
    String ratesAsXml = cbrRequest.getRatesAsXml(urlWithParams);
    List<CurrencyRate> rates = currencyRateParser.parse(ratesAsXml);

    return rates.stream()
        .filter(rate -> currencies.contains(rate.getCharCode()))
        .collect(Collectors.toList());
  }
}
