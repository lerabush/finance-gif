package ru.com.financegif.service;

import java.util.List;

public interface ExchangeRatesService {

    int getGifKeyValue(String charCode);

    void refreshRates();

    List<String> getCharCodes();
}
