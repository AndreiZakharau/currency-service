package com.zakharau.financial_transactions.service.currencyRate;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.zakharau.financial_transactions.config.CbrConfig;
import com.zakharau.financial_transactions.model.CurrencyRate;
import com.zakharau.financial_transactions.service.parser.CurrencyRateParser;
import com.zakharau.financial_transactions.service.request.CbrRequest;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CurrencyRateServiceImplTest {

  private static final String DATE_FORMAT = "dd/MM/yyyy";
  private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);

  @Mock
  private CbrRequest cbrRequest;
  @Mock
  private CurrencyRateParser currencyRateParser;
  @Mock
  private CbrConfig cbrConfig;
  @InjectMocks
  private CurrencyRateServiceImpl currencyRateServiceImpl;


  @Test
  void testGetCurrencyRateWithDate() {

    LocalDate date = LocalDate.of(2023, 11, 6);
    String urlWithParams = "http://example.com?date_req=06/11/2023";
    String ratesAsXml = "<rates>...</rates>";
    List<CurrencyRate> expectedRates = Arrays.asList(
        new CurrencyRate("840", "USD", "1", "Доллар США", "76,4479"),
        new CurrencyRate("978", "EUR", "1", "Евро", "82,3923")
    );

    when(cbrConfig.getUrl()).thenReturn("http://example.com");
    when(cbrRequest.getRatesAsXml(urlWithParams)).thenReturn(ratesAsXml);
    when(currencyRateParser.parse(ratesAsXml)).thenReturn(expectedRates);

    List<CurrencyRate> actualRates = currencyRateServiceImpl.getCurrencyRate(
        Arrays.asList("USD", "EUR"), date);

    assertEquals(expectedRates, actualRates);
    verify(cbrRequest).getRatesAsXml(urlWithParams);
    verify(currencyRateParser).parse(ratesAsXml);
    verifyNoMoreInteractions(cbrRequest, currencyRateParser);
  }

  @Test
  void testGetCurrencyRateWithoutDate() {
    LocalDate currentDate = LocalDate.now();
    String urlWithParams = "http://example.com?date_req=" + DATE_FORMATTER.format(currentDate);
    String ratesAsXml = "<rates>...</rates>";
    List<CurrencyRate> expectedRates = Arrays.asList(
        new CurrencyRate("840", "USD", "1", "Доллар США", "76,4479"),
        new CurrencyRate("978", "EUR", "1", "Евро", "82,3923")
    );

    when(cbrConfig.getUrl()).thenReturn("http://example.com");
    when(cbrRequest.getRatesAsXml(urlWithParams)).thenReturn(ratesAsXml);
    when(currencyRateParser.parse(ratesAsXml)).thenReturn(expectedRates);

    List<CurrencyRate> actualRates = currencyRateServiceImpl.getCurrencyRate(
        Arrays.asList("USD", "EUR"), null);

    assertEquals(expectedRates, actualRates);
    verify(cbrRequest).getRatesAsXml(urlWithParams);
    verify(currencyRateParser).parse(ratesAsXml);
    verifyNoMoreInteractions(cbrRequest, currencyRateParser);
  }
}