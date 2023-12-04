package com.intuit.apigateway.complaintservice.service;


import com.intuit.apigateway.complaintservice.dto.APIResponseDto;
import com.intuit.apigateway.complaintservice.dto.ComplaintDto;

import java.util.UUID;

public interface ComplaintService {
    ComplaintDto saveComplaint(ComplaintDto complaintDto);

    APIResponseDto getComplaintById(UUID complaintId);
}
