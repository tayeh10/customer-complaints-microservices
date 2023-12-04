package com.intuit.apigateway.complaintservice.controller;

import com.intuit.apigateway.complaintservice.dto.APIResponseDto;
import com.intuit.apigateway.complaintservice.service.ComplaintService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import com.intuit.apigateway.complaintservice.dto.ComplaintDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/complaints")
@AllArgsConstructor
public class ComplaintController {

    private ComplaintService complaintService;

    // Build Save Complaint REST API

    @PostMapping
    public ResponseEntity<ComplaintDto> saveComplaint(@Valid @RequestBody ComplaintDto complaintDto) {
        ComplaintDto savedComplaint = complaintService.saveComplaint(complaintDto);
        return new ResponseEntity<>(savedComplaint, HttpStatus.CREATED);
    }

    // Build Get Complaint REST API
    @GetMapping("{id}")
    public ResponseEntity<APIResponseDto> getComplaint(@PathVariable("id") UUID complaintId) {
        APIResponseDto apiResponseDto = complaintService.getComplaintById(complaintId);
        return new ResponseEntity<>(apiResponseDto, HttpStatus.OK);
    }

}
