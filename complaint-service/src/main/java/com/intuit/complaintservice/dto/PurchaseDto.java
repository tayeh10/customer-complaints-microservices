package com.intuit.complaintservice.dto;

import com.intuit.complaintservice.annotations.UUIDPattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseDto {
    @UUIDPattern(message = "purchaseId should be valid UUID")
    private String id;
    @UUIDPattern(message = "userId should be valid UUID")
    private String userId;
    @UUIDPattern(message = "productId should be valid UUID")
    private String productId;
    private String productName;
    private double pricePaidAmount;
    private String priceCurrency;
    private double discountPercent;
    @UUIDPattern(message = "merchantId should be valid UUID")
    private String merchantId;
    private Date purchaseDate;
}
