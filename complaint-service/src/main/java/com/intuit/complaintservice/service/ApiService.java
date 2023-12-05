package com.intuit.complaintservice.service;

import com.intuit.complaintservice.dto.PurchaseDto;
import com.intuit.complaintservice.dto.UserDto;
import com.intuit.complaintservice.service.impl.ComplaintServiceImpl;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@AllArgsConstructor
public class ApiService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApiService.class);

    private WebClient webClient;

    private final Environment environment;

    private final ExecutorService executorService = Executors.newFixedThreadPool(2);

    public CompletableFuture<UserDto> getUserByIdAsync(UUID userId) {
        LOGGER.info("inside getUserByIdAsync method");
        return CompletableFuture.supplyAsync(() -> getUserById(userId), executorService);
    }

    public CompletableFuture<PurchaseDto> getPurchaseByIdAsync(UUID purchaseId) {
        LOGGER.info("inside getPurchaseByIdAsync method");
        return CompletableFuture.supplyAsync(() -> getPurchaseById(purchaseId), executorService);
    }

    public UserDto getUserById(UUID userId) {
        LOGGER.info("inside getUserById method");
        UserDto userDto = webClient.get()
                .uri(environment.getProperty("user.service.url") + userId)
                .retrieve()
                .bodyToMono(UserDto.class)
                .block();
        return userDto;
    }

    public PurchaseDto getPurchaseById(UUID purchaseId) {
        LOGGER.info("inside getPurchaseById method");
        PurchaseDto purchaseDto = webClient.get()
                .uri(environment.getProperty("purchase.service.url") + purchaseId)
                .retrieve()
                .bodyToMono(PurchaseDto.class)
                .block();
        return purchaseDto;
    }
}
