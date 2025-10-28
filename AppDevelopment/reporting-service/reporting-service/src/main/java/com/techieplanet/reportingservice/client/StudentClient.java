package com.techieplanet.reportingservice.client;

import com.techieplanet.reportingservice.dto.StudentResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

@Component
public class StudentClient {

    private static final Logger LOGGER = Logger.getLogger(StudentClient.class.getName());
    private final WebClient webClient;

    public StudentClient(WebClient.Builder builder,
                         @Value("${student.service.base-url}") String baseUrl) {
        this.webClient = builder
                .baseUrl(baseUrl)
                .build();
    }

    public List<StudentResponse> getAllStudents() {
        try {
            return webClient.get()
                    .retrieve()
                    .bodyToMono(StudentResponse[].class)
                    .retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(1))
                            .filter(this::isRetryableException))
                    .onErrorResume(this::handleErrorFallback)
                    .map(Arrays::asList)
                    .block();
        } catch (Exception ex) {
            LOGGER.severe("Unexpected error fetching students: " + ex.getMessage());
            return Collections.emptyList();
        }
    }

    private boolean isRetryableException(Throwable throwable) {
        return throwable instanceof WebClientResponseException &&
                ((WebClientResponseException) throwable).getStatusCode().is5xxServerError();
    }

    private Mono<StudentResponse[]> handleErrorFallback(Throwable throwable) {
        LOGGER.warning("Failed to fetch students: " + throwable.getMessage());
        return Mono.just(new StudentResponse[0]);
    }
}

