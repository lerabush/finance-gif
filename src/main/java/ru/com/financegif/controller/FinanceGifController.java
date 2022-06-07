package ru.com.financegif.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.com.financegif.service.ExchangeRatesService;
import ru.com.financegif.service.GifService;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/gif-finance")
public class FinanceGifController {
    private final GifService gifService;
    private final ExchangeRatesService exchangeRatesService;

    @Value("${giphy.rich}")
    private String richTag;
    @Value("${giphy.broke}")
    private String brokeTag;
    @Value("${giphy.equal}")
    private String equalTag;
    @Value("${giphy.error}")
    private String errorTag;

    @PostConstruct
    private void initExchangeRates(){
        exchangeRatesService.refreshRates();
    }
    @GetMapping("/get-currencies")
    public List<String> getCurrenciesCodes(){
        return exchangeRatesService.getCharCodes();
    }

    @GetMapping("/get-gif/{currency}")
    public ResponseEntity<Map> getGif(@PathVariable String currency){
        ResponseEntity<Map> result = null;
        String gifTag = errorTag;
        int gifKey = exchangeRatesService.getGifKeyValue(currency);
        switch(gifKey){
            case 1:
                gifTag = this.richTag;
                break;
            case 0:
                gifTag = this.equalTag;
                break;
            case -1:
                gifTag = this.brokeTag;
                break;
        }
        result = gifService.getGif(gifTag);
        return result;
    }



}
