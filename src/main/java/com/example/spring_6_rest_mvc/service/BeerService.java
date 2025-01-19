package com.example.spring_6_rest_mvc.service;

import com.example.spring_6_rest_mvc.dto.BeerDTO;
import com.example.spring_6_rest_mvc.model.BeerStyle;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BeerService {

    List<BeerDTO> listBeers(String beerName, BeerStyle beerStyle, Boolean showInventory);

    Optional<BeerDTO> getBeerById(UUID id);

    BeerDTO saveNewBeer(BeerDTO beer);

    Optional<BeerDTO> updateBeer(UUID id, BeerDTO beer);

    Boolean deleteBeer(UUID id);

    Optional<BeerDTO> patchBeerById(UUID beerId, BeerDTO beer);
}
