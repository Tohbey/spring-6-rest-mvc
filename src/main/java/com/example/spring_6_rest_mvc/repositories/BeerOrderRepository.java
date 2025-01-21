package com.example.spring_6_rest_mvc.repositories;

import com.example.spring_6_rest_mvc.model.BeerOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BeerOrderRepository extends JpaRepository<BeerOrder, UUID> {
}
