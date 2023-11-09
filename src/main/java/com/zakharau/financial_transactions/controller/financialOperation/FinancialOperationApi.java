package com.zakharau.financial_transactions.controller.financialOperation;

import com.zakharau.financial_transactions.entity.FinancialOperation;
import com.zakharau.financial_transactions.exception.ExceptionInformation;
import com.zakharau.financial_transactions.model.CreateFinancialOperationModel;
import com.zakharau.financial_transactions.model.FinancialOperationInCurrency;
import com.zakharau.financial_transactions.model.FinancialOperationModel;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

@RequestMapping("operation")
@Validated
@Tag(name = "Financial operation Api", description = "Financial operation Api interface")
public interface FinancialOperationApi {

  @Operation(
      summary = "Save financial operation",
      tags = {"Operation"},
      description = "Save financial operation. Return operation with all parameters")
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "201",
          description = "Financial operation successfully saved",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = FinancialOperationModel.class))),
      @ApiResponse(
          responseCode = "400",
          description = "Payload is incorrect: malformed, missing mandatory attributes etc",
          content = @Content(schema = @Schema(implementation = ExceptionInformation.class))),
      @ApiResponse(
          responseCode = "500",
          description = "General application error",
          content = @Content(schema = @Schema(implementation = ExceptionInformation.class)))
      })
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  FinancialOperationModel
  save(@Valid @RequestBody CreateFinancialOperationModel operationModel);

  @Operation(
      summary = "Get all financial operation",
      tags = {"Operation"},
      description = "Get all financial operation. Return list financial operation with all parameters")
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "List of financial operation successfully returned",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = FinancialOperationModel.class))),
      @ApiResponse(
          responseCode = "500",
          description = "General application error",
          content = @Content(schema = @Schema(implementation = ExceptionInformation.class)))
  })
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  List<FinancialOperationModel> getAllOperation();

  @Operation(
      summary = "Update financial operation",
      tags = {"Operation"},
      description = "Returns a nude financial operation")
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Financial operation successfully returned",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = FinancialOperationModel.class))),
      @ApiResponse(
          responseCode = "500",
          description = "General application error",
          content = @Content(schema = @Schema(implementation = ExceptionInformation.class)))
  })
  @PutMapping
  @ResponseStatus(HttpStatus.CREATED)
  FinancialOperationModel updateOperation(@RequestBody FinancialOperationModel model);

  @Hidden
  @Operation(
      summary = "Delete financial operation by id",
      tags = {"Operation"})
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Financial operation successfully deleted. Returned 1"),
      @ApiResponse(
          responseCode = "500",
          description = "General application error",
          content = @Content(schema = @Schema(implementation = ExceptionInformation.class)))
  })
  @DeleteMapping("{id}")
  @ResponseStatus(HttpStatus.OK)
  void deletedOperation(@PathVariable UUID id);

  @Operation(
      summary = "Get operation by id",
      tags = {"Operation"},
      description = "get operation by id")
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Financial operation successfully returned",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = FinancialOperationModel.class))),
      @ApiResponse(
          responseCode = "500",
          description = "General application error",
          content = @Content(schema = @Schema(implementation = ExceptionInformation.class)))
  })
  @GetMapping("{id}")
  @ResponseStatus(HttpStatus.OK)
  Optional<FinancialOperation> getOperationById(@Valid @PathVariable UUID id);

  @Operation(
      summary = "Get all operations",
      tags = {"Operation"},
      description = "Get all operations. Return all financial operation for the period recalculated in the currency at the currency rate")
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "List financial operation successfully returned",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = FinancialOperationInCurrency.class))),
      @ApiResponse(
          responseCode = "500",
          description = "General application error",
          content = @Content(schema = @Schema(implementation = ExceptionInformation.class)))
  })
  @GetMapping("currency")
  @ResponseStatus(HttpStatus.OK)
  List<FinancialOperationInCurrency> getAllOperationByCurrency(
      @RequestParam(value = "currency", defaultValue = "USD",
          required = false) String currency,
      @DateTimeFormat(pattern = "dd-MM-yyyy") @RequestParam(value = "startDate", required = false) LocalDate startDate,
      @DateTimeFormat(pattern = "dd-MM-yyyy") @RequestParam(value = "finishDate", required = false) LocalDate finishDate);

}
