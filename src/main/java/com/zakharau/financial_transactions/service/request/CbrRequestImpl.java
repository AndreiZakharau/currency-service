package com.zakharau.financial_transactions.service.request;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.springframework.stereotype.Service;

@Service
public class CbrRequestImpl implements CbrRequest {

  @Override
  public String getRatesAsXml(String url) {

    try {
      HttpClient client = HttpClient.newHttpClient();
      HttpRequest request = HttpRequest.newBuilder()
          .uri(URI.create(url))
          .build();

      HttpResponse<String> response =
          client.send(request, HttpResponse.BodyHandlers.ofString());
      return response.body();
      //TODO refactor exceptions
    } catch (Exception e) {
      if (e instanceof InterruptedException) {
        Thread.currentThread().interrupt();
      }
      throw new RuntimeException(e);
    }
  }
}
