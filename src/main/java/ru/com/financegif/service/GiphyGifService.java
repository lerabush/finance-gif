package ru.com.financegif.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.com.financegif.client.FeignGifClient;

import java.util.Map;
/**
 * Внутренний сервис для работы с openexchangerates.org
 */
@Service
public class GiphyGifService implements GifService{
    private FeignGifClient gifClient;
    @Autowired
    public GiphyGifService(FeignGifClient client){
        this.gifClient = client;
    }
    @Value("${giphy.api_key}")
    private String apiKey;
    @Value("${giphy.rating}")
    private String rating;

    @Override
    public ResponseEntity<Map> getGif(String tag) {
        ResponseEntity<Map> gifResponse = gifClient.getRandomGifByTag(apiKey,tag,rating);
        if(gifResponse.getBody()!=null){
            gifResponse.getBody().put("tag",tag);
        }
        return gifResponse;
    }
}
