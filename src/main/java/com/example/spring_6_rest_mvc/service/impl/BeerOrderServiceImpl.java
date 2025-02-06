package com.example.spring_6_rest_mvc.service.impl;

import com.example.spring_6_rest_mvc.dto.BeerOrderCreateDTO;
import com.example.spring_6_rest_mvc.dto.BeerOrderDTO;
import com.example.spring_6_rest_mvc.exception.NotFoundException;
import com.example.spring_6_rest_mvc.mappers.BeerOrderMapper;
import com.example.spring_6_rest_mvc.model.Beer;
import com.example.spring_6_rest_mvc.model.BeerOrder;
import com.example.spring_6_rest_mvc.model.BeerOrderLine;
import com.example.spring_6_rest_mvc.model.Customer;
import com.example.spring_6_rest_mvc.repositories.BeerOrderRepository;
import com.example.spring_6_rest_mvc.repositories.BeerRepository;
import com.example.spring_6_rest_mvc.repositories.CustomerRepository;
import com.example.spring_6_rest_mvc.service.BeerOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BeerOrderServiceImpl implements BeerOrderService {

    private final BeerOrderRepository beerOrderRepository;
    private final BeerOrderMapper beerOrderMapper;
    private final CustomerRepository customerRepository;
    private final BeerRepository beerRepository;

    @Override
    public Optional<BeerOrderDTO> getById(UUID beerOrderId) {
        return Optional.ofNullable(beerOrderMapper.beerOrderToBeerOrderDto(beerOrderRepository.findById(beerOrderId)
                .orElse(null)));
    }

    @Override
    public Page<BeerOrderDTO> listOrders(Integer pageNumber, Integer pageSize) {

        if (pageNumber == null || pageNumber < 0) {
            pageNumber = 0;
        }

        if (pageSize == null || pageSize < 1) {
            pageSize = 25;
        }

        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);

        return beerOrderRepository.findAll(pageRequest).map(beerOrderMapper::beerOrderToBeerOrderDto);
    }

    @Override
    public BeerOrder createOrder(BeerOrderCreateDTO beerOrderCreateDTO) {

        Customer customer = customerRepository.findById(beerOrderCreateDTO.getCustomerId()).orElseThrow(NotFoundException::new);

        Set<BeerOrderLine> beerOrderLineSet = new HashSet<>();

        beerOrderCreateDTO.getBeerOrderLines().forEach(beerOrderLine -> {
           beerOrderLineSet.add(BeerOrderLine
                   .builder()
                           .orderQuantity(beerOrderLine.getOrderQuantity())
                           .beer(beerRepository.findById(beerOrderLine.getBeerId()).orElseThrow(NotFoundException::new))
                   .build());
        });

        return beerOrderRepository.save(
                BeerOrder.
                        builder()
                        .beerOrderLines(beerOrderLineSet)
                        .customerRef(beerOrderCreateDTO.getCustomerRef())
                        .customer(customer)
                        .build()
        );
    }
}
