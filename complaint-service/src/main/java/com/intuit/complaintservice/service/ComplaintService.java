package com.intuit.complaintservice.service;


import com.intuit.complaintservice.dto.APIResponseDto;
import com.intuit.complaintservice.dto.ComplaintDto;

import java.util.UUID;

public interface ComplaintService {
    ComplaintDto saveComplaint(ComplaintDto complaintDto);

    APIResponseDto getComplaintById(UUID complaintId);
}
