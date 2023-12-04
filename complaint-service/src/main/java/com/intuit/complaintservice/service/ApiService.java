package com.intuit.complaintservice.service;

import com.intuit.complaintservice.dto.PurchaseDto;
import com.intuit.complaintservice.dto.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@AllArgsConstructor
public class ApiService {


    private WebClient webClient;

    private final Environment environment;

    private final ExecutorService executorService = Executors.newFixedThreadPool(2);

    public CompletableFuture<UserDto> getUserByIdAsync(UUID userId) {
        return CompletableFuture.supplyAsync(() -> getUserById(userId), executorService);
    }

    public CompletableFuture<PurchaseDto> getPurchaseByIdAsync(UUID purchaseId) {
        return CompletableFuture.supplyAsync(() -> getPurchaseById(purchaseId), executorService);
    }

    public UserDto getUserById(UUID userId) {
        UserDto userDto = webClient.get()
                .uri(environment.getProperty("user.service.url") + userId)
                .retrieve()
                .bodyToMono(UserDto.class)
                .block();
        return userDto;
    }

    public PurchaseDto getPurchaseById(UUID purchaseId) {
        PurchaseDto purchaseDto = webClient.get()
                .uri(environment.getProperty("purchase.service.url") + purchaseId)
                .retrieve()
                .bodyToMono(PurchaseDto.class)
                .block();
        return purchaseDto;
    }
}
