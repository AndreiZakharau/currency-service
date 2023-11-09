package com.zakharau.financial_transactions.util;

import com.zakharau.financial_transactions.controller.currency.CurrencyApi;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CurrencyRateScheduler {

  private final CurrencyApi api;

  @Scheduled(cron = "${cron.profile}")
  public void getCurrencyRates() {

    List<String> currencies = new ArrayList<>();

    api.getCurrencyRate(currencies, null);
  }

}
