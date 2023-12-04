package com.intuit.complaintservice.mapper;


import com.intuit.complaintservice.dto.ComplaintDto;
import com.intuit.complaintservice.entity.Complaint;

import java.util.UUID;

public class ComplaintMapper {

    // Convert Complaint JPA Entity into ComplaintDto
    public static ComplaintDto mapToComplaintDto(Complaint complaint){
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
