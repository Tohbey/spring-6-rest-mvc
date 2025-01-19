package com.example.spring_6_rest_mvc.repositories;

import com.example.spring_6_rest_mvc.model.Beer;
import com.example.spring_6_rest_mvc.model.BeerStyle;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface BeerRepository extends JpaRepository<Beer, UUID> {
    Page<Beer> findAllByBeerNameIsLikeIgnoreCase(String beerName, Pageable pageable);

    Page<Beer> findAllByBeerStyle(@NotNull BeerStyle beerStyle, Pageable pageable);

    Page<Beer> findAllByBeerNameIsLikeIgnoreCaseAndBeerStyle(@NotNull String beerName,
                                                             @NotNull BeerStyle beerStyle,
                                                             Pageable pageable);
}
