package com.zakharau.financial_transactions.controller.currency;

import com.zakharau.financial_transactions.exception.ExceptionInformation;
import com.zakharau.financial_transactions.model.CurrencyRate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.LocalDate;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

@RequestMapping("/currencyRate")
@Tag(name = "Currency rate Api", description = "Currency rate Api interface")
public interface CurrencyApi {

  @Operation(
      summary = "Get currency rate",
      tags = {"CurrencyRate"},
      description = "Get currency rate by date and charCode. ")
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Financial operation successfully retrieved. "
              + "If no date is specified, the current date is used. "
              + "If currency is not specified, USD and EUR rates are returned",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = CurrencyRate.class))),
      @ApiResponse(
          responseCode = "400",
          description = "Payload is incorrect: malformed, missing mandatory attributes etc",
          content = @Content(schema = @Schema(implementation = ExceptionInformation.class))),
      @ApiResponse(
          responseCode = "404",
          description = "Resource not found",
          content = @Content(schema = @Schema(implementation = ExceptionInformation.class))),
      @ApiResponse(
          responseCode = "500",
          description = "General application error",
          content = @Content(schema = @Schema(implementation = ExceptionInformation.class)))
  })
  @GetMapping()
  @ResponseStatus(HttpStatus.OK)
  List<CurrencyRate> getCurrencyRate(
      @RequestParam(value = "currencies", defaultValue = "USD, EUR",
          required = false) List<String> currencies,
      @DateTimeFormat(pattern = "dd-MM-yyyy") @RequestParam(value = "date",
          required = false) LocalDate date);
}
