package com.intuit.apigateway.complaintservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class APIResponseDto {
    private ComplaintDto complaint;
    private UserDto user;
    private PurchaseDto purchase;
}
