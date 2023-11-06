package com.zakharau.financial_transactions.service.parser;

import com.zakharau.financial_transactions.model.CurrencyRate;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.hibernate.query.sqm.ParsingException;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

@Service
public class CurrencyRateParserXML implements CurrencyRateParser {

  @Override
  public List<CurrencyRate> parse(String ratesAsString) {

    List<CurrencyRate> rates = new ArrayList<>();
    DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
    builderFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
    builderFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
    try {
      builderFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
      DocumentBuilder documentBuilder = builderFactory.newDocumentBuilder();

      try (StringReader reader = new StringReader(ratesAsString)) {
        Document document = documentBuilder.parse(new InputSource(reader));
        document.getDocumentElement().normalize();

        NodeList list = document.getElementsByTagName("Valute");

        for (int valuteIdx = 0; valuteIdx < list.getLength(); valuteIdx++) {
          Node node = list.item(valuteIdx);
          if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;

            CurrencyRate rate = CurrencyRate.builder()
                .numCode(element.getElementsByTagName("NumCode").item(0).getTextContent())
                .charCode(element.getElementsByTagName("CharCode").item(0).getTextContent())
                .nominal(element.getElementsByTagName("Nominal").item(0).getTextContent())
                .name(element.getElementsByTagName("Name").item(0).getTextContent())
                .value(element.getElementsByTagName("Value").item(0).getTextContent())
                .build();
            rates.add(rate);
          }
        }
      }
      //TODO refactor exception
    } catch (Exception e) {
      throw new ParsingException(e.getMessage());
    }
    return rates;
  }
}
