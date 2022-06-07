package ru.com.financegif;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import ru.com.financegif.controller.FinanceGifController;
import ru.com.financegif.service.ExchangeRatesServiceImpl;
import ru.com.financegif.service.GiphyGifService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(FinanceGifController.class)
class FinanceGifApplicationTests {

    @Value("${giphy.rich}")
    private String richTag;
    @Value("${giphy.broke}")
    private String brokeTag;
    @Value("${giphy.equal}")
    private String equalTag;
    @Value("${giphy.error}")
    private String errorTag;

    private ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExchangeRatesServiceImpl exchangeService;
    @MockBean
    private GiphyGifService gifService;

    @Test
    void getCurrenciesListTest() throws Exception {
        List<String> response = new ArrayList<>();
        response.add("CURRENCY");
        Mockito.when(exchangeService.getCharCodes())
                .thenReturn(response);
        mockMvc.perform(get("/gif-finance/get-currencies")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0]").value("CURRENCY"));
    }

    @Test
    void getRichGifTest() throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("compareResult", this.richTag);
        ResponseEntity<Map> response = new ResponseEntity<>(map, HttpStatus.OK);
        Mockito.when(exchangeService.getGifKeyValue(anyString()))
                .thenReturn(1);
        Mockito.when(gifService.getGif(this.richTag))
                .thenReturn(response);

        mockMvc.perform(get("/gif-finance/get-gif/RICH")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.compareResult").value(this.richTag));
    }
    @Test
    void getBrokeGifTest() throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("compareResult", this.brokeTag);
        ResponseEntity<Map> response = new ResponseEntity<>(map, HttpStatus.OK);
        Mockito.when(exchangeService.getGifKeyValue(anyString()))
                .thenReturn(-1);
        Mockito.when(gifService.getGif(this.brokeTag))
                .thenReturn(response);

        mockMvc.perform(get("/gif-finance/get-gif/BROKE")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.compareResult").value(this.brokeTag));
    }
    @Test
    void getEqualGifTest() throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("compareResult", this.equalTag);
        ResponseEntity<Map> response = new ResponseEntity<>(map, HttpStatus.OK);
        Mockito.when(exchangeService.getGifKeyValue(anyString()))
                .thenReturn(0);
        Mockito.when(gifService.getGif(this.equalTag))
                .thenReturn(response);

        mockMvc.perform(get("/gif-finance/get-gif/EQUAL")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.compareResult").value(this.equalTag));
    }
    @Test
    void getErrorGifTest() throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("compareResult", this.errorTag);
        ResponseEntity<Map> response = new ResponseEntity<>(map, HttpStatus.OK);
        Mockito.when(exchangeService.getGifKeyValue(anyString()))
                .thenReturn(404);
        Mockito.when(gifService.getGif(this.errorTag))
                .thenReturn(response);

        mockMvc.perform(get("/gif-finance/get-gif/ERROR")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.compareResult").value(this.errorTag));
    }

}
