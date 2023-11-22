package com.tutorial.service;

import com.tutorial.dto.DTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class AccountService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CircuitBreakerFactory circuitBreakerFactory; // constract circuit breaker

    public List<DTO.responseAccount> getAccounts() {

        try {

            CircuitBreaker circuitBreaker = circuitBreakerFactory.create("accountServiceCb");

            ResponseEntity<DTO.responseAccount[]> result = circuitBreaker.run(
                    () -> restTemplate.getForEntity("http://localhost:3001/api/accounts", DTO.responseAccount[].class),
                    (e) -> ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build()
            ); // bungkus restTemplate dengan circuit breaker

            // kodisi jika gagal
            if (Objects.isNull(result.getBody())) {
                return new ArrayList<>();
            }

            return Arrays.asList(result.getBody());

        } catch (Throwable e) {
            log.error("Error: {}", e.getMessage());
            return new ArrayList<>();
        }

    }


}
