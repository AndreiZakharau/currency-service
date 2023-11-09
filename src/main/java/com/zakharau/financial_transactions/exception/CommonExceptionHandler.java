package com.zakharau.financial_transactions.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.METHOD_NOT_ALLOWED;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.ServletException;
import java.time.format.DateTimeParseException;
import java.util.stream.Collectors;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException.BadRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@RestControllerAdvice
public class CommonExceptionHandler {

  @ExceptionHandler(ServletException.class)
  @ResponseStatus(INTERNAL_SERVER_ERROR)
  public ExceptionInformation handleServletException(ServletException exception) {
    return new ExceptionInformation(
        ServletException.class.getSimpleName(),
        INTERNAL_SERVER_ERROR,
        "Houston, we have a problem");
  }

  @ExceptionHandler(MaxUploadSizeExceededException.class)
  @ResponseStatus(BAD_REQUEST)
  public ExceptionInformation handleSizeLimitExceededException(
      MaxUploadSizeExceededException exception) {
    return new ExceptionInformation(
        MaxUploadSizeExceededException.class.getSimpleName(),
        BAD_REQUEST,
        exception.getMessage());
  }

  @ExceptionHandler(IllegalArgumentException.class)
  @ResponseStatus(BAD_REQUEST)
  public ExceptionInformation handleIllegalArgumentException(IllegalArgumentException exception) {
    return new ExceptionInformation(
        IllegalArgumentException.class.getSimpleName(),
        BAD_REQUEST,
        "Illegal argument in method");
  }

  @ExceptionHandler(MissingServletRequestParameterException.class)
  @ResponseStatus(BAD_REQUEST)
  public ExceptionInformation handleMissingServletRequestParameterException(
      MissingServletRequestParameterException exception) {
    return new ExceptionInformation(
        MissingServletRequestParameterException.class.getSimpleName(),
        BAD_REQUEST,
        String.format("Parameter %s is required", exception.getParameterName()));
  }

  @ExceptionHandler(EntityNotFoundException.class)
  @ResponseStatus(NOT_FOUND)
  public ExceptionInformation handleNotFoundException(EntityNotFoundException exception) {
    return new ExceptionInformation(
        EntityNotFoundException.class.getSimpleName(),
        NOT_FOUND,
        "Entity does not exists");
  }

  @ExceptionHandler(BadRequest.class)
  @ResponseStatus(BAD_REQUEST)
  public ExceptionInformation handleBadRequestException(BadRequest exception) {
    return new ExceptionInformation(
        BadRequest.class.getSimpleName(),
        BAD_REQUEST,
        exception.getMessage());
  }

  @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
  @ResponseStatus(BAD_REQUEST)
  public ExceptionInformation handleMethodArgumentNotValidException(BindException exception) {
    String message =
        exception.getBindingResult().getAllErrors().stream()
            .map(DefaultMessageSourceResolvable::getDefaultMessage)
            .collect(Collectors.joining(", "));
    return new ExceptionInformation(BindException.class.getSimpleName(), BAD_REQUEST, message);
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  @ResponseStatus(BAD_REQUEST)
  public ExceptionInformation handleHttpMessageNotReadableException(
      HttpMessageNotReadableException exception) {
    return new ExceptionInformation(
        HttpMessageNotReadableException.class.getSimpleName(),
        BAD_REQUEST,
        exception.getLocalizedMessage());
  }

  @ExceptionHandler(DateTimeParseException.class)
  @ResponseStatus(BAD_REQUEST)
  public ExceptionInformation handleDateTimeParseException(
      DateTimeParseException exception) {
    return new ExceptionInformation(DateTimeParseException.class.getSimpleName(),
        BAD_REQUEST,
        "Invalid date format. Date format mast be yyyy-MM-dd (1980-03-27)");

  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  @ResponseStatus(BAD_REQUEST)
  public ExceptionInformation handleDataIntegrityViolationException(
      DataIntegrityViolationException exception) {
    String message =
        "Unable to execute the database operation as it will result in violation of data integrity. ";
    return new ExceptionInformation(
        DataIntegrityViolationException.class.getSimpleName(),
        BAD_REQUEST,
        message + exception.getMessage());
  }

  @ExceptionHandler(CurrencyParserException.class)
  @ResponseStatus(BAD_REQUEST)
  public ExceptionInformation handleCurrencyParserException(
      CurrencyParserException exception) {
    return new ExceptionInformation(
        CurrencyParserException.class.getSimpleName(),
        BAD_REQUEST,
        exception.getMessage());
  }

  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  @ResponseStatus(METHOD_NOT_ALLOWED)
  public ExceptionInformation handleHttpRequestMethodNotSupportedException(
      HttpRequestMethodNotSupportedException exception) {
    String messageSuffix = " with such parameters or path variables";
    return new ExceptionInformation(
        HttpRequestMethodNotSupportedException.class.getSimpleName(),
        METHOD_NOT_ALLOWED,
        exception.getLocalizedMessage() + messageSuffix);
  }

}
