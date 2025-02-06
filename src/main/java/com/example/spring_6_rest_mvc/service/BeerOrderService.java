package com.example.spring_6_rest_mvc.service;

import com.example.spring_6_rest_mvc.dto.BeerOrderCreateDTO;
import com.example.spring_6_rest_mvc.dto.BeerOrderDTO;
import com.example.spring_6_rest_mvc.model.BeerOrder;
import org.springframework.data.domain.Page;

import java.util.Optional;
import java.util.UUID;

public interface BeerOrderService {

    Optional<BeerOrderDTO> getById(UUID beerOrderId);

    Page<BeerOrderDTO> listOrders(Integer pageNumber, Integer pageSize);

    BeerOrder createOrder(BeerOrderCreateDTO beerOrderCreateDTO);
}
