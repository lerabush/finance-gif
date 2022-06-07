package ru.com.financegif.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.com.financegif.client.FeignOpenExchangeClient;
import ru.com.financegif.model.ExchangeRatesData;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.*;

/**
 * Внутренний сервис для работы с giphy.com
 */
@Service

public class ExchangeRatesServiceImpl implements ExchangeRatesService {

    private FeignOpenExchangeClient openExchangeClient;
    private ExchangeRatesData prevRates;
    private ExchangeRatesData currentRates;

    @Value("${openexchangerates.app_id}")
    private String appId;
    @Value("${openexchangerates.base}")
    private String base;

    @Autowired
    public ExchangeRatesServiceImpl(FeignOpenExchangeClient openExchangeClient) {
        this.openExchangeClient = openExchangeClient;
    }

    @Override
    public int getGifKeyValue(String charCode) {
        if (charCode == null) return 404;
        refreshRates();
        Double prevIndex = this.getCurrencyIndex(this.prevRates, charCode);
        Double currIndex = this.getCurrencyIndex(this.currentRates, charCode);
        System.out.println(prevIndex);
        System.out.println(currIndex);
        return prevIndex != null && currIndex != null ? Double.compare(currIndex, prevIndex) : 404;
    }

    @Override
    public void refreshRates() {
        long currentTime = System.currentTimeMillis();
        this.refreshCurrentRates();
        this.refreshPreviousRates(currentTime);
    }

    @Override
    public List<String> getCharCodes() {
        List<String> result = null;
        if (this.currentRates.getRates() != null) {
            result = new ArrayList<>(this.currentRates.getRates().keySet());
        }
        return result;
    }

    private void refreshCurrentRates() {
        this.currentRates = openExchangeClient.getLatestRates(base, appId);
    }

    private void refreshPreviousRates(long time) {
        Calendar prevDate = Calendar.getInstance();
        prevDate.setTimeInMillis(time);
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        prevDate.add(Calendar.DAY_OF_YEAR, -1);
        String paramPrevDate = formatter.format(prevDate.getTime());
        if (this.prevRates == null || comparePrevDates(prevDate) != 0) {
            this.prevRates = openExchangeClient.getHistoricalRates(paramPrevDate, base, appId);
        }
    }

    private int comparePrevDates(Calendar prevDate) {
        Long prevTimeStamp = this.prevRates.getTimestamp();
        Calendar checkDate = Calendar.getInstance();
        checkDate.setTimeInMillis(prevTimeStamp * 1000);
        return checkDate.compareTo(prevDate);
    }

    private Double getCurrencyIndex(ExchangeRatesData ratesData, String charCode) {
        Double targetRate = null;
        Double baseRate = null;
        if (ratesData != null && ratesData.getRates() != null) {
            targetRate = ratesData.getRates().get(charCode);
            baseRate = ratesData.getRates().get(this.base);
        }
        return divideRates(baseRate, targetRate);
    }

    private Double divideRates(Double firstRate, Double secondRate) {
        Double result = null;
        if (firstRate != null && secondRate != null) {
            result = new BigDecimal(firstRate / secondRate)
                    .setScale(5, RoundingMode.DOWN).doubleValue();
        }
        return result;
    }
}
