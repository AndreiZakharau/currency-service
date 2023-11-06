package com.zakharau.financial_transactions.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CbrConfig {

  String url;

  public CbrConfig(@Value("${cbr.url}") String url) {
    this.url = url;
  }

  public String getUrl() {
    return url;
  }
}
