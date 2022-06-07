package ru.com.financegif;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class FinanceGifApplication {

    public static void main(String[] args) {
        SpringApplication.run(FinanceGifApplication.class, args);
    }

}
