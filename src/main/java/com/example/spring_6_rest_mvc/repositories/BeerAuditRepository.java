package com.example.spring_6_rest_mvc.repositories;

import com.example.spring_6_rest_mvc.model.BeerAudit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BeerAuditRepository extends JpaRepository<BeerAudit, UUID> {
}
