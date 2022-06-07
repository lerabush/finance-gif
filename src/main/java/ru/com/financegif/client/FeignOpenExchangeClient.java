package ru.com.financegif.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import ru.com.financegif.model.ExchangeRatesData;

/**
 * Feign-client для получения курсов валют за сегодня и вчера
 */
@FeignClient(name = "OpenExchangeClient", url = "${openexchangerates.url}")
public interface FeignOpenExchangeClient {
    @GetMapping("/latest.json")
    ExchangeRatesData getLatestRates(@RequestParam("base") String base, @RequestParam("app_id") String app_id);

    @GetMapping("/historical/{date}.json")
    ExchangeRatesData getHistoricalRates(@PathVariable String date,@RequestParam("base") String base, @RequestParam("app_id") String app_id);

}
