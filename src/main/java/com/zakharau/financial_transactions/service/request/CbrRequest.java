package com.zakharau.financial_transactions.service.request;

public interface CbrRequest extends Request {

  String getRatesAsXml(String url);
}
