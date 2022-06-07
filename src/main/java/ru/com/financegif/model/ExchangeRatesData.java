package ru.com.financegif.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * Ответ от API openexchangerates.org
 */
@Slf4j
@Data
public class ExchangeRatesData {
    private String disclaimer;
    private String license;
    private Long timestamp;
    private String base;
    private Map<String,Double> rates;
}
