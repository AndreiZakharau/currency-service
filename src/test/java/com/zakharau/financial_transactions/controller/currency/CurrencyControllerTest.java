package com.zakharau.financial_transactions.controller.currency;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.zakharau.financial_transactions.model.CurrencyRate;
import com.zakharau.financial_transactions.service.currencyRate.CurrencyRateServiceImpl;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CurrencyControllerTest {

  @Mock
  private CurrencyRateServiceImpl currencyRateService;

  @InjectMocks
  private CurrencyController currencyController;


  @Test
  public void testGetCurrencyRate() {
    List<String> currencies = new ArrayList<>();
    currencies.add("USD");
    currencies.add("EUR");
    currencies.add("CNY");

    LocalDate date = LocalDate.of(2023, 3, 25);

    List<CurrencyRate> expectedRates = new ArrayList<>();
    expectedRates.add(new CurrencyRate("840", "USD", "1", "Доллар США", "76,4479"));
    expectedRates.add(new CurrencyRate("978", "EUR", "1", "Евро", "82,3923"));
    expectedRates.add(new CurrencyRate("156", "CNY", "1", "Китайский юань", "11,1146"));

    when(currencyRateService.getCurrencyRate(currencies, date)).thenReturn(expectedRates);

    List<CurrencyRate> actualRates = currencyController.getCurrencyRate(currencies, date);

    assertEquals(expectedRates, actualRates);

    verify(currencyRateService).getCurrencyRate(currencies, date);
  }

}