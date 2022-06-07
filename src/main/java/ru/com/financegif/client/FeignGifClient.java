package ru.com.financegif.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "gif-client",url="${giphy.url}")
public interface FeignGifClient {
    @GetMapping("/random")
    ResponseEntity<Map> getRandomGifByTag(@RequestParam("api_key") String apiKey,@RequestParam("tag") String tag,
                                          @RequestParam("rating")String rating);
}
