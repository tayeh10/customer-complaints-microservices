package com.intuit.complaintservice.service.impl;


import com.intuit.complaintservice.dto.APIResponseDto;
import com.intuit.complaintservice.dto.ComplaintDto;
import com.intuit.complaintservice.dto.PurchaseDto;
import com.intuit.complaintservice.dto.UserDto;
import com.intuit.complaintservice.entity.Complaint;
import com.intuit.complaintservice.exception.ResourceNotFoundException;
import com.intuit.complaintservice.mapper.ComplaintMapper;
import com.intuit.complaintservice.repository.ComplaintRepository;
import com.intuit.complaintservice.service.ApiService;
import com.intuit.complaintservice.service.ComplaintService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
@AllArgsConstructor
@PropertySource("classpath:application.properties")
public class ComplaintServiceImpl implements ComplaintService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ComplaintServiceImpl.class);

    private ComplaintRepository complaintRepository;

    // WebClient for Rest call Apis
    private WebClient webClient;

    // Environment - to fetch from properties file
    private final Environment environment;

    @Override
    public ComplaintDto saveComplaint(ComplaintDto complaintDto) {

        validateInputs(complaintDto);

        // Convert ComplaintDto into Complaint JPA Entity
        Complaint complaint = ComplaintMapper.mapToComplaint(complaintDto);

        Complaint savedComplaint = complaintRepository.save(complaint);

        // Convert Complaint JPA entity to ComplaintDto
        ComplaintDto savedComplaintDto = ComplaintMapper.mapToComplaintDto(savedComplaint);

        return savedComplaintDto;

    }

    private void validateInputs(ComplaintDto complaintDto) {
        ApiService apiService = new ApiService(webClient, environment);

        // Validate if user id exists in the DB
        UserDto userDto = apiService.getUserById(UUID.fromString(complaintDto.getUserId()));
        if(userDto == null)
        {
            LOGGER.error("User id: " + complaintDto.getUserId() + " Not found");
            throw new ResourceNotFoundException("USER_NOT_FOUND","User", "userId", complaintDto.getUserId());
        }

        if(complaintDto.getPurchaseId() != null && !complaintDto.getPurchaseId().isEmpty())
        {
            // Validate if purchase id exists in the DB
            PurchaseDto purchaseDto = apiService.getPurchaseById(UUID.fromString(complaintDto.getPurchaseId()));
            if(userDto == null)
            {
                LOGGER.error("Purchase id: " + complaintDto.getPurchaseId() + " Not found");
                throw new ResourceNotFoundException("PURCHASE_NOT_FOUND","Purchase", "purchaseId", complaintDto.getPurchaseId());
            }
        }
    }

    @Override
    public APIResponseDto getComplaintById(UUID complaintId) {

        LOGGER.info("Inside getComplaintById() method");

        Complaint complaint = complaintRepository.findById(complaintId).orElseThrow(
                () -> new ResourceNotFoundException("COMPLAINT_NOT_FOUND", "Complaint", "id", complaintId)
        );

        ApiService apiService = new ApiService(webClient, environment);

        LOGGER.info("Start Multi-threaded call to user and purchase");
        CompletableFuture<UserDto> userFuture = apiService.getUserByIdAsync(complaint.getUserId());
        CompletableFuture<PurchaseDto> purchaseFuture = null;
        if(complaint.getPurchaseId() != null) {
             purchaseFuture = apiService.getPurchaseByIdAsync(complaint.getPurchaseId());
        }

        // Wait for both tasks to complete
        UserDto userDto = userFuture.join();
        PurchaseDto purchaseDto = null;
        if(complaint.getPurchaseId() != null && purchaseFuture!= null) {
            purchaseDto = purchaseFuture.join();
        }

        // Convert Complaint JPA entity to ComplaintDto
        ComplaintDto complaintDto = ComplaintMapper.mapToComplaintDto(complaint);

        APIResponseDto apiResponseDto = new APIResponseDto();
        apiResponseDto.setComplaint(complaintDto);
        apiResponseDto.setUser(userDto);
        apiResponseDto.setPurchase(purchaseDto);

        return apiResponseDto;
    }
}
