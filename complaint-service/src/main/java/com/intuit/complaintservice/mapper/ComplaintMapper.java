package com.intuit.complaintservice.mapper;


import com.intuit.complaintservice.dto.ComplaintDto;
import com.intuit.complaintservice.entity.Complaint;
import com.intuit.complaintservice.service.ApiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class ComplaintMapper {

    private static final Logger LOGGER = LoggerFactory.getLogger(ComplaintMapper.class);

    // Convert Complaint JPA Entity into ComplaintDto
    public static ComplaintDto mapToComplaintDto(Complaint complaint){
        LOGGER.info("inside mapToComplaintDto method");
        ComplaintDto complaintDto = new ComplaintDto(
                complaint.getId(),
                (complaint.getUserId() == null) ? "" : complaint.getUserId().toString(),
                complaint.getSubject(),
                complaint.getComplaint(),
                (complaint.getPurchaseId() == null) ? "" : complaint.getPurchaseId().toString()
        );
        return complaintDto;
    }

    // Convert ComplaintDto into Complaint JPA Entity
    public static Complaint mapToComplaint(ComplaintDto complaintDto){
        LOGGER.info("inside mapToComplaint method");
        Complaint complaint = new Complaint(
                complaintDto.getId(),
                (complaintDto.getUserId() == null || complaintDto.getUserId().isEmpty()) ? null : UUID.fromString(complaintDto.getUserId()),
                complaintDto.getSubject(),
                complaintDto.getComplaint(),
                (complaintDto.getPurchaseId() == null || complaintDto.getPurchaseId().isEmpty()) ? null : UUID.fromString(complaintDto.getPurchaseId())
        );
        return complaint;
    }
}
