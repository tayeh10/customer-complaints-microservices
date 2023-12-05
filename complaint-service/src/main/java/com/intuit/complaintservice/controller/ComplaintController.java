package com.intuit.complaintservice.controller;

import com.intuit.complaintservice.dto.APIResponseDto;
import com.intuit.complaintservice.service.ApiService;
import com.intuit.complaintservice.service.ComplaintService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import com.intuit.complaintservice.dto.ComplaintDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/complaints")
@AllArgsConstructor
public class ComplaintController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ComplaintController.class);

    private ComplaintService complaintService;

    // Build Save Complaint REST API

    @PostMapping
    public ResponseEntity<ComplaintDto> saveComplaint(@Valid @RequestBody ComplaintDto complaintDto) {
        LOGGER.info("inside saveComplaint method");
        ComplaintDto savedComplaint = complaintService.saveComplaint(complaintDto);
        return new ResponseEntity<>(savedComplaint, HttpStatus.CREATED);
    }

    // Build Get Complaint REST API
    @GetMapping("{id}")
    public ResponseEntity<APIResponseDto> getComplaint(@PathVariable("id") UUID complaintId) {
        LOGGER.info("inside getComplaint method");
        APIResponseDto apiResponseDto = complaintService.getComplaintById(complaintId);
        return new ResponseEntity<>(apiResponseDto, HttpStatus.OK);
    }

}
