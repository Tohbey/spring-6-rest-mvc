package com.example.spring_6_rest_mvc.repositories;

import com.example.spring_6_rest_mvc.model.Beer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BeerRepository extends JpaRepository<Beer, UUID> {
}
