package com.zakharau.financial_transactions.service.parser;

import com.zakharau.financial_transactions.exception.CurrencyParserException;
import com.zakharau.financial_transactions.model.CurrencyRate;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CurrencyRateParserXmlTest {

  @InjectMocks
  private CurrencyRateParserXml currencyRateParserXml;

  @Test
  void testParse() {

    String ratesAsString = "<root><Valute><NumCode>1</NumCode><CharCode>USD</CharCode><Nominal>1</Nominal><Name>US Dollar</Name><Value>1.00</Value></Valute></root>";

    List<CurrencyRate> rates = currencyRateParserXml.parse(ratesAsString);

    Assertions.assertEquals(1, rates.size());
    CurrencyRate rate = rates.get(0);
    Assertions.assertEquals("1", rate.getNumCode());
    Assertions.assertEquals("USD", rate.getCharCode());
    Assertions.assertEquals("1", rate.getNominal());
    Assertions.assertEquals("US Dollar", rate.getName());
    Assertions.assertEquals("1.00", rate.getValue());
  }

  @Test
  void testParse_InvalidData_ExceptionThrown() {

    String ratesAsString = "<root><Valute><NumCode>1</NumCode><CharCode>USD</CharCode><Nominal>1</Nominal>";

    Assertions.assertThrows(CurrencyParserException.class, () -> {

      currencyRateParserXml.parse(ratesAsString);
    });
  }
}