package com.tutorial.config;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class CircuitBreaketConfig {

    @Bean
    public Customizer<Resilience4JCircuitBreakerFactory> getResilience4JCircuitBreakerFactoryCustomizer() {

        /**
         * note
         * name_circuit_breaket           komputasi                     keterangan
         *  CLOSED -> OPEN             failureRate > Threshold           akan di teruskan ke service consumer/subscriber
         *  HALF_OPEN -> CLOSED        failureRate < Threshold           akan di putus ke service    " " "
         *  HALF_OPEN -> OPEN          failureRate > Threshold           akan di teruskan ke service " " "
         *  OPEN -> HALF_OPEN          After Wait Duration               waktu tunggu untuk membuka state nya
         *
         */

        CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
                .failureRateThreshold(50) // konfigurasi berapa berapa persen abang batas gagal, ketika gagal 50% maka kita anggap gagal
                .waitDurationInOpenState(Duration.ofSeconds(1)) // akan menunggu 1 detik, kemudian/sehingga akan membuka state nya
                .slidingWindowSize(2) // docker failureRateThreshold, dimana request yang gagal akan di tampung di wadah. jika request gagal 2 kali makan dia memenuhi syarat Threshold 50%
                .build();

        TimeLimiterConfig timeLimiterConfig = TimeLimiterConfig.custom()
                .timeoutDuration(Duration.ofSeconds(4))
                .build();

        return factory -> factory.configureDefault(
                id -> new Resilience4JConfigBuilder(id)
                        .circuitBreakerConfig(circuitBreakerConfig)
                        .timeLimiterConfig(timeLimiterConfig)
                        .build());

    }

}
