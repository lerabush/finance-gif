package ru.com.financegif.service;

import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface GifService {
    ResponseEntity<Map> getGif(String tag);
}
