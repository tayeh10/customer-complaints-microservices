package com.intuit.apigateway.complaintservice.dto;

import com.intuit.apigateway.complaintservice.annotations.UUIDPattern;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    @NotEmpty(message = "userId should not be null or empty")
    @UUIDPattern(message = "userId should be valid UUID")
    private String id;
    private String fullName;
    private String emailAddress;
    private String physicalAddress;
}
