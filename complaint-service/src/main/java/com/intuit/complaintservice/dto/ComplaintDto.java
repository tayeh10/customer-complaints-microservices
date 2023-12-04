package com.intuit.complaintservice.dto;

import com.intuit.complaintservice.annotations.UUIDPattern;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ComplaintDto {
    private UUID id;

    // In Dto classes using String instead of UUID to do validation for the UUID input
    @NotEmpty(message = "userId should not be null or empty")
    @UUIDPattern(message = "userId should be valid UUID")
    private String userId;

    @NotEmpty(message = "subject should not be null or empty")
    private String subject;

    @Lob
    @NotEmpty(message = "complaint should not be null or empty")
    private String complaint;

    @UUIDPattern(message = "purchaseId should be valid UUID")
    private String purchaseId;
}
