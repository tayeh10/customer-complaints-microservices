package com.intuit.apigateway.complaintservice.repository;

import com.intuit.apigateway.complaintservice.entity.Complaint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ComplaintRepository extends JpaRepository<Complaint, UUID> {
}
